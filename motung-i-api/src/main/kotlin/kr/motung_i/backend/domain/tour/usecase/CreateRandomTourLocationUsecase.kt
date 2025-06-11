package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.GeometryResponse
import kr.motung_i.backend.domain.tour.usecase.dto.LocalPolygon
import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.GeoLocal
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.global.geojson.formatter.LocalFormatterService
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.persistence.tour.entity.Local
import org.geolatte.geom.Geometries
import org.geolatte.geom.Positions
import org.geolatte.geom.crs.CoordinateReferenceSystems
import org.geolatte.geom.jts.JTS
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class CreateRandomTourLocationUsecase(
    private val localsCache: LocalsCache,
    private val localFormatterService: LocalFormatterService,
    private val createTourLocationUsecase: CreateTourLocationUsecase,
) {
    fun execute(
        country: Country,
        regions: List<String>,
        districts: List<String>
    ): FetchRandomTourLocationResponse {
        val geoLocal = localsCache.findLocalByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val requestRegions = filterByRegion(geoLocal, regions, districts)
        val requestDistricts = filterByDistrict(geoLocal, districts, requestRegions)

        val districtsByRequestRegion = requestRegions.flatMap { it.geoDistricts }
        val allRequestDistricts = districtsByRequestRegion + requestDistricts

        val localPolygons = allRequestDistricts.flatMap { district ->
            district.geoNeighborhoods.flatMap { neighborhood ->
                neighborhood.geometry.components().map {
                    val local = Local(
                        localAlias = localFormatterService.formatToLocalAlias(
                            "${district.regionName} ${district.name} ${neighborhood.name}",
                            country,
                        ),
                        country = country,
                        regionAlias = localFormatterService.formatToRegionAlias(district.regionName, country),
                        districtAlias = district.alias,
                        neighborhood = neighborhood.name
                    )

                    LocalPolygon(
                        local = local,
                        localPolygon = neighborhood.geometry,
                        polygon = it
                    )
                }
            }
        }
        val localPolygonWithAreas = localPolygons.map { it to JTS.to(it.polygon).area }

        val randomLocalPolygon = fetchRandomLocalPolygon(localPolygonWithAreas)
        val randomLocation = fetchRandomLocationInPolygon(randomLocalPolygon)

        createTourLocationUsecase.execute(randomLocalPolygon.local, randomLocation)

        return FetchRandomTourLocationResponse(
            lat = randomLocation.lat,
            lon = randomLocation.lon,
            local = randomLocalPolygon.local.localAlias,
            geometry = GeometryResponse.fromMultiPolygon(randomLocalPolygon.localPolygon)
        )
    }

    private fun filterByRegion(
        geoLocal: GeoLocal,
        regions: List<String>,
        districts: List<String>
    ) = geoLocal.geoRegions.filter {
        if (regions.isEmpty() && districts.isEmpty()) return@filter true

        regions.contains(it.alias)
    }

    private fun filterByDistrict(
        geoLocal: GeoLocal,
        requestDistricts: List<String>,
        requestGeoRegions: List<GeoRegion>
    ) = geoLocal.geoRegions.filter {
        if (requestDistricts.isEmpty()) return@filter false

        val requestDistrictByRegion = requestGeoRegions
            .flatMap { regionByRequestRegion ->
                regionByRequestRegion.geoDistricts.map { it.alias }
            }
            .toSet()

        val isEmptyRegionDistrict = requestDistrictByRegion.isEmpty()
        val hasOutOfRegionDistrict = requestDistrictByRegion.any { it !in requestDistricts }
        (isEmptyRegionDistrict || hasOutOfRegionDistrict)
    }.flatMap { region ->
        region.geoDistricts.filter { requestDistricts.contains(it.alias) }
    }

    private fun fetchRandomLocalPolygon(
        localPolygonWithAreas: List<Pair<LocalPolygon, Double>>,
    ): LocalPolygon {
        val totalArea = localPolygonWithAreas.sumOf { it.second }

        val random = Random().nextDouble()
        var calculative = 0.0

        return localPolygonWithAreas.firstOrNull {
            calculative += it.second / totalArea
            random <= calculative
        }?.first ?: throw CustomException(CustomErrorCode.NOT_FOUND_FILTER_LOCATION)
    }

    private fun fetchRandomLocationInPolygon(randomLocalPolygon: LocalPolygon): GeoLocation {
        val minLon = randomLocalPolygon.polygon.boundingBox.lowerLeft().lon
        val minLat = randomLocalPolygon.polygon.boundingBox.lowerLeft().lat
        val maxLon = randomLocalPolygon.polygon.boundingBox.upperRight().lon
        val maxLat = randomLocalPolygon.polygon.boundingBox.upperRight().lat

        repeat(100) {
            val randomLon = Random().nextDouble(minLon, maxLon)
            val randomLat = Random().nextDouble(minLat, maxLat)
            val point = Positions.mkPosition(CoordinateReferenceSystems.WGS84, randomLon, randomLat)
            val jtsPoint = JTS.to(Geometries.mkPoint(point, CoordinateReferenceSystems.WGS84))

            if (JTS.to(randomLocalPolygon.polygon).covers(jtsPoint)) {
                return GeoLocation(
                    lat = randomLat,
                    lon = randomLon,
                )
            }
        }

        throw CustomException(CustomErrorCode.RANDOM_TOUR_LOCATION_PROCESSING_ERROR)
    }
}
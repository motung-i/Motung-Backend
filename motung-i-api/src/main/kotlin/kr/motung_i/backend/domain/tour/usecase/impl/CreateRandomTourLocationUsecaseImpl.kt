package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.GeometryResponse
import kr.motung_i.backend.domain.tour.usecase.CreateRandomTourLocationUsecase
import kr.motung_i.backend.domain.tour.usecase.CreateTourLocationUsecase
import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.domain.tour.usecase.dto.LocalPolygon
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoLocal
import kr.motung_i.backend.global.geojson.dto.GeoNeighborhood
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
class CreateRandomTourLocationUsecaseImpl(
    private val localsCache: LocalsCache,
    private val localFormatterService: LocalFormatterService,
    private val createTourLocationUsecase: CreateTourLocationUsecase,
) : CreateRandomTourLocationUsecase {
    override fun execute(
        country: Country,
        regions: List<String>,
        districts: List<String>
    ): FetchRandomTourLocationResponse {
        val geoLocal = localsCache.findLocalByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val allRequestDistricts = extractDistricts(geoLocal, regions , districts)
            .ifEmpty { geoLocal.geoRegions.flatMap { it.geoDistricts } }

        val localPolygons = createLocalPolygons(allRequestDistricts, country)
        val randomLocalPolygon = fetchRandomLocalPolygon(localPolygons)
        val randomLocation = fetchRandomLocationInPolygon(randomLocalPolygon)

        createTourLocationUsecase.execute(randomLocalPolygon.local, randomLocation)

        return FetchRandomTourLocationResponse(
            lat = randomLocation.lat,
            lon = randomLocation.lon,
            local = randomLocalPolygon.local.localAlias,
            geometry = GeometryResponse.fromMultiPolygon(randomLocalPolygon.localPolygon)
        )
    }

    private fun extractDistricts(
        geoLocal: GeoLocal,
        regions: List<String>,
        districts: List<String>
    ): List<GeoDistrict> {
        val geoRegions = geoLocal.geoRegions
            .filter { it.alias in regions }
        val geoDistricts = geoLocal.geoRegions
            .flatMap { it.geoDistricts }
            .filter { it.alias in districts }

        val requestDistrictsByRegion = geoRegions
            .flatMap { it.geoDistricts }
            .toSet()

        return (requestDistrictsByRegion + geoDistricts.toSet()).toList()
    }

    private fun createLocalPolygons(
        allRequestDistricts: List<GeoDistrict>,
        country: Country
    ): List<LocalPolygon> =
        allRequestDistricts.flatMap { district ->
            district.geoNeighborhoods.flatMap { neighborhood ->
                val geometry = neighborhood.geometry
                val local = createLocalFromGeoData(district, neighborhood, country)

                geometry.components().map { polygon ->
                    LocalPolygon(
                        local = local,
                        localPolygon = geometry,
                        polygon = polygon
                    )
                }
            }
        }

    private fun createLocalFromGeoData(
        district: GeoDistrict,
        neighborhood: GeoNeighborhood,
        country: Country
    ): Local = Local(
        localAlias = localFormatterService.formatToLocalAlias(
            "${district.regionName} ${district.name} ${neighborhood.name}",
            country,
        ),
        country = country,
        regionAlias = localFormatterService.formatToRegionAlias(district.regionName, country),
        districtAlias = district.alias,
        neighborhood = neighborhood.name
    )

    private fun fetchRandomLocalPolygon(
        localPolygon: List<LocalPolygon>,
    ): LocalPolygon {
        val totalArea = localPolygon.sumOf { JTS.to(it.polygon).area }

        val random = Random().nextDouble()
        var calculative = 0.0

        return localPolygon.firstOrNull {
            calculative += JTS.to(it.polygon).area / totalArea
            random <= calculative
        } ?: throw CustomException(CustomErrorCode.NOT_FOUND_FILTER_LOCATION)
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
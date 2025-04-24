package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.domain.tour.usecase.dto.LocalPolygon
import kr.motung_i.backend.domain.tour.usecase.dto.Location
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.Local
import kr.motung_i.backend.global.geojson.dto.Region
import kr.motung_i.backend.global.geojson.formatter.LocalFormatterService
import org.geolatte.geom.Geometries
import org.geolatte.geom.Positions
import org.geolatte.geom.crs.CoordinateReferenceSystems
import org.geolatte.geom.jts.JTS
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class FetchRandomTourLocationUsecase(
    private val localsCache: LocalsCache,
    private val localFormatterService: LocalFormatterService,
) {
    fun execute(
        country: kr.motung_i.backend.global.geojson.enums.Country,
        regions: List<String>,
        districts: List<String>
    ): FetchRandomTourLocationResponse {
        val local = localsCache.findLocalByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val requestRegions = filterByRegion(local, regions, districts)
        val requestDistricts = filterByDistrict(local, districts, requestRegions)

        val districtsByRequestRegion = requestRegions.flatMap { it.districts }
        val allRequestDistricts = districtsByRequestRegion + requestDistricts

        val localPolygons = allRequestDistricts.flatMap { district ->
                district.neighborhoods.flatMap { neighborhood ->
                    neighborhood.geometry.components().map {
                        val localAlias = localFormatterService.formatToLocalAlias(
                            "${district.regionName} ${district.name} ${neighborhood.name}",
                            country,
                        )
                        LocalPolygon(localAlias, it)
                    }
                }
        }
        val localPolygonWithAreas = localPolygons.map { it to JTS.to(it.polygon).area }

        val randomRegionPolygon = fetchRandomLocalPolygon(localPolygonWithAreas)
        val randomLocation = fetchRandomLocationInPolygon(randomRegionPolygon)

        return FetchRandomTourLocationResponse(
            lat = randomLocation.lat,
            lon = randomLocation.lon,
            region = randomRegionPolygon.localAlias
        )
    }

    private fun filterByRegion(
        local: Local,
        regions: List<String>,
        districts: List<String>
    ) = local.regions.filter {
        if (regions.isEmpty() && districts.isEmpty()) return@filter true

        regions.contains(it.alias)
    }

    private fun filterByDistrict(
        local: Local,
        requestDistricts: List<String>,
        requestRegions: List<Region>
    ) = local.regions.filter {
        if (requestDistricts.isEmpty()) return@filter false

        val requestDistrictByRegion = requestRegions
            .flatMap { regionByRequestRegion ->
                regionByRequestRegion.districts.map { it.alias }
            }
            .toSet()

        val isEmptyRegionDistrict = requestDistrictByRegion.isEmpty()
        val hasOutOfRegionDistrict = requestDistrictByRegion.any { it !in requestDistricts }
        (isEmptyRegionDistrict || hasOutOfRegionDistrict)
    }.flatMap { region ->
        region.districts.filter { requestDistricts.contains(it.alias) }
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

    private fun fetchRandomLocationInPolygon(randomLocalPolygon: LocalPolygon): Location {
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
                return Location(
                    lat = randomLat,
                    lon = randomLon,
                )
            }
        }

        throw CustomException(CustomErrorCode.RANDOM_TOUR_LOCATION_PROCESSING_ERROR)
    }
}
package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.domain.tour.usecase.dto.Location
import kr.motung_i.backend.domain.tour.usecase.dto.RegionPolygon
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.global.geojson.GeoJsonFeaturesCache
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeature
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeatures
import kr.motung_i.backend.global.geojson.enums.Country
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
    private val geoJsonFeaturesCache: GeoJsonFeaturesCache,
    private val tourFormatterService: TourFormatterService,
) {
    fun execute(
        country: Country,
        regions: List<String>,
        districts: List<String>
    ): FetchRandomTourLocationResponse {
        val countryGeoJsonFeature = geoJsonFeaturesCache.findCountryGeoJsonFeatureByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val geoJsonFeaturesByRegion = filterByRegion(countryGeoJsonFeature, regions, districts)
        val geoJsonFeaturesByDistrict = filterByDistrict(countryGeoJsonFeature, districts, geoJsonFeaturesByRegion)
        val allGeoJsonFeatures = geoJsonFeaturesByRegion + geoJsonFeaturesByDistrict

        val regionPolygons = allGeoJsonFeatures.flatMap { geoJsonFeature ->
            geoJsonFeature.geometry.components().map {
                RegionPolygon(geoJsonFeature.local.region, it)
            }
        }
        val regionPolygonWithAreas = regionPolygons.map { it to JTS.to(it.polygon).area }

        val randomRegionPolygon = fetchRandomRegionPolygon(regionPolygonWithAreas)
        val randomLocation = fetchRandomLocationInPolygon(randomRegionPolygon)

        return FetchRandomTourLocationResponse(
            lat = randomLocation.lat,
            lon = randomLocation.lon,
            region = "${randomRegionPolygon.region.name}, ${randomRegionPolygon.region.district.neighborhood.name}"
        )
    }

    private fun filterByRegion(
        countryGeoJsonFeature: GeoJsonFeatures,
        regions: List<String>,
        districts: List<String>
    ) = countryGeoJsonFeature.geoJsonFeatures.filter {
        if (regions.isEmpty() && districts.isEmpty()) return@filter true

        regions.contains(it.local.region.alias)
    }

    private fun filterByDistrict(
        countryGeoJsonFeature: GeoJsonFeatures,
        districts: List<String>,
        geoJsonFeaturesByRegion: List<GeoJsonFeature>
    ) = countryGeoJsonFeature.geoJsonFeatures.filter { geoJsonFeature ->
        if (districts.isEmpty()) return@filter false

        val requestDistrictByRegion = geoJsonFeaturesByRegion.map {
            it.local.region.district.alias
        }.toSet()
        val savedDistrict = geoJsonFeature.local.region.district.alias

        val isEmptyRegionDistrict = requestDistrictByRegion.isEmpty()
        val hasOutOfRegionDistrict = requestDistrictByRegion.any { it !in districts }
        (isEmptyRegionDistrict || hasOutOfRegionDistrict) && districts.contains(savedDistrict)
    }

    private fun fetchRandomRegionPolygon(
        regionPolygonWithAreas: List<Pair<RegionPolygon, Double>>,
    ): RegionPolygon {
        val totalArea = regionPolygonWithAreas.sumOf { it.second }

        val random = Random().nextDouble()
        var calculative = 0.0

        return regionPolygonWithAreas.firstOrNull {
            calculative += it.second / totalArea
            random <= calculative
        }?.first ?: throw CustomException(CustomErrorCode.NOT_FOUND_FILTER_LOCATION)
    }

    private fun fetchRandomLocationInPolygon(randomRegionPolygon: RegionPolygon): Location {
        val minLon = randomRegionPolygon.polygon.boundingBox.lowerLeft().lon
        val minLat = randomRegionPolygon.polygon.boundingBox.lowerLeft().lat
        val maxLon = randomRegionPolygon.polygon.boundingBox.upperRight().lon
        val maxLat = randomRegionPolygon.polygon.boundingBox.upperRight().lat

        repeat(100) {
            val randomLon = Random().nextDouble(minLon, maxLon)
            val randomLat = Random().nextDouble(minLat, maxLat)
            val point = Positions.mkPosition(CoordinateReferenceSystems.WGS84, randomLon, randomLat)
            val jtsPoint = JTS.to(Geometries.mkPoint(point, CoordinateReferenceSystems.WGS84))

            if (JTS.to(randomRegionPolygon.polygon).covers(jtsPoint)) {
                return Location(
                    lat = randomLat,
                    lon = randomLon,
                )
            }
        }

        throw CustomException(CustomErrorCode.RANDOM_TOUR_LOCATION_PROCESSING_ERROR)
    }
}
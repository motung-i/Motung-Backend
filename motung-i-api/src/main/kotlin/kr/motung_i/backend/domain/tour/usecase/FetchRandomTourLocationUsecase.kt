package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.formatter.TourFormatterService
import kr.motung_i.backend.global.geojson.GeoJsonFeaturesCache
import kr.motung_i.backend.global.geojson.dto.local.Region
import kr.motung_i.backend.global.geojson.enums.Country
import org.geolatte.geom.G2D
import org.geolatte.geom.Geometries
import org.geolatte.geom.Polygon
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

        val geoJsonFeaturesByRegions = countryGeoJsonFeature.geoJsonFeatures.filter {
            if (regions.isEmpty() && districts.isEmpty()) return@filter true
            val savedRegion = tourFormatterService.formatToTourRegion(it.local.region.name, country)
            regions.contains(savedRegion)
        }

        val geoJsonFeaturesByDistrict = countryGeoJsonFeature.geoJsonFeatures.filter { geoJsonFeature ->
            if (districts.isEmpty()) return@filter false
            val savedDistrict =
                tourFormatterService.formatToTourDistrict(geoJsonFeature.local.region.district.name, country)
            val requestRegionDistrict = geoJsonFeaturesByRegions.map {
                tourFormatterService.formatToTourDistrict(it.local.region.district.name, country)
            }
            (requestRegionDistrict.isEmpty() || requestRegionDistrict.any { it !in districts }) && districts.contains(savedDistrict)
        }


        val polygonsWithRegion = mutableListOf<Pair<Polygon<G2D>, Region>>()
        val allGeoJsonFeatures = geoJsonFeaturesByRegions + geoJsonFeaturesByDistrict
        allGeoJsonFeatures.forEach { geoJsonFeature ->
            geoJsonFeature.geometry.components().forEach { polygon ->
                polygonsWithRegion.add(polygon to geoJsonFeature.local.region)
            }
        }

        val polygons = polygonsWithRegion.map { polygon: Pair<Polygon<G2D>, Region> ->
            val jtsPolygon = JTS.to(polygon.first)
            val area = jtsPolygon.area
            polygon to area
        }

        val totalArea = polygons.sumOf { it.second }

        val random = Math.random()
        var calculative = 0.0

        val randomPolygon = polygons.firstOrNull {
            calculative += it.second / totalArea
            random <= calculative
        }?.first ?: throw CustomException(CustomErrorCode.NOT_FOUND_FILTER_LOCATION)

        val minLon = randomPolygon.first.boundingBox.lowerLeft().lon
        val minLat = randomPolygon.first.boundingBox.lowerLeft().lat
        val maxLon = randomPolygon.first.boundingBox.upperRight().lon
        val maxLat = randomPolygon.first.boundingBox.upperRight().lat

        for (retry in 1 .. 100) {
            val randomLon = Random().nextDouble(minLon, maxLon)
            val randomLat = Random().nextDouble(minLat, maxLat)
            val point = Positions.mkPosition(CoordinateReferenceSystems.WGS84, randomLon, randomLat)

            val jtsPoint = JTS.to(Geometries.mkPoint(point, CoordinateReferenceSystems.WGS84))
            val jtsPolygon = JTS.to(randomPolygon.first)

            if (jtsPolygon.covers(jtsPoint)) {
                return FetchRandomTourLocationResponse(
                    lat = randomLat,
                    lon = randomLon,
                    region = "${randomPolygon.second.name}, ${randomPolygon.second.district.neighborhood.name }"
                )
            }
        }

        throw CustomException(CustomErrorCode.RANDOM_TOUR_LOCATION_PROCESSING_ERROR)
    }
}
package kr.motung_i.backend.testfixture

import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoLocal
import kr.motung_i.backend.global.geojson.dto.GeoNeighborhood
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.global.third_party.open_ai.dto.response.CreateModelContentResponse
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.tour.entity.Location
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour_comment.entity.TourComment
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.GlobalFixture.createTestModelContentResponse
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.geolatte.geom.*
import org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84
import org.geolatte.geom.crs.Geographic2DCoordinateReferenceSystem
import java.util.*
import kotlin.random.Random

object TourFixture {
    fun createTestTour(
        id: UUID = UUID.randomUUID(),
        user: User = createTestUser(),
        local: Local = createTestLocal(),
        location: Location = Location(0.0, 0.0),
        isActive: Boolean = false,
    ): Tour {
        return Tour(
            id = id,
            user = user,
            local = local,
            location = location
        ).apply {
            if (isActive) {
                this.activate()
            }
        }
    }

    fun createTestLocal(
        localAlias: String = "testLocalAlias",
        country: Country = Country.entries.random(),
        regionAlias: String = "testRegionAlias",
        districtAlias: String = "testDistrictAlias",
        neighborhood: String = "testNeighborhood"
    ): Local {
        return Local(
            localAlias = localAlias,
            country = country,
            regionAlias = regionAlias,
            districtAlias = districtAlias,
            neighborhood = neighborhood,
        )
    }

    fun createTestRegions(): List<GeoRegion> {
        val geoNeighborhoods = listOf(
            createTestGeoNeighborhood(name = "NA"),
            createTestGeoNeighborhood(name = "NB")
        )
        val geoDistrictsA = listOf(
            createTestGeoDistrict(name = "DA", regionName = "RA", geoNeighborhoods = geoNeighborhoods),
            createTestGeoDistrict(name = "DB", regionName = "RA", geoNeighborhoods = geoNeighborhoods)
        )
        val geoDistrictsB = listOf(
            createTestGeoDistrict(name = "DA", regionName = "RB", geoNeighborhoods = geoNeighborhoods),
            createTestGeoDistrict(name = "DB", regionName = "RB", geoNeighborhoods = geoNeighborhoods)
        )

        return listOf(
            createTestGeoRegion(name = "RA", geoDistricts = geoDistrictsA),
            createTestGeoRegion(name = "RB", geoDistricts = geoDistrictsB)
        )
    }

    fun createTestGeoLocal(
        country: Country = Country.entries.random(),
        geoRegions: List<GeoRegion> = listOf(createTestGeoRegion())
    ): GeoLocal {
        return GeoLocal(
            country = country,
            geoRegions = geoRegions
        )
    }

    fun createTestGeoRegion(
        name: String = "testRegion",
        geoDistricts: List<GeoDistrict> = listOf(createTestGeoDistrict(regionName = name))
    ): GeoRegion {
        return GeoRegion(
            name = name,
            alias = name + "Alias",
            geoDistricts = geoDistricts
        )
    }

    fun createTestGeoDistrict(
        name: String = "testDistrict",
        geoNeighborhoods: List<GeoNeighborhood> = listOf(createTestGeoNeighborhood()),
        regionName: String = "testRegion",
    ): GeoDistrict {
        return GeoDistrict(
            name = name,
            alias = name + "Alias",
            geoNeighborhoods = geoNeighborhoods,
            regionName = regionName,
        )
    }

    fun createTestGeoNeighborhood(
        name: String = "testNeighborhood",
        geometry: MultiPolygon<G2D> = createTestGeometry()
    ): GeoNeighborhood {
        return GeoNeighborhood(
            name = name,
            geometry = geometry
        )
    }

    fun createTestGeometry(
        crs: Geographic2DCoordinateReferenceSystem = WGS84,
        positions: List<G2D> = listOf(
            Positions.mkPosition(crs, 0.0, 0.0),
            Positions.mkPosition(crs, 0.0, 1.0),
            Positions.mkPosition(crs, 1.0, 1.0),
            Positions.mkPosition(crs, 1.0, 0.0),
            Positions.mkPosition(crs, 0.0, 0.0),
        )
    ): MultiPolygon<G2D> {
        val positionSequence = PositionSequenceBuilders.variableSized(G2D::class.java)
            .addAll(positions)
            .toPositionSequence()

        return Geometries.mkMultiPolygon(
            Geometries.mkPolygon(
                Geometries.mkLinearRing(positionSequence, crs)
            )
        )
    }

    fun createTestTourComment(
        id: Long = Random.nextLong(),
        tour: Tour = createTestTour(),
        modelContent: CreateModelContentResponse = createTestModelContentResponse()
    ): TourComment {
        val (restaurantComment, sightseeingSpotsComment, cultureComment) =
            modelContent
                .toOpenAiRecommendation()
                .toFormattedComments()

        return TourComment(
            id = id,
            tour = tour,
            restaurantComment = restaurantComment,
            sightseeingSpotsComment = sightseeingSpotsComment,
            cultureComment = cultureComment,
        )
    }
}
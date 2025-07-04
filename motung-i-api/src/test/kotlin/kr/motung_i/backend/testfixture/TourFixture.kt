package kr.motung_i.backend.testfixture

import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.tour.entity.Location
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import java.util.UUID

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
}
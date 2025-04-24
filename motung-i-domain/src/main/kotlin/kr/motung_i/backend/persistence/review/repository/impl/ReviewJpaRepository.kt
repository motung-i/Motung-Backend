package kr.motung_i.backend.persistence.review.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ReviewJpaRepository : JpaRepository<Review, UUID> {

    @Query(
        """
        SELECT r 
        FROM Review r JOIN FETCH r.user
        WHERE (:country = '' OR r.local.country = :country) 
        AND (:regionAlias = '' OR r.local.regionAlias = :regionAlias) 
        AND (:districtAlias = '' OR r.local.districtAlias = :districtAlias) 
        AND (:neighborhood = '' OR r.local.neighborhood = :neighborhood)
        """
    )
    fun findWithUserByLocalAlias(
        country: String,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
    ): List<Review>
}
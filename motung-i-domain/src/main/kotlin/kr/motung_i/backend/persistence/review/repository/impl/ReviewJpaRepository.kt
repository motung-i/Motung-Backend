package kr.motung_i.backend.persistence.review.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.tour_location.entity.Country
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ReviewJpaRepository : JpaRepository<Review, UUID> {

    @Query(
        """
        SELECT r
        FROM Review r JOIN FETCH r.user LEFT JOIN FETCH r.reports
        WHERE (:country IS NULL OR r.local.country = :country) 
        AND (:regionAlias = '' OR r.local.regionAlias = :regionAlias) 
        AND (:districtAlias = '' OR r.local.districtAlias = :districtAlias) 
        AND (:neighborhood = '' OR r.local.neighborhood = :neighborhood)
        AND (:localAlias = '' OR r.local.localAlias = :localAlias)
        AND (:onlyByImage = false OR size(r.imageUrls) > 0)
        AND (:onlyByReported = false OR size(r.reports) > 0)
        ORDER BY r.createdAt DESC
        """
    )
    fun findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreatedAt(
        country: Country?,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
        localAlias: String,
        onlyByImage: Boolean,
        onlyByReported: Boolean,
    ): List<Review>

    @Query(
        """
        SELECT r
        FROM Review r JOIN FETCH r.user
        WHERE r.user = :user
        ORDER BY r.createdAt DESC
        """
    )
    fun findWithUserByUserOrderByCreatedAt(user: User): List<Review>
}
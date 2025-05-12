package kr.motung_i.backend.persistence.review.repository

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.tour_location.entity.Country
import kr.motung_i.backend.persistence.user.entity.User
import java.util.UUID

interface ReviewRepository {
    fun findById(id: UUID): Review?

    fun save(review: Review)

    fun findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreatedAt(
        country: Country?,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
        localAlias: String,
        onlyByImage: Boolean,
        onlyByReported: Boolean,
    ): List<Review>

    fun findWithUserByUserOrderByCreatedAt(user: User): List<Review>

    fun delete(review: Review)
}
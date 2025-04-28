package kr.motung_i.backend.persistence.review.repository

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.tour.entity.Country

interface ReviewRepository {
    fun save(review: Review)

    fun findWithUserByLocalAliasAndOnlyByImage(
        country: Country?,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
        localAlias: String,
        onlyByImage: Boolean,
    ): List<Review>
}
package kr.motung_i.backend.persistence.review.repository

import kr.motung_i.backend.persistence.review.entity.Review

interface ReviewRepository {
    fun save(review: Review)

    fun findWithUserByLocalAliasAndOnlyByImage(
        country: String,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
        localAlias: String,
        onlyByImage: Boolean,
    ): List<Review>
}
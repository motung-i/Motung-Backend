package kr.motung_i.backend.persistence.review.repository

import kr.motung_i.backend.persistence.review.entity.Review

interface ReviewRepository {
    fun save(review: Review)

    fun findWithUserByLocalAlias(
        country: String,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
    ): List<Review>
}
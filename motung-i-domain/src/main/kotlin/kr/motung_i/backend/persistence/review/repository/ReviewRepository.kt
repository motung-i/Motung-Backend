package kr.motung_i.backend.persistence.review.repository

import kr.motung_i.backend.persistence.review.entity.Review

interface ReviewRepository {
    fun save(review: Review)
}
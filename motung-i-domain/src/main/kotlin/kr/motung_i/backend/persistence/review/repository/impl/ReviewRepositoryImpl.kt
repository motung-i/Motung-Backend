package kr.motung_i.backend.persistence.review.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import org.springframework.stereotype.Repository

@Repository
class ReviewRepositoryImpl(
    private val reviewJpaRepository: ReviewJpaRepository,
) : ReviewRepository {
    override fun save(review: Review) {
        reviewJpaRepository.save(review)
    }

    override fun findWithUserByLocalAlias(
        country: String,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String
    ): List<Review> =
        reviewJpaRepository.findWithUserByLocalAlias(
            country = country,
            regionAlias = regionAlias,
            districtAlias = districtAlias,
            neighborhood = neighborhood
        )
}
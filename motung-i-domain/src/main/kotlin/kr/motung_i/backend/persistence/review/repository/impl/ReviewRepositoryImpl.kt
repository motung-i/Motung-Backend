package kr.motung_i.backend.persistence.review.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ReviewRepositoryImpl(
    private val reviewJpaRepository: ReviewJpaRepository,
) : ReviewRepository {
    override fun findById(id: UUID): Review? =
        reviewJpaRepository.findByIdOrNull(id)

    override fun save(review: Review) {
        reviewJpaRepository.save(review)
    }

    override fun findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreateAt(
        country: Country?,
        regionAlias: String,
        districtAlias: String,
        neighborhood: String,
        localAlias: String,
        onlyByImage: Boolean,
        onlyByReported: Boolean,
    ): List<Review> =
        reviewJpaRepository.findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreateAt(
            country = country,
            regionAlias = regionAlias,
            districtAlias = districtAlias,
            neighborhood = neighborhood,
            localAlias = localAlias,
            onlyByImage = onlyByImage,
            onlyByReported = onlyByReported,
        )

    override fun delete(review: Review) {
        reviewJpaRepository.delete(review)
    }
}
package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.global.geojson.enums.Country
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchReviewsUsecase(
    val reviewRepository: ReviewRepository,
) {
    fun execute(
        country: Country?,
        region: String,
        district: String,
        neighborhood: String,
    ): FetchReviewsResponse =
        FetchReviewsResponse.toDto(
            reviewRepository.findWithUserByLocalAlias(
                country = country?.name ?: "",
                regionAlias = region,
                districtAlias = district,
                neighborhood = neighborhood,
            )
        )
}
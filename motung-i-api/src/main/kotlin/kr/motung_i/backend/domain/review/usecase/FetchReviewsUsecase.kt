package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchReviewsUsecase(
    val reviewRepository: ReviewRepository,
) {
    fun execute(request: FetchReviewsRequest): FetchReviewsResponse =
        FetchReviewsResponse.toDto(
            reviewRepository.findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreatedAt(
                country = request.country,
                regionAlias = request.region ?: "",
                districtAlias = request.district ?: "",
                neighborhood = request.neighborhood ?: "",
                localAlias = request.local ?: "",
                onlyByImage = request.onlyByImage ?: false,
                onlyByReported = false,
            )
        )
}
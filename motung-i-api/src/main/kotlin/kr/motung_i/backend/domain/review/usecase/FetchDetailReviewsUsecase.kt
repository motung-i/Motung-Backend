package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchDetailReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchDetailReviewsResponse
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchDetailReviewsUsecase(
    val reviewRepository: ReviewRepository,
) {
    fun execute(request: FetchDetailReviewsRequest): FetchDetailReviewsResponse =
        FetchDetailReviewsResponse.toDto(
            reviewRepository.findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreateAt(
                country = request.country,
                regionAlias = request.region ?: "",
                districtAlias = request.district ?: "",
                neighborhood = request.neighborhood ?: "",
                localAlias = request.local ?: "",
                onlyByImage = request.onlyByImage ?: false,
                onlyByReported = request.onlyByReport ?: false,
            ).sortedByDescending {
                it.reports.size
            }
        )
}
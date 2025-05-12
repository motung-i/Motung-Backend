package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.response.FetchMyReviewsResponse
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchMyReviewsUsecase(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val reviewRepository: ReviewRepository,
) {
    fun execute(): FetchMyReviewsResponse {
        val currentUser = fetchCurrentUserUsecase.execute()

        return FetchMyReviewsResponse.toDto(
            reviewRepository.findWithUserByUserOrderByCreatedAt(currentUser)
        )
    }
}
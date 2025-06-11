package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourCommentMyselfResponse
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_comment.repository.TourCommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchTourCommentMyselfUsecase(
    private val tourRepository: TourRepository,
    private val tourCommentRepository: TourCommentRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {
    fun execute(): FetchTourCommentMyselfResponse {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findByUserAndIsActive(currentUser, true)
            ?: throw CustomException(CustomErrorCode.NOT_ACTIVATED_TOUR)

        val tourComment = tourCommentRepository.findByTour(tour)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_TOUR_COMMENT)

        return FetchTourCommentMyselfResponse.toDto(tourComment)
    }
}
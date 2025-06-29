package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.usecase.DeleteTourUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteTourUsecaseImpl(
    private val tourRepository: TourRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
): DeleteTourUsecase {
    override fun execute() {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findByUserAndIsActive(currentUser, true)
            ?: throw CustomException(CustomErrorCode.NOT_ACTIVATED_TOUR)

        tourRepository.delete(tour)
    }
}
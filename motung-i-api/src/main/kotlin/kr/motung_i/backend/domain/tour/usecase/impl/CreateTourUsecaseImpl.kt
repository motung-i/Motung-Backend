package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.usecase.CreateTourUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTourUsecaseImpl(
    private val tourRepository: TourRepository,
    private val currentUserUsecase: FetchCurrentUserUsecase,
): CreateTourUsecase {
    override fun execute() {
        val currentUser = currentUserUsecase.execute()

        val tour = tourRepository.findByUser(currentUser)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_TOUR)

        if (tourRepository.existsByUserAndIsActivate(currentUser, true)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_TOUR)
        }

        tour.activate()
    }
}
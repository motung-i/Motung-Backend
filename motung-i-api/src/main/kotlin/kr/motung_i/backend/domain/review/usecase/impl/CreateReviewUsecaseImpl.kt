package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.s3.usecase.UploadImageUsecase
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class CreateReviewUsecaseImpl(
    private val reviewRepository: ReviewRepository,
    private val tourRepository: TourRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val uploadImageUsecase: UploadImageUsecase,
): CreateReviewUsecase {
    override fun execute(images: List<MultipartFile>, request: CreateReviewRequest) {
        val currentUser = fetchCurrentUserUsecase.execute()

        val tour = tourRepository.findByUserAndIsActive(currentUser, true)
            ?: throw CustomException(CustomErrorCode.NOT_ACTIVATED_TOUR)

        val imageUrls = images.mapNotNull {
            uploadImageUsecase.execute(it)
        }

        reviewRepository.save(
            Review(
                user = currentUser,
                local = tour.local.copy(),
                isRecommend = request.isRecommend,
                description = request.description,
                imageUrls = imageUrls
            )
        )
        tourRepository.delete(tour)
    }
}
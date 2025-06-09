package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.s3.usecase.UploadImageUsecase
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_location.repository.TourLocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class CreateReviewUsecase(
    private val reviewRepository: ReviewRepository,
    private val tourRepository: TourRepository,
    private val tourLocationRepository: TourLocationRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val uploadImageUsecase: UploadImageUsecase,
) {
    fun execute(images: List<MultipartFile>, request: CreateReviewRequest) {
        val currentUser = fetchCurrentUserUsecase.execute()

        val tour = tourRepository.findByUser(currentUser)
            ?: throw CustomException(CustomErrorCode.INVALID_TOUR_LOCATION)

        val imageUrls = images.mapNotNull {
            uploadImageUsecase.execute(it)
        }

        reviewRepository.save(
            Review(
                user = currentUser,
                local = tour.tourLocation.local.copy(),
                isRecommend = request.isRecommend,
                description = request.description,
                imageUrls = imageUrls
            )
        )
        tourLocationRepository.deleteByUser(currentUser)
    }
}
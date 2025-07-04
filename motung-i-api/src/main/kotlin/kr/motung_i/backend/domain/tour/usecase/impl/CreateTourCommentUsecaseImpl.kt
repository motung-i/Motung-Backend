package kr.motung_i.backend.domain.tour.usecase.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.motung_i.backend.domain.tour.usecase.CreateTourCommentUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.open_ai.OpenAiAdapter
import kr.motung_i.backend.global.third_party.open_ai.dto.OpenAiRecommendation
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_comment.entity.TourComment
import kr.motung_i.backend.persistence.tour_comment.repository.TourCommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTourCommentUsecaseImpl(
    private val openAiAdapter: OpenAiAdapter,
    private val tourCommentRepository: TourCommentRepository,
    private val tourRepository: TourRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
): CreateTourCommentUsecase {
    override fun execute() {
        val currentUser = fetchCurrentUserUsecase.execute()

        val tour = tourRepository.findByUser(currentUser)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_TOUR)

        if (tourCommentRepository.existsByTour(tour)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_TOUR_COMMENT)
        }

        val createModelContentResponse = openAiAdapter.createModelContent(
            "${tour.local.country.alias} ${tour.local.regionAlias} ${tour.local.districtAlias} ${tour.local.neighborhood}"
        )
        val modelContent = createModelContentResponse.output.first().content.first().text
        val openAiRecommendation = jacksonObjectMapper().readValue<OpenAiRecommendation>(modelContent)

        val (restaurantComment, sightseeingSpotsComment, cultureComment) = listOf(
            openAiRecommendation.restaurants,
            openAiRecommendation.natureOrSightseeingSpots,
            openAiRecommendation.culturalExperiences
        ).map { recommendation -> recommendation.joinToString("\n") { it.toString() } }

        tourCommentRepository.save(
            TourComment(
                tour = tour,
                restaurantComment = restaurantComment,
                sightseeingSpotsComment = sightseeingSpotsComment,
                cultureComment = cultureComment,
            )
        )
    }
}
package kr.motung_i.backend.domain.tour.usecase

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.open_ai.dto.OpenAiRecommendation
import kr.motung_i.backend.global.third_party.open_ai.dto.request.CreateModelContentRequest
import kr.motung_i.backend.global.third_party.open_ai.feign.OpenAiFeignClient
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_location.repository.TourLocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTourUsecase(
    private val tourRepository: TourRepository,
    private val tourLocationRepository: TourLocationRepository,
    private val currentUserUsecase: FetchCurrentUserUsecase,
    private val openAiFeignClient: OpenAiFeignClient,
) {
    fun execute() {
        val currentUser = currentUserUsecase.execute()

        val tourLocation = tourLocationRepository.findByUser(currentUser)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_TOUR_LOCATION)

        if (tourRepository.existsByUser(currentUser)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_TOUR)
        }

        val createModelContentResponse = openAiFeignClient.createModelContent(
            CreateModelContentRequest.fromLocalAlias(
                "${tourLocation.local.country.alias} ${tourLocation.local.regionAlias} ${tourLocation.local.districtAlias} ${tourLocation.local.neighborhood}"
            )
        )
        val modelContent = createModelContentResponse.output.first().content.first().text
        val openAiRecommendation = jacksonObjectMapper().readValue<OpenAiRecommendation>(modelContent)

        val (restaurantComment, cafeComment, sightseeingSpotsComment, cultureComment) = listOf(
            openAiRecommendation.restaurants,
            openAiRecommendation.cafesOrDessertPlaces,
            openAiRecommendation.natureOrSightseeingSpots,
            openAiRecommendation.culturalExperiences
        ).map { recommendation -> recommendation.joinToString("\n") { it.toString() } }

        tourRepository.save(
            Tour(
                user = currentUser,
                tourLocation = tourLocation,
                restaurantComment = restaurantComment,
                cafeComment = cafeComment,
                sightseeingSpotsComment = sightseeingSpotsComment,
                cultureComment = cultureComment,
            )
        )
    }
}
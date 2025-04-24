package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import kr.motung_i.backend.domain.review.usecase.FetchReviewsUsecase
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("review")
class ReviewController(
    private val createReviewUsecase: CreateReviewUsecase,
    private val fetchReviewsUsecase: FetchReviewsUsecase,
) {
    @PostMapping
    fun createReview(
        @RequestPart("images", required = false) images: List<MultipartFile>,
        @RequestPart("request") @Valid createReviewRequest: CreateReviewRequest
    ): Unit =
        createReviewUsecase.execute(images, createReviewRequest).run {
            ResponseEntity.ok()
        }

    @GetMapping
    fun fetchReviews(
        @RequestParam(required = false) country: Country?,
        @RequestParam(required = false) region: String,
        @RequestParam(required = false) district: String,
        @RequestParam(required = false) neighborhood: String,
        @RequestParam onlyByImage: Boolean,
    ): ResponseEntity<FetchReviewsResponse> =
        fetchReviewsUsecase.execute(country, region, district, neighborhood, onlyByImage).run {
            ResponseEntity.ok(this)
        }

}
package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import kr.motung_i.backend.domain.review.usecase.FetchReviewsUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun fetchReviews(@ModelAttribute fetchReviewsRequest: FetchReviewsRequest): ResponseEntity<FetchReviewsResponse> =
        fetchReviewsUsecase.execute(fetchReviewsRequest).run {
            ResponseEntity.ok(this)
        }

}
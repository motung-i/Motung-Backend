package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.ReportReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import kr.motung_i.backend.domain.review.usecase.FetchReviewsUsecase
import kr.motung_i.backend.domain.review.usecase.ReportReviewUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("review")
class ReviewController(
    private val createReviewUsecase: CreateReviewUsecase,
    private val fetchReviewsUsecase: FetchReviewsUsecase,
    private val reportReviewUsecase: ReportReviewUsecase,
) {
    @PostMapping
    fun createReview(
        @RequestPart("images", required = false) images: List<MultipartFile>,
        @RequestPart("request") @Valid createReviewRequest: CreateReviewRequest
    ): ResponseEntity<Unit> =
        createReviewUsecase.execute(images, createReviewRequest).run {
            ResponseEntity.noContent().build()
        }

    @GetMapping
    fun fetchReviews(@ModelAttribute fetchReviewsRequest: FetchReviewsRequest): ResponseEntity<FetchReviewsResponse> =
        fetchReviewsUsecase.execute(fetchReviewsRequest).run {
            ResponseEntity.ok(this)
        }

    @PostMapping("/{reviewId}/report")
    fun reportReview(
        @PathVariable reviewId: UUID,
        @RequestBody @Valid reportReviewRequest: ReportReviewRequest,
    ): ResponseEntity<Unit> =
        reportReviewUsecase.execute(reviewId, reportReviewRequest).run {
            ResponseEntity.noContent().build()
        }

}
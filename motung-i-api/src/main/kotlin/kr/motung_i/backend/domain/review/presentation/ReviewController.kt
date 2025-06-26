package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.ReportReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchMyReviewsResponse
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import kr.motung_i.backend.domain.review.usecase.FetchMyReviewsUsecase
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
    private val fetchMyReviewsUsecase: FetchMyReviewsUsecase,
    private val reportReviewUsecase: ReportReviewUsecase,
) {
    @PostMapping
    fun createReview(
        @RequestPart("images", required = false) images: List<MultipartFile>?,
        @RequestPart("request") @Valid request: CreateReviewRequest
    ): ResponseEntity<Unit> =
        createReviewUsecase.execute(images ?: listOf(), request).run {
            ResponseEntity.noContent().build()
        }

    @GetMapping
    fun fetchReviews(@ModelAttribute request: FetchReviewsRequest): ResponseEntity<FetchReviewsResponse> =
        fetchReviewsUsecase.execute(request).run {
            ResponseEntity.ok(this)
        }

    @GetMapping("/myself")
    fun fetchMyReviews(): ResponseEntity<FetchMyReviewsResponse> =
        fetchMyReviewsUsecase.execute().run {
            ResponseEntity.ok(this)
        }


    @PostMapping("/{reviewId}/report")
    fun reportReview(
        @PathVariable reviewId: UUID,
        @RequestBody @Valid request: ReportReviewRequest,
    ): ResponseEntity<Unit> =
        reportReviewUsecase.execute(reviewId, request).run {
            ResponseEntity.noContent().build()
        }

}
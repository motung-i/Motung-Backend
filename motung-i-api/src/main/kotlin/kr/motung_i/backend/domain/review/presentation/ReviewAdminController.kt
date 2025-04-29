package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.request.RemoveReviewRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.FetchDetailReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchDetailReviewsResponse
import kr.motung_i.backend.domain.review.usecase.FetchDetailReviewsUsecase
import kr.motung_i.backend.domain.review.usecase.RemoveReviewUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/admin/review")
class ReviewAdminController(
    private val fetchDetailReviewsUsecase: FetchDetailReviewsUsecase,
    private val removeReviewUsecase: RemoveReviewUsecase,
) {
    @GetMapping
    fun fetchDetailReviews(@ModelAttribute request: FetchDetailReviewsRequest): ResponseEntity<FetchDetailReviewsResponse> =
        fetchDetailReviewsUsecase.execute(request).run {
            ResponseEntity.ok(this)
        }

    @DeleteMapping("{reviewId}")
    fun removeReview(
        @PathVariable reviewId: UUID,
        @RequestBody @Valid removeReviewRequest: RemoveReviewRequest,
    ): ResponseEntity<Unit> =
        removeReviewUsecase.execute(reviewId, removeReviewRequest).run {
            ResponseEntity.noContent().build()
        }


}
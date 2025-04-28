package kr.motung_i.backend.domain.review.presentation

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchDetailReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchDetailReviewsResponse
import kr.motung_i.backend.domain.review.usecase.FetchDetailReviewsUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/review")
class ReviewAdminController(
    private val fetchDetailReviewsUsecase: FetchDetailReviewsUsecase
) {
    @GetMapping
    fun fetchDetailReviews(@ModelAttribute request: FetchDetailReviewsRequest): ResponseEntity<FetchDetailReviewsResponse> =
        fetchDetailReviewsUsecase.execute(request).run {
            ResponseEntity.ok(this)
        }
}
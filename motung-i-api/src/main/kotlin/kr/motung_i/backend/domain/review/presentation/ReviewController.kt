package kr.motung_i.backend.domain.review.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.review.presentation.dto.CreateReviewRequest
import kr.motung_i.backend.domain.review.usecase.CreateReviewUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("review")
class ReviewController(
    private val createReviewUsecase: CreateReviewUsecase
) {
    @PostMapping
    fun createReview(
        @RequestPart("images", required = false) images: List<MultipartFile>,
        @RequestPart("request") @Valid createReviewRequest: CreateReviewRequest
    ): Unit =
        createReviewUsecase.execute(images, createReviewRequest).run {
            ResponseEntity.ok()
        }
}
package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import org.springframework.web.multipart.MultipartFile

interface CreateReviewUsecase {
    fun execute(images: List<MultipartFile>, request: CreateReviewRequest)
}
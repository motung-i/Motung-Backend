package kr.motung_i.backend.testfixture

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchDetailReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.TourFixture.createTestLocal
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import java.util.*

object ReviewFixture {
    fun createTestReview(
        id: UUID = UUID.randomUUID(),
        user: User = createTestUser(),
        local: Local = createTestLocal(),
        isRecommend: Boolean = false,
        description: String = "testReview description",
        imageUrls: List<String> = listOf("https://image/1", "https://image/2"),
    ): Review {
        return Review(
            id = id,
            user = user,
            local = local,
            isRecommend = isRecommend,
            description = description,
            imageUrls = imageUrls
        )
    }

    fun createTestFetchDetailReviewsRequest(
        country: Country? = null,
        region: String? = null,
        neighborhood: String? = null,
        district: String? = null,
        local: String? = null,
        onlyByImage: Boolean? = null,
        onlyByReport: Boolean? = null,
    ): FetchDetailReviewsRequest {
        return FetchDetailReviewsRequest(
            country = country,
            region = region,
            neighborhood = neighborhood,
            district = district,
            local = local,
            onlyByImage = onlyByImage,
            onlyByReport = onlyByReport,
        )
    }

    fun createTestFetchReviewsRequest(
        country: Country? = null,
        region: String? = null,
        neighborhood: String? = null,
        district: String? = null,
        local: String? = null,
        onlyByImage: Boolean? = null,
    ): FetchReviewsRequest {
        return FetchReviewsRequest(
            country = country,
            region = region,
            neighborhood = neighborhood,
            district = district,
            local = local,
            onlyByImage = onlyByImage,
        )
    }
}

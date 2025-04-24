package kr.motung_i.backend.persistence.review.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReviewJpaRepository: JpaRepository<Review, UUID> {
}
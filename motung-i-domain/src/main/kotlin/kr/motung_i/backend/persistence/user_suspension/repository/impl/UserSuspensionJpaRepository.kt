package kr.motung_i.backend.persistence.user_suspension.repository.impl

import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import org.springframework.data.jpa.repository.JpaRepository

interface UserSuspensionJpaRepository: JpaRepository<UserSuspension, Long> {
}
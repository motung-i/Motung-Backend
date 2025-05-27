package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchCurrentUserUsecase {
    fun execute(): User = SecurityContextHolder.getContext().authentication.principal as User
}

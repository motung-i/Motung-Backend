package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchCurrentUserUsecaseImpl: FetchCurrentUserUsecase {
    override fun execute(): User = SecurityContextHolder.getContext().authentication.principal as User
}

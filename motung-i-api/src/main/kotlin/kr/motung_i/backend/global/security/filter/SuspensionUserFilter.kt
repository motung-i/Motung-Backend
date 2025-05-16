package kr.motung_i.backend.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.security.exception.SuspensionUserExceptionHandler
import kr.motung_i.backend.global.security.exception.dto.response.SuspensionUserExceptionResponse
import kr.motung_i.backend.persistence.user_suspension.repository.UserSuspensionRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime

@Component
class SuspensionUserFilter(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val userSuspensionRepository: UserSuspensionRepository,
    private val suspensionUserExceptionHandler: SuspensionUserExceptionHandler
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        if (SecurityContextHolder.getContext().authentication == null) {
            filterChain.doFilter(request, response)
            return
        }

        val currentUser = fetchCurrentUserUsecase.execute()
        val userSuspension = userSuspensionRepository.findWithReasonsByUser(currentUser)

        if (userSuspension == null) {
            filterChain.doFilter(request, response)
            return
        }

        val resumeAt = userSuspension.resumeAt
        val now = LocalDateTime.now()

        if (resumeAt.isEqual(now) || resumeAt.isBefore(now)) {
            userSuspensionRepository.delete(userSuspension)
            filterChain.doFilter(request, response)
            return
        }

        return suspensionUserExceptionHandler.handle(
            response, SuspensionUserExceptionResponse.toDto(userSuspension)
        )
    }
}
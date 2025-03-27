package kr.motung_i.backend.global.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.global.exception.dto.response.CustomExceptionResponse
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.characterEncoding = Charsets.UTF_8.name()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.writer?.print(objectMapper.writeValueAsString(CustomExceptionResponse(CustomErrorCode.FORBIDDEN)))
    }
}
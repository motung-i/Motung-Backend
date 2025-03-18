package kr.motung_i.backend.global.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.global.exception.dto.CustomExceptionResponse
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint(
    val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.characterEncoding = Charsets.UTF_8.name()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.writer?.print(objectMapper.writeValueAsString(CustomExceptionResponse(CustomErrorCode.UNAUTHORIZED)))
    }
}
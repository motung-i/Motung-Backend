package kr.motung_i.backend.global.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.exception.dto.response.SuspensionUserExceptionResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class SuspensionUserExceptionHandler(
    private val objectMapper: ObjectMapper
) {
    fun handle(
        response: HttpServletResponse?,
        suspensionUserExceptionResponse: SuspensionUserExceptionResponse
    ) {
        response?.status = CustomErrorCode.SUSPENDED_USER.statusCode.value()
        response?.characterEncoding = Charsets.UTF_8.name()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.writer?.print(objectMapper.writeValueAsString(suspensionUserExceptionResponse))
    }
}
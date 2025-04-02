package kr.motung_i.backend.global.exception.handler

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.dto.response.CustomExceptionResponse
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import java.lang.RuntimeException

@Slf4j
@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun customExceptionHandler(exception: CustomException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(exception.customErrorCode, exception.detailMessage)
        return ResponseEntity.status(response.status.statusCode).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(
            CustomErrorCode.VALIDATION_ERROR,
            exception.fieldError?.defaultMessage ?: CustomErrorCode.VALIDATION_ERROR.statusMessage,
        )
        return ResponseEntity.status(response.status.statusCode).body(response)
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(exception: HandlerMethodValidationException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(
            CustomErrorCode.PARAMETER_ERROR,
            CustomErrorCode.PARAMETER_ERROR.statusMessage,
        )
        return ResponseEntity.status(response.status.statusCode).body(response)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(
            CustomErrorCode.MISSING_REQUEST_BODY,
            CustomErrorCode.MISSING_REQUEST_BODY.statusMessage,
        )
        return ResponseEntity.status(response.status.statusCode).body(response)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(exception: RuntimeException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(
            CustomErrorCode.UNKNOWN_SERVER_ERROR,
            exception.message ?: CustomErrorCode.UNKNOWN_SERVER_ERROR.statusMessage,
        )
        return ResponseEntity.status(response.status.statusCode).body(response)
    }

}
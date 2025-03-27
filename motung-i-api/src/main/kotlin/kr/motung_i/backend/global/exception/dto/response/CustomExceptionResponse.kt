package kr.motung_i.backend.global.exception.dto.response

import kr.motung_i.backend.global.exception.enums.CustomErrorCode

data class CustomExceptionResponse(
    val status: CustomErrorCode,
    val statusMessage: String
) {
    constructor(customErrorCode: CustomErrorCode) : this(
        status = customErrorCode,
        statusMessage = customErrorCode.statusMessage
    )
}
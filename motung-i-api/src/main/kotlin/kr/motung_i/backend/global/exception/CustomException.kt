package kr.motung_i.backend.global.exception

import kr.motung_i.backend.global.exception.enums.CustomErrorCode

class CustomException(
    val customErrorCode: CustomErrorCode,
    val detailMessage: String,
): RuntimeException(detailMessage) {
    constructor(customErrorCode: CustomErrorCode) : this (
        customErrorCode = customErrorCode,
        detailMessage = customErrorCode.statusMessage,
    )
}
package kr.motung_i.backend.global.exception.enums

import org.springframework.http.HttpStatus

enum class CustomErrorCode(
    val statusCode: HttpStatus,
    val statusMessage: String
) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 토큰이 존재하지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 페이지입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "의도하지 않은 오류가 발생했습니다."),

    NOT_ALLOWED_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "허용되지 않은 이미지 확장자 입니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    NOT_FOUND_MUSIC(HttpStatus.NOT_FOUND, "존재하지 않는 음악입니다."),
}
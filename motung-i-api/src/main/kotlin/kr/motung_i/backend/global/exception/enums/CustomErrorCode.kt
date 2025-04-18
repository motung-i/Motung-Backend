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
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "본문 요청이 비어있거나 잘못된 형식입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않는 구성의 바디 요청 값 입니다."),
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않는 구성의 파라미터 요청 값 입니다."),

    NOT_ALLOWED_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "허용되지 않은 이미지 확장자 입니다."),
    NOT_FOUND_IMAGE(HttpStatus.BAD_REQUEST, "이미지가 존재하지 않습니다."),
    IMAGE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패했습니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    NOT_FOUND_MUSIC(HttpStatus.NOT_FOUND, "존재하지 않는 음악입니다."),
    CANNOT_MODIFY_APPROVED_MUSIC(HttpStatus.BAD_REQUEST, "승인된 음악은 삭제할 수 없습니다."),
    MUSIC_ALREADY_APPROVED(HttpStatus.BAD_REQUEST, "이미 승인된 음악입니다."),

    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "존재하지 않는 꿀템입니다."),
    CANNOT_MODIFY_APPROVED_ITEM(HttpStatus.BAD_REQUEST, "승인된 꿀템은 삭제할 수 없습니다."),
    ITEM_ALREADY_APPROVED(HttpStatus.BAD_REQUEST, "이미 승인된 꿀템입니다."),

    NOT_FOUND_TRAVEL_INFO(HttpStatus.NOT_FOUND, "존재하지 않는 여행 정보입니다."),
}
package kr.motung_i.backend.global.exception.enums

import org.springframework.http.HttpStatus

enum class CustomErrorCode(
    val statusCode: HttpStatus,
    val statusMessage: String
) {
    //일반
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 토큰이 존재하지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 페이지입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "의도하지 않은 오류가 발생했습니다."),

    //검증
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "본문 요청이 비어있거나 잘못된 형식입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않는 구성의 바디 요청 값 입니다."),
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않는 구성의 파라미터 요청 값 입니다."),

    //이미지
    NOT_ALLOWED_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "허용되지 않은 이미지 확장자 입니다."),
    NOT_FOUND_IMAGE(HttpStatus.BAD_REQUEST, "이미지가 존재하지 않습니다."),
    IMAGE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패했습니다."),

    //유저
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    SUSPENDED_USER(HttpStatus.FORBIDDEN, "정지된 유저입니다."),
    SUSPENDED_ADMIN(HttpStatus.FORBIDDEN, "관리자를 정지할 수 없습니다."),
    ALREADY_EXISTS_NICKNAME(HttpStatus.FORBIDDEN, "이미 존재하는 닉네임 입니다."),

    //음악
    NOT_FOUND_MUSIC(HttpStatus.NOT_FOUND, "존재하지 않는 음악입니다."),
    NOT_FOUND_MUSIC_URL(HttpStatus.NOT_FOUND, "존재하지 않는 음악 링크입니다."),
    CANNOT_MODIFY_APPROVED_MUSIC(HttpStatus.BAD_REQUEST, "승인된 음악은 삭제할 수 없습니다."),
    MUSIC_ALREADY_APPROVED(HttpStatus.BAD_REQUEST, "이미 승인된 음악입니다."),

    //꿀템
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "존재하지 않는 꿀템입니다."),
    CANNOT_MODIFY_APPROVED_ITEM(HttpStatus.BAD_REQUEST, "승인된 꿀템은 삭제할 수 없습니다."),
    ITEM_ALREADY_APPROVED(HttpStatus.BAD_REQUEST, "이미 승인된 꿀템입니다."),

    //여행 정보
    NOT_FOUND_TRAVEL_INFO(HttpStatus.NOT_FOUND, "존재하지 않는 여행 정보입니다."),

    //국가
    NOT_FOUND_COUNTRY_GEOJSON(HttpStatus.NOT_FOUND, "해당 국가의 지리 정보가 존재하지 않습니다."),
    NOT_FOUND_GEOJSON(HttpStatus.INTERNAL_SERVER_ERROR, "GEOJSON 파일이 1개이상 존재해야 합니다."),
    NOT_FOUND_COUNTRY_FORMATTER(HttpStatus.NOT_FOUND, "해당 국가의 필터 정보가 존재하지 않습니다."),
    NOT_FOUND_LOCAL(HttpStatus.NOT_FOUND, "지원하지 않는 지역입니다."),

    //여행
    NOT_FOUND_FILTER_LOCATION(HttpStatus.NOT_FOUND, "해당 지역에 대한 정보가 존재하지 않습니다."),
    RANDOM_TOUR_LOCATION_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 지역을 뽑는 도중 오류가 발생했습니다."),
    NOT_FOUND_TOUR_LOCATION(HttpStatus.NOT_FOUND, "존재하지 않는 랜덤 여행지 입니다."),
    NOT_FOUND_TOUR(HttpStatus.NOT_FOUND, "여행중인 상태가 아닙니다."),
    ALREADY_EXISTS_TOUR(HttpStatus.BAD_REQUEST, "이미 존재하는 여행이 있습니다."),

    //리뷰
    INVALID_TOUR_LOCATION(HttpStatus.BAD_REQUEST, "여행중인 상태가 아닙니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
}
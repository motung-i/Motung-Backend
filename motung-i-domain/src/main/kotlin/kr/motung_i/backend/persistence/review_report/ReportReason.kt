package kr.motung_i.backend.persistence.review_report

enum class ReportReason(
    val description: String,
) {
    PERSONAL_INFORMATION("개인정보노출"),
    PROMOTION("홍보성/상업적"),
    ABUSE("욕설/인신공격"),
    SENSATIONALISM("음란/선정성"),
    ETC("기타"),
}
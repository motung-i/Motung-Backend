package kr.motung_i.backend.persistence.user_suspension.entity

enum class SuspensionTarget(
    val description: String
) {
    MUSIC("음악"),
    ITEM("꿀템"),
    REVIEW("리뷰"),
}
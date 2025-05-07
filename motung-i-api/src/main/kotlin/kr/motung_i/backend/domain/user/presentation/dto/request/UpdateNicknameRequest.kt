package kr.motung_i.backend.domain.user.presentation.dto.request

import jakarta.validation.constraints.Pattern

data class UpdateNicknameRequest(
    @field:Pattern(regexp = "^(?![ㄱ-ㅎ]+\$)(?![ㅏ-ㅣ]+\$)[가-힣a-zA-Z0-9]+\$", message = "공백, 특수문자, 자음, 모음을 포함할 수 없습니다.")
    val nickname: String,
)
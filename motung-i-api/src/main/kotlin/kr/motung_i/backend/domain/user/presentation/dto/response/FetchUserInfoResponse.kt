package kr.motung_i.backend.domain.user.presentation.dto.response

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Role

data class FetchUserInfoResponse(
    val nickname: String?,
    val role: Role,
) {
    companion object {
        fun fromUser(user: User): FetchUserInfoResponse =
            FetchUserInfoResponse(
                nickname = user.nickname,
                role = user.role,
            )
    }
}

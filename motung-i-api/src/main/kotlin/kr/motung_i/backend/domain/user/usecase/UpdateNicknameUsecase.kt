package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest

interface UpdateNicknameUsecase {
    fun execute(request: UpdateNicknameRequest)
}
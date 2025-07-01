package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.persistence.user.entity.User

interface CreateUserUsecase {
    fun execute(user: User): User
}
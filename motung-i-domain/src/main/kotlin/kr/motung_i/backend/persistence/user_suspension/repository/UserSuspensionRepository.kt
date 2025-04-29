package kr.motung_i.backend.persistence.user_suspension.repository

import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension

interface UserSuspensionRepository {
    fun save(userSuspension: UserSuspension)
}
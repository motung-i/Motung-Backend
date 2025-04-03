package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item

interface ItemCustomRepository {
    fun save(item : Item)
}
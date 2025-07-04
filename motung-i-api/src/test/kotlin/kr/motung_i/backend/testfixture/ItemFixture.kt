package kr.motung_i.backend.testfixture

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import java.util.*

object ItemFixture {
    fun createTestItem(
        user: User = createTestUser(),
        name: String = "testItem",
        coupangUrl: String = "https://coupang.com",
        description: String = "testItem description",
        rankNumber: Int? = null,
    ): Item {
        return Item(
            id = UUID.randomUUID(),
            user = user,
            name = name,
            coupangUrl = coupangUrl,
            description = description,
        ).apply {
            rankNumber?.let { this.approveItem(rankNumber) }
        }
    }
}
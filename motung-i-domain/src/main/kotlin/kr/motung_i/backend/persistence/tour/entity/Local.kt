package kr.motung_i.backend.persistence.tour.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Local(
    @Column(nullable = false)
    val localAlias: String,

    @Column(nullable = false)
    val country: String,

    @Column(nullable = false)
    val regionAlias: String,

    @Column(nullable = false)
    val districtAlias: String,

    @Column(nullable = false)
    val neighborhood: String,
)
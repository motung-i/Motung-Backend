package kr.motung_i.backend.persistence.tour.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Location(
    @Column(nullable = false)
    val lat: Double,

    @Column(nullable = false)
    val lon: Double,
)
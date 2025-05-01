package kr.motung_i.backend.persistence.tour_location.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Location(
    @Column(nullable = false)
    val lat: Double,

    @Column(nullable = false)
    val lon: Double,
)
package kr.motung_i.backend.global.geojson.dto

import kr.motung_i.backend.persistence.tour.entity.Country

data class Local(
    val country: Country,
    val regions: List<Region>,
)
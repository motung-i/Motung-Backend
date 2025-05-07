package kr.motung_i.backend.global.geojson.dto

import kr.motung_i.backend.persistence.tour_location.entity.Country

data class GeoLocal(
    val country: Country,
    val geoRegions: List<GeoRegion>,
)
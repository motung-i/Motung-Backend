package kr.motung_i.backend.global.geojson.dto

import kr.motung_i.backend.global.geojson.dto.local.Local
import org.geolatte.geom.*

data class GeoJsonFeature(
    val local: Local,
    val geometry: MultiPolygon<G2D>,
)
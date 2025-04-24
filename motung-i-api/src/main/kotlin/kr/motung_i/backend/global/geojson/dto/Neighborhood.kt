package kr.motung_i.backend.global.geojson.dto

import org.geolatte.geom.G2D
import org.geolatte.geom.MultiPolygon

data class Neighborhood (
    val name: String,
    val geometry: MultiPolygon<G2D>
)
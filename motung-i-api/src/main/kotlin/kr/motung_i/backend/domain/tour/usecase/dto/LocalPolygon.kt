package kr.motung_i.backend.domain.tour.usecase.dto

import org.geolatte.geom.G2D
import org.geolatte.geom.Polygon

data class LocalPolygon(
    val localAlias: String,
    val polygon: Polygon<G2D>,
)
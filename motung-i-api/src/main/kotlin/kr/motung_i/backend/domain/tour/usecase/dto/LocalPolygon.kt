package kr.motung_i.backend.domain.tour.usecase.dto

import kr.motung_i.backend.persistence.tour_location.entity.Local
import org.geolatte.geom.G2D
import org.geolatte.geom.Polygon

data class LocalPolygon(
    val local: Local,
    val polygon: Polygon<G2D>,
)
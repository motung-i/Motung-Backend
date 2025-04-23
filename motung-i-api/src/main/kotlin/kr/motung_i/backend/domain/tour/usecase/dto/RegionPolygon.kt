package kr.motung_i.backend.domain.tour.usecase.dto

import kr.motung_i.backend.global.geojson.dto.local.Region
import org.geolatte.geom.G2D
import org.geolatte.geom.Polygon

data class RegionPolygon(
    val region: Region,
    val polygon: Polygon<G2D>,
)
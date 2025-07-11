package kr.motung_i.backend.domain.tour.presentation.dto.response

import kr.motung_i.backend.global.util.MultiPolygonUtil.toList
import org.geolatte.geom.G2D
import org.geolatte.geom.MultiPolygon

data class GeometryResponse(
    val type: String,
    val coordinates: List<List<List<List<Double>>>>,
) {
    companion object {
        fun fromMultiPolygon(multiPolygon: MultiPolygon<G2D>): GeometryResponse = GeometryResponse(
            type = multiPolygon.geometryType.camelCased,
            coordinates = multiPolygon.toList()
        )
    }
}
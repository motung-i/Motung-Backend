package kr.motung_i.backend.global.util

import org.geolatte.geom.G2D
import org.geolatte.geom.Geometries
import org.geolatte.geom.MultiPolygon
import org.geolatte.geom.Positions
import org.geolatte.geom.crs.Geographic2DCoordinateReferenceSystem
import org.geolatte.geom.jts.JTS

object MultiPolygonUtil {
    fun MultiPolygon<G2D>.isPointIn(lon: Double, lat: Double, crs: Geographic2DCoordinateReferenceSystem): Boolean {
        val point = Positions.mkPosition(crs, lon, lat)
        val jtsPoint = JTS.to(Geometries.mkPoint(point, crs))

        return JTS.to(this).covers(jtsPoint)
    }

    fun MultiPolygon<G2D>.toList(): List<List<List<List<Double>>>> {
        return this.components().map { polygon ->
            polygon.components().map { ring ->
                ring.positions.map {
                    listOf(it.lon, it.lat)
                }
            }
        }
    }
}
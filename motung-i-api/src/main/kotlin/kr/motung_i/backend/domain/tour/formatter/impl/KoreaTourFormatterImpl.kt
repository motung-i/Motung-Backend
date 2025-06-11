package kr.motung_i.backend.domain.tour.formatter.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatter
import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.persistence.tour.entity.Country

class KoreaTourFormatterImpl : TourFormatter {
    override fun formatToTourFilterRegion(geoRegion: GeoRegion, country: Country): String {
        if (geoRegion.name.last() == '시') {
            return country.etc
        }

        return geoRegion.alias
    }

    override fun formatToTourFilterCityRegion(geoRegion: GeoRegion, country: Country): Pair<String, String> {
        return geoRegion.name.substring(2, geoRegion.name.length) to geoRegion.alias
    }

    override fun formatToTourFilterDistrict(geoDistrict: GeoDistrict): Pair<String, String> {
        val type =
            if (geoDistrict.name.last() == '구' && geoDistrict.name.contains('시')) {
                geoDistrict.name.substring(0, geoDistrict.name.lastIndexOf('시') + 1)
            } else {
                geoDistrict.name
            }.last().toString()

        return type to geoDistrict.alias
    }
}
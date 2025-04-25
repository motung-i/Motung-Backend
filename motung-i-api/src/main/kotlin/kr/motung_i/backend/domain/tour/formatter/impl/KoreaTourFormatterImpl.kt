package kr.motung_i.backend.domain.tour.formatter.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatter
import kr.motung_i.backend.global.geojson.dto.District
import kr.motung_i.backend.global.geojson.dto.Region
import kr.motung_i.backend.persistence.tour.entity.Country

class KoreaTourFormatterImpl : TourFormatter {
    override fun formatToTourFilterRegion(region: Region, country: Country): String {
        if (region.name.last() == '시') {
            return country.etc
        }

        return region.alias
    }

    override fun formatToTourFilterCityRegion(region: Region, country: Country): Pair<String, String> {
        return region.name.substring(2, region.name.length) to region.alias
    }

    override fun formatToTourFilterDistrict(district: District): Pair<String, String> {
        val type =
            if (district.name.last() == '구' && district.name.contains('시')) {
                district.name.substring(0, district.name.lastIndexOf('시') + 1)
            } else {
                district.name
            }.last().toString()

        return type to district.alias
    }
}
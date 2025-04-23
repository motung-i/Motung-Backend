package kr.motung_i.backend.domain.tour.formatter.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatter
import kr.motung_i.backend.global.geojson.dto.local.District
import kr.motung_i.backend.global.geojson.dto.local.Region

class KoreaTourFormatterImpl : TourFormatter {
    override fun formatToTourFilterRegion(region: Region): String {
        if (region.name.last() == '시') {
            return "기타"
        }

        return region.alias
    }

    override fun formatToTourFilterDistrict(district: District): Pair<Char, String> {
        val type = district.name.last()
        return type to district.alias
    }
}
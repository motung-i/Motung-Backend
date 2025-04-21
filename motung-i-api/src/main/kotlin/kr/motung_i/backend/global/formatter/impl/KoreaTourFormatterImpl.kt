package kr.motung_i.backend.global.formatter.impl

import kr.motung_i.backend.global.formatter.TourFormatter

class KoreaTourFormatterImpl : TourFormatter {
    override fun formatToTourFilterRegion(region: String): String {
        if (region.last() == '시') {
            return "기타"
        }

        return if(region.length == 4) {
            "${region[0]}${region[2]}"
        } else {
            "${region[0]}${region[1]}"
        }
    }

    override fun formatToTourFilterDistricts(district: String): Pair<Char, String> {
        var type = district.last()
        if (type == '구') {
            return '시' to district.substring(0, district.lastIndexOf('시') + 1)
        }

        return type to district
    }
}
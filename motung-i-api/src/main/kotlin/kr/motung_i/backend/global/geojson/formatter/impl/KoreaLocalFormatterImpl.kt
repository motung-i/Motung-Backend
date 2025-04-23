package kr.motung_i.backend.global.geojson.formatter.impl

import kr.motung_i.backend.global.geojson.formatter.LocalFormatter

class KoreaLocalFormatterImpl : LocalFormatter {
    override fun formatToRegionAlias(region: String): String {
        return if(region.length == 4) {
            "${region[0]}${region[2]}"
        } else {
            "${region[0]}${region[1]}"
        }
    }

    override fun formatToDistrictAlias(district: String): String {
        if (district.last() == '구') {
            return district.substring(0, district.lastIndexOf('시') + 1)
        }

        return district
    }
}
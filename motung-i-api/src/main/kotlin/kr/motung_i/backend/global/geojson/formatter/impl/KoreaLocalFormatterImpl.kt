package kr.motung_i.backend.global.geojson.formatter.impl

import kr.motung_i.backend.global.geojson.formatter.LocalFormatter

class KoreaLocalFormatterImpl : LocalFormatter {
    override fun formatToRegionAlias(region: String): String {
        return if (region.length == 4) {
            "${region[0]}${region[2]}"
        } else {
            "${region[0]}${region[1]}"
        }
    }

    override fun formatToDistrictAlias(district: String): String {
        if (district.last() == '구' && district.contains('시')) {
            return district.substring(0, district.lastIndexOf('시') + 1).dropLast(1)
        }

        return district.dropLast(1)
    }

    override fun formatToLocalAlias(local: String): String {
        val region = local.split(" ")[0]
        val district = local.split(" ")[1]
        val neighborhood = local.split(" ")[2]

        val formatDistrict =
            if (district.last() == '구' && district.contains('시')) {
                district.substring(0, district.lastIndexOf('시') + 1)
            } else {
                district
            }

        return if (region.last() == '시') {
            "$region, $neighborhood"
        } else {
            "$formatDistrict, $neighborhood"
        }
    }
}
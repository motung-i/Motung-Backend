package kr.motung_i.backend.global.formatter.impl

import kr.motung_i.backend.global.formatter.TourFormatter

class KoreaTourFormatterImpl : TourFormatter {
    override fun formatToTourFilterRegion(regionSet: Set<String>): Set<String> {
        val koreaRegionSet = regionSet.filter { it.last() != '시' }
            .map {
                if(it.length == 4) {
                    "${it[0]}${it[2]}"
                } else {
                    "${it[0]}${it[1]}"
                }
            }
            .toMutableSet()

        koreaRegionSet.add("기타")

        return koreaRegionSet
    }
}
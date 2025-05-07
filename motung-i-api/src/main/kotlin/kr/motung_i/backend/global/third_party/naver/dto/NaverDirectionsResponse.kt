package kr.motung_i.backend.global.third_party.naver.dto

data class NaverDirectionsResponse(
    val route: Route
) {
    data class Route(
        val traavoidtoll: List<Directions>,
        val traoptimal: List<Directions>,
    )

    data class Directions(
        val guide: List<Guide>,
        val path: List<List<Double>>,
        val summary: Summary,
    )

    data class Guide(
        val instructions: String,
    )

    data class Summary(
        val distance: Int,
        val duration: Int,
        val goal: Location,
        val start: Location,
        val taxiFare: Int,
        val tollFare: Int,
    )

    data class Location(
        val location: List<Double>
    )
}
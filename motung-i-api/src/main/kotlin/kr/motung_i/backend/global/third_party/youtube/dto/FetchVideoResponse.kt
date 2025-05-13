package kr.motung_i.backend.global.third_party.youtube.dto

data class FetchVideoResponse(
    val items: List<VideoItem>
) {
    data class VideoItem(
        val snippet: Snippet
    )

    data class Snippet(
        val thumbnails: Thumbnails
    )

    data class Thumbnails(
        val medium: Thumbnail
    )

    data class Thumbnail(
        val url: String
    )
}
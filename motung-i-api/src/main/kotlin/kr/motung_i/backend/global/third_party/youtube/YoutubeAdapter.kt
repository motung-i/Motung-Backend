package kr.motung_i.backend.global.third_party.youtube

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.youtube.dto.FetchVideoResponse
import kr.motung_i.backend.global.third_party.youtube.feign.YoutubeFeignClient
import org.springframework.stereotype.Service

@Service
class YoutubeAdapter(
    private val youtubeFeignClient: YoutubeFeignClient,
) {
    fun fetchVideo(url: String): FetchVideoResponse {
        val youtubeId = extractYoutubeId(url)
        return youtubeFeignClient.fetchVideos(
            part = "snippet",
            id = youtubeId,
        )
    }

    private fun extractYoutubeId(youtubeUrl: String): String {
        val match = Regex("""^(https?:\/\/)?(www\.)?(youtube\.com|youtube-nocookie\.com|youtu\.be)\/(watch\?v=|embed\/|v\/)?([\w\-]+)(\S+)?$""")
            .matchEntire(youtubeUrl)
        return match?.groups?.get(5)?.value ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC_URL)
    }
}
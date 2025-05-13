package kr.motung_i.backend.global.third_party.youtube.feign

import kr.motung_i.backend.global.third_party.youtube.configuration.YoutubeFeignConfiguration
import kr.motung_i.backend.global.third_party.youtube.dto.FetchVideoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "youtube",
    url = "https://www.googleapis.com/youtube/v3",
    configuration = [YoutubeFeignConfiguration::class],
)
interface YoutubeFeignClient {

    @GetMapping("/videos")
    fun fetchVideos(
        @RequestParam("part") part: String,
        @RequestParam("id") id: String
    ): FetchVideoResponse
}
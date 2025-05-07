package kr.motung_i.backend.global.third_party.naver.feign

import kr.motung_i.backend.global.third_party.naver.configuration.NaverFeignConfiguration
import kr.motung_i.backend.global.third_party.naver.dto.NaverDirectionsResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "naver",
    url = "https://maps.apigw.ntruss.com/map-direction/v1",
    configuration = [NaverFeignConfiguration::class],
)
interface NaverDirectionsFeignClient {

    @GetMapping("/driving")
    fun fetchDirections(
        @RequestParam("start") start: String,
        @RequestParam("goal") goal: String,
        @RequestParam("option", required = false)
        option: String = "traoptimal:traavoidtoll",
    ): NaverDirectionsResponse
}
package kr.motung_i.backend.domain.home.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping
    fun home(): ResponseEntity<String> =
        ResponseEntity.ok("ok")
}
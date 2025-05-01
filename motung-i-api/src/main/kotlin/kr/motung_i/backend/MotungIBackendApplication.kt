package kr.motung_i.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import java.util.TimeZone

@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
class MotungIBackendApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<MotungIBackendApplication>(*args)
}

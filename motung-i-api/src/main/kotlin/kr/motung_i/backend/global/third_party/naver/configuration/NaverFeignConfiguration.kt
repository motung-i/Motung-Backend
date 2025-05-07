package kr.motung_i.backend.global.third_party.naver.configuration

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import feign.RequestInterceptor
import feign.codec.Decoder
import kr.motung_i.backend.global.third_party.naver.properties.NaverProperties
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.time.format.DateTimeFormatter

class NaverFeignConfiguration(
    private val naverProperties: NaverProperties,
) {
    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor { requestTemplate ->
            requestTemplate.header("X-NCP-APIGW-API-KEY-ID", naverProperties.clientId)
            requestTemplate.header("X-NCP-APIGW-API-KEY", naverProperties.clientSecretKey)
        }

    @Bean
    fun naverFeignDecoder(): Decoder {
        val objectMapper =
            Jackson2ObjectMapperBuilder()
                .serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .serializers(LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)

        val messageConverter = MappingJackson2HttpMessageConverter(objectMapper.build())
        val converter = HttpMessageConverters(messageConverter)
        val objectFactory = ObjectFactory<HttpMessageConverters> { converter }

        return SpringDecoder(objectFactory)
    }
}
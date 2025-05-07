package kr.motung_i.backend.global.third_party.open_ai.configuration

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import feign.RequestInterceptor
import feign.codec.Encoder
import kr.motung_i.backend.global.third_party.open_ai.properties.OpenAIProperties
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.time.format.DateTimeFormatter

class OpenAiFeignConfiguration(
    private val openAIProperties: OpenAIProperties,
) {
    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor { requestTemplate ->
            requestTemplate.header("Authorization", "Bearer ${openAIProperties.apiKey}")
        }

    @Bean
    fun openAiFeignEncoder(): Encoder {
        val objectMapper =
            Jackson2ObjectMapperBuilder()
                .serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .serializers(LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)

        val messageConverter = MappingJackson2HttpMessageConverter(objectMapper.build())
        val converter = HttpMessageConverters(messageConverter)
        val objectFactory = ObjectFactory<HttpMessageConverters> { converter }

        return SpringEncoder(objectFactory)
    }
}
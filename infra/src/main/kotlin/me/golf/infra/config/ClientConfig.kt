package me.golf.infra.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class ClientConfig(
    @Value("\${payment.host}")
    private val host: String,

    @Value("\${payment.port}")
    private val port: Int,
) {

    @Bean
    fun paymentClient(): RestClient {
        val connectionTimeout = Duration.ofSeconds(DEFAULT_CONNECTION_TIMEOUT)
        val readTimeout = Duration.ofSeconds(DEFAULT_READ_TIMEOUT)

        val httpRequestFactory = SimpleClientHttpRequestFactory()
            .apply {
                setConnectTimeout(connectionTimeout.toMillis().toInt())
                setReadTimeout(readTimeout.toMillis().toInt())
            }

        return RestClient.builder()
            .baseUrl("$HTTP_PREFIX$host:$port")
            .requestFactory(httpRequestFactory)
            .defaultHeaders { it[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON_VALUE }
            .build()
    }

    companion object {
        private const val DEFAULT_CONNECTION_TIMEOUT = 3L
        private const val DEFAULT_READ_TIMEOUT = 5L
        private const val HTTP_PREFIX = "http://"
    }
}
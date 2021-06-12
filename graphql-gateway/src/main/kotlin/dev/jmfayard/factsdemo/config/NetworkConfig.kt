package dev.jmfayard.factsdemo.config

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class NetworkConfig {
    @Bean
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(2))
            .readTimeout(Duration.ofSeconds(2))
            .build()

    @Bean
    fun ktorClient(okHttpClient: OkHttpClient): HttpClient =
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = defaultSerializer()
            }
            engine {
                preconfigured = okHttpClient
            }
        }
}
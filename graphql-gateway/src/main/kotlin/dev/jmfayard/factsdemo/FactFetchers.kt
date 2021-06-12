package dev.jmfayard.factsdemo

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.concurrent.CompletionStage


@Component
class DogFactFetcher(
    val webClient: WebClient
) {
    fun get(): CompletionStage<Fact?> =
        webClient.get().uri("https://some-random-api.ml/facts/dog")
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(DogFact::class.java)
            .map {
                Fact(
                    fact = it.fact,
                    length = it.fact.length,
                    latency = calculateDuration(timestampFromEnv())
                )
            }
            .toFuture()
}

@Component
class CatFactFetcher(
    val webClient: WebClient,
    val catCircuitBreaker: CircuitBreaker
)  {
    fun get(): CompletionStage<Fact?> =
        webClient.get().uri("https://catfact.ninja/fact")
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(CatFact::class.java)
            .map {
                Fact(
                    fact = it.fact,
                    length = it.length,
                    latency = calculateDuration(timestampFromEnv())
                )
            }
            .timeout(Duration.ofMillis(2500))
            .transform(CircuitBreakerOperator.of(catCircuitBreaker))
            .toFuture()
}

data class DogFact(val fact: String)

data class CatFact(val fact: String, val length: Int)

fun calculateDuration(timestamp: Long?): Long? =
    timestamp?.let { System.currentTimeMillis() - it }

fun timestampFromEnv(): Long = 0L
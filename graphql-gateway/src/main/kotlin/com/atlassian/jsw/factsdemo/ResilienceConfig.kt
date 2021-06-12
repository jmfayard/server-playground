package com.atlassian.jsw.factsdemo

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class ResilienceConfig(
    val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    @Bean
    fun catCircuitBreaker(): CircuitBreaker {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .slidingWindow(3, 3, COUNT_BASED)
            .waitDurationInOpenState(Duration.ofMillis(5000))
            .failureRateThreshold(0.5f)
            .build()
        return circuitBreakerRegistry.circuitBreaker(
            "CatCCB",
            circuitBreakerConfig
        )
    }
}
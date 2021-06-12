package dev.jmfayard.factsdemo.config

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
    val circuitBreakerConfig = CircuitBreakerConfig.custom()
        .slidingWindow(3, 3, COUNT_BASED)
        .waitDurationInOpenState(Duration.ofMillis(5000))
        .failureRateThreshold(0.5f)
        .build()

    @Bean("catCircuitBreaker")
    fun catCircuitBreaker(): CircuitBreaker {
        return circuitBreakerRegistry.circuitBreaker(
            "CatCCB",
            circuitBreakerConfig
        )
    }

    @Bean("dogCircuitBreaker")
    fun dogCircuitBreaker(): CircuitBreaker {
        return circuitBreakerRegistry.circuitBreaker(
            "DogCCB",
            circuitBreakerConfig
        )
    }

}
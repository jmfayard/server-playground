package dev.jmfayard.factsdemo

import graphql.kickstart.tools.GraphQLQueryResolver
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import io.ktor.client.*
import io.ktor.client.request.*
import org.springframework.stereotype.Component


@Component
class DogFactQueryResolver(
    val ktorClient: HttpClient,
    val dogCircuitBreaker: CircuitBreaker
) : GraphQLQueryResolver {

    suspend fun dog(): Fact {
        val start = System.currentTimeMillis()

        val catFact = dogCircuitBreaker.executeSuspendFunction {
            ktorClient.get<DogFact>(DOG_FACT_URL)
        }
        return Fact(
            fact = catFact.fact,
            length = catFact.fact.length,
            latency = calculateDuration(start)
        )
    }
}

@Component
class CatFactQueryResolver(
    val ktorClient: HttpClient,
    val catCircuitBreaker: CircuitBreaker
) : GraphQLQueryResolver {
    suspend fun cat(): Fact {
        val start = System.currentTimeMillis()
        val catFact = catCircuitBreaker.executeSuspendFunction  {
            ktorClient.get<CatFact>(CAT_FACT_URLS)
        }
        return Fact(
            fact = catFact.fact,
            length = catFact.length,
            latency = calculateDuration(start)
        )
    }
}


fun calculateDuration(timestamp: Long): Int =
    (System.currentTimeMillis() - timestamp).toInt()

package dev.jmfayard.factsdemo

import graphql.kickstart.tools.GraphQLQueryResolver
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import io.ktor.client.*
import io.ktor.client.request.*
import org.springframework.stereotype.Component

@Component
class AnimalsQueryResolver(
    val animalsRepository: AnimalsRepository
) : GraphQLQueryResolver {

    suspend fun dog(): Fact = animalsRepository.dog()

    suspend fun cat(): Fact = animalsRepository.cat()
}


@Component
class AnimalsRepository(
    val ktorClient: HttpClient,
    val dogCircuitBreaker: CircuitBreaker,
    val catCircuitBreaker: CircuitBreaker
) {
    suspend fun dog(): Fact {
        val start = System.currentTimeMillis()

        val dogFact = dogCircuitBreaker.executeSuspendFunction {
            ktorClient.get<DogFact>(DOG_FACT_URL)
        }
        return Fact(
            fact = dogFact.fact,
            length = dogFact.fact.length,
            latency = (System.currentTimeMillis() - start).toInt()
        )
    }

    suspend fun cat(): Fact {
        val start = System.currentTimeMillis()
        val catFact = catCircuitBreaker.executeSuspendFunction  {
            ktorClient.get<CatFact>(CAT_FACT_URLS)
        }
        return Fact(
            fact = catFact.fact,
            length = catFact.length,
            latency = (System.currentTimeMillis() - start).toInt()
        )
    }
}




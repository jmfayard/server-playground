package dev.jmfayard.factsdemo


import graphql.kickstart.tools.SchemaParser
import graphql.kickstart.tools.SchemaParser.Companion.newParser
import graphql.kickstart.tools.SchemaParserOptions
import graphql.scalar.GraphqlLongCoercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class GraphQLConfig {
    @Bean
    fun graphQLSchema(
        webClient: WebClient, catCircuitBreaker: CircuitBreaker, options: SchemaParserOptions
    ): GraphQLSchema {
        return SchemaParser.newParser()
            .options(options)
            .file("graphql/schema.graphqls")
            .resolvers(
                CatFactFetcher(webClient, catCircuitBreaker),
                DogFactFetcher(webClient)
            )
            .scalars(scalars())
            .build()
            .makeExecutableSchema()
    }

    fun scalars(): List<GraphQLScalarType> = listOf(
        GraphQLScalarType.newScalar().name("Long").coercing(GraphqlLongCoercing()).build()
    )

    @Bean
    fun buildOptions(): SchemaParserOptions = SchemaParserOptions.defaultOptions()


}
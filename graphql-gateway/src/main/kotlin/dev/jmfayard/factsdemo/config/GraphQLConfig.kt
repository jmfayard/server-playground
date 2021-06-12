package dev.jmfayard.factsdemo.config


import dev.jmfayard.factsdemo.CatFactQueryResolver
import dev.jmfayard.factsdemo.DogFactResolver
import graphql.kickstart.tools.SchemaParser
import graphql.kickstart.tools.SchemaParserOptions
import graphql.scalar.GraphqlLongCoercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.ktor.client.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GraphQLConfig {
    @Bean
    fun graphQLSchema(
        httpClient: HttpClient, catCircuitBreaker: CircuitBreaker, options: SchemaParserOptions
    ): GraphQLSchema {
        return SchemaParser.newParser()
            .options(options)
            .file("graphql/schema.graphqls")
            .resolvers(
                CatFactQueryResolver(httpClient, catCircuitBreaker),
                DogFactResolver(httpClient)
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
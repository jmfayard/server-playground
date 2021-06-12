package dev.jmfayard.factsdemo.config


import dev.jmfayard.factsdemo.CatFactQueryResolver
import dev.jmfayard.factsdemo.DogFactQueryResolver
import graphql.kickstart.tools.SchemaParser
import graphql.kickstart.tools.SchemaParserOptions
import graphql.schema.GraphQLSchema
import io.ktor.client.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GraphQLConfig {
    @Bean
    fun graphQLSchema(
        httpClient: HttpClient,
        catFactQueryResolver: CatFactQueryResolver,
        dogFactQueryResolver: DogFactQueryResolver,
    ): GraphQLSchema {
        return SchemaParser.newParser()
            .file("graphql/schema.graphqls")
            .resolvers(catFactQueryResolver, dogFactQueryResolver)
            .build()
            .makeExecutableSchema()
    }

}
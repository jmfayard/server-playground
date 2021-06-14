package dev.jmfayard.factsdemo.config


import dev.jmfayard.factsdemo.AnimalsQueryResolver
import graphql.kickstart.tools.SchemaParser
import graphql.schema.GraphQLSchema
import io.ktor.client.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GraphQLConfig {
    @Bean
    fun graphQLSchema(
        httpClient: HttpClient,
        animalsQueryResolver: AnimalsQueryResolver
    ): GraphQLSchema {
        return SchemaParser.newParser()
            .file("graphql/schema.graphqls")
            .resolvers(animalsQueryResolver)
            .build()
            .makeExecutableSchema()
    }

}
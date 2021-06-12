package com.atlassian.jsw.factsdemo

import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphqlConfig {

    @Bean
    fun factsWiring(
        dogFactFetcher: DogFactFetcher,
        catFactFetcher: CatFactFetcher
    ): RuntimeWiring =
        RuntimeWiring.newRuntimeWiring()
            .type(
                newTypeWiring("Query")
                    .dataFetcher("dog", dogFactFetcher)
                    .dataFetcher("cat", catFactFetcher)
            )
            .build()

    @Bean
    fun graphQLSchema(wiring: RuntimeWiring): GraphQLSchema =
        SchemaGenerator().makeExecutableSchema(
            parseSchema(),
            wiring
        )

    companion object {
        private fun parseSchema() =
            GraphqlConfig::class.java.getResource("/graphql/schema.graphqls")
                .openStream().bufferedReader().use {
                    SchemaParser().parse(it)
                }
    }
}

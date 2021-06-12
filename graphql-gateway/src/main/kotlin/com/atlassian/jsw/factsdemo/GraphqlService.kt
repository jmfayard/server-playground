package com.atlassian.jsw.factsdemo

import graphql.ExecutionResult
import graphql.schema.GraphQLSchema
import reactor.core.publisher.Mono

interface GraphqlService {

    fun processQuery(
        query: String
    ): Mono<ExecutionResult>

    fun getSchema(): Mono<GraphQLSchema>
}

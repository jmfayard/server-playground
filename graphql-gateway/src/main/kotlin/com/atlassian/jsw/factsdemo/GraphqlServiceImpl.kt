package com.atlassian.jsw.factsdemo

import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GraphqlServiceImpl(val graphQLSchema: GraphQLSchema) : GraphqlService {
    override fun processQuery(
        query: String
    ): Mono<ExecutionResult> = Mono.subscriberContext().flatMap { sc ->
        Mono.fromFuture(
            GraphQL
                .newGraphQL(graphQLSchema)
                .build()
                .executeAsync(
                    ExecutionInput.newExecutionInput()
                        .query(query)
                        .context(sc.get("timestamp"))
                        .build()
                )
        )
    }

    override fun getSchema(): Mono<GraphQLSchema> {
        return Mono.just(graphQLSchema)
    }
}

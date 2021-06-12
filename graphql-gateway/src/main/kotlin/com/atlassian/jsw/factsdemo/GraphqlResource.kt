package com.atlassian.jsw.factsdemo

import graphql.schema.idl.SchemaPrinter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Component
@RestController
class GraphqlResource(val graphQLService: GraphqlService) {

    private val schemaPrinter = SchemaPrinter(
        SchemaPrinter.Options.defaultOptions()
            .includeExtendedScalarTypes(true)
            .includeIntrospectionTypes(true)
            .includeScalarTypes(true)
    )

    @PostMapping(
        path = ["/graphql"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun graphql(
        @RequestBody body: Map<String, Any>
    ): Mono<Map<String, Any>> {
        val query: String = body["query"] as String? ?: "{}"
        val timestamp = System.currentTimeMillis()
        return graphQLService.processQuery(query)
            .map { it.toSpecification() }
            .subscriberContext {
                it.put("timestamp", timestamp)
            }
            .doOnSuccess {
                log.info("Took ${System.currentTimeMillis() - timestamp}ms.")
            }
    }

    @GetMapping("/graphql-schema")
    fun graphQLSchema(): Mono<String> {
        return graphQLService.getSchema().map { schemaPrinter.print(it) }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(GraphqlResource::class.java)
    }
}

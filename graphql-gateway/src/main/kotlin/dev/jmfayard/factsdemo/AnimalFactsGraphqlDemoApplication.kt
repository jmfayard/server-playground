package dev.jmfayard.factsdemo

import graphql.kickstart.tools.SchemaParser
import graphql.kickstart.tools.SchemaParserOptions
import graphql.schema.GraphQLSchema
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.coroutines.CoroutineContext

@SpringBootApplication
class AnimalFactsGraphqlDemoApplication

fun main(args: Array<String>) {
    runApplication<AnimalFactsGraphqlDemoApplication>(*args)
}


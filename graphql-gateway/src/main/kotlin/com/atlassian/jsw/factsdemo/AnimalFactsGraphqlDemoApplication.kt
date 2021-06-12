package com.atlassian.jsw.factsdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnimalFactsGraphqlDemoApplication

fun main(args: Array<String>) {
    runApplication<AnimalFactsGraphqlDemoApplication>(*args)
}

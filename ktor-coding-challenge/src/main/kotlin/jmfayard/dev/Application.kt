package jmfayard.dev

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jmfayard.dev.api.Sessions
import jmfayard.dev.api.Users
import jmfayard.dev.plugins.configureMonitoring
import jmfayard.dev.plugins.configureRouting
import jmfayard.dev.plugins.configureTemplating
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
    }
    configureRouting()
    configureMonitoring()
    configureTemplating()
    configureDatabase()

}

fun configureDatabase() {
    Database.connect("jdbc:h2:mem:users")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users, Sessions)
    }
}

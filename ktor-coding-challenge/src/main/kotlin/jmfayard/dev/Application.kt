package jmfayard.dev

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jmfayard.dev.plugins.configureMonitoring
import jmfayard.dev.plugins.configureRouting
import jmfayard.dev.plugins.configureTemplating

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
    configureMonitoring()
    configureTemplating()

}

package jmfayard.dev

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jmfayard.dev.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
    }.start(wait = true)
}

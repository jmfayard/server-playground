package jmfayard.dev.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import jmfayard.dev.api.dto.HealthCheck
import jmfayard.dev.api.dto.easterEgg
import jmfayard.dev.api.dto.timestamp
import jmfayard.dev.api.github
import jmfayard.dev.api.users
import kotlinx.datetime.Clock

fun Application.configureRouting() {

    install(Locations) {
    }

    routing {
        heathcheck()
        users()
        github()

        get("/api/timemachine/logs/mcfly") {
            call.respond(easterEgg())
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

fun Routing.heathcheck() {
    get("/api/healthcheck") {
        call.respond(HealthCheck("github-api", "1.0", timestamp()))
    }
}

object DI {
    var clock: Clock = Clock.System
}


class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

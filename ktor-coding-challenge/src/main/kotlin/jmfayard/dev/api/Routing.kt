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


@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

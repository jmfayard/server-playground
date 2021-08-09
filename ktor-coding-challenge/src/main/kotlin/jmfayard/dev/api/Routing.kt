package jmfayard.dev.plugins

import io.ktor.application.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jmfayard.dev.api.UserDaoInstance
import jmfayard.dev.api.dto.*
import jmfayard.dev.openlibrary.ktorClient

fun Application.configureRouting() {
    install(Locations) {
    }



    routing {
        route("/api") {
            get("healthcheck") {
                call.respond(HealthCheck("github-api", "1.0", timestamp()))
            }
            get("timemachine/logs/mcfly") {
                call.respond(easterEgg())
            }
        }
        route("/api/users") {
            post("/register") {
                val usernamePassword = call.receive<UsernamePassword>()
                val usernameToken = UserDaoInstance.createUser(usernamePassword)
                if (usernameToken == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                } else {
                    call.respond(usernameToken)
                }
            }

            get {
                call.respond(UserDaoInstance.listRegisteredUsers())
            }

            post("login") {
                val token = call.receive<Token>()
                val username = UserDaoInstance.userByToken(token)
                if (username == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                } else {
                    call.respond(username)
                }
            }

            post("me") {
                val token = call.receive<Token>()
                val usernameToken = UserDaoInstance.userByToken(token)
                if (usernameToken == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                } else {
                    call.respond(usernameToken)
                }
            }
        }

        route("api/github") {
            get("feed") {
                val stream = ktorClient.get<String>("https://api.github.com/users/jmfayard/events?page=1&per_page=1") {
                    header("Accept", "application/vnd.github.v3+json")
                }
                call.respondText(stream)
            }
        }

        get("/") {
            call.respondText("Hello World!")
        }
        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
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

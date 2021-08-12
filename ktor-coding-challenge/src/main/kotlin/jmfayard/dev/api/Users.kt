package jmfayard.dev.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jmfayard.dev.api.dto.Token
import jmfayard.dev.api.dto.UsernamePassword

fun Routing.users() {
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
}

package jmfayard.dev.api

import io.ktor.application.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import jmfayard.dev.openlibrary.ktorClient

fun Routing.github() {
    route("api/github") {
        get("feed") {
            try {
                val stream =
                    ktorClient.get<String>("https://api.github.com/users/jmfayard/events?page=1&per_page=1") {
                        header("Accept", "application/vnd.github.v3+json")
                    }
                call.respondText(stream)
            } catch (e: Throwable) {
                System.err.println("Exception $e while fetching the feed")
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Cannot fetch the feed"))
            }

        }

        get("users/{actor}") {
            val actor = call.parameters["actor"]
            try {
                val user = ktorClient.get<GithubUser>("https://api.github.com/users/$actor") {
                    header("Accept", "application/vnd.github.v3+json")
                }
                call.respond(user.toUserInformation())
            } catch (e: Throwable) {
                System.err.println("Exception $e while fetching user informations for $actor")
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "User $actor not found"))
            }

        }
    }
}

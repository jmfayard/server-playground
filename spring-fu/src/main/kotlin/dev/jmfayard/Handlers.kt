package dev.jmfayard

import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse
import java.security.Principal


@Suppress("UNUSED_PARAMETER")
class UserHandler(
    private val repository: UserRepository,
    private val configuration: SampleProperties
) {

    suspend fun listApi(request: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait<User>(repository.findAll())

    suspend fun userApi(request: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValueAndAwait(repository.findOne(request.pathVariable("login")))

    suspend fun listView(request: ServerRequest) =
            ok().renderAndAwait("users", mapOf("users" to repository.findAll()))

    suspend fun conf(request: ServerRequest) =
            ok().bodyValueAndAwait(configuration.message)
}
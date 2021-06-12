package dev.jmfayard

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody

class HttpBinHandler(
    val objectMapper: ObjectMapper
) {
    val treeToMap = object : TypeReference<Map<String, String>>() {}

    suspend fun get(request: ServerRequest): ServerResponse =
        request.sendHttpBinResponse()

    suspend fun post(request: ServerRequest): ServerResponse =
        request.sendHttpBinResponse {
            data = request.awaitBody()
            val node = jacksonObjectMapper().readTree(data!!) as ObjectNode
            json = node.fieldNames().asSequence().associate { it to node.get(it).textValue() }
        }
}

data class HttpBinError(
    val request: String,
    val message: String,
    val code: HttpStatus,
    val cause: Throwable? = null
)
typealias Parameters = Map<String, Any?>

class HttpBinResponse(
    var args: Parameters? = null,
    var headers: Map<String, List<String>>? = null,
    var origin: String? = null,
    var url: String? = null,
    var `user-agent`: String? = null,
    var data: String? = null,
    //var files: Map<String, PartData.FileItem>? = null,
    var form: Parameters? = null,
    var json: Map<String, String>? = null,
    var gzipped: Boolean? = null,
    var deflated: Boolean? = null,
    var method: String? = null,
    var cookies: Map<String, String>? = null
)

fun HttpBinResponse.clear() {
    args = null
    headers = null
    url = null
    origin = null
    method = null
}


/**
 * By default send what is expected for /get
 * Use a lambda to customize the response
 **/
suspend fun ServerRequest.sendHttpBinResponse(configure: suspend HttpBinResponse.() -> Unit = {}): ServerResponse {
    val response = HttpBinResponse(
        args = queryParams(),
        url = uri().toString(),
        origin = this.headers().firstHeader("Origin"),
        method = methodName(),
        headers = headers().asHttpHeaders(),
        `user-agent` = headers().firstHeader("User-Agent")
    )
    response.configure()
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response).awaitFirst()
}




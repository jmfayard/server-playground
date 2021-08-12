package jmfayard.dev.util

import okhttp3.mockwebserver.MockResponse
import java.io.File

fun jsonMockResponse(path: String, status: Int = 200, builder: MockResponse.() -> Unit = {}): MockResponse {
    val json = textResource(path)
    return MockResponse()
        .setResponseCode(status)
        .setHeader("Content-Type", "application/json")
        .setBody(json)
        .apply(builder)
}

fun textResource(path: String): String {
    val testResources: File = File(".").absoluteFile.resolve("src/test/resources")
    return testResources.resolve(path).readText()
}
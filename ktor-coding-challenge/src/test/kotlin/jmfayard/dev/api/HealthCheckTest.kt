package jmfayard.dev.api

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.ktor.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import jmfayard.dev.module
import jmfayard.dev.plugins.DI
import jmfayard.dev.util.textResource
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class HealthCheckTest : StringSpec({

    "healthcheck" {
        DI.clock = object : Clock {
            override fun now(): Instant = Instant.fromEpochMilliseconds(1628769658995)
        }
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/healthcheck").apply {
                // https://kotest.io/docs/assertions/ktor-matchers.html
                response shouldHaveStatus HttpStatusCode.OK
                response.content?.shouldEqualJson(
                    """
                                |{
                                |  "name": "github-api",
                                |  "version": "1.0",
                                |  "time": 1628769658995
                                |}
                            """.trimMargin()
                )
            }
        }
    }

    "Easter egg" {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/timemachine/logs/mcfly").apply {
                response shouldHaveStatus HttpStatusCode.OK
                response.content?.shouldEqualJson(textResource("json/easter-egg.json"))
            }
        }
    }
})
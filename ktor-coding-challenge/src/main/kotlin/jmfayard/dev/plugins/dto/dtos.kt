package jmfayard.dev.plugins.dto

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class HealthCheck(val name: String, val version: String, val time: Long)

fun timestamp() = Clock.System.now().toEpochMilliseconds()

fun easterEgg(): List<HealthCheck> = listOf(
    HealthCheck("My mom is in love with me", "1.0", -446723100),
    HealthCheck("I go to the future and my mom end up with the wrong guy", "2.0", 1445470140),
    HealthCheck("I go to the past and you will not believe what happens next", "3.0", Long.MIN_VALUE),
)

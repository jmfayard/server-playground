package jmfayard.dev.plugins.dto

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class HealthCheck(val name: String, val version: String, val time: Long)

fun timestamp() = Clock.System.now().toEpochMilliseconds()
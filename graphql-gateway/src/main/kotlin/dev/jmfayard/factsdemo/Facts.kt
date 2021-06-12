package dev.jmfayard.factsdemo

import kotlinx.serialization.Serializable

const val DOG_FACT_URL = "https://some-random-api.ml/facts/dog"

@Serializable
data class DogFact(
    val fact: String
)

const val CAT_FACT_URLS = "https://catfact.ninja/fact"

@Serializable
data class CatFact(
    val fact: String,
    val length: Int
)

data class Fact(
    val fact: String,
    val length: Int,
    val latency: Int?
)
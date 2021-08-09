 package jmfayard.dev.openlibrary

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

 val okHttpClient = OkHttpClient.Builder()
     .addNetworkInterceptor(
         HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
     )
     .build()
 val ktorClient = HttpClient(OkHttp) {
     install(JsonFeature) {
         serializer = KotlinxSerializer(
             kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
         )
     }
     engine {
         preconfigured = okHttpClient
     }
 }
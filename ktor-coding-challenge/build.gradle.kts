plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "jmfayard.dev"
version = "0.0.1"
application {
    mainClass.set("jmfayard.dev.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation( "io.ktor:ktor-server-core:_")
    implementation("io.ktor:ktor-locations:_")
    implementation("io.ktor:ktor-server-host-common:_")
    implementation("io.ktor:ktor-mustache:_")
    implementation(Ktor.features.serialization)
    implementation(Ktor.client.serialization)
    implementation(Ktor.server.netty)
    implementation(Ktor.client.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:_")
    implementation("ch.qos.logback:logback-classic:_")

    implementation("org.jetbrains.exposed:exposed-core:_")
    implementation("org.jetbrains.exposed:exposed-dao:_")
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.jetbrains.exposed:exposed-jdbc:_")
    implementation("com.h2database:h2:_")

    testImplementation("io.ktor:ktor-server-tests:_")
    testImplementation("org.jetbrains.kotlin:kotlin-test:_")
    testImplementation(Testing.kotest.assertions.ktor)
    testImplementation(Testing.kotest.assertions.json)
    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Square.okHttp3.mockWebServer)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
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
    implementation("io.ktor:ktor-server-core:_")
    implementation("io.ktor:ktor-locations:_")
    implementation("io.ktor:ktor-server-host-common:_")
    implementation("io.ktor:ktor-mustache:_")
    implementation("io.ktor:ktor-serialization:_")
    implementation("io.ktor:ktor-server-netty:_")
    implementation("ch.qos.logback:logback-classic:_")
    testImplementation("io.ktor:ktor-server-tests:_")
    testImplementation("org.jetbrains.kotlin:kotlin-test:_")
}
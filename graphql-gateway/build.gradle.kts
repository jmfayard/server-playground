import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

group = "dev.jmfayard"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.graphql-java-kickstart:graphql-java-tools:_")
	implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:_")
	implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:_")
	implementation("com.graphql-java-kickstart:graphql-java-tools:_")

	implementation("io.github.resilience4j:resilience4j-reactor:_")
	implementation("io.github.resilience4j:resilience4j-spring-boot2:_")
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:_")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation(Ktor.client.core)
	implementation(Ktor.client.okHttp)
	implementation(Ktor.client.serialization)
	implementation(KotlinX.serialization.core)

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

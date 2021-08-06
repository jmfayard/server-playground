pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}
plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.11.1-SNAPSHOT"
}

rootProject.name = "ktor-testing"
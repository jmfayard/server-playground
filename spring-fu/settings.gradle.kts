pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}
plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.10.1-SNAPSHOT"
////                            # available:"0.10.1"
////                            # available:"0.10.2-SNAPSHOT"
}

rootProject.name = "spring-fu"

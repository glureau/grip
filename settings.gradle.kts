rootProject.name = "grip"
pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("plugin.serialization") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

include(":plugin")
include(":sample")

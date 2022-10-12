buildscript {
    val kotlinVersion: String by project
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

val localProperties = java.util.Properties().apply {
    val file = File(rootProject.rootDir, "local.properties")
    if (file.exists()) {
        load(java.io.FileInputStream(file))
    }
}

plugins {
    id("maven-publish")
    id("org.ajoberstar.git-publish") version "3.0.1"
    id("org.ajoberstar.grgit") version "4.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

group = "com.glureau.grip"
version = "0.2.0"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
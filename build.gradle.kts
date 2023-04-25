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

allprojects {
    group = "com.glureau.grip"
    version = "0.4.5"
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
plugins {
    kotlin("multiplatform")
    id("com.glureau.grip") version "0.2.0"
}
version= "1.2.3"
kotlin {
    jvm()
    sourceSets {
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

grip {
    replaceInPlace = true
}
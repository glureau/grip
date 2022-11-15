plugins {
    kotlin("multiplatform")
    id("com.glureau.grip") version "0.4.3"
}
version= "1.2.3"
kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
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
    files = fileTree(projectDir) {
        include("README.md")
    }
}
tasks["grip"].dependsOn("jvmJar")
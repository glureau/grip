import java.net.URI

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.15.0"
    `java-gradle-plugin`
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("11"))
    }
}

dependencies {
    compileOnly(gradleApi())
}

pluginBundle {
    website = "https://github.com/glureau/grip"
    vcsUrl = "https://github.com/glureau/grip"
    tags = listOf("replace", "grip", "swap", "markdown", "documentation")
}

gradlePlugin {
    plugins {
        create("grip") {
            // This is a fully-qualified plugin id, short id of 'kotlinx-knit' is added manually in resources
            id = "com.glureau.grip"
            implementationClass = "com.glureau.grip.GripPlugin"
            displayName = "Replacement In-Place and file automation tools"
            description = "Update dynamically your files! Add a directive in your documentation and automatically include your library version, or another gradle variable, or a system environment variable, or even a file."
            implementationClass = "com.glureau.grip.GripPlugin"
        }
    }
}

/*
publishing {
    repositories {
        if (localProperties.getProperty("REPOSITORY_URL") != null) {
            maven {
                url = uri(localProperties.getProperty("REPOSITORY_URL"))
                credentials {
                    username = localProperties.getProperty("REPOSITORY_USERNAME")
                    password = localProperties.getProperty("REPOSITORY_PASSWORD")
                }
            }
        }
    }
}

val gitUser = System.getenv("GIT_USER")
val gitPassword = System.getenv("GIT_PASSWORD")
if (gitUser != null && gitPassword != null) {
    System.setProperty("org.ajoberstar.grgit.auth.username", gitUser)
    System.setProperty("org.ajoberstar.grgit.auth.password", gitPassword)
}

tasks.create<Delete>("cleanMavenLocalArtifacts") {
    delete = setOf("$buildDir/mvn-repo/")
}

tasks.create<Sync>("copyMavenLocalArtifacts") {
    group = "publishing"
    dependsOn(":compiler:publishToMavenLocal", ":lib:publishToMavenLocal")

    val userHome = System.getProperty("user.home")
    val groupDir = project.group.toString().replace('.', '/')
    val localRepository = "$userHome/.m2/repository/$groupDir/"

    from(localRepository) {
        include("* /${ project.version } // remove space if uncommenting the area
/**")
}

into("$buildDir/mvn-repo/$groupDir/")
}

gitPublish {
repoUri.set("git@github.com:glureau/grip.git")
branch.set("mvn-repo")
contents.from("$buildDir/mvn-repo")
preserve { include("**") }
val head = grgit.head()
commitMessage.set("${head.abbreviatedId}: ${project.version} : ${head.fullMessage}")
}
tasks["copyMavenLocalArtifacts"].dependsOn("cleanMavenLocalArtifacts")
tasks["gitPublishCopy"].dependsOn("copyMavenLocalArtifacts")

 */
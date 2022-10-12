package com.glureau.grip

import org.gradle.api.Project
import java.text.SimpleDateFormat
import java.util.*

fun directives(project: Project) = listOf<Directive>(
    Directive("INSERT") { params ->
        var filesContent = ""
        val fileTree = project.fileTree(project.projectDir)
        fileTree.include(params[0])

        fileTree.files.forEach {
            println(" - file = $it")
            filesContent += "\n" + it.readText()
        }

        filesContent
    },
    Directive("GRADLE_PROPERTIES") { params -> project.properties[params[0]].toString() },
    Directive("SYSTEM_ENV") { params -> System.getenv(params[0]) },
    Directive("DATETIME") { params -> SimpleDateFormat(params[0]).format(Calendar.getInstance().time) },
)
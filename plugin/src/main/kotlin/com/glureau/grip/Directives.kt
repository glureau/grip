package com.glureau.grip

import org.gradle.api.Project
import java.text.SimpleDateFormat
import java.util.*

val paramsMatcher = Regex("([^\"]\\S*|\".+?\")\\s*", RegexOption.DOT_MATCHES_ALL)

fun directives(project: Project) = listOf<Directive>(
    Directive("INSERT") { params ->
        var filesContent = ""
        val fileTree = project.fileTree(project.projectDir)
        fileTree.include(params)

        fileTree.files
            .sortedBy { it.path.substringBeforeLast(".") }
            .forEach {
                println(" - file = $it")
                filesContent += "\n" + it.readText()
            }

        filesContent
    },
    Directive("INSERT_DIRECTORIES") { paramStr ->
        val params = paramsMatcher.findAll(paramStr).toList()

        var filesContent = ""
        val fileTree = project.fileTree(project.projectDir)
        fileTree.include(params[0].value.trim())

        fileTree.files
            .sortedBy { it.path.substringBeforeLast(".") }
            .forEach {
                println(" - file = $it")
                val header = params[1].value.removeSurrounding("\"")
                    .replace("%FILE%", it.nameWithoutExtension)
                    .replace("%LASTDIR%", it.parentFile.name)
                    .replace("%LASTLASTDIR%", it.parentFile.parentFile.name)
                filesContent += "\n" + header + it.readText()
            }

        filesContent
    },
    Directive("GRADLE_PROPERTIES") { params -> project.properties[params].toString() },
    Directive("SYSTEM_ENV") { params -> System.getenv(params) },
    Directive("DATETIME") { params -> SimpleDateFormat(params).format(Calendar.getInstance().time) },
)
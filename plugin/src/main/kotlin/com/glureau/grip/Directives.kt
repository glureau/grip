package com.glureau.grip

import org.gradle.api.Project
import java.io.File
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

        val dirPrinted = mutableSetOf<File>()
        val filePrinted = mutableSetOf<File>()
        fileTree.files
            .sortedBy { it.path.substringBeforeLast(".") }
            .forEach {
                println(" - file = $it")
                filesContent += "\n"
                if (!dirPrinted.contains(it.parentFile)) {
                    dirPrinted += it.parentFile
                    filesContent += (params.getOrNull(1)?.value ?: "")
                        .trim()
                        .removeSurrounding("\"")
                        .replace("%LASTDIR%", it.parentFile.name)
                }
                if (!filePrinted.contains(it)) {
                    filePrinted += it
                    filesContent += (params.getOrNull(2)?.value ?: "")
                        .trim()
                        .removeSurrounding("\"")
                        .replace("%FILE%", it.nameWithoutExtension)
                }
                filesContent += it.readText()
            }

        filesContent
    },
    Directive("GRADLE_PROPERTIES") { params -> project.properties[params].toString() },
    Directive("SYSTEM_ENV") { params -> System.getenv(params) },
    Directive("DATETIME") { params -> SimpleDateFormat(params).format(Calendar.getInstance().time) },
)
package com.glureau.grip

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GripTask : DefaultTask() {
    @OutputDirectory
    abstract fun getGeneratedFileDir(): RegularFileProperty

    init {
        val generatedDir = File(project.buildDir.path + "/grip/")
        getGeneratedFileDir().set(generatedDir)
    }

    @TaskAction
    fun execute() {
        val ext = project.extensions.getByType(GripExtension::class.java)


        /**
         * Usually syntax of a directive is defined by :
         * <!--$ COMMAND some params -->
         * <!--$ END -->
         */
        val filesToParse = ext.files ?: project.fileTree(project.projectDir).apply {
            // By default, we pick only markdowns on the src directory, but there is really no limitation
            include("**/**.md")
            // Avoid re-loading the generated files
            exclude("**/grip/**")
        }

        filesToParse.files
            // Sort files so that we start with the more detailed "packages" and can include child modules in parent modules
            .sortedByDescending { it.path.count(File.separatorChar::equals) }
            .forEach { file ->
                println("Parsing $file")
                val (tokenStart, tokenEnd) = getTokensFromFile(file)
                val fileContent = file.readText()
                val allMatches = tokenStart.findAll(fileContent).toList()

                val updatedContent = if (allMatches.isEmpty()) {
                    fileContent
                } else {
                    updatedContent(fileContent, allMatches, tokenEnd, ext)
                }

                val toFile = if (ext.replaceInPlace) file else {
                    val target =
                        File(getGeneratedFileDir().get().asFile.path + file.path.substringAfter(project.projectDir.path))
                    target.parentFile.mkdirs()
                    target
                }
                toFile.writeText(updatedContent)
                println("Write on $toFile ! ${toFile.exists()}")
            }
    }

    private fun updatedContent(
        fileContent: String,
        allMatches: List<MatchResult>,
        tokenEnd: Regex,
        ext: GripExtension,
    ): String {
        var updatedContent = fileContent.substring(0, allMatches.first().range.first)
        val directives = directives(project)
        allMatches.forEachIndexed { index, startMatch ->
            val directiveWithParams = startMatch.groupValues[1]
            val endMatch = tokenEnd.find(fileContent, startIndex = startMatch.range.last) ?: error("boom")
            val endIndex = endMatch.range.first
            val oldContent = fileContent.substring(startMatch.range.last + 1, endIndex)

            if (ext.replaceInPlace)
                updatedContent += startMatch.groupValues[0]
            val directiveKey = directiveWithParams.substringBefore(" ")
            val params = directiveWithParams.substringAfter(" ").trim()
            directives.firstOrNull { it.key == directiveKey }.let { d ->
                if (d == null) {
                    println("Unknown directive '$directiveKey' ($directiveWithParams)")
                    println("  Available directives: " + directives.joinToString { it.key })
                    updatedContent += oldContent
                } else {
                    println("Execute directive $directiveWithParams")
                    // Split by " " is a bit tricky, may be specific to the current Directive instead
                    updatedContent += d.action(params)
                }
            }

            if (ext.replaceInPlace) {
                updatedContent += fileContent.substring(endMatch.range)
            }
            updatedContent += fileContent.substring(
                endMatch.range.last + 1,
                allMatches.getOrNull(index + 1)?.range?.start ?: fileContent.length
            )
        }
        return updatedContent
    }

    private fun getTokensFromFile(file: File): Pair<Regex, Regex> {
        // Structure to handle tokens based on file extension
        val markdownTokens = Regex("<!--\\$ (.*?)-->", RegexOption.DOT_MATCHES_ALL) to Regex("<!-- END \\$-->")
        return when {
            file.endsWith(".md") -> markdownTokens
            else -> markdownTokens
        }
    }
}
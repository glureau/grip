package com.glureau.grip

import org.gradle.api.Plugin
import org.gradle.api.Project

// TODO: Name this project ? Like K2D for "Kotlin to Documentation"? (not related to current file, general todo)
// TODO: Rename that for InPlaceReplace? It's not actually limited to Markdown...
class GripPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("grip", GripExtension::class.java)
        target.tasks.create("grip", GripTask::class.java) {
            it.description = "Replace content from grip directives"
            it.group = "documentation"
        }
    }
}


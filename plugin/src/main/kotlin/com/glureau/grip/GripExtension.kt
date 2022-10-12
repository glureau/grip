package com.glureau.grip

import org.gradle.api.file.FileCollection

open class GripExtension {
    var replaceInPlace: Boolean = true
    var files: FileCollection? = null
}
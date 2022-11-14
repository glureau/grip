package com.glureau.grip

data class Directive(val key: String, val action: (params: String) -> String)
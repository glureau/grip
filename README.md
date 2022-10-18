# GRIP (Gradle Replace In-Place)

A gradle tool to update some values in your (documentation) files by running a task.

(inspired by [Knit](https://github.com/Kotlin/kotlinx-knit))

## Directives

### Insert a gradle properties: GRADLE_PROPERTIES
```markdown
<!--$ GRADLE_PROPERTIES version --><!-- END $-->
```

Take 1 parameter that must be the name of the gradle properties on your project.


### Insert a system environment variable: SYSTEM_ENV
```markdown
<!--$ SYSTEM_ENV LOGNAME --><!-- END $-->
```
Take 1 parameter that must be the name of one of the environment variable on the computer running grip.


### Insert a local file
```markdown
<!--$ INSERT src/commonMain/kotlin/com/glureau/grip/sample/doc.md --><!-- END $-->
```
Take 1 parameter that uses the [gradle file pattern](https://docs.gradle.org/current/javadoc/org/gradle/api/tasks/util/PatternFilterable.html).

For example, the previous INSERT could be extended to:

```markdown
<!--$ INSERT src/**/sample/*.md --><!-- END $-->
```

### Insert a date/time: DATETIME
```markdown
<!--$ DATETIME yyyy.MM.dd G 'at' HH:mm:ss z --><!-- END $-->
```
Take 1 parameter that represents the [formatting](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) of the current time (`Calendar.getInstance()`).

## Setup

```kotlin
plugins {
    id("com.glureau.grip") version "0.3.0"
}
```

Then you can add additional configuration, with:

```kotlin
grip {
    // When replaceInPlace is false, the files are processed and the resulting file is stored in build/grip
    replaceInPlace = false

    // Choose the files that grip should process
    files = fileTree(project.projectDir) {
        // By default, we pick only markdowns, but there is really no limitation on type file.
        include("**/**.md")
        // Avoid re-loading the generated files
        exclude("**/grip/**")
    }
}
```

### File types support

Currently the replacement token is made for Markdown and tools like mkdocs or dokka as it's considering a web comment format.

If you want to use the same plugin on other type of files, please open an issue with your needs!

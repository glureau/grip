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
    id("com.glureau.grip") version "0.4.4"
}
```

Then you can add additional configuration, with:

```kotlin
grip {
    // When replaceInPlace is false, the files are processed and the resulting file is stored in build/grip, see [Advanced](#Advanced)
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

To update the files: `$ ./gradlew grip`


### File types support

Currently the replacement token is made for Markdown and tools like mkdocs or dokka as it's considering a web comment format.

If you want to use the same plugin on other type of files, please open an issue with your needs!

## Advanced

With GRIP you can replace in place but you can also generates files in the `build/grip` directory. (Only one mode available at a time.)

This allow to not modify your repository while using the new file copy. Also the produced file doesn't contain the original directives.

### Execution order

Files retrieved by the `grip.files` extension are sorted so that the longer path are parsed first. For example `src/com/glureau/grip/README.md` (4 parent levels) is parsed before `src/README.md` (only 1 parent). This combined with `replaceInPlace = false` allow a multiple passes approach.

Let's say you want to have a global documentation that contains multiple parts, each part in a different module. And that those parts can also be splitted in more fine-grained documentation, with also directives in it:
- `moduleA/doc.md` // General documentation about module B
- `moduleB/doc.md` // General documentation about module B
- `moduleB/featureX/doc.md` // Documentation about feature X inside the module B
- `moduleB/featureY/doc.md` // Documentation about feature Y inside the module B
- `doc.md` // Global documentation
In `moduleB/doc.md` you can decide to load the documentation from every features of the module B. And from `doc.md` you can decide to load documentation from module A and from module B. In this approach, the produced file for `doc.md` will contain the content of all other markdowns.

Once this setup is in place, `./gradlew grip` will generate a file in `build/grip/doc.md` (among other files) that you can directly use to publish somewhere else.

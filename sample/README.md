### Those values below are updated automatically...

Library version **_v.<!--$ GRADLE_PROPERTIES version -->1.2.3<!-- END $-->_** (from gradle properties)

---

Kotlin used to build the plugin **_<!--$ GRADLE_PROPERTIES kotlinVersion -->1.7.10<!-- END $-->_** (also from gradle
properties)

---

Documentation update from computer with system env **_LOGNAME=<!--$ SYSTEM_ENV LOGNAME -->glureau<!-- END $-->_**

---

Last documentation update: **_<!--$ DATETIME yyyy.MM.dd G 'at' HH:mm:ss z -->2023.04.25 AD at 12:27:32 CEST<!-- END $-->_**

---

Inclusion of another document : <!--$ INSERT src/commonMain/kotlin/com/glureau/grip/sample/doc.md -->

Documentation of the package **com.glureau.grip.sample**, loaded dynamically!
<!-- END $-->

---

Inclusion of a generated file : <!--$ INSERT build/tmp/jvmJar/MANIFEST.MF -->
Manifest-Version: 1.0

<!-- END $-->

---
Inclusion of multiple files
<!--$ INSERT_DIRECTORIES src/commonMain/kotlin/com/glureau/grip/sample/p*/*.md
"
## %LASTDIR%
"
"
#### %FILE%
"-->

## Paf

#### Foo
foo

## Pif

#### Bar
this is bar documentation

## Pouf

#### Baz
Baz is really awesome

#### Baz2
Baz 2 is also **awesome**<!-- END $-->

--
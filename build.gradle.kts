version = "0.1.0"
group = "app.khadga"

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    kotlin("kapt") version "1.5.10"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

tasks.register("subproject") {
    val subproject: String by project
    doLast {
        // Create the source dirs
        mkdir("$subproject/src/main/java")
        mkdir("$subproject/src/main/kotlin")
        mkdir("$subproject/src/main/resources")
        mkdir("$subproject/src/test/kotlin")

        // Create a dummy README.md
        file("$subproject/README.md").writeText("""
            # $subproject
            
            Explain what this project is about
        """.trimIndent())

        // Create a dummy build.gradle.kts
        file("$subproject/build.gradle.kts").writeText("""
            version = "0.1.0"
            group = "app.khadga"
            
            plugins {
            
            }
            
            dependencies {
            
            }
        """.trimIndent())

        // Add to settings.gradle.kts
        val settings = file("settings.gradle.kts").readText()
        val includeRe = """include\((\s+(.*)\s*)+\)""".toRegex()
        val matched = includeRe.find(settings)
        if (matched != null) {
            println(matched.groupValues[0])
            println("===========")
            val includes = matched.groupValues.first()
                .replace("include(", "")
                .replace(")", "")
                .replace("""\s+""".toRegex(), "")
                .split(",") + listOf(""""$subproject"""")

            val replace = includes.sorted().joinToString(",\n") {
                "    $it"
            }
            val sub = "include(\n$replace\n)"
            println(sub)
            val newSettings = includeRe.replace(settings, sub)
            println(newSettings)
            file("settings.gradle.kts").writeText(newSettings)
        }
    }
}
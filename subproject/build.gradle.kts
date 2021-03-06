version = "0.1.0"
group = "com.github.rarebreed"

plugins {
   id("com.github.rarebreed.common.kotlin.lib")
}

dependencies {

}

tasks.register("make") {
    val dir: String by project
    val lang = "scala"
    val subDir = "../$dir"
    doLast {
        // Create the source dirs
        mkdir("$subDir/src/main/java")
        mkdir("$subDir/src/main/$lang/com/github/rarebreed/$dir")
        mkdir("$subDir/src/main/resources")
        mkdir("$subDir/src/test/$lang")

        // Create a dummy README.md
        file("$subDir/README.md").writeText("""
            # $dir
            
            Explain what this project is about
        """.trimIndent())

        // Create a dummy build.gradle.kts
        file("$subDir/build.gradle.kts").writeText("""
            version = "0.1.0"
            group = "app.khadga.$dir"
            
            plugins {
                // id("com.github.rarebreed.common.kotlin.lib")
                scala
            }
            
            dependencies {
                
            }
        """.trimIndent())

        // Add to settings.gradle.kts
        val settings = file("${project.rootDir}/settings.gradle.kts").readText()
        val includeRe = """include\((\s+(.*)\s*)+\)""".toRegex()
        val matched = includeRe.find(settings)
        if (matched != null) {
            println(matched.groupValues[0])
            println("===========")
            val includes = matched.groupValues.first()
                .replace("include(", "")
                .replace(")", "")
                .replace("""\s+""".toRegex(), "")
                .split(",") + listOf(""""$dir"""")

            val replace = includes.sorted().joinToString(",\n") {
                "    $it"
            }
            val sub = "include(\n$replace\n)"
            println(sub)
            val newSettings = includeRe.replace(settings, sub)
            println(newSettings)
            file("${project.rootDir}/settings.gradle.kts").writeText(newSettings)
        }
    }
}

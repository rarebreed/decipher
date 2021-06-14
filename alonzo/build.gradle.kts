version = "0.1.0"

plugins {
    id("com.github.rarebreed.common.kotlin.lib")
    kotlin("plugin.serialization") version "1.5.10"

    // To publish to maven
    `maven-publish`
    signing
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val kotlinVersion: String by project

dependencies {
    implementation("com.google.auto.service:auto-service:1.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion") {
        description = "Reflection for kotlin"
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

// TODO: Figure out how to add this to the common-kotlin-lib plugin
publishing {
    publications {
        create<MavenPublication>("alonzo") {
            artifactId = "alonzo"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("alonzo")
                description.set("Annotation library for FP")
                url.set("http://www.example.com/library")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("placeoftheway")
                        name.set("Sean Toner")
                        email.set("placeoftheway@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/rarebreed/decipher.git")
                    developerConnection.set("scm:git:ssh://github.com/rarebreed/decipher.git")
                    url.set("https://github.com/rarebreed/decipher")
                }
            }
        }
    }

    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
        mavenLocal()
    }
}

// TODO: Create OpenGPG key
signing {
    sign(publishing.publications["alonzo"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
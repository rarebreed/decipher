version = "0.1.0"

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val kotlinVersion: String by project

dependencies {
    implementation("com.google.auto.service:auto-service:1.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion")) {
        description = "align versions of all Kotlin components"
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion") {
        description = "Use JDK 8 version standard lib"
    }
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion") {
        description = "Reflection for kotlin"
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0") {
        description = "Coroutines for kotlin"
    }
}
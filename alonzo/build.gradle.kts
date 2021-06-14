version = "0.1.0"

plugins {
    id("com.github.rarebreed.common.kotlin.lib")
    kotlin("plugin.serialization") version "1.5.10"
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
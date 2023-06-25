rootProject.name = "autocleanup-junit"

pluginManagement {
    repositories {
        mavenCentral()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("jvm") version kotlinVersion apply false
    }
}

include(":autocleanup-junit5")

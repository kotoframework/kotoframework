rootProject.name = "kotoframework"
include("koto-core")
include("koto-basic-wrapper")
include("koto-spring-wrapper")
include("koto-jdbi-wrapper")
include("koto-plugins")

pluginManagement {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "org.jetbrains.kotlin") {
                useVersion("1.8.0")
            }
        }
    }
}
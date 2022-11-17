import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.dokka") version("1.7.20")
}

group = "com.kotoframework"
version = file("../koto.version").readText().trim()

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}


tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    outputDirectory.set(File(rootDir, "dokkadocs"))
    dokkaSourceSets {
        named("main") {
            moduleName.set("Koto Core")
            includes.from("packages.md")
            sourceLink {
                localDirectory.set(file("src/main/kotlin/com/kotoframework"))
                remoteUrl.set(
                    URL(
                        "https://github.com/kotoframework/kotoframework/tree/main/koto-core/src/main/kotlin/com/kotoframework"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}

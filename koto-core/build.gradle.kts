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
    compileOnly("org.apache.commons:commons-dbcp2:2.9.0")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20")
    testImplementation("mysql:mysql-connector-java:8.0.30")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
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

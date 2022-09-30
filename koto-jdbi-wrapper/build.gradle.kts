import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
}

group = "com.kotoframework"
version = file("../koto.version").readText().trim()

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":koto-core"))
    compileOnly("org.apache.commons:commons-dbcp2:2.9.0")
    compileOnly("org.jdbi:jdbi3-bom:3.32.0")
    compileOnly("org.jdbi:jdbi3-core:3.32.0")
    compileOnly("org.jdbi:jdbi3-kotlin:3.32.0")
    compileOnly("org.jdbi:jdbi3-kotlin-sqlobject:3.32.0")
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


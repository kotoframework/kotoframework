import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.20"
}

group = "com.kotoframework"
version = file("../koto.version").readText().trim()

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":koto-core"))
    compileOnly("org.apache.commons:commons-dbcp2:2.9.0")
    compileOnly("org.springframework:spring-jdbc:5.3.23")
    compileOnly("org.springframework:spring-tx:5.3.23")
    compileOnly("org.springframework:spring-beans:5.3.23")
    compileOnly("org.springframework:spring-core:5.3.23")
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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
//    id("criteria-parser-plugin")
}

group = "com.kotoframework"
version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation(kotlin("test"))
    testImplementation(project(":koto-core"))
    testImplementation(project(":koto-spring-wrapper"))
    testImplementation(project(":koto-jdbi-wrapper"))
    testImplementation(project(":koto-basic-wrapper"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

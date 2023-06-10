import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.kotoframework"
version = "2.0.0-snapshot"

plugins {
    kotlin("jvm") version "1.8.0"
//    id("com.kotoframework.koto-criteria-plugin") version "2.0.0"
}

repositories {
    mavenCentral()
    mavenLocal()
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
//    testImplementation("com.kotoframework:koto-criteria-plugin:${version}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

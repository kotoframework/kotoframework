import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "com.kotoframework"
version = file("koto.version").readText().trim()

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
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

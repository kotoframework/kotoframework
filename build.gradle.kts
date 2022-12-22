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
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation(kotlin("test"))
    testImplementation(project(":koto-core"))
    testImplementation(project(":koto-spring-wrapper"))
    testImplementation(project(":koto-basic-wrapper"))
    testImplementation("mysql:mysql-connector-java:8.0.31")
    testImplementation("org.apache.commons:commons-dbcp2:2.9.0")
    testImplementation("org.jdbi:jdbi:2.78")
    testImplementation("org.springframework:spring-beans:5.3.23")
    testImplementation("org.springframework:spring-jdbc:5.3.23")
    testImplementation("org.springframework:spring-tx:5.3.23")
    testImplementation("org.springframework:spring-beans:5.3.23")
    testImplementation("org.springframework:spring-core:5.3.23")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

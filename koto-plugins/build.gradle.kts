import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    kotlin("jvm") version "1.8.0"
    id("signing")
    id("maven-publish")
    id("java-gradle-plugin")
    id("kotlin-kapt")
}


val kotlin_plugin_id: String = "com.kotoframework.plugins.koto-gradle-plugin"
val artifactId: String = "koto-gradle-plugin"
group = rootProject.group
version = rootProject.version

val jarSources by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.map { it.allSource })
}

val jarJavadoc by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val config: Properties =
    groovy.util.ConfigSlurper().parse(File("${rootProject.projectDir.path}/gradle.properties").toURI().toURL())
        .toProperties()

config.entries.joinToString(separator = ";") { it.toString() }.let {
    println("config path:${rootProject.projectDir.path}")
    println("config:$it")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.0")
    implementation("com.google.auto.service:auto-service:1.0.1")
    kapt("com.google.auto.service:auto-service:1.0.1")
    testImplementation(project(":koto-core"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
}

gradlePlugin {
    plugins {
        create("KotoGradlePlugin") {
            id = "koto-gradle-plugin"
            implementationClass = "com.kotoframework.plugins.KotoGradlePlugin"
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing { // 发布至本地仓库
    publications {
        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = "koto-gradle-plugin"
            version = project.version.toString()

            pom {
                name.set("${project.group}:${project.name}")
                description.set("A gradle plugin provided by koto for parsing SQL Criteria expressions at compile time.")
                url.set("https://www.kotoframework.com")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/kotoframework/kotoframework")
                    connection.set("scm:git:https://github.com/kotoframework/kotoframework.git")
                    developerConnection.set("scm:git:ssh://git@github.com:kotoframework/kotoframework.git")
                }
                developers {
                    developer {
                        id.set("ousc")
                        name.set("ousc")
                        email.set("sundaiyue@foxmail.com")
                    }
                }
            }
        }

        repositories {
            mavenLocal()
            maven {
                name = "snapshot"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = "${config["sonatypeUsername"]}"
                    password = "${config["sonatypePassword"]}"
                }
            }
        }
    }
}
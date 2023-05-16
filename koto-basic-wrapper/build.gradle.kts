import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    id("java")
    kotlin("jvm") version "1.8.0"
    id("signing")
    id("maven-publish")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":koto-core"))
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

val config: java.util.Properties =
    groovy.util.ConfigSlurper().parse(File("${rootProject.projectDir.path}/gradle.properties").toURI().toURL()).toProperties()

config.entries.joinToString(separator = ";") { it.toString() }.let {
    println("config path:${rootProject.projectDir.path}")
    println("config:$it")
}

val jarSources by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.map { it.allSource })
}

val jarJavadoc by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = "koto-basic-wrapper"
            version = project.version.toString()

            pom {
                name.set("${project.group}:")
                description.set("Kotoframework 's built-in database operation plug-in based on the original jdbc supports variable templates and multiple databases.")
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
            maven {
                name = "central"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = "${config["sonatypeUsername"]}"
                    password = "${config["sonatypePassword"]}"
                }
            }
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

signing {
    //sign publishing.publications.mavenJava
    sign(publishing.publications["dist"])
}

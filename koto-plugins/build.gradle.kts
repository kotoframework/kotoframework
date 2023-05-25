import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("signing")
    id("maven-publish")
    id("java-gradle-plugin")
    id("kotlin-kapt")
}


group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.0")
    implementation("com.google.auto.service:auto-service:1.0.1")
    kapt("com.google.auto.service:auto-service:1.0.1")
    compileOnly(project(":koto-core"))
    testCompileOnly(project(":koto-core"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
}

gradlePlugin {
    plugins {
        create("koto-criteria-plugin") {
            id = "koto-criteria-plugin"
            implementationClass = "com.kotoframework.plugin.KotoCriteriaPlugin"
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
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = "koto-criteria-plugin"
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.40"
}

group = "hunden"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.javalin:javalin:2.6.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    compile ("org.jetbrains.exposed:exposed:0.14.2")
    compile ("org.xerial:sqlite-jdbc:3.21.0.1")
    compile ("org.slf4j:slf4j-simple:1.7.25")
    compile ("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    compile ("org.jsoup:jsoup:1.12.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task("stage") {
    //todo  dependsOn.add("clean")
    dependsOn.add("build")
    dependsOn.add("copyToLib")
}
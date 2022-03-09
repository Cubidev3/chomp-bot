import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.valentinegb"
version = "1.4.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.kord:kord-core:0.8.0-M10")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("io.ktor:ktor-client-gson:1.6.7")
    implementation("org.discordbots:DBL-Java-Library:2.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    configurations.compileClasspath.get().forEach {
        from(if (it.isDirectory) it else zipTree(it))
    }
}

tasks.create("stage") {
    dependsOn("build")
}

application {
    mainClass.set("MainKt")
}
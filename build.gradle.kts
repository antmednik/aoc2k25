plugins {
    kotlin("jvm") version "2.2.0"
}

group = "io.antmednik"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--add-modules", "jdk.incubator.vector")
}

tasks.withType<JavaExec>() {
    jvmArgs("--add-modules", "jdk.incubator.vector")
}

kotlin {
    jvmToolchain(22)
}
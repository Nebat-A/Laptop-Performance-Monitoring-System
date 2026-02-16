plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.prometheus:simpleclient:0.16.0")
    implementation("io.prometheus:simpleclient_httpserver:0.16.0")
    implementation("com.github.oshi:oshi-core:6.4.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}
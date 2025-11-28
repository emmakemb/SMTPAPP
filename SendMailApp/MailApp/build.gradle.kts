plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "main.java"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    // Jakarta Mail dependencies for sending emails
    implementation("jakarta.mail:jakarta.mail-api:2.1.3")
    implementation("org.eclipse.angus:angus-mail:2.0.3")

    // JUnit 4 for testing
    testImplementation("junit:junit:4.13.2")
}

application {
    // Main class for the JavaFX app
    mainClass.set("main.java.EmailSenderApp")
}

tasks.test {
    useJUnit()
}

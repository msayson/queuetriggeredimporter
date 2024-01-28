/*
  Applies core plugins provided by Gradle.
 */
plugins {
    // Java for compiling and testing Java source files
    java
    // Supports building Java CLI applications
    application
    // Supports style checks for Java source files
    checkstyle
}

configurations.checkstyle {
    resolutionStrategy.capabilitiesResolution.withCapability("com.google.collections:google-collections") {
        select("com.google.guava:guava:0")
    }
}

/*
  Configures the checkstyle plugin
 */
checkstyle {
  sourceSets = listOf(the<SourceSetContainer>()["main"])
  toolVersion = "10.13.0"
  configFile = File("${rootDir}/config/checkstyle/checkstyle.xml")
  configProperties.put("checkstyle.suppression.filter", "${rootDir}/config/checkstyle/checkstyle-suppressions.xml")
  maxErrors = 0
  maxWarnings = 0
  setIgnoreFailures(false)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    google()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.marksayson.demos.queuetriggeredimporter.Program")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

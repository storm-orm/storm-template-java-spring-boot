import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    // The Storm plugin imports the BOM, adds storm-java21 and storm-core, wires the metamodel
    // annotation processor, and enables the preview flags that Storm's Java String Templates
    // (JEP 430) require on compile, test and run, bootRun included.
    id("st.orm") version "1.15.0"
}

group = "com.example"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")

    // The starter builds the ORMTemplate from the DataSource, finds the repositories and binds the
    // storm.* properties; storm-jackson3 serializes Ref fields.
    implementation("st.orm:storm-spring-boot-starter")
    implementation("st.orm:storm-jackson3")

    // The application runs on H2 out of the box and switches to PostgreSQL through the postgres
    // profile alone, so both dialect modules and both drivers are on the runtime path.
    runtimeOnly("st.orm:storm-h2")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("st.orm:storm-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // @StormTest runs each test on a database of its own and rolls it back afterwards.
    testImplementation("st.orm:storm-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("st.orm:storm-h2")
    testRuntimeOnly("com.h2database:h2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
}

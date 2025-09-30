dependencies {

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)
    implementation(rootProject.libs.springframework.spring.boot.starter.security)
    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springframework.spring.boot.starter.cache)
    implementation(rootProject.libs.springframework.spring.boot.starter.validation)
    implementation(rootProject.libs.springframework.spring.boot.starter.test)

    implementation(rootProject.libs.springframework.spring.boot.kafka)

    implementation(libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    runtimeOnly(rootProject.libs.postgresql.postgresql)

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)

    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {

    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }
}
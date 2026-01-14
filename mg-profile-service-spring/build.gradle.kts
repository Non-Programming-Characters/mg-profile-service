dependencies {

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)
    implementation(rootProject.libs.springframework.spring.boot.starter.security)
    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springframework.spring.boot.starter.cache)
    implementation(rootProject.libs.springframework.spring.boot.starter.validation)
    implementation(rootProject.libs.springframework.spring.boot.starter.test)

    implementation(rootProject.libs.flywaydb.flyway.core)
    implementation(rootProject.libs.flywaydb.flyway.database.postgresql)

    implementation(rootProject.libs.springframework.spring.boot.eureka.client)

    implementation(rootProject.libs.springframework.spring.boot.kafka)

    implementation(libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    runtimeOnly(rootProject.libs.postgresql.postgresql)

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)

    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    listOf(
        "mg-profile-service-common",
        "mg-profile-service-common-jpa",

        "mg-profile-service-profile",
        "mg-profile-service-profile-spring-jpa-adapter",
        "mg-profile-service-profile-spring-rest-adapter",
        "mg-profile-service-profile-spring-kafka-adapter",

        "mg-profile-service-user-snapshot",
        "mg-profile-service-user-snapshot-spring-jpa-adapter",
        "mg-profile-service-user-snapshot-spring-kafka-adapter",

        "mg-profile-service-friendship-relation",
        "mg-profile-service-friendship-relation-spring-jpa-adapter",
        "mg-profile-service-friendship-relation-spring-rest-adapter"
    ).forEach {
        implementation(project(":$it"))
    }
}

tasks.withType<Test> {

    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }
}
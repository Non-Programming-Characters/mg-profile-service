dependencies {
    implementation(project(":mg-profile-service-common"))

    implementation(project(":mg-profile-service-profile"))

    implementation(project(":mg-profile-service-user-snapshot"))

    implementation(rootProject.libs.springframework.spring.boot.kafka)
    implementation(rootProject.libs.jackson.core.databind)
    implementation(rootProject.libs.jackson.datatype.jsr310)
}
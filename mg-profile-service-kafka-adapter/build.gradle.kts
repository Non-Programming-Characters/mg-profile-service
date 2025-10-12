dependencies {
    implementation(project(":mg-profile-service-common"))

    implementation(project(":mg-profile-service-principal"))

    implementation(project(":mg-profile-service-profile"))

    implementation(project(":mg-profile-service-user-snapshot"))

    implementation(rootProject.libs.springframework.spring.boot.kafka)
}
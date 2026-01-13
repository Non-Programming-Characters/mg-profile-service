dependencies {
    implementation(rootProject.libs.springframework.spring.boot.kafka)

    implementation(project(":mg-profile-service-common"))

    implementation(project(":mg-profile-service-profile"))
}
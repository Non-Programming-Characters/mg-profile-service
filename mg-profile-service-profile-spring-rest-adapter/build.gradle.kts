dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.web)

    implementation(project(":mg-profile-service-common"))

    implementation(project(":mg-profile-service-profile"))
}
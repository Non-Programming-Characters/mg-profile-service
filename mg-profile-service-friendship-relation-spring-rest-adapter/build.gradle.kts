dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.web)

    implementation(project(":mg-profile-service-friendship-relation"))

    implementation(project(":mg-profile-service-common"))
}
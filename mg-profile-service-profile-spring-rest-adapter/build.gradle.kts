dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    implementation(project(":mg-profile-service-common"))

    implementation(project(":mg-profile-service-profile"))
}
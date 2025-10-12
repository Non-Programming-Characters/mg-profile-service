dependencies {
    implementation(project(":mg-profile-service-common"))
    implementation(project(":mg-profile-service-common-jpa"))

    implementation(project(":mg-profile-service-profile"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}
dependencies {
    implementation(project(":mg-profile-service-common"))
    implementation(project(":mg-profile-service-common-jpa"))

    implementation(project(":mg-profile-service-user-snapshot"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}
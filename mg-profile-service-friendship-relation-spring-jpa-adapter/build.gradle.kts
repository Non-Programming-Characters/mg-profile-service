dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)

    implementation(project(":mg-profile-service-friendship-relation"))

    implementation(project(":mg-profile-service-common-jpa"))
    implementation(project(":mg-profile-service-common"))
}
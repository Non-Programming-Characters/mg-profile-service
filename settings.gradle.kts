rootProject.name = "mg-profile-service"

listOf(
    "mg-profile-service-spring",

    "mg-profile-service-common",
    "mg-profile-service-common-jpa",

    "mg-profile-service-principal",
    "mg-profile-service-principal-spring-security-adapter",
).forEach {
    include(it)
}

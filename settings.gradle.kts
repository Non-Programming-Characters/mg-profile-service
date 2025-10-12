rootProject.name = "mg-profile-service"

listOf(
    "mg-profile-service-spring",

    "mg-profile-service-common",
    "mg-profile-service-common-jpa",

    "mg-profile-service-principal",
    "mg-profile-service-principal-spring-security-adapter",

    "mg-profile-service-kafka-adapter",

    "mg-profile-service-profile",
    "mg-profile-service-profile-spring-jpa-adapter",

    "mg-profile-service-user-snapshot",
    "mg-profile-service-user-snapshot-spring-jpa-adapter"
).forEach {
    include(it)
}
include("mg-profile-service-profile-spring-rest-adapter")
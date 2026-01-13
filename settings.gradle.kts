rootProject.name = "mg-profile-service"

listOf(
    "mg-profile-service-spring",

    "mg-profile-service-common",
    "mg-profile-service-common-jpa",

    "mg-profile-service-profile",
    "mg-profile-service-profile-spring-jpa-adapter",
    "mg-profile-service-profile-spring-rest-adapter",
    "mg-profile-service-profile-spring-kafka-adapter",

    "mg-profile-service-user-snapshot",
    "mg-profile-service-user-snapshot-spring-jpa-adapter",
    "mg-profile-service-user-snapshot-spring-kafka-adapter",

    "mg-profile-service-friendship-relation",
    "mg-profile-service-friendship-relation-spring-jpa-adapter",
    "mg-profile-service-friendship-relation-spring-rest-adapter"
).forEach {
    include(it)
}
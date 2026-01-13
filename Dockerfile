FROM openjdk:26-ea-29-jdk AS builder
ARG SPRING_MODULE="mg-profile-service-spring"
ARG JAR_FILE=${SPRING_MODULE}/build/libs/${SPRING_MODULE}.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
FROM openjdk:26-ea-29-jdk
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
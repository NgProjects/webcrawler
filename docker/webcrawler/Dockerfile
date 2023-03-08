FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package install
RUN mkdir -p target/dependency && (cd target/dependency; cp ../webcrawler.jar .)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/webcrawler.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
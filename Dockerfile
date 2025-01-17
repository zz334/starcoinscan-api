FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine
ARG STARCOIN_ES_URL
ARG STARCOIN_ES_PROTOCOL
ARG STARCOIN_ES_PORT
ARG STARCOIN_ES_USER
ARG STARCOIN_ES_PWD

ENV STARCOIN_ES_URL=$STARCOIN_ES_URL
ENV STARCOIN_ES_PROTOCOL=$STARCOIN_ES_PROTOCOL
ENV STARCOIN_ES_PORT=$STARCOIN_ES_PORT
ENV STARCOIN_ES_USER=$STARCOIN_ES_USER
ENV STARCOIN_ES_PWD=$STARCOIN_ES_PWD
RUN addgroup -S starcoin && adduser -S starcoin -G starcoin
VOLUME /tmp
USER starcoin
ARG DEPENDENCY=/workspace/app/target
COPY --from=build ${DEPENDENCY}/scan-1.0-SNAPSHOT.jar /app/lib/app.jar
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-jar","app/lib/app.jar","-Dspring.main.lazy-initialization=true","STARCOIN_ES_URL=$STARCOIN_ES_URL","STARCOIN_ES_PROTOCOL=$STARCOIN_ES_PROTOCOL","STARCOIN_ES_PORT=$STARCOIN_ES_PORT","STARCOIN_ES_USER=$STARCOIN_ES_USER","STARCOIN_ES_PWD=$STARCOIN_ES_PWD"]
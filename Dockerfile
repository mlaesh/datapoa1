FROM gradle:6.9.0-jdk11 as builder
ARG VERSION=1
ARG JAR_PATH=build/libs/poa-api-1.0.1-SNAPSHOT.jar
ARG PROFILE
ARG ADDITIONAL_OPTS

WORKDIR /
ADD . .

RUN gradle build
RUN mv /$JAR_PATH /app.jar

FROM adoptopenjdk/openjdk11:alpine
WORKDIR /
COPY  --from=builder /app.jar /

ENV VERSION=$VERSION
ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["/bin/sh","-c","java ${ADDITIONAL_OPTS} -jar app.jar --spring.profiles.active=${PROFILE}"]
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Gradle wrapper 포함된 프로젝트 기준
COPY . .

RUN ./gradlew clean build -x test

WORKDIR /app/build/libs

# 최신 jar 하나를 app.jar로 복사
RUN cp *.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

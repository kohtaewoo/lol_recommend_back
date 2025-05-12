FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

# ✅ gradlew 실행권한 부여 (필수!)
RUN chmod +x ./gradlew

# ✅ 빌드 수행
RUN ./gradlew clean build -x test

WORKDIR /app/build/libs
RUN cp *.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

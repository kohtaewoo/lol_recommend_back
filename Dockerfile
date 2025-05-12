FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# 모든 소스 복사
COPY . .

# gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# Spring Boot fat jar 빌드 (bootJar만 실행)
RUN ./gradlew clean bootJar -x test

# .jar 파일 app.jar로 복사 (이름 모를 경우 패턴 매칭 사용)
RUN cp build/libs/*.jar app.jar

# 앱 실행 포트
EXPOSE 8080

# jar 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

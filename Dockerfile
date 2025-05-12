FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar

ENV BACKEND_SECRET_KEY=${BACKEND_SECRET_KEY}
ENV PYTHON_SERVER_URL=${PYTHON_SERVER_URL}

ENTRYPOINT ["java", "-jar", "app.jar"]

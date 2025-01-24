FROM eclipse-temurin:17-jre

WORKDIR /app

COPY /target/uberjar/shortcut-url.jar /app/shortcut-url.jar

EXPOSE 8080

CMD ["java", "-jar", "shortcut-url.jar"]
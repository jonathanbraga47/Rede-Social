FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copia o pom e baixa as dependências (otimiza cache)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copia o código e gera o jar
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
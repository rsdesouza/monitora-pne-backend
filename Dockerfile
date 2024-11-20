# Etapa 1: Use uma imagem do Maven para compilar o JAR
FROM maven:3.8.7-eclipse-temurin-17 AS builder

# Copie os arquivos do projeto para o container
COPY . /app
WORKDIR /app

# Compile o projeto e gere o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Use uma imagem do JDK para executar o JAR gerado
FROM eclipse-temurin:17-jdk-alpine

# Copie o JAR gerado para a imagem final
COPY --from=builder /app/target/monitora-pne-backend-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]

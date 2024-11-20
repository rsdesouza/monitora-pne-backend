# Use uma imagem base do JDK para executar sua aplicação
FROM eclipse-temurin:17-jdk-alpine

# Adiciona um argumento para configurar o JAR final
ARG JAR_FILE=target/monitora-pne-backend-0.0.1-SNAPSHOT.jar

# Copia o JAR gerado pelo Maven/Gradle para o container
COPY ${JAR_FILE} app.jar

# Exponha a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]

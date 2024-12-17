# Etapa 1: Compilação do código
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Define o diretório de trabalho para a compilação
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml .
COPY src ./src

# Compila o projeto usando Maven e cria o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para execução da aplicação
FROM eclipse-temurin:17-jdk

# Define o diretório de trabalho na imagem final
WORKDIR /app

# Copia o JAR gerado na etapa de build para o diretório atual
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
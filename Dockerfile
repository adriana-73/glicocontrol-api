# 1. Usa uma imagem oficial do Java para compilar e rodar
# atualização do Dockerfile
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia os ficheiros do Maven e do projeto
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Dá permissão ao mvnw e compila o projeto ignorando os testes para ser mais rápido
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# 2. Prepara a imagem final mais leve para execução
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copia o jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
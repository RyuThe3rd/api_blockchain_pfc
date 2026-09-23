# ===== ETAPA 1: Compilação =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o código fonte e compila
COPY . .
RUN mvn clean package -DskipTests

# ===== ETAPA 2: Imagem Final Leve para Produção =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia APENAS o JAR gerado na Etapa 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
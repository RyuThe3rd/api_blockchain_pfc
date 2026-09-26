# ===== ETAPA 1: Compilação (Build) =====
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia os arquivos de configuração e o Gradle Wrapper
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY gradlew /app/

# Garante permissão de execução para o gradlew
RUN chmod +x gradlew

# Baixa as dependências e compila usando o wrapper do projeto
COPY src /app/src
RUN ./gradlew bootJar --no-daemon -x test

# ===== ETAPA 2: Imagem Final Leve para Produção =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria um usuário não-root por segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia APENAS o JAR gerado pelo Gradle na Etapa 1
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar 2>/dev/null || COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
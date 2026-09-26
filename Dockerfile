# ===== ETAPA 1: Compilação (Build) =====
FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /app

# Copia os arquivos de configuração de dependências primeiro (otimização de cache)
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY gradlew /app/

# Copia o código fonte e gera o JAR (ignorando testes para build mais rápido)
COPY src /app/src
RUN gradle bootJar --no-daemon -x test

# ===== ETAPA 2: Imagem Final Leve para Produção =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria um usuário não-root por questões de segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia APENAS o JAR gerado pelo Gradle na Etapa 1
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar 2>/dev/null || COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta padrão (o Cloud Run sobrescreve com a variável PORT)
EXPOSE 8080

# Executa a aplicação garantindo suporte à variável PORT do Cloud Run
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
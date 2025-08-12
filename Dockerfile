# 1. BUILD STAGE
# ========================
FROM eclipse-temurin:17-jdk AS builder

# Set working directory inside build container
WORKDIR /app

# Copy Maven wrapper scripts and config for better layer caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Pre-fetch dependencies
RUN ./mvnw dependency:go-offline

# Copia el código fuente AHORA. Esto asegura que si los archivos fuente cambian,
# la caché para la compilación Maven se invalide.
COPY src/ ./src/

# Construye la aplicación (saltando los tests para una compilación más rápida)
RUN ./mvnw clean package -DskipTests

# ========================
# 2. RUN STAGE
# ========================
# Para que la imagen final sea más pequeña y eficiente, y respetando
# la intención "slim" de la consigna original, usamos una imagen JRE ligera.
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside runtime container
WORKDIR /app

# Copia el JAR construido desde la etapa 'builder'.
# Asumimos que Maven generó un único JAR en el directorio 'target'.
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto de la aplicación.
# Asegúrate de que este puerto coincida con el `server.port` en tu `application.yml`
# y el mapeo de puertos en tu `docker-compose.yml` (que es 9080).
EXPOSE 9080

# Inicia la aplicación Java
ENTRYPOINT ["java", "-jar", "app.jar"]

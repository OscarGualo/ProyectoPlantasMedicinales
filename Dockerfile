# Imagen única de la aplicación (doc 06 §14, AD-02): el frontend compilado se sirve desde el backend.
# Construir:  docker build -t huertos-medicinales .
# Ejecutar:   docker run -p 8080:8080 -e DB_URL=... -e DB_USER=... -e DB_PASSWORD=... -e DEMO_PASSWORD=... huertos-medicinales

# 1. Frontend: Vite genera la SPA estática.
FROM node:22-alpine AS frontend
WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci --no-audit --no-fund
COPY frontend/ ./
RUN npm run build

# 2. Backend: la SPA se copia a static/ y Maven empaqueta un solo archivo ejecutable.
FROM eclipse-temurin:21-jdk AS backend
WORKDIR /app/backend
COPY backend/.mvn .mvn
COPY backend/mvnw backend/pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline
COPY backend/src src
COPY --from=frontend /app/frontend/dist src/main/resources/static
# Las pruebas se ejecutan en CI con Testcontainers; aquí solo se empaqueta.
RUN ./mvnw -q -B package -DskipTests

# 3. Ejecución: solo el JRE y el archivo de la aplicación, con un usuario sin privilegios.
FROM eclipse-temurin:21-jre
RUN groupadd --system huertos && useradd --system --gid huertos huertos
WORKDIR /app
COPY --from=backend /app/backend/target/huertos-medicinales.jar app.jar
USER huertos
ENV COOKIE_SECURE=true
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]

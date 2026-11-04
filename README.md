# Huertos Medicinales Comunitarios

Aplicación web educativa con gamificación para los huertos medicinales comunitarios del Ecuador
(ISWD652 Calidad de Software, EPN, período 2026-B). Este repositorio contiene la **versión 1**
(iteración 1, entrega el 13/11/2026).

La documentación del proyecto está en [`docs/`](docs/):

| Documento | Contenido |
|---|---|
| [01 Visión y alcance](docs/01_Vision_y_alcance_v0.5.md) | Objetivos, capacidades C1–C8 y alcance |
| [02 Plan de la iteración 1](docs/02_Plan_iteracion_1_v1.3.md) | Historias, esfuerzo, calendario y definición de terminado |
| [03 Historias de usuario](docs/03_Historias_de_usuario_I1_v1.2.md) | HU-001 a HU-020 con criterios de aceptación |
| [04 Casos de prueba](docs/04_Casos_de_prueba_I1_v1.2.md) | P-001 a P-055 y registro de ejecuciones |
| [05 Reglas, datos y trazabilidad](docs/05_Reglas_datos_y_trazabilidad_v1.2.md) | Glosario, reglas RN, mensajes MSG y datos de prueba |
| [06 Arquitectura y diseño](docs/06_Arquitectura_y_diseno_v0.1.md) | Monolito modular, modelo de datos, API y pruebas |

## Estructura

```text
backend/    Spring Boot 4 (Java 21): API REST, seguridad, reglas de negocio y cálculo lunar
frontend/   React 19 + Vite + TypeScript: mapa (Leaflet), ficha pública y pantallas de gestión
docs/       Documentos 01 a 06 y evidencias de pruebas
docker-compose.yml   PostgreSQL local
```

## Requisitos

- JDK 21 o superior
- Docker Desktop (PostgreSQL local y Testcontainers en las pruebas)
- Node.js LTS (frontend)

## Ejecutar en local

```bash
docker compose up -d                 # PostgreSQL en localhost:5433 (usuario y clave: huertos)

cd backend
DEMO_PASSWORD=una-clave-local ./mvnw spring-boot:run      # API en http://localhost:8080
#   PowerShell: $env:DEMO_PASSWORD="una-clave-local"; .\mvnw.cmd spring-boot:run

cd frontend
npm install
npm run dev                          # SPA en http://localhost:5173 (envía /api al backend)
```

La migración `V2__demo.sql` carga datos de demostración ficticios: tres huertos publicados y los líderes
`lider.demo1`, `lider.demo2` y `lider.demo3`. Su contraseña es el valor de `DEMO_PASSWORD` **la primera vez
que se crea la base**; sin esa variable nadie puede ingresar. Para empezar de cero:
`docker compose down -v && docker compose up -d`.

Variables de entorno: `DB_URL`, `DB_USER`, `DB_PASSWORD`, `DEMO_PASSWORD`, `COOKIE_SECURE` (true en el
hosting). Las credenciales reales nunca se guardan en el repositorio.

## Pruebas

```bash
cd backend
./mvnw verify                        # unitarias + integración (requiere Docker en ejecución)
```

Cada prueba automática lleva el identificador de su caso del documento 04 en el nombre, por ejemplo
`P-032 Registro de una siembra con fotografía`. El reporte de cobertura queda en
`backend/target/site/jacoco/index.html`.

## Forma de trabajo

- Una rama por historia (`hu-014-registrar-siembra`) y un pull request revisado por otro integrante.
- Mensajes de commit que citan la historia: `HU-014: valida el tamaño de la fotografía`.
- Una historia está terminada cuando cumple la definición de terminado del documento 02 §6.

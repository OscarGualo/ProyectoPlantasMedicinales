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
frontend/   React 19 + Vite + TypeScript: mapa (Leaflet), ficha, 3D (three.js), calendario, reto y gestión
docs/       Documentos 01 a 06 y evidencias de pruebas
docker-compose.yml   PostgreSQL local (bases huertos y huertos_e2e)
Dockerfile           imagen única: compila el frontend y lo sirve desde el backend
.github/workflows/   CI: pruebas del backend, build del frontend e imagen
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
hosting) y `PORT` (8080 por defecto). Las credenciales reales nunca se guardan en el repositorio.

## Pruebas

```bash
cd backend
./mvnw verify                        # unitarias + integración (requiere Docker en ejecución)
```

```bash
cd frontend
npx playwright install chromium      # solo la primera vez
npx playwright test                  # recorridos en navegador: P-023 a P-031, P-052 a P-055
```

Playwright levanta solo el backend con el perfil `e2e` (puerto 8081, base `huertos_e2e`, reloj fijo en
31/10/2026 12:00 y CAL-T para los días 10 a 14/10/2026) y Vite en el puerto 5174. Antes de cada caso restaura
los datos del documento 05 §7. Requiere el contenedor de la base en ejecución (`docker compose up -d`); si el
volumen se creó antes de existir `huertos_e2e`:
`docker compose exec postgres psql -U huertos -c "CREATE DATABASE huertos_e2e;"`.

Cada prueba automática lleva el identificador de su caso del documento 04 en el nombre, por ejemplo
`P-032 Registro de una siembra con fotografía`. El reporte de cobertura queda en
`backend/target/site/jacoco/index.html`.

## Imagen para el despliegue

```bash
docker build -t huertos-medicinales .
docker run -p 8080:8080   -e DB_URL=jdbc:postgresql://<servidor>:5432/<base> -e DB_USER=<usuario> -e DB_PASSWORD=<clave>   -e DEMO_PASSWORD=<clave-demo> huertos-medicinales
```

La imagen trae `COOKIE_SECURE=true` (la cookie de sesión solo viaja por HTTPS). Para probarla en local contra
el contenedor de compose, agrega `-e COOKIE_SECURE=false` y usa `host.docker.internal:5433` como servidor.

## Forma de trabajo

- Una rama por historia (`hu-014-registrar-siembra`) y un pull request revisado por otro integrante.
- Mensajes de commit que citan la historia: `HU-014: valida el tamaño de la fotografía`.
- Una historia está terminada cuando cumple la definición de terminado del documento 02 §6.

# Huertos Medicinales Comunitarios

Aplicación web educativa con gamificación para los huertos medicinales comunitarios del Ecuador
(ISWD652 Calidad de Software, EPN, período 2026-B). Este repositorio contiene la **versión 1**
(iteración 1, entrega el 13/11/2026): las historias HU-001 a HU-020.

- **Visitante (sin cuenta):** mapa de huertos, ficha pública, recorrido 3D, calendario lunar y reto
  *Documentación lunar*.
- **Líder de huerto:** ubicación, características y publicación del huerto; plantas, cultivos, bitácora de siembras
  con fotografía, condición lunar, corrección e historial.

## Contenido

1. [Estructura](#estructura)
2. [Opción A: todo con Docker](#opción-a-todo-con-docker) (la forma más rápida de verla funcionando)
3. [Opción B: entorno de desarrollo](#opción-b-entorno-de-desarrollo)
4. [Pruebas](#pruebas)
5. [Despliegue](#despliegue)
6. [Variables de entorno](#variables-de-entorno)
7. [Solución de problemas](#solución-de-problemas)
8. [Documentación](#documentación)

## Estructura

```text
backend/             Spring Boot 4 (Java 21): API REST, seguridad, reglas de negocio y cálculo lunar
frontend/            React 19 + Vite + TypeScript: mapa (Leaflet), 3D (three.js), calendario, reto y gestión
docs/                Documentos 01 a 06 del proyecto
docker/initdb/       Script que crea la base de las pruebas en navegador (huertos_e2e)
docker-compose.yml   PostgreSQL local y, con el perfil app, la aplicación completa
Dockerfile           Imagen única: compila el frontend y lo sirve desde el backend
.github/workflows/   CI: pruebas del backend, build del frontend e imagen Docker
```

Arquitectura: monolito modular (doc 06, AD-01). Una sola imagen sirve la API en `/api/**` y la aplicación React en
el resto de rutas. La base es PostgreSQL y el esquema lo crea Flyway al arrancar.

La versión integrada de I1 añade la migración `V3__integridad_envios.sql`,
el control de contenido de los reenvíos y los campos `versionBase` y
`claveEnvio` para las correcciones. Estos contratos se completan al reunir
los aportes del equipo, actualizando frontend y backend juntos.

Antes de aplicar V3 sobre información existente, se verifica en una copia
de la base que se conservan las siembras, versiones y fotografías previas.

## Opción A: todo con Docker

Solo necesitas [Docker Desktop](https://www.docker.com/products/docker-desktop/) abierto.

```bash
git clone https://github.com/OscarGualo/ProyectoPlantasMedicinales.git
cd ProyectoPlantasMedicinales

cp .env.example .env          # PowerShell: Copy-Item .env.example .env
#   Edita .env y cambia DEMO_PASSWORD por una clave tuya.

docker compose --profile app up -d --build
```

La primera construcción tarda unos minutos (descarga dependencias de Node y Maven). Luego abre
**http://localhost:8080**.

- Ingresa en **http://localhost:8080/ingresar** con `lider.demo1`, `lider.demo2` o `lider.demo3` y la clave que
  pusiste en `DEMO_PASSWORD`.
- Los datos son de demostración y ficticios (migración `V2__demo.sql`): tres huertos publicados en el mapa.

| Acción | Comando |
|---|---|
| Ver el registro de la aplicación | `docker compose --profile app logs -f app` |
| Detener todo | `docker compose --profile app down` |
| Volver a construir tras cambiar código | `docker compose --profile app up -d --build` |
| Borrar los datos y empezar de cero | `docker compose --profile app down -v` |
| Usar otro puerto (8080 ocupado) | `APP_PORT=8090` en `.env` |

> `DEMO_PASSWORD` se aplica **solo la primera vez que se crea la base**. Si la cambias después, borra los datos con
> `docker compose --profile app down -v` y vuelve a levantar.

## Opción B: entorno de desarrollo

Requisitos: JDK 21 o superior, Node.js 22 LTS y Docker Desktop.

```bash
docker compose up -d                 # solo PostgreSQL, en localhost:5433 (usuario y clave: huertos)

cd backend
DEMO_PASSWORD=una-clave-local ./mvnw spring-boot:run      # API en http://localhost:8080
#   PowerShell: $env:DEMO_PASSWORD="una-clave-local"; .\mvnw.cmd spring-boot:run

cd frontend                          # en otra terminal
npm install
npm run dev                          # http://localhost:5173 (envía /api al backend)
```

La base del contenedor usa el puerto **5433** del equipo para no chocar con un PostgreSQL instalado localmente.

## Pruebas

```bash
cd backend
./mvnw verify                        # unitarias + integración de API (requiere Docker en ejecución)
```

Las pruebas de integración usan Testcontainers: cada ejecución levanta un PostgreSQL efímero, con el reloj fijado en
31/10/2026 12:00 y los datos de prueba del documento 05 §7. El reporte de cobertura queda en
`backend/target/site/jacoco/index.html`.

Si Docker no está disponible, se puede usar un PostgreSQL local desechable
en `127.0.0.1:55432`, base `huertos_test` y usuario y contraseña de prueba
`huertos`. La alternativa se activa desde la carpeta backend con:

./mvnw verify -Dhuertos.pruebas.postgres-externo=true

En PowerShell:

.\mvnw.cmd '-Dhuertos.pruebas.postgres-externo=true' verify

La suite borra y repuebla las tablas de esa base antes de cada caso.
Esta instancia se reserva exclusivamente para pruebas.

```bash
docker compose up -d                 # la base debe estar arriba
cd frontend
npx playwright install chromium      # solo la primera vez
npx playwright test                  # recorridos en navegador, incluido P-055 (demostración completa)
```

Playwright levanta el backend con el perfil `e2e` en el puerto 8081,
utiliza la base aislada `huertos_e2e` y ejecuta Vite en el puerto 5174.
Los datos se restauran antes de cada caso.

En el candidato integrado, P-055 incluye un recorrido con CAL-T y otro
con fechas reales fuera de su tramo controlado. P-050 compara los eventos
lunares de noviembre de 2026 con
[USNO](https://aa.usno.navy.mil/calculated/moon/phases?year=2026).
Estas verificaciones se comprueban al integrar los aportes correspondientes.



## Despliegue

La aplicación se despliega como **un contenedor** construido con el `Dockerfile` más **una base PostgreSQL
administrada** (doc 06 §14). Sirve cualquier servicio que construya imágenes desde un repositorio de GitHub y
ofrezca PostgreSQL, por ejemplo Render, Railway o Fly.io.

### 1. Construir y probar la imagen en tu equipo

```bash
docker build -t huertos-medicinales .

docker run --rm -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5433/huertos \
  -e DB_USER=huertos -e DB_PASSWORD=huertos \
  -e DEMO_PASSWORD=una-clave-local \
  -e COOKIE_SECURE=false \
  huertos-medicinales
```

`host.docker.internal` apunta al PostgreSQL de `docker compose up -d`. `COOKIE_SECURE=false` solo se usa en local,
porque `http://localhost` no es HTTPS.

### 2. Crear la base PostgreSQL en el servicio

Crea una base PostgreSQL 15 o superior y copia sus datos de conexión. Los servicios suelen entregar una URL del tipo
`postgres://usuario:clave@servidor:5432/base`. Spring necesita esos datos **por separado y en formato JDBC**:

| Dato del servicio | Variable de la aplicación |
|---|---|
| `servidor`, puerto y `base` | `DB_URL=jdbc:postgresql://servidor:5432/base?sslmode=require` |
| `usuario` | `DB_USER=usuario` |
| `clave` | `DB_PASSWORD=clave` |

Agrega `?sslmode=require` si el servicio exige conexión cifrada (la mayoría de los planes administrados lo exigen).
Si la base está en la misma red privada del servicio, puede no hacer falta.

### 3. Crear el servicio web desde el repositorio

1. Conecta el repositorio de GitHub y elige el tipo **Docker** (el servicio detecta el `Dockerfile` de la raíz).
2. Configura las variables de entorno: `DB_URL`, `DB_USER`, `DB_PASSWORD` y `DEMO_PASSWORD` (una clave **nueva**,
   distinta de la de tu equipo). La imagen ya trae `COOKIE_SECURE=true`.
3. El puerto: la aplicación escucha en `PORT` (8080 si no existe). Si el servicio asigna `PORT` solo, no hagas nada;
   si pide el puerto del contenedor, indica `8080`.
4. Ruta de verificación de salud (si la pide): `/api/publico/huertos`.
5. Despliega. En el primer arranque Flyway crea las tablas y carga los datos de demostración.

### 4. Comprobar el despliegue

- `https://<tu-servicio>/` muestra el mapa con los tres huertos de demostración.
- `https://<tu-servicio>/huertos/1/reto` carga el reto (las rutas de la aplicación las resuelve el backend).
- Ingresa con `lider.demo1` y la `DEMO_PASSWORD` del servicio.

> Algunos planes gratuitos suspenden la aplicación tras un rato sin visitas y el primer acceso tarda en
> despertarla. **Ábrela unos minutos antes de la demostración** (doc 06 §14).

### Integración continua

`.github/workflows/ci.yml` configura cuatro trabajos: pruebas del backend,
compilación del frontend, recorridos Playwright con PostgreSQL y
construcción de la imagen Docker.

El flujo se activa en cada pull request y en los cambios enviados a `main`.
La imagen se construye después de que los otros tres trabajos terminen
correctamente. Los reportes de pruebas y cobertura se publican como
artefactos en GitHub Actions.

El resultado de cada ejecución se verifica en la pestaña Actions del
repositorio.

## Variables de entorno

| Variable | Valor por defecto | Uso |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5433/huertos` | Conexión JDBC a PostgreSQL |
| `DB_USER` | `huertos` | Usuario de la base |
| `DB_PASSWORD` | `huertos` | Clave de la base |
| `DEMO_PASSWORD` | vacía | Clave de los líderes de demostración; vacía = nadie puede ingresar |
| `COOKIE_SECURE` | `false` (`true` en la imagen) | La cookie de sesión solo viaja por HTTPS |
| `PORT` | `8080` | Puerto HTTP de la aplicación |

Las credenciales reales nunca se guardan en el repositorio: `.env` está en `.gitignore` y `.env.example` solo trae
valores de ejemplo.

## Solución de problemas

| Síntoma | Causa y solución |
|---|---|
| `Could not find a valid Docker environment` al correr pruebas | Docker Desktop no está abierto. Ábrelo y espera a que diga *Engine running*. |
| El backend no conecta a la base o falla la autenticación | Otro PostgreSQL ocupa el puerto. El proyecto usa **5433**; revisa `DB_URL`. |
| No puedo ingresar con `lider.demo1` | La base se creó sin `DEMO_PASSWORD` o con otra clave. Borra los datos (`down -v`) y vuelve a levantar con la variable. |
| Playwright falla al restaurar los datos | Falta la base `huertos_e2e` en un volumen creado antes: `docker compose exec postgres psql -U huertos -c "CREATE DATABASE huertos_e2e;"` |
| `port is already allocated` | El puerto 8080 o 5433 está ocupado. Cambia `APP_PORT` en `.env` o detén el otro proceso. |
| Tras desplegar, el ingreso no mantiene la sesión | El sitio no está en HTTPS y `COOKIE_SECURE=true`. Usa la URL `https://` del servicio. |

## Documentación

| Documento | Contenido |
|---|---|
| [01 Visión y alcance](docs/01_Vision_y_alcance_v0.5.md) | Objetivos, capacidades C1–C8 y alcance |
| [02 Plan de la iteración 1](docs/02_Plan_iteracion_1_v1.3.md) | Historias, esfuerzo, calendario y definición de terminado |
| [03 Historias de usuario](docs/03_Historias_de_usuario_I1_v1.2.md) | HU-001 a HU-020 con criterios de aceptación |
| [04 Casos de prueba](docs/04_Casos_de_prueba_I1_v1.2.md) | P-001 a P-055 y registro de ejecuciones |
| [05 Reglas, datos y trazabilidad](docs/05_Reglas_datos_y_trazabilidad_v1.2.md) | Glosario, reglas RN, mensajes MSG y datos de prueba |
| [06 Arquitectura y diseño](docs/06_Arquitectura_y_diseno_v0.1.md) | Monolito modular, modelo de datos, API y pruebas |

Estado del trabajo y siguientes pasos: [`SIGUIENTES_PASOS.md`](SIGUIENTES_PASOS.md).

## Forma de trabajo

- Una rama por historia (`hu-014-registrar-siembra`) y un pull request revisado por otro integrante.
- Mensajes de commit que citan la historia: `HU-014: valida el tamaño de la fotografía`.
- Una historia está terminada cuando cumple la definición de terminado del documento 02 §6.

## Corrección de autoridad y catálogo institucional (12/11/2026)

La comunidad escoge al líder y el MSP lo autoriza. El líder incorpora fichas del catálogo institucional de lectura y gestiona fotografías, cultivos y evidencias; no redacta información medicinal. La carga inicial contiene cuatro fichas trazadas a los anexos MSP, sin dosis ni usos terapéuticos incorporados. V4 conserva relaciones y registros operativos, sustituye el contenido educativo coincidente por su referencia institucional y mantiene privados los registros sin correspondencia.

La documentación actual está en [docs/v2_2](docs/v2_2/README.md). El candidato se verificó localmente con 94 pruebas Java, 21 recorridos y migración de una copia V3 a V4; el CI de la nueva integración debe ejecutarse en GitHub. No usar datos o contraseñas de demostración como acreditación de autorización real.

# Manual de instalación, pruebas y demostración

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Requisitos del entorno

La ejecución por contenedores requiere Git y Docker Desktop con el motor activo. Las pruebas locales requieren además Java 21 y Node 22 con npm. El puerto de aplicación utilizado por este manual es 8090; PostgreSQL se publica en 5433. Esos puertos deben estar disponibles. Las credenciales de este manual pertenecen exclusivamente a datos ficticios de demostración.

## 2. Obtener la revisión documentada

La instalación se realiza en un directorio sin una carpeta techmOS-I1 previa.

```powershell
git clone --branch develop --single-branch https://github.com/OscarGualo/ProyectoPlantasMedicinales.git techmOS-I1
Set-Location techmOS-I1
git switch --detach 78d87974152092c2d07125060021d0afdae9dac9
```

La selección del commit permite reproducir esta documentación aunque develop reciba cambios posteriores.

## 3. Configurar y arrancar con Docker

```powershell
@'
DB_USER=huertos
DB_PASSWORD=huertos
DEMO_PASSWORD=PruebaTechmos2026
APP_PORT=8090
'@ | Set-Content -Encoding ascii .env

docker compose -p techmos-i1-documentado --profile app up -d --build
docker compose -p techmos-i1-documentado --profile app ps
```

La aplicación se consulta en [localhost:8090](http://localhost:8090). Las cuentas lider.demo1, lider.demo2 y lider.demo3 utilizan la contraseña de demostración configurada. DEMO_PASSWORD se aplica al crear la base por primera vez; modificar la variable después no cambia automáticamente contraseñas existentes.

El proyecto Compose separado conserva su propio volumen. La construcción inicial descarga imágenes y dependencias. El resultado de CI registrado confirma que el Dockerfile de la revisión construyó correctamente.

## 4. Ejecutar las suites

Los comandos se ejecutan desde la raíz del repositorio y requieren Java 21 disponible en la misma terminal.

```powershell
java -version
node --version
npm.cmd --version

docker compose -p techmos-i1-documentado up -d --wait postgres

Push-Location backend
.\mvnw.cmd verify
Pop-Location

Push-Location frontend
npm.cmd ci
npm.cmd run build
npx.cmd playwright install chromium
npx.cmd playwright test --workers=1 --retries=0
Pop-Location
```

La referencia registrada es de 89 pruebas Java y 14 recorridos de navegador aprobados. Esto no ejecuta por sí solo los protocolos físicos y formales ni los nuevos casos complementarios de auditoría. Playwright utiliza huertos_e2e, puerto 8081 y Vite 5174; Testcontainers utiliza una base efímera para Java. El perfil e2e nunca se utiliza como perfil de la demostración pública porque incluye restauración de datos de ensayo.

## 5. Variables de ejecución

| Variable | Uso |
|---|---|
| DB_URL | Conexión JDBC de PostgreSQL. |
| DB_USER y DB_PASSWORD | Cuenta y contraseña de la base del entorno. |
| DEMO_PASSWORD | Contraseña inicial de las cuentas ficticias de V2. |
| APP_PORT | Puerto del host del servicio app en Compose. |
| PORT | Puerto interno de Spring Boot en ejecución directa. |
| COOKIE_SECURE | false en HTTP local; true en entorno HTTPS. |
| DB_URL_E2E | Conexión independiente para los recorridos de navegador. |
| HUERTOS_API | Destino del proxy de Vite en desarrollo. |

## 6. Construir un JAR integrado

```powershell
Push-Location frontend
npm.cmd ci
npm.cmd run build
Pop-Location

Push-Location backend
.\mvnw.cmd clean compile
New-Item -ItemType Directory -Force target/classes/static | Out-Null
Copy-Item -Path '../frontend/dist/*' -Destination target/classes/static -Recurse -Force
.\mvnw.cmd '-Dmaven.jar.forceCreation=true' '-DskipTests' package
Pop-Location
```

Omitir las pruebas en ese último empaquetado no acredita su aprobación; la ejecución de verify se registra por separado. El JAR resultante es backend/target/huertos-medicinales.jar. Una ejecución directa requiere DB_URL, DB_USER, DB_PASSWORD y DEMO_PASSWORD coherentes con su base. Compose configura esas variables para el arranque descrito en la sección 3.

## 7. Recorrido de demostración

1. Abrir el mapa y seleccionar un huerto publicado.
2. Consultar la ficha, sus plantas y los cultivos por zona.
3. Abrir el 3D, rotar, acercar y seleccionar una zona. Identificar el carácter esquemático del croquis.
4. Abrir el calendario y seleccionar los días de siembra.
5. Ingresar como lider.demo1 y consultar la gestión de su huerto.
6. Registrar una siembra con fotografía en 18/10/2026 a las 08:00 de America/Guayaquil. La convención del piloto la clasifica conforme.
7. Corregir la fecha a 03/10/2026 a las 08:00 con motivo y consultar las dos versiones.
8. Consultar la insignia. Su desactivación requiere que no quede otra siembra elegible de la comunidad; esa condición inicial se prepara en la base de ensayo, no se presupone para toda base de demostración.
9. Mostrar el rechazo de una imagen inválida y una operación de otro huerto.

El guion de demostración no oculta las incidencias del documento 08. La comprobación física de Android, el contraste con un croquis real y la validación cultural se registran por separado.

## 8. Detener la aplicación conservando datos

```powershell
docker compose -p techmos-i1-documentado --profile app down
```

El comando conserva el volumen de PostgreSQL. No es necesario borrar datos para detener el producto.

[Índice de documentación](README.md)

# Estado de la iteración 1 y siguientes pasos

> Archivo de continuidad: léelo antes de retomar el trabajo. Actualizado el **03/11/2026**.
> Entrega de la versión 1: **viernes 13/11/2026, 09:00** (doc 02).

## 1. Cómo retomar en 2 minutos

```bash
# 1. Base de datos (puerto 5433: el 5432 lo ocupa un PostgreSQL instalado en Windows)
docker compose up -d

# 2. Backend en http://localhost:8080 (carga datos de demostración con esta contraseña)
cd backend
DEMO_PASSWORD=demo-huertos-2026 ./mvnw spring-boot:run
#   PowerShell: $env:DEMO_PASSWORD="demo-huertos-2026"; .\mvnw.cmd spring-boot:run

# 3. Frontend en http://localhost:5173
cd frontend
npm install
npm run dev

# Pruebas del backend (requiere Docker Desktop abierto)
cd backend && ./mvnw verify            # hoy: 63 pruebas, 0 fallos, 1 omitida (P-050 contra REF-LUNA)

# Pruebas de navegador (Playwright; requiere el contenedor de la base arriba)
cd frontend && npx playwright test     # hoy: 11/11 en verde
#   Levanta solo el backend con el perfil e2e (puerto 8081, base huertos_e2e, reloj fijo 31/10/2026 12:00)
#   y Vite en 5174. Antes de cada caso restaura los datos con POST /api/publico/e2e/restaurar.
```

- Cuenta de demostración: `lider.demo1` / `demo-huertos-2026` (también `lider.demo2`, `lider.demo3`).
- Cuentas de prueba (tests y e2e, `backend/src/main/resources/e2e/datos-prueba.sql`): `l1`…`l5` / `clave-prueba`.
- Si cambias `V1__esquema.sql` o `V2__demo.sql`, borra la base: `docker compose down -v && docker compose up -d`.
- La base `huertos_e2e` la crea `docker/initdb/01-base-e2e.sql` solo en un volumen nuevo. Con un volumen anterior:
  `docker compose exec postgres psql -U huertos -c "CREATE DATABASE huertos_e2e;"`.

## 2. Qué está hecho

| Historia | Backend | Frontend | Pruebas automáticas |
|---|---|---|---|
| HU-001 Iniciar sesión | ✅ | ✅ `/ingresar` | P-001, P-002, P-003 |
| HU-002 Cerrar sesión | ✅ | ✅ barra | P-004, P-005 |
| HU-003 Ubicación | ✅ (zona horaria por provincia) | ✅ `/gestion` | P-006, P-007 |
| HU-004 Características | ✅ | ✅ `/gestion` | P-008, P-009, P-010 |
| HU-005 Publicar | ✅ | ✅ `/gestion` | P-011, P-012 |
| HU-006 Registrar planta | ✅ (foto JPEG/PNG ≤ 5 MB) | ✅ `/gestion/plantas` | P-013, P-014, P-015 |
| HU-007 Modificar planta | ✅ | ✅ `/gestion/plantas` | P-016, P-017, P-018 |
| HU-008 Registrar cultivo | ✅ | ✅ `/gestion/cultivos` | P-019, P-020 |
| HU-009 Estado del cultivo | ✅ | ✅ `/gestion/cultivos` | P-021, P-022 |
| HU-010 Mapa | ✅ `GET /api/publico/huertos` | ✅ `/` | P-023, P-024 (Playwright) |
| HU-011 Ficha pública | ✅ `GET /api/publico/huertos/{id}` | ✅ `/huertos/:id` | P-025, P-026 (Playwright) |
| HU-012 Recorrido 3D | — (usa la ficha) | ✅ `/huertos/:id/3d` | P-028 (Playwright); ⏳ P-027 manual en celular |
| HU-013 Cultivos de una zona | — | ✅ panel de zona en 3D | P-029, P-030, P-031 (Playwright) |
| HU-014 Registrar siembra | ✅ `POST /api/cultivos/{id}/siembras` | ✅ `/gestion/cultivos/:id/bitacora` | P-032 a P-037 |
| HU-015 Condición lunar | ✅ en `GET /api/cultivos/{id}/bitacora` | ✅ sello en la bitácora | P-038, P-039, P-040 |
| HU-019 Calendario lunar | ✅ `GET /api/publico/calendario-lunar` | ✅ `/calendario` | P-050 (⏳ ±1 h con REF-LUNA), P-051 |
| HU-020 Filtro por actividad | ✅ parámetro `actividad` | ✅ `/calendario` | P-052, P-053, P-054 (API y Playwright) |

Además:
- Módulo `luna`: fases con commons-suncalc, intervalos por puntos medios (RN-09), regla de siembra (RN-06), tabla de
  actividades por fase (RN-10), CAL-T para pruebas (`ConfigCalT`) y el calendario público.
- Paquete `actividades`: `Actividad` + `ActividadVersion` (solo inserción), clave de envío UUID contra duplicados.
- Escena 3D con three.js + @react-three/fiber (sin drei), en un paquete aparte que solo se descarga en `/huertos/:id/3d`
  (248 KB gzip; el inicial pesa 108 KB gzip). Sin WebGL o si falla la carga: MSG-13 + lista de zonas.
- Infraestructura e2e: `frontend/playwright.config.ts`, `frontend/e2e/` (fixtures `datos` y `consola`: un caso falla si
  la consola del navegador registra un error no declarado), perfil Spring `e2e` y `RestauracionE2eController`
  (solo existe con ese perfil).
- Catálogo completo MSG-01 a MSG-23 en `comun/Mensaje.java` (textos exactos del doc 03).
- Esquema completo en `V1__esquema.sql`, incluidas `actividad` y `actividad_version` (solo inserción, con trigger).
- Interfaz rediseñada con el mundo **«La etiqueta de la planta seca»** (ver `DESIGN.md` y sección 5).
- `PRODUCT.md` (contexto de producto) y `DESIGN.md` + `.impeccable/design.json` (sistema de diseño).

## 3. Qué falta, en orden (calendario del doc 02)

### Bloque 9–10/11: 3D, siembras, condición lunar y calendario — ✅ hecho (03/11)
Queda solo **P-027** (manual): recorrer el 3D de H1 en un celular Android de gama media y compararlo con el croquis CR1.

### Bloque 11/11: correcciones, historial y reto
7. **HU-016 Corregir una siembra**: nueva versión `numero+1` con motivo (MSG-12), autoridad (MSG-03), foto opcional
   (MSG-07). P-041 a P-044.
8. **HU-017 Historial**: versiones de la más reciente a la más antigua; sin correcciones → MSG-23. P-045, P-046.
9. **HU-018 Reto Documentación lunar**: insignia calculada al consultar con la versión vigente (RN-08); sin
   siembras elegibles → MSG-18. `GET /api/publico/huertos/{id}/reto`, ruta `/huertos/:id/reto`. P-047 a P-049.

### Bloque 12/11: integración, despliegue y documentos
10. **Playwright**: falta P-055 (los demás casos de interfaz ya están en `frontend/e2e/`). Sumar al e2e los casos de
    HU-016 a HU-018 que tengan criterio de interfaz.
11. **Prueba manual con evidencia** P-027 (3D en celular Android) → capturas en `docs/evidencias/`.
12. **Registrar resultados** en las tablas de ejecución de `docs/04_Casos_de_prueba_I1_v1.2.md`.
13. **Dockerfile** multietapa (node → maven → JRE 21) y **CI** `.github/workflows/ci.yml` (backend `mvn verify`,
    frontend `npm run build`, e2e).
14. **Hosting** con plan gratuito + PostgreSQL administrada; `COOKIE_SECURE=true`, `DEMO_PASSWORD` por variable.
15. **Documentos**:
    - Doc 05 §6: completar el catálogo MSG-02 a MSG-23 (el PDF original solo trae MSG-01).
    - Doc 06: paquete `consulta` (ficha y lista pública), `GET /api/publico/huertos` devuelve conteos, CAL-T sustituye
      `CalculadoraFase` (no la fuente de efemérides), zona `Pacific/Galapagos`, base local en 5433, paquete
      `actividades`, perfil `e2e`, three.js sin drei.
    - Listado de herramientas y tecnologías con versiones; README; informe `Proyecto-Version1-nombreEquipo.pdf`.

## 4. Pendientes que dependen del cliente o del equipo

- Croquis real **CR1** de H1 (hoy hay uno provisional en `V2__demo.sql`).
- Fuente oficial de **REF-LUNA** (fases de noviembre de 2026) para la prueba P-050.
- Elección del **hosting**.
- Validar con un líder los intervalos de fase (RN-09) y la tabla RN-10 (D-06).
- Lámina de **hierba luisa** (*Cymbopogon citratus*): no se encontró una de dominio público con el mismo estilo.

## 5. Cómo se trabaja la interfaz (4 skills combinadas)

Instaladas: `ui-ux-pro-max` (en `~/.claude/skills`) y en `.claude/skills/`: `impeccable`, `taste-skill`
(`design-taste-frontend`) y `emil-design-eng`.

| Capa | Skill | Regla |
|---|---|---|
| Base obligatoria | ui-ux-pro-max | Gana todo conflicto de accesibilidad y usabilidad (contraste, 44 px, navegación, formularios) |
| Proceso | impeccable | Contrato en `.impeccable/surfaces/`, detector `impeccable detect --json frontend/src`, revisión final, `DESIGN.md` |
| Dirección de arte | taste-skill | Sin antetítulos, degradados, sombras en reposo, vidrio ni líneas finas; `inspect.mjs` en 1440/768/390 |
| Detalles | emil-design-eng | `cubic-bezier(0.23, 1, 0.32, 1)`, menos de 300 ms, `scale(0.97)` al presionar, hover solo con puntero fino |

Para pantallas nuevas (3D, bitácora, calendario, reto): **heredan el mundo** de `DESIGN.md`; no se vuelve a sortear
dirección. Al terminar cada una:

```bash
node .claude/skills/taste-skill/scripts/inspect.mjs http://localhost:5173/<ruta> --app --out .taste-skill/<nombre>
.claude/skills/impeccable/scripts/impeccable detect --json frontend/src
```

## 6. Decisiones tomadas que no están en los documentos 01–06

- Características del huerto en texto libre (los documentos no definen catálogos ni mensaje de error para ellos).
- H3 de prueba tiene un quinto líder `l5` (el doc 05 no dice quién lo gestiona).
- Un cultivo con planta de otro huerto responde MSG-03 (no hay mensaje específico).
- La zona horaria se asigna sola: Galápagos → `Pacific/Galapagos`; el resto → `America/Guayaquil`.
- Los datos de prueba fijan E1 «Plantae probatoria» y los cultivos sembrados el 30/10 (para que P-021 muestre una
  fecha nueva con el reloj fijo).
- H1 de prueba tiene además las zonas C1 (camino), CE1 (cerca) y B1 (bodega) para comparar el 3D con el croquis.
- Siembra: la fecha y hora se escriben en la hora local del huerto; el servidor las convierte con su zona horaria.
- Calendario: la fase de cada día es la vigente a las 12:00 de America/Guayaquil; horas en formato de 24 h.
- Señal en 3D: el sello de cada cultivo lleva texto («Caliente», «Fría», «Sin clasificar»), nunca solo color.

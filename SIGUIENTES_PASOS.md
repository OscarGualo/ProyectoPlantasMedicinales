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
cd backend && ./mvnw verify            # hoy: 48/48 en verde
```

- Cuenta de demostración: `lider.demo1` / `demo-huertos-2026` (también `lider.demo2`, `lider.demo3`).
- Cuentas de prueba (solo en tests, `datos-prueba.sql`): `l1`…`l5` / `clave-prueba`.
- Si cambias `V1__esquema.sql` o `V2__demo.sql`, borra la base: `docker compose down -v && docker compose up -d`.

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
| HU-010 Mapa | ✅ `GET /api/publico/huertos` | ✅ `/` | ⏳ P-023, P-024 (Playwright) |
| HU-011 Ficha pública | ✅ `GET /api/publico/huertos/{id}` | ✅ `/huertos/:id` | ⏳ P-025, P-026 (Playwright) |

Además:
- Módulo `luna` listo: cálculo de fases con commons-suncalc, intervalos por puntos medios (RN-09), regla de
  siembra (RN-06), tabla de actividades por fase (RN-10) y CAL-T para pruebas (`ConfigCalT`). Falta exponerlo por API.
- Catálogo completo MSG-01 a MSG-23 en `comun/Mensaje.java` (textos exactos del doc 03).
- Esquema completo en `V1__esquema.sql`, incluidas `actividad` y `actividad_version` (solo inserción, con trigger).
- Interfaz rediseñada con el mundo **«La etiqueta de la planta seca»** (ver `DESIGN.md` y sección 5).
- `PRODUCT.md` (contexto de producto) y `DESIGN.md` + `.impeccable/design.json` (sistema de diseño).

## 3. Qué falta, en orden (calendario del doc 02)

### Bloque 9–10/11: 3D, siembras, condición lunar y calendario
1. **HU-012 Recorrer el huerto en 3D** (frontend): instalar `three`, `@react-three/fiber`, `@react-three/drei`;
   cargar la ruta con `lazy` (el paquete pesa). La escena se arma con `ficha.zonas[].geometria` (`x`, `z` = centro;
   `ancho` en x, `largo` en z, `alto`). `OrbitControls`. Si no hay WebGL o falla la carga: MSG-13 + lista de zonas y
   cultivos del mismo huerto (ErrorBoundary). Ruta `/huertos/:id/3d` (hoy es «en construcción» en `App.tsx`).
2. **HU-013 Cultivos de una zona en 3D**: clic o toque en una zona → panel con sus cultivos y sello caliente,
   fría o sin clasificar; zona sin cultivos → MSG-22. Datos ya disponibles en la ficha pública.
3. **HU-014 Registrar una siembra** (backend + frontend):
   - Entidades `Actividad` y `ActividadVersion` (tablas ya existen), `ServicioActividades.registrar` transaccional.
   - Orden: cultivo ajeno → MSG-19 (403); `clave_envio` repetida → devuelve la existente (200); fecha futura →
     MSG-08; foto inválida → MSG-07; error al guardar → rollback y MSG-11 (503).
   - `POST /api/cultivos/{id}/siembras` (multipart + `claveEnvio`), `GET /api/cultivos/{id}/bitacora`.
   - Frontend: `/gestion/cultivos/:id/bitacora`; la clave UUID se genera al abrir el formulario y se reutiliza.
   - Pruebas P-032 a P-037 (P-037 con `@MockitoBean` del repositorio de fotos que falla).
4. **HU-015 Condición lunar**: en la bitácora, `CalculadoraFase` + `ReglaSiembra` con la zona horaria del huerto
   → MSG-15, MSG-16 o MSG-17. Pruebas P-038 a P-040 con `@Import(ConfigCalT.class)`.
5. **HU-019 Calendario lunar**: `GET /api/publico/calendario-lunar?desde&dias&actividad` (30 días, fase del día a las
   12:00 en America/Guayaquil, instantes de fases principales, rango ±12 meses → MSG-20). Frontend `/calendario`.
   Pruebas P-050, P-051 con el cálculo real (sin CAL-T).
6. **HU-020 Filtro por actividad**: `ActividadAgricola` ya define qué fase recomienda cada actividad. P-052 a P-054.

### Bloque 11/11: correcciones, historial y reto
7. **HU-016 Corregir una siembra**: nueva versión `numero+1` con motivo (MSG-12), autoridad (MSG-03), foto opcional
   (MSG-07). P-041 a P-044.
8. **HU-017 Historial**: versiones de la más reciente a la más antigua; sin correcciones → MSG-23. P-045, P-046.
9. **HU-018 Reto Documentación lunar**: insignia calculada al consultar con la versión vigente (RN-08); sin
   siembras elegibles → MSG-18. `GET /api/publico/huertos/{id}/reto`, ruta `/huertos/:id/reto`. P-047 a P-049.

### Bloque 12/11: integración, despliegue y documentos
10. **Playwright** (ya instalado en `frontend`): P-023 a P-026, P-050 a P-055 contra la app levantada.
11. **Pruebas manuales con evidencia** P-027 a P-031 (3D en celular Android) → capturas en `docs/evidencias/`.
12. **Registrar resultados** en las tablas de ejecución de `docs/04_Casos_de_prueba_I1_v1.2.md`.
13. **Dockerfile** multietapa (node → maven → JRE 21) y **CI** `.github/workflows/ci.yml` (backend `mvn verify`,
    frontend `npm run build`, e2e).
14. **Hosting** con plan gratuito + PostgreSQL administrada; `COOKIE_SECURE=true`, `DEMO_PASSWORD` por variable.
15. **Documentos**:
    - Doc 05 §6: completar el catálogo MSG-02 a MSG-23 (el PDF original solo trae MSG-01).
    - Doc 06: paquete `consulta` (ficha y lista pública), `GET /api/publico/huertos` devuelve conteos, CAL-T sustituye
      `CalculadoraFase` (no la fuente de efemérides), zona `Pacific/Galapagos`, base local en 5433.
    - Listado de herramientas y tecnologías con versiones; README; informe `Proyecto-Version1-nombreEquipo.pdf`.
16. **Primer commit**: todavía no hay ningún commit en el repositorio.

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

**ESCUELA POLITÉCNICA NACIONAL**
Ingeniería de Software
CALIDAD DE SOFTWARE (ISWD652) · GR2SW · Período 2026-B

# Arquitectura y diseño del producto

**Huertos Medicinales Comunitarios**
Versión 0.1 · Propuesta para la versión 1

- **Equipo:** [Nombre del equipo]
- **Integrantes:**
  - [Integrante 1]
  - [Integrante 2]
  - [Integrante 3]
  - [Integrante 4]
  - [Integrante 5]
  - [Integrante 6]
- **Fecha:** 2 de noviembre de 2026

*Propuesta del equipo; se actualiza con lo realmente construido*

---

## 1. Propósito y alcance

Este documento propone la arquitectura y el diseño de la versión 1 de Huertos Medicinales Comunitarios. Sirve de guía para que el equipo de 6 integrantes construya las historias HU-001 a HU-020 en el plazo del 3 al 12 de noviembre de 2026, y como base del entregable «arquitectura y diseño del producto» y del «listado de herramientas y tecnologías».

Son recomendaciones: el equipo las confirma o ajusta el 3/11 según su experiencia. Antes de la entrega del 13/11, el documento se actualiza para describir lo que realmente se implementó; no debe presentarse como construido algo que solo está propuesto.

Se apoya en los documentos 01 (visión y alcance), 02 (plan), 03 (historias), 04 (casos de prueba) y 05 (reglas, mensajes y trazabilidad). Las vistas siguen el modelo C4 (contexto, contenedores y componentes), complementado con el modelo de datos y diagramas de secuencia.

## 2. Impulsores de la arquitectura

La arquitectura responde a estos impulsores. Cada decisión de la sección 3 indica cuál atiende.

| Impulsor | Origen | Consecuencia para el diseño |
|---|---|---|
| Plazo de 8 días hábiles con 6 personas | D-07, plan | Pocas piezas, un solo despliegue y tecnologías que el equipo ya conozca o aprenda rápido. |
| Pruebas ejecutadas con resultados como entregable | Consigna de Calidad | Diseño testeable: reloj y fases lunares sustituibles, API comprobable sin interfaz, pruebas automáticas en cada cambio. |
| Solo el líder modifica su huerto | RN-01 | Autorización en el servidor por huerto, no solo ocultando botones. |
| Consulta pública sin cuenta y datos privados protegidos | RN-02, LOPDP | Rutas y respuestas públicas separadas que nunca incluyen datos privados. |
| Ninguna siembra sin evidencia | RN-05 | La fotografía y la siembra se guardan en una sola transacción. |
| Reintentos sin duplicados | RN-04 | Clave de envío única por siembra. |
| Correcciones con historial | RN-07 | Versiones que solo se agregan, nunca se sobrescriben. |
| Prácticas lunares obligatorias y verificables | RN-06, RN-09, RN-10 | Módulo lunar aislado, con cálculo astronómico y una fuente controlada para pruebas. |
| 3D en celulares y conectividad variable | Visión 1.6 y 3.3 | Escena liviana generada desde datos y alternativa en lista. |
| Crecimiento posterior: editor 3D, almacén, foro, gamificación completa | Visión 2.3 | Módulos con límites claros que se pueden ampliar sin rehacer lo existente. |

## 3. Decisiones principales

Cada decisión indica su motivo, la alternativa descartada y la señal que obligaría a revisarla.

| ID | Decisión | Motivo | Alternativa descartada | Revisar si… |
|---|---|---|---|---|
| AD-01 | Monolito modular: un backend organizado por módulos de negocio. | Un solo despliegue y depuración simple para 8 días; los módulos mantienen límites claros. | Microservicios: multiplican despliegues, red y pruebas sin beneficio para el piloto. | Un módulo necesita escalar o desplegarse aparte. |
| AD-02 | Backend Spring Boot (Java) y frontend React; Spring Boot sirve la SPA compilada. | Java es el lenguaje de cursos previos de la carrera (por confirmar con el equipo); Spring cubre seguridad, validación, persistencia y pruebas. Mismo origen: sin CORS ni tokens. | Node.js (NestJS) o Django: igual de válidos si el equipo los domina más. | El 3/11 la mayoría del equipo domina mejor otro stack. |
| AD-03 | Sesión con cookie (Spring Security) y protección CSRF. | Más simple y segura que JWT para una SPA del mismo origen; cierre de sesión real en el servidor (HU-002). | JWT en el navegador: el cierre de sesión no invalida el token sin trabajo adicional. | Aparece un cliente móvil nativo u otro dominio. |
| AD-04 | La escena 3D se genera desde las zonas del huerto guardadas como datos. | Una sola fuente de verdad para escena, zonas y cultivos; sin modelado en Blender; prepara el editor del líder (D-01). | Modelos 3D (glTF) por huerto: costosos de hacer y desvinculados de los datos. | El cliente exige realismo visual que las figuras simples no logran. |
| AD-05 | Fotografías en PostgreSQL. | La foto y la siembra se guardan o se revierten juntas (RN-05); sin otro servicio. | Almacenamiento de objetos (S3 o similar): mejor a escala, pero agrega otra pieza y otra falla posible. | El volumen de fotos crece más allá del piloto. |
| AD-06 | Fases lunares detrás de una interfaz, con reloj inyectable. | Permite el cálculo real (commons-suncalc) y CAL-T en pruebas, como exige el documento 04. | Llamar a un servicio web de efemérides: dependencia de red y resultados no controlables. | La validación con el líder (D-06) cambia la definición de fase. |
| AD-07 | Condición lunar e insignia se calculan al consultarlas; no se guardan. | Siempre coherentes con la versión vigente; una corrección se refleja sin recálculo aparte. | Guardar resultados y recalcular por eventos: más código y riesgo de quedar desactualizados. | Las consultas se vuelven lentas con muchos huertos. |
| AD-08 | Versiones de actividades de solo inserción. | Historial completo y auditable (RN-07, HU-017). | Actualizar el registro y guardar un log aparte: el log puede divergir. | No aplica en la versión 1. |
| AD-09 | Validaciones en el servidor con el catálogo MSG-01 a MSG-23. | Los criterios y casos esperan mensajes exactos; la interfaz no es una barrera de seguridad. | Validar solo en el navegador. | No aplica. |
| AD-10 | Instantes en UTC y zona horaria guardada por huerto. | La fase depende de la hora local: Ecuador continental (UTC-5) y Galápagos (UTC-6). | Guardar horas locales sin zona. | No aplica. |

## 4. Vista de contexto

> **Figura 1.** Contexto del sistema (C4, nivel 1). *(Diagrama disponible en el PDF original.)*

El sistema no se integra con sistemas del MSP en la versión 1: el equipo crea las cuentas de los líderes con la lista que entregue el cliente. El único servicio externo es el mapa base de OpenStreetMap, cuya política de uso exige mostrar la atribución y no hacer uso intensivo; para un piloto es suficiente. El representante gubernamental aparece punteado porque sus permisos están pendientes (D-04).

## 5. Vista de contenedores

> **Figura 2.** Contenedores (C4, nivel 2). «Contenedor» en C4 es una unidad ejecutable, no necesariamente Docker. *(Diagrama disponible en el PDF original.)*

| Contenedor | Tecnología | Responsabilidad |
|---|---|---|
| Aplicación web (SPA) | React, Vite, Leaflet, React Three Fiber | Pantallas públicas y de gestión, mapa, escena 3D, calendario. No contiene reglas de seguridad: todo se valida en el backend. |
| Backend | Spring Boot (Java LTS) | API REST, sesión, autorización, validaciones, reglas de negocio, cálculo lunar y entrega de los archivos de la SPA. |
| Base de datos | PostgreSQL | Datos del huerto, plantas, cultivos, actividades con sus versiones y fotografías. Esquema versionado con Flyway. |

## 6. Componentes del backend

> **Figura 3.** Módulos del backend (C4, nivel 3). *(Diagrama disponible en el PDF original.)*

| Módulo | Responsabilidad | Historias | Reglas |
|---|---|---|---|
| `acceso` | Inicio y cierre de sesión; verificar que el usuario de la sesión es el líder del huerto afectado. | HU-001, HU-002 | RN-01 |
| `huertos` | Ficha, ubicación, características, publicación y zonas (que también forman la escena 3D). | HU-003–HU-005, HU-010–HU-012 | RN-01, RN-02 |
| `plantas` | Fichas educativas por huerto. | HU-006, HU-007 | RN-02, RN-03 |
| `cultivos` | Cultivo = planta + zona del mismo huerto, con estado. | HU-008, HU-009, HU-013 | RN-03 |
| `actividades` | Registro de siembras, clave de envío, versiones y corrección. | HU-014, HU-016, HU-017 | RN-04, RN-05, RN-07 |
| `fotos` | Validar el contenido real (JPEG o PNG) y el tamaño; guardar y servir imágenes. | HU-006, HU-014, HU-016 | RN-05 |
| `luna` | Fase de un instante, regla de siembra, calendario y actividades por fase. | HU-015, HU-019, HU-020 | RN-06, RN-09, RN-10 |
| `retos` | Elegibilidad e insignia *Documentación lunar*. | HU-018 | RN-08 |
| `comun` | Errores de negocio con su código MSG, manejador de errores, configuración del reloj. | Todas | — |

**Reglas de dependencia:** los controladores solo traducen HTTP y delegan; las reglas viven en los servicios y clases de dominio. No se permiten ciclos: `actividades` usa `cultivos` y `luna`, y `retos` usa `actividades` y `luna`, nunca al revés. El módulo `luna` no depende de la base de datos, por lo que se prueba de forma unitaria.

**Estructura de paquetes sugerida:**

```text
ec.epn.huertos
├── acceso        SesionController · AutorizacionHuerto · Usuario
├── huertos       HuertoGestionController · HuertoPublicoController
│                 ServicioHuertos · Huerto · Zona
├── plantas       PlantaController · ServicioPlantas · Planta
├── cultivos      CultivoController · ServicioCultivos · Cultivo · EstadoCultivo
├── actividades   SiembraController · ServicioActividades
│                 Actividad · ActividadVersion
├── fotos         ServicioFotos · Foto · ValidadorImagen
├── luna          ServicioFaseLunar · FuenteFaseLunar · EfemeridesSuncalc
│                 ReglaSiembra · ActividadesPorFase · CalendarioController
├── retos         ServicioRetos · RetoController
└── comun         ErrorNegocio · Mensaje (MSG-xx) · ManejadorErrores · RelojConfig
```

## 7. Modelo de datos

> **Figura 4.** Modelo de datos de la versión 1. *(Diagrama disponible en el PDF original.)*

- **Mismo huerto garantizado en la base de datos:** `cultivo` guarda `huerto_id` y usa claves foráneas compuestas `(planta_id, huerto_id)` y `(zona_id, huerto_id)`. Aunque el servicio ya valida MSG-09, la base impide un cultivo con planta o zona de otro huerto.
- **Idempotencia:** `actividad.clave_envio` es única. Un reintento con la misma clave devuelve la siembra existente (HU-014, CA-02).
- **Historial:** `actividad_version` tiene restricción única `(actividad_id, numero)`. La versión vigente es la de mayor número. Las versiones nunca se actualizan ni se borran.
- **Restricciones de defensa:** `CHECK` de superficie ≥ 0, latitud entre -90 y 90, longitud entre -180 y 180, y «publicado implica coordenadas». Los mensajes al usuario los produce el servicio; la base es la última barrera.
- **Comunidad separada del huerto:** evita fijar cardinalidades que siguen pendientes (D-03). La insignia se calcula por comunidad.
- **No se guardan datos derivados:** la condición lunar y la insignia se calculan al consultar (AD-07).
- **Migraciones Flyway:** V1 crea el esquema y V2 carga los datos de demostración. Los datos de prueba de la sección 7 del documento 05 se cargan solo en las pruebas.

## 8. Diseño del frontend

| Ruta | Acceso | Pantalla e historias |
|---|---|---|
| `/` | Público | Mapa del Ecuador con marcadores (HU-010). |
| `/huertos/:id` | Público | Ficha pública con plantas y cultivos (HU-011). |
| `/huertos/:id/3d` | Público | Escena 3D, selección de zonas y señalética (HU-012, HU-013). |
| `/huertos/:id/reto` | Público | Reto e insignia *Documentación lunar* (HU-018). |
| `/calendario` | Público | Calendario lunar y filtro por actividad (HU-019, HU-020). |
| `/ingresar` | Público | Inicio de sesión (HU-001). |
| `/gestion` | Líder | Ubicación, características y publicación del huerto (HU-003 a HU-005). |
| `/gestion/plantas` | Líder | Registro y modificación de fichas (HU-006, HU-007). |
| `/gestion/cultivos` | Líder | Cultivos y estados (HU-008, HU-009). |
| `/gestion/cultivos/:id/bitacora` | Líder | Siembras, condición lunar, corrección e historial (HU-014 a HU-017). |

### 8.1 Escena 3D generada desde datos

Cada zona guarda su tipo y una geometría simple en metros. El componente de la escena dibuja el terreno y una caja por zona; los cultivos se dibujan como marcadores dentro de su zona, coloreados por la clasificación de su planta (rojo caliente, verde fría, gris sin clasificar). Ejemplo de geometría:

```json
{ "codigo": "Z1", "tipo": "CULTIVO",
  "geometria": { "x": 0, "z": 0, "ancho": 4.0, "largo": 1.2, "alto": 0.3 } }
```

- El croquis aprobado (CR1) se traduce a estas zonas en la migración de datos; no se requiere modelado 3D.
- Controles de órbita (rotar, acercar y alejar) de la librería drei; la selección se hace con el clic o toque sobre la zona.
- Si el navegador no soporta WebGL o la carga falla, un límite de error muestra MSG-13 y la lista de zonas y cultivos (HU-012, CA-02).
- Figuras simples sin texturas para funcionar en celulares de gama media.

### 8.2 Formularios y comunicación con el backend

- Un cliente HTTP común agrega el token CSRF, envía la cookie de sesión y convierte las respuestas de error en el mensaje MSG correspondiente.
- El formulario de siembra genera la clave de envío al abrirse y la reutiliza en cada reintento; así el reenvío por falla de red no duplica la siembra.
- Los botones deshabilitados ayudan al usuario, pero no sustituyen las validaciones del servidor.

## 9. API REST

Las rutas públicas responden sin sesión y nunca incluyen datos privados. Las de gestión exigen sesión y que el usuario sea el líder del huerto afectado.

| Método | Ruta | Acceso | Historias | Errores posibles |
|---|---|---|---|---|
| POST | `/api/sesion` | Público | HU-001 | MSG-01 |
| DELETE | `/api/sesion` | Sesión | HU-002 | — |
| GET | `/api/publico/huertos` | Público | HU-010 | — |
| GET | `/api/publico/huertos/{id}` | Público | HU-011, HU-012, HU-013 | MSG-14 |
| GET | `/api/publico/huertos/{id}/reto` | Público | HU-018 | MSG-14 |
| GET | `/api/publico/calendario-lunar?desde&dias&actividad` | Público | HU-019, HU-020 | MSG-20 |
| GET | `/api/fotos/{id}` | Público\* | HU-006, HU-014 | MSG-14 |
| PUT | `/api/huertos/{id}/ubicacion` | Líder | HU-003 | MSG-02, 03, 06 |
| PUT | `/api/huertos/{id}/caracteristicas` | Líder | HU-004 | MSG-02, 03, 04, 05 |
| POST | `/api/huertos/{id}/publicacion` | Líder | HU-005 | MSG-03, 21 |
| POST | `/api/huertos/{id}/plantas` | Líder | HU-006 | MSG-03, 04, 07 |
| PUT | `/api/plantas/{id}` | Líder | HU-007 | MSG-03, 04, 07 |
| POST | `/api/huertos/{id}/cultivos` | Líder | HU-008 | MSG-03, 09 |
| PATCH | `/api/cultivos/{id}/estado` | Líder | HU-009 | MSG-03, 10 |
| POST | `/api/cultivos/{id}/siembras` | Líder | HU-014 | MSG-07, 08, 11, 19 |
| GET | `/api/cultivos/{id}/bitacora` | Líder | HU-014, HU-015 | MSG-03 |
| POST | `/api/siembras/{id}/correcciones` | Líder | HU-016 | MSG-03, 07, 12 |
| GET | `/api/siembras/{id}/historial` | Líder | HU-017 | MSG-03, 23 |

\* Solo fotografías de contenido publicado; las demás responden como no encontradas.

**Formato de error y correspondencia con HTTP:**

```http
HTTP 400
{ "codigo": "MSG-04",
  "mensaje": "Completa el campo obligatorio: Nombre.",
  "campo": "nombre" }
```

| HTTP | Uso |
|---|---|
| 400 | Datos inválidos: MSG-04 a MSG-10, MSG-12, MSG-20, MSG-21. |
| 401 | Credenciales incorrectas (MSG-01) o sesión finalizada (MSG-02). |
| 403 | Operación sobre un huerto, cultivo o siembra ajenos (MSG-03, MSG-19). |
| 404 | Huerto no publicado o inexistente (MSG-14); no se distingue entre ambos para no revelar datos. |
| 503 | Falla al guardar la fotografía (MSG-11); la transacción se revierte completa. |

El límite de carga de archivos del servidor se configura algo por encima de 5 MB (por ejemplo, 6 MB) para que el propio servicio valide el tamaño y responda MSG-07, en lugar de un error genérico del servidor.

## 10. Escenarios dinámicos

### 10.1 Registrar una siembra (HU-014)

> **Figura 5.** Registro de una siembra con clave de envío y transacción única. *(Diagrama disponible en el PDF original.)*

### 10.2 Consultar el reto y la condición lunar (HU-015, HU-018)

> **Figura 6.** Cálculo de la insignia a partir de la condición lunar de las siembras vigentes. *(Diagrama disponible en el PDF original.)*

### 10.3 Cálculo de la fase lunar (RN-09)

La fuente de efemérides entrega los instantes de las fases principales (luna nueva, cuarto creciente, luna llena, cuarto menguante). Cada fase abarca desde el punto medio con la fase principal anterior hasta el punto medio con la siguiente:

```text
faseEn(instante, zona):
  eventos ← efemérides entre instante − 10 días e instante + 10 días
  para cada evento Pi con anterior Pi−1 y siguiente Pi+1:
    inicio ← punto medio (Pi−1, Pi); fin ← punto medio (Pi, Pi+1)
    si inicio ≤ instante < fin: devolver fase de Pi
  devolver vacío   // → pendiente de comprobación (MSG-17)

Luna nueva → luna oscura   ·  Cuarto creciente  → luna creciente
Luna llena → luna llena    ·  Cuarto menguante  → luna menguante
```

En el calendario, la fase de un día es la vigente a las 12:00 en la zona horaria correspondiente. En pruebas, CAL-T implementa la misma interfaz devolviendo una fase fija por día.

## 11. Seguridad y protección de datos

- Contraseñas con hash BCrypt; mensaje único MSG-01 para no revelar si el usuario existe.
- Cookie de sesión `HttpOnly`, `Secure` y `SameSite=Lax`; protección CSRF de Spring Security en las operaciones de modificación.
- Autorización por huerto en el servicio, verificada también ante solicitudes directas (casos de HU-001 CA-03 y HU-014 CA-05).
- Respuestas públicas construidas con objetos específicos que no contienen el contacto del responsable ni datos de la cuenta (RN-02).
- La imagen se valida por su contenido (firma del archivo y decodificación), no solo por la extensión.
- Credenciales y cadenas de conexión en variables de entorno; nunca en el repositorio.
- Datos personales mínimos en la versión 1 (LOPDP): no se registran miembros, fechas de nacimiento ni género.

## 12. Atributos de calidad

| Atributo (ISO/IEC 25010) | Cómo lo atiende el diseño | Cómo se comprueba |
|---|---|---|
| Seguridad | Sesión en servidor, autorización por huerto, CSRF, separación pública y privada. | Casos P-003, P-005, P-036, P-043, P-025. |
| Fiabilidad | Transacción única con la foto; idempotencia; versiones sin sobrescritura. | Casos P-033, P-037, P-041. |
| Adecuación funcional | Reglas en el dominio y catálogo de mensajes. | Casos P-001 a P-055. |
| Mantenibilidad | Módulos sin ciclos, reglas aisladas, migraciones versionadas. | Revisión de código en cada pull request; pruebas unitarias del dominio. |
| Eficiencia | Escena con figuras simples; fotos limitadas a 5 MB. | Propuesta: consultas públicas en menos de 2 s en el hosting de demostración; escena fluida en un celular de gama media (P-027). |
| Usabilidad | Mensajes en lenguaje simple; alternativa en lista; diseño adaptable a celulares. | Observaciones de la demostración con el cliente. |
| Portabilidad | Una imagen Docker con la aplicación completa. | La misma imagen corre en local, en CI y en el hosting. |

## 13. Estrategia de pruebas

La estrategia combina niveles según lo que cada caso necesita comprobar. Las pruebas automáticas llevan el ID del caso en su nombre, por ejemplo `@DisplayName("P-032 Registro de una siembra con fotografía")`, para enlazar el reporte de CI con el documento 04. Los resultados obtenidos se registran en el documento 04 con fecha, responsable y versión.

| Nivel | Herramientas | Qué verifica | Casos principales |
|---|---|---|---|
| Unitario | JUnit 5, AssertJ, Mockito | Regla de siembra, cálculo de fase, elegibilidad de la insignia, validadores. | P-038–P-040, P-047–P-049 (lógica) |
| Integración de API | Spring Boot Test, MockMvc, Testcontainers (PostgreSQL) | Autorización, persistencia, transacción, idempotencia y mensajes exactos. | P-001–P-003 a P-021–P-022, P-032–P-037, P-041–P-044, P-045–P-046 |
| Recorrido web | Playwright | Navegación, mapa, formularios, calendario y recorrido de la demostración. | P-023–P-024, P-025–P-026, P-050–P-051, P-052–P-054, P-055 |
| Manual con evidencia | Navegador y celular Android; capturas | Experiencia 3D en dispositivos reales y comparación con el croquis. | P-027–P-028, P-029–P-031 |

- **Dobles de prueba:** reloj fijo (`java.time.Clock`) en 31/10/2026 12:00 y fuente de fases CAL-T, inyectados por configuración de pruebas.
- **Falla al guardar la foto:** se simula con un repositorio que lanza un error, comprobando que no queda la siembra (HU-014, CA-06).
- **Efemérides reales:** una prueba compara el cálculo de noviembre de 2026 con REF-LUNA, con tolerancia de una hora.
- **Cobertura:** JaCoCo genera un reporte en cada ejecución. Es una señal de qué código se ejecutó, no una prueba de que los casos sean buenos.
- **Integración continua:** GitHub Actions compila y ejecuta todas las pruebas en cada pull request; la rama principal solo acepta cambios con las pruebas en verde y una revisión.

## 14. Despliegue y entornos

> **Figura 7.** Flujo desde el entorno local hasta el hosting de demostración. *(Diagrama disponible en el PDF original.)*

| Entorno | Contenido | Datos |
|---|---|---|
| Local | PostgreSQL con docker compose; backend y frontend en modo desarrollo. | Datos de demostración (Flyway V2). |
| CI | GitHub Actions con PostgreSQL efímero (Testcontainers). | Datos de prueba del documento 05, creados por cada prueba. |
| Demostración | Un contenedor con la aplicación y una base PostgreSQL administrada, en un servicio con plan gratuito. | Datos de demostración; contraseñas desde variables de entorno. |

**Construcción en varias etapas:** la imagen compila primero el frontend, copia el resultado al backend y empaqueta todo en un solo archivo ejecutable. Antes de elegir el hosting el 3/11, confirmar las condiciones vigentes del plan gratuito: algunos suspenden la aplicación por inactividad, por lo que conviene abrirla unos minutos antes de la demostración.

## 15. Repositorio y forma de trabajo

```text
huertos-medicinales/
├── backend/                                  proyecto Maven (Spring Boot)
│   └── src/main/resources/db/migration/      V1__esquema.sql, V2__demo.sql
├── frontend/                                 proyecto Vite (React)
├── docs/                                     documentos 01 a 06 y evidencias de pruebas
├── docker-compose.yml                        PostgreSQL local
├── Dockerfile                                imagen de la aplicación
├── .github/workflows/                        ci.yml
└── README.md                                 instalación, ejecución, pruebas y despliegue
```

- Una rama por historia (por ejemplo, `hu-014-registrar-siembra`) y un pull request con revisión de otro integrante.
- El pull request se aprueba solo si cumple la definición de terminado del plan: criterios cumplidos, casos ejecutados y registrados, CI en verde y documentación actualizada.
- Mensajes de commit que citan la historia, por ejemplo «HU-014: valida el tamaño de la fotografía».
- Un tablero con una tarjeta por historia (GitHub Projects) para ver el avance diario.

## 16. Herramientas y tecnologías (borrador)

Las versiones exactas se fijan al crear el repositorio el 3/11 y se registran en esta tabla para el listado entregable. Se recomienda usar versiones estables y con soporte vigente.

| Categoría | Herramienta | Uso y justificación |
|---|---|---|
| Lenguaje backend | Java (versión LTS) | Usado en cursos previos (por confirmar el 3/11); tipado fuerte y buen soporte de pruebas. |
| Framework backend | Spring Boot: Web, Security, Data JPA, Validation | API REST, sesión, autorización, persistencia y validaciones con poco código. |
| Base de datos | PostgreSQL y Flyway | Restricciones y transacciones confiables; esquema versionado. |
| Cálculo lunar | commons-suncalc | Instantes de las fases lunares sin depender de un servicio en línea. |
| Frontend | React, Vite, React Router | SPA con ecosistema amplio y arranque rápido. |
| Mapa | Leaflet, react-leaflet, OpenStreetMap | Mapa gratuito con marcadores; requiere mostrar la atribución. |
| 3D | three.js, React Three Fiber, drei | Escena desde datos con controles de órbita y selección. |
| Pruebas backend | JUnit 5, AssertJ, Mockito, MockMvc, Testcontainers, JaCoCo | Pruebas unitarias y de API sobre PostgreSQL real; reporte de cobertura. |
| Pruebas web | Playwright | Recorridos automáticos en navegador, incluido el de la demostración. |
| Control de versiones y CI | Git, GitHub, GitHub Actions | Repositorio entregable, revisiones y verificación automática. |
| Empaquetado y despliegue | Docker y un hosting con plan gratuito | Misma imagen en todos los entornos; URL pública para la demostración. |
| Diseño y diagramas | Mermaid (o Visual Paradigm) | Diagramas versionados junto con el código. |

## 17. Riesgos técnicos y pruebas de viabilidad

El 3/11 se realizan tres pruebas cortas antes de comprometer el diseño. Cada una tiene un criterio de éxito y un plan alternativo.

| Prueba de viabilidad | Éxito si… | Si falla |
|---|---|---|
| Esqueleto desplegado | Una página de la SPA servida por Spring Boot consulta la base y está publicada en internet el mismo día. | Cambiar de hosting o publicar frontend y backend por separado (agrega configuración de CORS). |
| Escena 3D desde datos | Una escena con 4 zonas generadas desde JSON se rota y selecciona en un celular de gama media. | Reducir la escena a una vista isométrica fija con zonas seleccionables y negociarlo con el cliente (D-01). |
| Fases de noviembre de 2026 | commons-suncalc coincide con REF-LUNA con diferencia menor de una hora. | Implementar el algoritmo de Meeus directamente o ajustar la tolerancia con el cliente. |

| Riesgo | Respuesta |
|---|---|
| Curva de aprendizaje de Spring Security o Three.js | Asignar esas piezas a quienes tengan más experiencia; el resto trabaja sobre esa base. |
| Integración tardía entre frontend y backend | Acordar el contrato de la API (sección 9) el 3/11 e integrar historia por historia, no al final. |
| Plan gratuito con límites o suspensión | Desplegar desde el primer día y tener la ejecución local con Docker como respaldo para la demostración. |
| Fotos que llenan la base de datos | Límite de 5 MB y cantidad acotada en el piloto; pasar a almacenamiento de objetos si crece (AD-05). |

## 18. Decisiones para confirmar el 3/11

- ¿La mayoría del equipo domina Java y Spring? Si no, usar el mismo diseño con Node.js (NestJS) o el stack que mejor conozcan.
- Hosting y base de datos de la demostración, tras revisar sus condiciones vigentes.
- Responsables de los módulos backend, del frontend público, de las pantallas de gestión, de las pruebas y del despliegue.
- Contrato de la API y formato de las zonas (geometría) antes de que frontend y backend trabajen en paralelo.
- Croquis de H1 (CR1) para preparar la escena y la migración de datos de demostración.

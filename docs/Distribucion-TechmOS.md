# Distribución del trabajo y responsabilidades

<div align="justify" style="text-align: justify;">

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Organización técnica

Los seis aportes están integrados en la revisión de develop identificada en este documento. La tabla describe responsabilidades del código y revisión cruzada. La autoría histórica de los cambios se conserva en Git.

| Integrante | Responsabilidad | Archivos o componentes principales | Revisión cruzada |
|---|---|---|---|
| Mauricio López | Base de datos, mensajes, entorno y CI | .github/workflows/ci.yml; backend/mvnw; comun/Mensaje.java; comun/ManejadorErrores.java; db/migration/V3__integridad_envios.sql | Dhalin Avila |
| Dhalin Avila | Calendario lunar y pruebas de integración | backend/luna; frontend/paginas/Calendario.tsx; frontend/e2e/calendario.spec.ts | Mauricio López |
| Oscar Gualotuña | Mapa, ficha pública, escena y recorrido | frontend/paginas/Mapa.tsx; FichaHuerto.tsx; Recorrido3D.tsx; escena/Escena3D.tsx; frontend/e2e/recorrido-3d.spec.ts | Christian Yanchatipan |
| Christian Yanchatipan | Sesión, autorización y fotografías | backend/acceso; backend/fotos; frontend/sesion/ContextoSesion.tsx; frontend/api/cliente.ts; frontend/e2e/csrf.spec.ts | Oscar Gualotuña |
| Jean Espinosa | Corrección de siembras, historial e insignia | ServicioActividades.corregir; SiembraController; frontend/paginas/gestion/Bitacora.tsx; backend/retos | Gabriel Vinueza |
| Gabriel Vinueza | Registro de siembras, reenvíos y atomicidad | ServicioActividades.registrar; HuellaEnvio; SiembraController; pruebas de integridad y concurrencia | Jean Espinosa |

**Tabla 1.** Responsabilidades técnicas por integrante, con archivos o componentes principales y revisión cruzada.

## 2. Trabajo de cierre

| ID | Prioridad | Incidencia | Resultado observado | Prueba | Responsable técnico | Estado |
|---|---|---|---|---|---|---|
| INC-01 | Alta | Cierre de sesión interrumpido | La interfaz oculta la cuenta mientras la sesión del servidor sigue activa; reaparece al recargar. | P-071 | Christian Yanchatipan | Corregida en candidato local; pendiente de integración |
| INC-02 | Alta | Zona no cultivable | El servidor acepta cultivos en zonas de tipo BODEGA, CAMINO y CERCA con HTTP 201. | P-070 | Gabriel Vinueza | Corregida en candidato local; pendiente de integración |
| INC-03 | Alta | Orden de respuestas del filtro lunar | Una respuesta antigua puede reemplazar la última actividad seleccionada. | P-072 | Dhalin Avila | Corregida en candidato local; pendiente de integración |
| INC-04 | Media | Hora de actualización de cultivos en Galápagos | La pantalla de gestión usa America/Guayaquil aunque el huerto pertenece a Pacific/Galapagos. | P-074 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |
| INC-05 | Media | Campos públicos de plantas | Altitud y distribución se guardan y se devuelven en la API, pero se omiten en la ficha pública. | P-073 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |
| INC-06 | Media | Longitud de nombre común | Un nombre común de 121 caracteres produce HTTP 500; la columna admite 120. | P-075 | Mauricio López | Corregida en candidato local; pendiente de integración |
| INC-07 | Media | Fallo de cartografía | La lista continúa disponible, pero no se informa que las teselas del mapa no pudieron cargarse. | P-076 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |

**Tabla 2.** Trabajo de cierre: incidencias, prueba asociada, responsable técnico y estado.

La distribución de las incidencias identifica responsables técnicos del cierre y no afirma que los cambios ya se hayan realizado. La revisión cruzada comprende contraste del requisito, reproducción del defecto, comprobación del cambio y actualización de la evidencia.

## 3. Integración y coordinación

Gabriel y Jean comparten ServicioActividades y SiembraController por operación: registro y corrección respectivamente. Los cambios en esas unidades se coordinan mediante revisión del archivo completo. Oscar coordina con Christian la interfaz de sesión y las rutas públicas. Dhalin y Mauricio revisan los resultados de regresión y la identidad de la revisión documentada.

## 4. Estimación histórica

Las historias mantienen 148 horas-persona y el plan completo 225 horas-persona con reserva. El reparto previo de preparación e integración sumaba 36 horas-persona. Son antecedentes de estimación y no una declaración de trabajo real ejecutado ni de capacidad disponible del 12/11. El avance se acredita por código, pruebas y entregables.

[Índice de documentación](README.md)

</div>

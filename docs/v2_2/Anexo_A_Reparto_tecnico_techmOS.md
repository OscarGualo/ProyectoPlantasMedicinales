# Anexo A. Distribución técnica del código integrado

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Código integrado y ámbitos de responsabilidad

La documentación técnica utiliza la revisión `78d87974152092c2d07125060021d0afdae9dac9`. El código ya está integrado; este anexo no redistribuye parches de una base anterior. Las rutas de backend se interpretan bajo backend/src/main/java/ec/epn/huertos y las de interfaz bajo frontend/src.

## Mauricio López

**Responsabilidad:** Base de datos, mensajes, entorno y CI.

**Revisión cruzada:** Dhalin Avila.

**Unidades principales:** .github/workflows/ci.yml; backend/mvnw; comun/Mensaje.java; comun/ManejadorErrores.java; db/migration/V3__integridad_envios.sql.

La migración V3 agrega integridad de envíos sin editar las migraciones históricas aplicadas. El wrapper conserva modo ejecutable 100755. El catálogo tiene MSG-01 a MSG-29 y el manejo de errores omite detalles internos.

## Dhalin Avila

**Responsabilidad:** Calendario lunar y pruebas de integración.

**Revisión cruzada:** Mauricio López.

**Unidades principales:** backend/luna; frontend/paginas/Calendario.tsx; frontend/e2e/calendario.spec.ts.

El calendario reutiliza el cálculo real de fases y las recomendaciones. CAL-T queda limitado a ensayos controlados. INC-03 requiere ordenar las respuestas del filtro por la última intención de la persona usuaria.

## Oscar Gualotuña

**Responsabilidad:** Mapa, ficha pública, escena y recorrido.

**Revisión cruzada:** Christian Yanchatipan.

**Unidades principales:** frontend/paginas/Mapa.tsx; FichaHuerto.tsx; Recorrido3D.tsx; escena/Escena3D.tsx; frontend/e2e/recorrido-3d.spec.ts.

La escena utiliza geometrías por zona y cultivos asociados. La lista permite consultar el mismo huerto si la escena falla. INC-04, INC-05 e INC-07 afectan presentación horaria, ficha educativa y cartografía.

## Christian Yanchatipan

**Responsabilidad:** Sesión, autorización y fotografías.

**Revisión cruzada:** Oscar Gualotuña.

**Unidades principales:** backend/acceso; backend/fotos; frontend/sesion/ContextoSesion.tsx; frontend/api/cliente.ts; frontend/e2e/csrf.spec.ts.

Cada modificación requiere sesión y CSRF; los servicios verifican el huerto autorizado. La visibilidad de fotos distingue evidencia vigente, histórica y privada. INC-01 afecta el estado visible tras un cierre interrumpido.

## Jean Espinosa

**Responsabilidad:** Corrección de siembras, historial e insignia.

**Revisión cruzada:** Gabriel Vinueza.

**Unidades principales:** ServicioActividades.corregir; SiembraController; frontend/paginas/gestion/Bitacora.tsx; backend/retos.

Una corrección utiliza versión base y clave de envío, agrega una revisión inmutable y conserva el motivo. El historial se consulta por líder autorizado y la insignia usa siembras vigentes publicables.

## Gabriel Vinueza

**Responsabilidad:** Registro de siembras, reenvíos y atomicidad.

**Revisión cruzada:** Jean Espinosa.

**Unidades principales:** ServicioActividades.registrar; HuellaEnvio; SiembraController; pruebas de integridad y concurrencia.

El registro autoriza el cultivo, valida la imagen, calcula una huella canónica y serializa reintentos por clave. Foto, actividad y versión se escriben en una transacción. INC-02 exige completar la validación del tipo de zona al registrar cultivos.

## 8. Contratos de integración

| Operación | Contrato |
|---|---|
| Alta de siembra | POST /api/cultivos/{id}/siembras; fechaHecho, tecnica, descripcion, claveEnvio y foto. |
| Corrección | POST /api/siembras/{id}/correcciones; versionBase, claveEnvio, motivo, fechaHecho y foto opcional. |
| Historial | GET /api/siembras/{id}/historial; sesión del líder del huerto. |
| Consulta pública | GET /api/publico/huertos y GET /api/publico/huertos/{id}. |
| Calendario | GET /api/publico/calendario-lunar; desde, dias y actividad. |
| Reto | GET /api/publico/huertos/{id}/reto; solo sustento público vigente. |
| Errores | codigo, mensaje y campo cuando corresponde; sin detalles internos. |

## 9. Verificación común

El cierre requiere que cada cambio incluya su reproducción, regresión, revisión cruzada y resultado identificado por commit. El catálogo de pruebas y el registro de incidencias pertenecen a los documentos 04 y 08.

[Índice de documentación](README.md)

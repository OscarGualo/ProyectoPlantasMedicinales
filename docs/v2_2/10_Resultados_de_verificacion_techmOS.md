# Resultados de verificación del candidato local

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Identidad y alcance

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

| Conjunto | Resultado | Entorno y alcance |
|---|---|---|
| Maven verify | 94 aprobadas; cero fallos, errores u omisiones | JDK 21; PostgreSQL real mediante Testcontainers |
| Frontend build | Aprobado | TypeScript y Vite |
| Playwright | 21 aprobadas; sin reintentos | Edge instalado en Windows; 14 recorridos existentes y siete nuevos |
| Migración V3 → V4 | Conservación de datos comprobada | Copia aislada de la base de auditoría; comparación de actividad, actividad_version, foto y cultivo |
| CI de este candidato | Sin ejecución remota | El repositorio remoto se consultó con permiso de lectura |

## 2. Correcciones de la auditoría

| ID | Corrección | Prueba | Resultado del candidato |
|---|---|---|---|
| INC-01 | Cierre interrumpido | P-071 | El usuario permanece autenticado visualmente hasta confirmar el cierre y puede reintentar. |
| INC-02 | Zonas no cultivables | P-070 | BODEGA, CAMINO y CERCA se rechazan con MSG-29 sin registrar cultivo. |
| INC-03 | Respuestas lunares desordenadas | P-072 | Una respuesta anterior no sustituye la última selección. |
| INC-04 | Hora de Galápagos | P-074 | La fecha de actualización usa la zona horaria del huerto. |
| INC-05 | Campos de la ficha pública | P-073 | Se muestran altitud y distribución cuando la respuesta institucional los aporta; prueba de presentación con datos simulados. |
| INC-06 | Nombre común de 121 caracteres | P-075 | El contrato nuevo rechaza nombres libres con MSG-28, sin error 500. |
| INC-07 | Error de cartografía | P-076 | Se informa el error y se mantiene acceso por lista. |

P-077 comprueba el mapa sin huertos y sus cero marcadores. P-078 a P-082 agregan selección institucional, rechazo de códigos inválidos, protección de registros anteriores, aislamiento y ausencia de editor medicinal. P-013 a P-018 se actualizan para el contrato autorizado por el cliente. El catálogo usado en las pruebas incluye registros sintéticos explícitos; no deben confundirse con las cuatro fichas iniciales de los anexos MSP.

## 3. Evidencia reproducible

- [94 resultados Java](evidencias/java_correccion.csv).
- [21 recorridos de navegador](evidencias/navegador_correccion.csv).
- [Datos antes de migrar](evidencias/migracion_antes.txt) y [después de migrar](evidencias/migracion_despues.txt).
- [Comprobación del JAR normal](evidencias/comprobacion_jar.json).

Los archivos históricos conservados en evidencias corresponden al commit base y permiten contrastar la auditoría inicial. El candidato se identifica por el manifiesto del paquete y la huella SHA-256 de cada archivo distribuido.

## 4. Límites del veredicto

El candidato resuelve las siete incidencias reproducidas y aplica la aclaración de autoridad institucional. Permite continuar la integración y preparar la demostración académica. No acredita aceptación del cliente, autorización real de las cuentas, validación clínica, ejecución en Android físico, rendimiento formal, restauración operativa de respaldos, validación comunitaria de la convención lunar ni despliegue público con HTTPS. No se atribuye al CI del commit base la verificación de las correcciones nuevas.

[Índice](README.md)

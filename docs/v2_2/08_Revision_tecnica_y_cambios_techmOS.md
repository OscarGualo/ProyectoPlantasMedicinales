# Revisión técnica y control de cambios

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Objeto

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

## 2. Incidencias verificadas

| ID | Corrección | Prueba | Resultado del candidato |
|---|---|---|---|
| INC-01 | Cierre interrumpido | P-071 | El usuario permanece autenticado visualmente hasta confirmar el cierre y puede reintentar. |
| INC-02 | Zonas no cultivables | P-070 | BODEGA, CAMINO y CERCA se rechazan con MSG-29 sin registrar cultivo. |
| INC-03 | Respuestas lunares desordenadas | P-072 | Una respuesta anterior no sustituye la última selección. |
| INC-04 | Hora de Galápagos | P-074 | La fecha de actualización usa la zona horaria del huerto. |
| INC-05 | Campos de la ficha pública | P-073 | Se muestran altitud y distribución cuando la respuesta institucional los aporta; prueba de presentación con datos simulados. |
| INC-06 | Nombre común de 121 caracteres | P-075 | El contrato nuevo rechaza nombres libres con MSG-28, sin error 500. |
| INC-07 | Error de cartografía | P-076 | Se informa el error y se mantiene acceso por lista. |

Las siete correcciones se consideran verificadas en la copia local. El cierre de integración exige aplicar el paquete, ejecutar las suites en la rama del equipo y revisar el CI del nuevo PR. El resultado del CI del commit base no se atribuye a este candidato.

## 3. Cambio solicitado por el cliente

La comunidad escoge al líder y el MSP lo autoriza. HU-006 y HU-007 sustituyen el editor de contenido por selección del catálogo y gestión de fotografía. RF-021 a RF-024 y la regla RN-11 expresan el mismo límite. RF-055 conserva el etiquetado futuro, con información medicinal de lectura proveniente del MSP. El líder mantiene la responsabilidad sobre sus registros del huerto, sus cultivos y sus evidencias.

## 4. Calidad pendiente fuera de estas correcciones

P-027 mantiene pendiente Android físico y contraste con croquis real. P-063 no acredita HTTPS en un alojamiento publicado. P-066, P-067 y P-068 conservan pendientes las mediciones formales de rendimiento y recuperación. La prueba de migración demuestra conservación de datos en una copia, no constituye una prueba formal del procedimiento de respaldo y recuperación. La convención lunar requiere validación comunitaria documentada. El catálogo inicial es una selección limitada de cuatro fichas, con fuente, sin dosificaciones ni recomendaciones terapéuticas incorporadas.

[Índice](README.md)

## 5. Aclaración del calendario lunar, edición 2.2.1

El cliente precisa que las siembras se planifican en función del calendario lunar. Se ajustan la visión, el plan, HU-019, el glosario y RF-084 para separar fechas y actividades del calendario, cálculo de fases, registro de hechos y ciclo agrícola. Las reglas de conformidad, el historial y la insignia conservan su contrato. No se modifica código ni se atribuye una nueva ejecución de pruebas a esta edición documental. La aceptación de los intervalos culturales no se presume por el ajuste terminológico.

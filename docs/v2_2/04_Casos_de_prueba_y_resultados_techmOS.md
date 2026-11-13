# Casos de prueba y resultados de verificación

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

El catálogo vigente contiene 82 protocolos identificados. P-013 a P-018 se actualizan por la aclaración del cliente, P-070 a P-077 verifican las correcciones y P-078 a P-082 amplían la comprobación institucional. La suite local del candidato contiene 94 pruebas Java y 21 recorridos de navegador aprobados; esos conteos no equivalen a 82 protocolos manuales completos.

## Convenciones de ejecución y resultado

- Cada caso usa un estado inicial independiente. El reloj fijo de la suite es 31/10/2026 a las 12:00 de America/Guayaquil.
- CAL-T es sintético. P-055 incluye además un recorrido con fechas reales fuera de su tramo controlado.
- «Escenario automatizado aprobado» acredita únicamente las comprobaciones de su prueba. Los pasos de interfaz, dispositivo, transporte o medición no ejecutados se identifican por separado.
- Las incidencias del commit base se conservan en la evidencia histórica. Los protocolos P-070 a P-077 registran su reejecución aprobada sobre el candidato local; el resultado nuevo proviene de pruebas, no de una corrección editorial.
- La auditoría complementaria se ejecutó el 11/11/2026 en hora de Ecuador continental, sobre un JAR con perfil normal, PostgreSQL aislado y Edge 157 en Windows 11.

## Índice de protocolos

| ID | Caso | Estado de la evidencia |
|---|---|---|
| P-001 | Inicio de sesión con credenciales válidas | Escenario automatizado aprobado |
| P-002 | Inicio de sesión con credenciales incorrectas | Escenario automatizado aprobado |
| P-003 | Modificación de un huerto no asignado | Escenario automatizado aprobado |
| P-004 | Cierre de sesión | Escenario automatizado aprobado |
| P-005 | Modificación después de cerrar sesión | Escenario automatizado aprobado |
| P-006 | Actualización de la ubicación del huerto | Escenario automatizado aprobado |
| P-007 | Coordenadas fuera de rango | Escenario automatizado aprobado |
| P-008 | Actualización de las características del huerto | Escenario automatizado aprobado |
| P-009 | Huerto sin nombre | Escenario automatizado aprobado |
| P-010 | Superficie negativa | Escenario automatizado aprobado |
| P-011 | Publicación de un huerto con ubicación | Escenario automatizado aprobado |
| P-012 | Publicación de un huerto sin ubicación | Escenario automatizado aprobado |
| P-013 | Registro de una planta medicinal | Cobertura parcial |
| P-014 | Planta con un dato obligatorio faltante | Escenario automatizado aprobado |
| P-015 | Planta con fotografía no admitida | Escenario automatizado aprobado |
| P-016 | Modificación de una planta | Escenario automatizado aprobado |
| P-017 | Independencia de fichas entre huertos | Escenario automatizado aprobado |
| P-018 | Borrado de un dato obligatorio | Escenario automatizado aprobado |
| P-019 | Registro de cultivos de una misma planta | Escenario automatizado aprobado |
| P-020 | Cultivo en una zona de otro huerto | Escenario automatizado aprobado |
| P-021 | Cambio de estado de un cultivo | Escenario automatizado aprobado |
| P-022 | Estado fuera del catálogo | Escenario automatizado aprobado |
| P-023 | Marcadores de los huertos publicados | Cobertura parcial |
| P-024 | Huerto no publicado en el mapa | Escenario automatizado aprobado |
| P-025 | Consulta de la ficha pública de un huerto | Cobertura parcial |
| P-026 | Enlace directo a un huerto no publicado | Escenario automatizado aprobado |
| P-027 | Recorrido de la escena 3D | Sin ejecución registrada |
| P-028 | Fallo de carga de la escena | Escenario automatizado aprobado |
| P-029 | Cultivos de una zona | Cobertura parcial |
| P-030 | Señalética caliente y fría | Cobertura parcial |
| P-031 | Zona sin cultivos | Cobertura parcial |
| P-032 | Registro de una siembra con fotografía | Escenario automatizado aprobado |
| P-033 | Envío repetido de una siembra | Escenario automatizado aprobado |
| P-034 | Siembra con fotografía no válida | Escenario automatizado aprobado |
| P-035 | Siembra con fecha futura | Escenario automatizado aprobado |
| P-036 | Siembra en un cultivo de otro huerto | Escenario automatizado aprobado |
| P-037 | Falla al guardar la fotografía | Escenario automatizado aprobado |
| P-038 | Siembra conforme con la condición lunar | Escenario automatizado aprobado |
| P-039 | Siembra no conforme con la condición lunar | Escenario automatizado aprobado |
| P-040 | Siembra sin información de fase | Escenario automatizado aprobado |
| P-041 | Corrección de una siembra con motivo | Escenario automatizado aprobado |
| P-042 | Corrección sin motivo | Escenario automatizado aprobado |
| P-043 | Corrección por el líder de otro huerto | Escenario automatizado aprobado |
| P-044 | Fotografía no válida en una corrección | Escenario automatizado aprobado |
| P-045 | Historial de una siembra corregida | Escenario automatizado aprobado |
| P-046 | Historial de una siembra sin correcciones | Escenario automatizado aprobado |
| P-047 | Insignia obtenida por la comunidad | Escenario automatizado aprobado |
| P-048 | Insignia única con varias siembras elegibles | Escenario automatizado aprobado |
| P-049 | Comunidad sin siembras elegibles | Escenario automatizado aprobado |
| P-050 | Calendario lunar de los próximos 30 días | Escenario automatizado aprobado |
| P-051 | Fecha fuera del rango del calendario | Escenario automatizado aprobado |
| P-052 | Filtro de días de siembra | Escenario automatizado aprobado |
| P-053 | Filtro de días de cosecha de raíces | Escenario automatizado aprobado |
| P-054 | Retiro del filtro | Escenario automatizado aprobado |
| P-055 | Recorrido integrado de la primera iteración | Escenario automatizado aprobado |
| P-056 | Gestión privada de un huerto no publicado | Escenario automatizado aprobado |
| P-057 | Corrección con fecha futura | Escenario automatizado aprobado |
| P-058 | Reintentos simultáneos de siembra | Escenario automatizado aprobado |
| P-059 | Reutilización de clave con otros datos | Escenario automatizado aprobado |
| P-060 | Conflicto entre correcciones | Escenario automatizado aprobado |
| P-061 | Insignia sin revelar huertos privados | Escenario automatizado aprobado |
| P-062 | Continuidad y límites de los intervalos lunares | Escenario automatizado aprobado |
| P-063 | Ciclo de sesión y protección CSRF | Cobertura parcial |
| P-064 | Acceso directo a fotografías privadas | Escenario automatizado aprobado |
| P-065 | Reversión después de guardar evidencia | Escenario automatizado aprobado |
| P-066 | Carga y navegación 3D en el perfil Q1-I1 | Sin ejecución registrada |
| P-067 | Tiempo de consulta de información | Sin ejecución registrada |
| P-068 | Recuperación de datos y evidencias | Sin ejecución registrada |
| P-069 | Reintento de corrección confirmada | Escenario automatizado aprobado |
| P-070 | Rechazo de zonas no cultivables | Aprobado en candidato local |
| P-071 | Cierre de sesión interrumpido | Aprobado en candidato local |
| P-072 | Respuestas de filtro desordenadas | Aprobado en candidato local |
| P-073 | Campos institucionales disponibles en ficha | Aprobado en candidato local |
| P-074 | Hora de gestión de Galápagos | Aprobado en candidato local |
| P-075 | Rechazo de nombre libre largo | Aprobado en candidato local |
| P-076 | Indisponibilidad cartográfica | Aprobado en candidato local |
| P-077 | Mapa sin huertos publicados | Aprobado en candidato local |

## P-001. Inicio de sesión con credenciales válidas

**Descripción:**
Comprobar que un líder con cuenta activa inicia sesión y accede solo a la gestión del huerto que tiene asignado.
**Trazabilidad:** HU-001 / CA-01 · RN-01.
**Datos de prueba:** L1, H1, H2.

**Procedimiento de prueba:**

1. Abrir la aplicación sin sesión.
2. Seleccionar "Iniciar sesión" e ingresar el usuario y la contraseña de L1.
3. Revisar los huertos que se ofrecen para gestionar.

**Resultados esperados:**
Se inicia la sesión de L1 y se muestra la gestión de H1. No se ofrece la gestión de H2.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SesionPruebaIntegracion.p001.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-002. Inicio de sesión con credenciales incorrectas

**Descripción:**
Comprobar que el sistema no inicia sesión con credenciales incorrectas y no revela cuál dato falló.
**Trazabilidad:** HU-001 / CA-02 · RN-01.
**Datos de prueba:** L1 y el usuario inexistente "usuario.inexistente".

**Procedimiento de prueba:**

1. Ingresar el usuario de L1 con una contraseña incorrecta.
2. Ingresar el usuario "usuario.inexistente" con cualquier contraseña.

**Resultados esperados:**
En ambos intentos se muestra exactamente MSG-01 «Usuario o contraseña incorrectos.» y no se inicia sesión.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SesionPruebaIntegracion.p002.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-003. Modificación de un huerto no asignado

**Descripción:**
Comprobar que un líder no puede modificar un huerto que no le fue asignado, ni desde la pantalla ni con solicitudes directas al servidor.
**Trazabilidad:** HU-001 / CA-03 · RN-01.
**Datos de prueba:** L2 (líder de H2), H1. Cliente HTTP o herramientas de desarrollador.

**Procedimiento de prueba:**

1. Anotar el nombre actual de H1.
2. Ingresar como L2.
3. Intentar abrir la gestión de H1 escribiendo su dirección en el navegador.
4. Enviar directamente al servidor una solicitud de cambio del nombre de H1.
5. Consultar H1 sin sesión.

**Resultados esperados:**
Los pasos 3 y 4 se rechazan con MSG-03 «No tienes permiso para modificar la información de este huerto.». H1 conserva el nombre anotado.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SesionPruebaIntegracion.p003.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-004. Cierre de sesión

**Descripción:**
Comprobar que el líder finaliza su sesión y vuelve a la consulta pública.
**Trazabilidad:** HU-002 / CA-01 · RN-01.
**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Ingresar como L1.
2. Seleccionar "Cerrar sesión".
3. Intentar abrir la gestión de H1.

**Resultados esperados:**
Tras el paso 2 se muestra la consulta pública. En el paso 3 el sistema solicita iniciar sesión.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SesionPruebaIntegracion.p004.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-005. Modificación después de cerrar sesión

**Descripción:**
Comprobar que el servidor rechaza una modificación enviada con los datos de una sesión ya cerrada.
**Trazabilidad:** HU-002 / CA-02 · RN-01.
**Datos de prueba:** L1, H1. Cliente HTTP o herramientas de desarrollador.

**Procedimiento de prueba:**

1. Ingresar como L1 y anotar el nombre de H1.
2. Cambiar el nombre de H1, capturar la solicitud enviada y restaurar el nombre original.
3. Cerrar sesión.
4. Reenviar al servidor la solicitud capturada.
5. Consultar H1 sin sesión.

**Resultados esperados:**
La solicitud del paso 4 se rechaza con MSG-02 «Tu sesión ha finalizado. Inicia sesión para continuar.». H1 conserva el nombre anotado.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SesionPruebaIntegracion.p005.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-006. Actualización de la ubicación del huerto

**Descripción:**
Comprobar que la ubicación se guarda y se recupera, incluidos los valores límite de las coordenadas.
**Trazabilidad:** HU-003 / CA-01 · RN-01.
**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Como L1, guardar: provincia Pichincha; parroquia "Parroquia de prueba"; dirección "Dirección de prueba"; latitud -0.2; longitud -78.5.
2. Cerrar sesión, volver a ingresar y abrir la ubicación de H1.
3. Guardar latitud 90 y longitud -180; recargar; restaurar los valores del paso 1.

**Resultados esperados:**
Los pasos 2 y 3 muestran exactamente los valores guardados.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p006.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-007. Coordenadas fuera de rango

**Descripción:**
Comprobar que no se guardan coordenadas fuera de rango.
**Trazabilidad:** HU-003 / CA-02 · RN-01.
**Datos de prueba:** L1, H1 con la ubicación de P-006.

**Procedimiento de prueba:**

1. Guardar latitud 91 y recargar.
2. Guardar longitud 181 y recargar.

**Resultados esperados:**
Ambos intentos muestran MSG-06 «Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180.» y H1 conserva latitud -0.2 y longitud -78.5.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p007.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-008. Actualización de las características del huerto

**Descripción:**
Comprobar que las características se guardan y se recuperan, incluida la superficie 0.
**Trazabilidad:** HU-004 / CA-01 · RN-01, RN-02.
**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Como L1, guardar: nombre "Huerto Demostrativo A"; descripción "Huerto de prueba"; diseño "en tierra: camellones"; cerca "cerca viva"; superficie 25 m²; suelo "margoso"; fuente de agua "canal"; técnica de riego "regadera"; abono "compost".
2. Cerrar sesión, volver a ingresar y abrir las características.
3. Guardar superficie 0, recargar y restaurar 25 m².

**Resultados esperados:**
Los pasos 2 y 3 muestran exactamente los valores guardados.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p008.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-009. Huerto sin nombre

**Descripción:**
Comprobar que el nombre es obligatorio.
**Trazabilidad:** HU-004 / CA-02 · RN-01.
**Datos de prueba:** L1, H1 con nombre "Huerto Demostrativo A".

**Procedimiento de prueba:**

1. Borrar el nombre y guardar.
2. Recargar las características.

**Resultados esperados:**
Se muestra MSG-04 «Completa el campo obligatorio: {campo}.» con el campo "Nombre". H1 conserva el nombre "Huerto Demostrativo A".

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p009.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-010. Superficie negativa

**Descripción:**
Comprobar que la superficie no puede ser menor que 0.
**Trazabilidad:** HU-004 / CA-03 · RN-01.
**Datos de prueba:** L1, H1 con superficie 25 m².

**Procedimiento de prueba:**

1. Guardar superficie -1.
2. Recargar las características.

**Resultados esperados:**
Se muestra MSG-05 «La superficie debe ser un número mayor o igual a 0.». H1 conserva la superficie de 25 m².

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p010.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-011. Publicación de un huerto con ubicación

**Descripción:**
Comprobar que un huerto con nombre y coordenadas se publica y aparece en el mapa.
**Trazabilidad:** HU-005 / CA-01 · RN-02.
**Datos de prueba:** L4 (líder de H5), H5 no publicado con nombre y coordenadas.

**Procedimiento de prueba:**

1. Ingresar como L4 y publicar H5.
2. Cerrar sesión y abrir el mapa.

**Resultados esperados:**
H5 aparece en el mapa en sus coordenadas registradas.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p011.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-012. Publicación de un huerto sin ubicación

**Descripción:**
Comprobar que un huerto sin coordenadas no puede publicarse.
**Trazabilidad:** HU-005 / CA-02 · RN-02.
**Datos de prueba:** L3 (líder de H4), H4 no publicado y sin coordenadas.

**Procedimiento de prueba:**

1. Ingresar como L3 y solicitar la publicación de H4.
2. Cerrar sesión y abrir el mapa.

**Resultados esperados:**
Se muestra MSG-21 «El huerto no tiene ubicación registrada y no puede publicarse en el mapa.». H4 continúa no publicado y no tiene marcador en el mapa.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HuertoGestionPruebaIntegracion.p012.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-013. Incorporación desde catálogo

**Descripción:** Verificar selección y publicación institucional.
**Trazabilidad:** HU-006/CA-01; RF-021 y RF-024.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Ingresar como L1 y elegir TEST-FRIA.
2. Guardar con F1.
3. Consultar H1 sin sesión.

**Resultado esperado:** Ficha vinculada a TEST-FRIA, datos obtenidos del servidor y fotografía visibles; los textos sintéticos permanecen identificados como prueba.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p013. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-014. Ficha de catálogo no seleccionada

**Descripción:** Rechazar la selección faltante.
**Trazabilidad:** HU-006/CA-02; RF-023.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Como L1, enviar catalogoCodigo vacío.
2. Consultar las plantas de H1.

**Resultado esperado:** HTTP 400, MSG-04 y campo catalogoCodigo; no se crea ficha.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p014. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-015. Imagen no admitida y límite exacto

**Descripción:** Comprobar que cambiar el origen de textos conserva la validación de fotos.
**Trazabilidad:** HU-006/CA-03; RN-05.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Seleccionar TEST-FRIA con F2 y con F3.
2. Repetir con F4 de exactamente 5 MB.

**Resultado esperado:** F2 y F3 se rechazan con MSG-07, sin altas; F4 se admite.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p015. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-016. Cambio de fotografía con contenido protegido

**Descripción:** Verificar modificación exclusiva de la foto.
**Trazabilidad:** HU-007/CA-01; RF-022.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Como L1, sustituir la fotografía de E1.
2. Consultar la ficha.

**Resultado esperado:** Foto actualizada; descripción institucional sin modificaciones.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p016. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-017. Independencia de fotografías entre huertos

**Descripción:** Verificar aislamiento de las fotografías.
**Trazabilidad:** HU-007/CA-02; RN-02.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Actualizar la foto de E1 en H1.
2. Consultar la ficha de la misma especie en H2.

**Resultado esperado:** La foto de H2 permanece intacta.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p017. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-018. Rechazo de edición de usos

**Descripción:** Verificar protección del contenido en el servidor.
**Trazabilidad:** HU-007/CA-03; RN-11.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Como L1, enviar catalogoCodigo junto con usos manipulados.
2. Consultar la ficha y los datos persistidos.

**Resultado esperado:** HTTP 400, MSG-28; el contenido institucional anterior permanece intacto.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.p018. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-019. Registro de cultivos de una misma planta

**Descripción:**
Comprobar que una planta puede tener cultivos distintos en zonas distintas.
**Trazabilidad:** HU-008 / CA-01 · RN-03.
**Datos de prueba:** L1, E1, Z1, Z2. Reloj fijado.

**Procedimiento de prueba:**

1. Como L1, registrar K1: planta E1, zona Z1.
2. Registrar K2: planta E1, zona Z2.
3. Consultar K1 y K2.

**Resultados esperados:**
K1 y K2 muestran planta E1, su zona, estado "sembrado" y fecha 31/10/2026 12:00.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CultivoPruebaIntegracion.p019.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-020. Cultivo en una zona de otro huerto

**Descripción:**
Comprobar que no se registra un cultivo en una zona de otro huerto.
**Trazabilidad:** HU-008 / CA-02 · RN-03.
**Datos de prueba:** L1, E1, Z3 (de H2).

**Procedimiento de prueba:**

1. Como L1, enviar un cultivo de E1 en Z3, que pertenece a H2.
2. Enviar un cultivo con E2 de H2 en Z1 de H1.
3. Consultar los cultivos de H1 y H2.

**Resultados esperados:**
Ambos intentos se rechazan sin registrar cultivos. Zona ajena: MSG-09. Planta ajena: error de validación que identifica la referencia inválida sin revelar datos del otro huerto. Ninguna referencia cruzada se persiste.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CultivoPruebaIntegracion.p020.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-021. Cambio de estado de un cultivo

**Descripción:**
Comprobar que el estado pertenece al cultivo y no a la planta.
**Trazabilidad:** HU-009 / CA-01 · RN-03.
**Datos de prueba:** L1, K1 y K2 en estado "sembrado".

**Procedimiento de prueba:**

1. Como L1, cambiar K1 a "en crecimiento".
2. Consultar K1, K2 y la ficha de E1.

**Resultados esperados:**
K1 muestra "en crecimiento" y una nueva fecha de actualización. K2 sigue "sembrado". La ficha de E1 no muestra un estado.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CultivoPruebaIntegracion.p021.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-022. Estado fuera del catálogo

**Descripción:**
Comprobar que solo se aceptan estados del catálogo.
**Trazabilidad:** HU-009 / CA-02 · RN-03.
**Datos de prueba:** L1, K1.

**Procedimiento de prueba:**

1. Como L1, enviar directamente al servidor el estado "gigante" para K1.
2. Consultar K1.

**Resultados esperados:**
Se muestra MSG-10 «Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido.». K1 conserva su estado anterior.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CultivoPruebaIntegracion.p022.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-023. Marcadores de los huertos publicados

**Descripción:**
Comprobar que el mapa muestra un marcador por cada huerto publicado en su ubicación registrada.
**Trazabilidad:** HU-010 / CA-01 · RN-02.
**Datos de prueba:** H1 y H2 publicados con coordenadas.

**Procedimiento de prueba:**

1. Sin sesión, abrir el mapa.
2. Comparar la posición de cada marcador con las coordenadas registradas de H1 y H2.

**Resultados esperados:**
Aparecen marcadores de H1 y H2 en sus coordenadas registradas.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-023 Marcadores de los huertos publicados.

**Cobertura del protocolo:** parcial. La prueba verifica presencia y cantidad de marcadores; no compara por sí sola cada posición con las coordenadas.

## P-024. Huerto no publicado en el mapa

**Descripción:**
Comprobar que un huerto no publicado no aparece en el mapa.
**Trazabilidad:** HU-010 / CA-02 · RN-02.
**Datos de prueba:** H3 no publicado.

**Procedimiento de prueba:**

1. Sin sesión, abrir el mapa y buscar H3.

**Resultados esperados:**
H3 no tiene marcador.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-024 Huerto no publicado en el mapa.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-025. Consulta de la ficha pública de un huerto

**Descripción:**
Comprobar que cada marcador abre la ficha correcta y que no muestra datos privados.
**Trazabilidad:** HU-011 / CA-01 · RN-02.
**Datos de prueba:** H1 con contacto del responsable registrado, H2.

**Procedimiento de prueba:**

1. Sin sesión, seleccionar el marcador de H1.
2. Revisar los datos mostrados y abrir sus plantas, cultivos y vista 3D.
3. Volver al mapa y seleccionar H2.

**Resultados esperados:**
Cada marcador abre la ficha de su huerto con sus plantas, cultivos y acceso a la vista 3D. No se muestran el contacto del responsable ni datos de la cuenta.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-025 Consulta de la ficha pública de un huerto.

**Cobertura del protocolo:** parcial. El recorrido comprueba identidad del huerto, secciones, enlace 3D y ausencia de contacto privado; P-073 expone la omisión de campos educativos.

## P-026. Enlace directo a un huerto no publicado

**Descripción:**
Comprobar que no se pueden consultar los datos de un huerto no publicado mediante su enlace.
**Trazabilidad:** HU-011 / CA-02 · RN-02.
**Datos de prueba:** H3 no publicado.

**Procedimiento de prueba:**

1. Sin sesión, abrir el enlace directo de H3.

**Resultados esperados:**
Se muestra MSG-14 «El huerto solicitado no existe o no está publicado.» y ningún dato de H3.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-026 Enlace directo a un huerto no publicado.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-027. Recorrido de la escena 3D

**Descripción:**
Comprobar representación del croquis y navegación en dispositivos registrados. El tiempo de carga se mide en P-066.
**Trazabilidad:** HU-012 / CA-01.
**Datos:** H1, CR1 versionado, modelo de computador y celular Android, navegador y versión anotados antes de ejecutar.

**Procedimiento de prueba:**

1. Abrir la vista 3D de H1.
2. Rotar, acercar y alejar la vista.
3. Comparar con CR1 las zonas Z1 y Z2, los caminos, la cerca y la bodega.
4. Repetir en el otro dispositivo.

**Resultados esperados:**
Las cuatro categorías presentes en CR1 se identifican en posiciones correspondientes. La navegación funciona en ambos dispositivos.

### Resultado registrado

**Estado:** ejecutado en el candidato local; véase documento 10. No se acredita la condición física o formal del protocolo.

## P-028. Fallo de carga de la escena

**Descripción:**
Comprobar que, si la escena no carga, el visitante consulta el mismo huerto en forma de lista.
**Trazabilidad:** HU-012 / CA-02 · RN-02.
**Datos de prueba:** H1. Entorno de prueba con el recurso de la escena bloqueado.

**Procedimiento de prueba:**

1. Preparar H1 y mantener disponible su consulta de zonas y cultivos.
2. Simular WebGL no disponible o un fallo controlado del componente de escena, sin bloquear los datos de la lista.
3. Abrir el 3D; después retirar el fallo y recargar.

**Resultados esperados:**
Se muestra MSG-13 «No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.» y la lista de Z1 y Z2 con sus cultivos, sin la escena de otro huerto. Al restaurar, la escena de H1 se muestra.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-028 Fallo de carga de la escena.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-029. Cultivos de una zona

**Descripción:**
Comprobar que al seleccionar una zona se muestran sus cultivos con planta y estado.
**Trazabilidad:** HU-013 / CA-01 · RN-03.
**Datos de prueba:** H1; K1 en Z1; K2, K4 y K5 en Z2.

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z1.
2. Seleccionar Z2.

**Resultados esperados:**
Z1 muestra K1. Z2 muestra K2, K4 y K5. Cada cultivo indica planta, estado informado y fecha de actualización. No aparecen cultivos de H2.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-029 Cultivos de una zona.

**Cobertura del protocolo:** parcial. La selección automatizada utiliza botones laterales; la selección directa de objetos 3D no queda acreditada por ese recorrido.

## P-030. Señalética caliente y fría

**Descripción:**
Comprobar el color de cada cultivo según la clasificación de su planta.
**Trazabilidad:** HU-013 / CA-02 · RN-03.
**Datos de prueba:** Z2 con K2 (E1, fría), K4 (E3, caliente) y K5 (E4, sin clasificación).

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z2.

**Resultados esperados:**
K2 se señala en verde, K4 en rojo y K5 en gris.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-030 Señalética caliente y fría.

**Cobertura del protocolo:** parcial. La prueba comprueba texto y colores de los sellos del panel; no inspecciona el material de cada objeto WebGL.

## P-031. Zona sin cultivos

**Descripción:**
Comprobar el mensaje de una zona sin cultivos.
**Trazabilidad:** HU-013 / CA-03 · RN-03.
**Datos de prueba:** H1 con la zona Z4 sin cultivos.

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z4.

**Resultados esperados:**
Se muestra MSG-22 «Esta zona no tiene cultivos registrados.».

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-031 Zona sin cultivos.

**Cobertura del protocolo:** parcial. La zona vacía se selecciona mediante un botón lateral, sin acreditar la selección directa en el lienzo.

## P-032. Registro de una siembra con fotografía

**Descripción:**
Comprobar que la siembra se guarda con fecha del hecho, fecha de registro y autor separados, y que persiste.
**Trazabilidad:** HU-014 / CA-01 · RN-04, RN-05.
**Datos de prueba:** L1, K1, F1. Reloj fijado.

**Procedimiento de prueba:**

1. Como L1, registrar una siembra de K1: fecha y hora 10/10/2026 08:00; técnica semillas; descripción "Siembra de prueba"; fotografía F1.
2. Consultar la bitácora de K1.
3. Cerrar sesión, volver a ingresar y consultar la bitácora.

**Resultados esperados:**
La bitácora muestra la siembra A1 con fecha del hecho 10/10/2026 08:00, fecha de registro 31/10/2026 12:00, autor L1, técnica, descripción y F1, también después de volver a ingresar. El estado de K1 no cambia automáticamente.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p032.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-033. Envío repetido de una siembra

**Descripción:**
Comprobar que un reenvío por falla de conexión no duplica la siembra y que una siembra distinta sí se registra.
**Trazabilidad:** HU-014 / CA-02 · RN-04.
**Datos de prueba:** L1, K1, F1, F4. Cliente HTTP o simulación de red.

**Procedimiento de prueba:**

1. Registrar A1 como en P-032 y reenviar la misma solicitud con el mismo identificador de envío.
2. Registrar otra siembra A2 de K1 con fecha 13/10/2026 09:00 y fotografía F4.
3. Consultar la bitácora de K1.

**Resultados esperados:**
La bitácora contiene exactamente dos siembras: una A1 y una A2.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p033.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-034. Siembra con fotografía no válida

**Descripción:**
Comprobar que no se registra una siembra con evidencia no admitida.
**Trazabilidad:** HU-014 / CA-03 · RN-05.
**Datos de prueba:** L1, K1, F2, F3.

**Procedimiento de prueba:**

1. Registrar una siembra de K1 con F2.
2. Registrar una siembra de K1 con F3.
3. Consultar la bitácora.

**Resultados esperados:**
Ambos intentos muestran MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.». La bitácora no contiene esas siembras.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p034.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-035. Siembra con fecha futura

**Descripción:**
Comprobar que la fecha del hecho no puede ser posterior al momento actual, incluido su valor límite.
**Trazabilidad:** HU-014 / CA-04 · RN-05.
**Datos de prueba:** L1, K1, F1. Reloj fijado en 31/10/2026 12:00.

**Procedimiento de prueba:**

1. Intentar registrar una siembra el 01/11/2026 08:00.
2. Registrar otra exactamente en el instante del reloj 31/10/2026 12:00.
3. Intentar otra un segundo después del reloj.

**Resultados esperados:**
Pasos 1 y 3: MSG-08 y no se registra. Paso 2: se registra, porque el instante igual al reloj no es futuro.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p035.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-036. Siembra en un cultivo de otro huerto

**Descripción:**
Comprobar que un líder no registra actividades en cultivos ajenos.
**Trazabilidad:** HU-014 / CA-05 · RN-01, RN-04.
**Datos de prueba:** L1, K3 (de H2).

**Procedimiento de prueba:**

1. Como L1, enviar directamente al servidor una siembra para K3.
2. Consultar la bitácora de K3.

**Resultados esperados:**
Se muestra MSG-19 «El cultivo seleccionado no pertenece a tu huerto.». La bitácora de K3 no cambia.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p036.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-037. Falla al guardar la fotografía

**Descripción:**
Verificar fallo antes de insertar la fotografía. La reversión después de escribirla se comprueba adicionalmente en P-065.
**Trazabilidad:** HU-014 / CA-06; RN-05.
**Datos:** L1, K1, F1; sustitución controlada de FotoRepositorio en pruebas, sin desactivar PostgreSQL completo.

**Procedimiento de prueba:**

1. Inyectar un fallo al intentar guardar la fotografía.
2. Enviar una siembra y consultar la base.
3. Retirar el fallo y reintentar la misma clave.

**Resultados esperados:**
Primer intento: MSG-11 y ninguna siembra. Tras retirar el fallo se registra una sola siembra con evidencia.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** SiembraPruebaIntegracion.p037.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-038. Siembra conforme con la condición lunar

**Descripción:**
Comprobar que una siembra en luna creciente o llena se evalúa conforme usando la fecha del hecho.
**Trazabilidad:** HU-015 / CA-01 · RN-06, RN-09.
**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Configurar CAL-T en el entorno de prueba.
2. Registrar A1 con fecha del hecho 10/10/2026 08:00 y consultar su condición lunar.
3. Registrar A3 con fecha del hecho 13/10/2026 08:00 y consultar su condición lunar.

**Resultados esperados:**
A1 muestra fase "luna creciente", la regla RN-06 y MSG-15 «Conforme con la condición lunar: la siembra se realizó en {fase}.». A3 muestra fase "luna llena" y MSG-15 «Conforme con la condición lunar: la siembra se realizó en {fase}.». La fecha de registro (31/10/2026) no influye en la fase.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CondicionLunarPruebaIntegracion.p038.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-039. Siembra no conforme con la condición lunar

**Descripción:**
Comprobar que una siembra en luna menguante u oscura se evalúa no conforme y se conserva.
**Trazabilidad:** HU-015 / CA-02 · RN-06.
**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Registrar una siembra con fecha 11/10/2026 08:00 y consultar su condición lunar.
2. Registrar una siembra con fecha 14/10/2026 08:00 y consultar su condición lunar.
3. Consultar la bitácora.

**Resultados esperados:**
La primera muestra "luna menguante" y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.»; la segunda "luna oscura" y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.». Ambas permanecen en la bitácora.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CondicionLunarPruebaIntegracion.p039.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-040. Siembra sin información de fase

**Descripción:**
Comprobar que, sin información de fase, la siembra queda pendiente de comprobación.
**Trazabilidad:** HU-015 / CA-03 · RN-06.
**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Registrar una siembra con fecha 12/10/2026 08:00 y consultar su condición lunar.
2. Consultar la bitácora.

**Resultados esperados:**
La siembra muestra MSG-17 «Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra.» y aparece en la bitácora como pendiente de comprobación.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CondicionLunarPruebaIntegracion.p040.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-041. Corrección de una siembra con motivo

**Descripción:**
Comprobar que la corrección crea una versión nueva, conserva la anterior y evalúa la condición lunar con la versión vigente.
**Trazabilidad:** HU-016 / CA-01 · RN-06, RN-07.
**Datos de prueba:** L1, A1 conforme (10/10/2026), CAL-T.

**Procedimiento de prueba:**

1. Como L1, corregir la fecha de A1 a 11/10/2026 08:00 con el motivo "Fecha registrada por error".
2. Consultar A1 y su condición lunar.
3. Consultar en la base de datos de prueba las versiones de A1.

**Resultados esperados:**
A1 muestra la fecha 11/10/2026 y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.». Existen dos versiones: la vigente del 11/10/2026 con su motivo y autor, y la anterior del 10/10/2026 sin modificar.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CorreccionPruebaIntegracion.p041.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-042. Corrección sin motivo

**Descripción:**
Comprobar que no se acepta una corrección sin motivo.
**Trazabilidad:** HU-016 / CA-02 · RN-07.
**Datos de prueba:** L1, A1 conforme.

**Procedimiento de prueba:**

1. Como L1, cambiar la fecha de A1 sin escribir motivo y guardar.
2. Consultar A1 y su condición lunar.

**Resultados esperados:**
Se muestra MSG-12 «Indica el motivo de la corrección.». A1 conserva su fecha y su condición lunar.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CorreccionPruebaIntegracion.p042.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-043. Corrección por el líder de otro huerto

**Descripción:**
Comprobar que solo el líder del huerto puede corregir sus siembras.
**Trazabilidad:** HU-016 / CA-03 · RN-01, RN-07.
**Datos de prueba:** L2, A1 de H1.

**Procedimiento de prueba:**

1. Como L2, enviar directamente al servidor una corrección de A1 con motivo.
2. Consultar A1.

**Resultados esperados:**
Se muestra MSG-03 «No tienes permiso para modificar la información de este huerto.». A1 no cambia.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CorreccionPruebaIntegracion.p043.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-044. Fotografía no válida en una corrección

**Descripción:**
Comprobar que una corrección con evidencia no admitida no reemplaza la fotografía vigente.
**Trazabilidad:** HU-016 / CA-04 · RN-05, RN-07.
**Datos de prueba:** L1, A1 con F1, F2.

**Procedimiento de prueba:**

1. Como L1, reemplazar la fotografía de A1 por F2 con el motivo "Cambio de foto".
2. Consultar A1.

**Resultados esperados:**
Se muestra MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.». A1 conserva F1.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CorreccionPruebaIntegracion.p044.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-045. Historial de una siembra corregida

**Descripción:**
Comprobar que el historial muestra todas las versiones en orden con autor, fecha y motivo.
**Trazabilidad:** HU-017 / CA-01 · RN-07.
**Datos de prueba:** L1, A1 corregida como en P-041.

**Procedimiento de prueba:**

1. Como L1, consultar el historial de A1.

**Resultados esperados:**
Dos versiones en orden descendente, ambas con fecha del hecho, fotografía consultable por el líder y autor. La vigente incluye fecha de corrección y motivo; la original se identifica como registro inicial, sin motivo de corrección inventado.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HistorialPruebaIntegracion.p045.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-046. Historial de una siembra sin correcciones

**Descripción:**
Comprobar el mensaje de una siembra que nunca se corrigió.
**Trazabilidad:** HU-017 / CA-02 · RN-07.
**Datos de prueba:** L1, A3 sin correcciones.

**Procedimiento de prueba:**

1. Como L1, consultar el historial de A3.

**Resultados esperados:**
Se muestra MSG-23 «Esta siembra no tiene correcciones registradas.».

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** HistorialPruebaIntegracion.p046.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-047. Insignia obtenida por la comunidad

**Descripción:**
Comprobar que la insignia se muestra con la siembra que la sustenta y la explicación de la práctica.
**Trazabilidad:** HU-018 / CA-01 · RN-08.
**Datos de prueba:** H1 con A1 conforme y fotografía F1.

**Procedimiento de prueba:**

1. Sin sesión, consultar el reto Documentación lunar de H1.

**Resultados esperados:**
Se muestra la insignia Documentación lunar de la comunidad de H1, la siembra A1 como sustento y la explicación: la siembra se realiza en luna creciente o luna llena según la práctica.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** ElegibilidadInsigniaTest.unaElegible; RetoPruebaIntegracion.p047.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-048. Insignia única con varias siembras elegibles

**Descripción:**
Comprobar que varias siembras elegibles no duplican la insignia y que corregir una no la retira si queda otra.
**Trazabilidad:** HU-018 / CA-02 · RN-07, RN-08.
**Datos de prueba:** L1, H1 con A1 (10/10) y A3 (13/10) conformes, CAL-T.

**Procedimiento de prueba:**

1. Consultar el reto de H1 y recargar la página.
2. Como L1, corregir A1 a 11/10/2026 08:00 con motivo.
3. Consultar el reto de H1.

**Resultados esperados:**
En los pasos 1 y 3 se muestra una sola insignia. Después de la corrección sigue activa, sustentada por A3.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** ElegibilidadInsigniaTest.variasElegibles; RetoPruebaIntegracion.p048.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-049. Comunidad sin siembras elegibles

**Descripción:**
Comprobar que las siembras no conformes o pendientes no otorgan la insignia.
**Trazabilidad:** HU-018 / CA-03 · RN-08.
**Datos de prueba:** L1, H1 sin siembras, CAL-T.

**Procedimiento de prueba:**

1. Consultar el reto de H1.
2. Registrar una siembra con fecha 11/10/2026 y otra con fecha 12/10/2026.
3. Consultar el reto de H1.

**Resultados esperados:**
En los pasos 1 y 3 no hay insignia y se muestra MSG-18 «Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena.». No se otorgan puntos, niveles ni posiciones de ranking.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** ElegibilidadInsigniaTest.sinElegibles; RetoPruebaIntegracion.p049.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-050. Calendario lunar de los próximos 30 días

**Descripción:**
Comprobar que el calendario muestra fases correctas para Ecuador continental y las actividades recomendadas con su fuente.
**Trazabilidad:** HU-019 / CA-01 · RN-09, RN-10.
**Datos de prueba:** Reloj fijado en 31/10/2026 12:00. REF-LUNA.

**Procedimiento de prueba:**

1. Sin sesión, abrir el calendario con cálculo real y reloj fijo.
2. Comparar eventos principales con REF-LUNA independiente, incluyendo sus vecinos externos al mes.
3. Revisar fase diaria según RN-09, actividades, fuente y zona horaria indicada.
4. Ejecutar P-062 para límites de los intervalos.

**Resultados esperados:**
Se muestran 30 días a partir del 31/10/2026, cada uno con una fase. Las horas de las fases principales coinciden con REF-LUNA con una diferencia máxima de una hora. Las actividades corresponden a RN-10 e indican su fuente.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CalendarioPruebaIntegracion.p050; CalendarioPruebaIntegracion.p050ReferenciaOficial.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-051. Fecha fuera del rango del calendario

**Descripción:**
Comprobar el rango disponible del calendario.
**Trazabilidad:** HU-019 / CA-02 · RN-09.
**Datos de prueba:** Reloj fijado en 31/10/2026 12:00.

**Procedimiento de prueba:**

1. Solicitar el calendario de diciembre de 2027.
2. Solicitar el calendario de septiembre de 2025.

**Resultados esperados:**
En ambos casos se muestra MSG-20 «El calendario lunar está disponible desde {fecha inicial} hasta {fecha final}.» con el rango 31/10/2025 a 31/10/2027 y se conserva la vista anterior.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CalendarioPruebaIntegracion.p051.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-052. Filtro de días de siembra

**Descripción:**
Comprobar que el filtro de siembra resalta exactamente los días recomendados.
**Trazabilidad:** HU-020 / CA-01 · RN-10.
**Datos de prueba:** Calendario de P-050.

**Procedimiento de prueba:**

1. Elegir la actividad "Siembra".

**Resultados esperados:**
Se resaltan solo los días en luna creciente o luna llena.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CalendarioPruebaIntegracion.p052; Playwright · P-052 Filtro de días de siembra.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-053. Filtro de días de cosecha de raíces

**Descripción:**
Comprobar que el filtro de cosecha de semillas y raíces resalta exactamente los días recomendados.
**Trazabilidad:** HU-020 / CA-02 · RN-10.
**Datos de prueba:** Calendario de P-050.

**Procedimiento de prueba:**

1. Elegir la actividad "Cosecha de semillas y raíces".

**Resultados esperados:**
Se resaltan solo los días en luna menguante.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CalendarioPruebaIntegracion.p053; Playwright · P-053 Filtro de días de cosecha de raíces.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-054. Retiro del filtro

**Descripción:**
Comprobar que quitar el filtro restablece la vista completa.
**Trazabilidad:** HU-020 / CA-03 · RN-10.
**Datos de prueba:** Calendario filtrado por "Siembra".

**Procedimiento de prueba:**

1. Quitar el filtro.

**Resultados esperados:**
Se muestran todos los días sin resaltar.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CalendarioPruebaIntegracion.p054; Playwright · P-054 Retiro del filtro.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-055. Recorrido integrado de la primera iteración

**Descripción:**
Verificar el recorrido de demostración con cálculo real y estado inicial conocido; no sustituye las pruebas individuales.
**Trazabilidad:** HU-001 a HU-020, recorrido representativo.
**Datos:** reloj fijo; H1 publicado sin siembras, K1 y K2; F1; dos instantes pasados documentados desde REF-LUNA y RN-09, uno conforme y otro no conforme. No usar CAL-T ni precargar A1/A3.

**Procedimiento de prueba:**

1. Como visitante, ubicar H1, abrir su ficha, recorrer el 3D y seleccionar Z2.
2. Abrir calendario real y filtrar siembra.
3. Como L1, actualizar H1 y el estado de K1; comprobar K2.
4. Registrar la única siembra en el instante conforme del 18/10/2026 a las 08:00 de America/Guayaquil y consultar el reto.
5. Corregir esa siembra al instante no conforme con motivo; consultar historial y reto.
6. Como L2, intentar modificar H1; cerrar sesión y revisar la consulta pública.

**Resultados esperados:**
La fase coincide con la referencia y la convención registrada. Paso 4: una siembra y una insignia. Paso 5: dos versiones, condición no conforme e insignia desactivada, porque no queda ninguna otra siembra elegible. El huerto ajeno no cambia ni se revelan datos privados.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-055 Recorrido integrado de la primera iteración · CAL-T; Playwright · P-055 Recorrido integrado de la primera iteración · efemérides reales.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-056. Gestión privada de un huerto no publicado

**Descripción:**
Gestión privada de un huerto no publicado.
**Trazabilidad:** HU-001 / CA-04.
**Datos de prueba:** L3 y H4 no publicado; planta y cultivo propios preparados.

**Procedimiento de prueba:**

1. Ingresar como L3 y recargar la gestión.
2. Consultar ficha, zonas, plantas y cultivos de H4 mediante sus rutas privadas.
3. Repetir las solicitudes como L1 y sin sesión.

**Resultados esperados:**
L3 recupera únicamente datos autorizados; L1 recibe 403 y la consulta sin sesión recibe 401; las rutas públicas no exponen H4.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.gestionPrivada.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-057. Corrección con fecha futura

**Descripción:**
Corrección con fecha futura.
**Trazabilidad:** HU-016 / CA-05.
**Datos de prueba:** L1, A1, CAL-T; reloj fijo.

**Procedimiento de prueba:**

1. Corregir A1 a un minuto después del reloj, con motivo y foto válida.
2. Consultar versiones, fotografía vigente y condición lunar.

**Resultados esperados:**
MSG-08. No cambia la versión vigente, no se agrega una revisión ni una foto y se conserva la evaluación.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** CorreccionPruebaIntegracion.fechaFutura.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-058. Reintentos simultáneos de siembra

**Descripción:**
Reintentos simultáneos de siembra.
**Trazabilidad:** HU-014 / CA-07.
**Datos de prueba:** H1 sin actividades; L1, K1, F1; PostgreSQL real.

**Procedimiento de prueba:**

1. Enviar en paralelo dos solicitudes idénticas con la misma clave; sincronizar su llegada al guardado.
2. Consultar actividad, versiones y fotografías.

**Resultados esperados:**
Una respuesta de creación y otra de recuperación, ambas con el mismo identificador. Una sola siembra, una versión y una evidencia; ningún error interno por la carrera.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.envioSimultaneo.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-059. Reutilización de clave con otros datos

**Descripción:**
Reutilización de clave con otros datos.
**Trazabilidad:** HU-014 / CA-08.
**Datos de prueba:** L1, K1, F1; un envío confirmado.

**Procedimiento de prueba:**

1. Reenviar la clave cambiando fecha o fotografía.
2. Consultar el registro y contar actividades y versiones.

**Resultados esperados:**
409 y MSG-24; el contenido anterior permanece y no se crea otra actividad.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.claveConOtrosDatos.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-060. Conflicto entre correcciones

**Descripción:**
Conflicto entre correcciones.
**Trazabilidad:** HU-016 / CA-06.
**Datos de prueba:** L1, A1 versión 1; dos solicitudes con claves distintas.

**Procedimiento de prueba:**

1. Enviar simultáneamente dos correcciones diferentes basadas en la versión 1.
2. Consultar el historial; volver a intentar la rechazada tras revisar la nueva versión.

**Resultados esperados:**
Una crea la versión 2; la otra recibe 409 y MSG-25. El nuevo intento deliberado sobre versión 2 puede crear versión 3. Ninguna versión confirmada se sobrescribe.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.correccionSimultanea.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-061. Insignia sin revelar huertos privados

**Descripción:**
Insignia sin revelar huertos privados.
**Trazabilidad:** HU-018 / CA-04.
**Datos de prueba:** Comunidad C1: H1 publicado sin siembras elegibles y H6 privado con una siembra elegible.

**Procedimiento de prueba:**

1. Consultar públicamente el reto de H1 y revisar el cuerpo de la respuesta.
2. Agregar una siembra elegible a H1 y repetir.

**Resultados esperados:**
Primero no hay insignia pública ni datos de H6. Después hay una insignia sustentada únicamente por la siembra pública de H1.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.insigniaSoloConHuertosPublicados.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-062. Continuidad y límites de los intervalos lunares

**Descripción:**
Continuidad y límites de los intervalos lunares.
**Trazabilidad:** HU-015 / CA-04.
**Datos de prueba:** Eventos sintéticos en días relativos −7,4; 0; 7,4; 14,8; conjunto real ampliado REF-LUNA.

**Procedimiento de prueba:**

1. Consultar el día relativo 3,6 y cada límite menos un segundo, en el límite y más un segundo.
2. Recorrer un mes real hora a hora. Comparar instantes equivalentes expresados en Guayaquil y Galápagos.

**Resultados esperados:**
El día 3,6 tiene fase; no hay huecos ni solapamientos. El límite pertenece al intervalo siguiente. Un mismo instante obtiene la misma fase, independientemente de su representación local.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** RN-09: intervalos entre puntos medios.faseEntreEventos.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-063. Ciclo de sesión y protección CSRF

**Descripción:**
Ciclo de sesión y protección CSRF.
**Trazabilidad:** Q-I1-01.
**Datos de prueba:** Aplicación desplegada con configuración equivalente a entrega.

**Procedimiento de prueba:**

1. Obtener token, iniciar sesión, renovar token y guardar una ficha.
2. Cerrar sesión; reenviar una modificación con la sesión anterior.
3. Probar modificación con sesión válida y token ausente o inválido.

**Resultados esperados:**
Inicio, modificación y cierre correctos. La sesión anterior no modifica datos y responde MSG-02. Token inválido con sesión válida: 403 y MSG-26. Ningún rechazo cambia datos.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** Playwright · P-063 Sesión y token CSRF se renuevan y rechazan la cabecera anterior.

**Cobertura del protocolo:** parcial. La rotación de token y los rechazos se verificaron en HTTP local; el transporte HTTPS y las cookies del alojamiento requieren evidencia del entorno de entrega.

## P-064. Acceso directo a fotografías privadas

**Descripción:**
Acceso directo a fotografías privadas.
**Trazabilidad:** HU-011 / CA-03.
**Datos de prueba:** H4 privado; A1 de H1 publicada con dos versiones y fotografías diferentes.

**Procedimiento de prueba:**

1. Solicitar sin sesión foto de H4, versión anterior de A1 y versión vigente de A1.
2. Repetir las dos primeras como su líder autorizado y como otro líder.

**Resultados esperados:**
Visitante: 404 para las dos primeras y foto vigente pública para la última. Líder propio: acceso permitido. Líder ajeno: 404; no se filtran bytes ni metadatos privados.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.fotoHistoricaPrivada.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

**Ampliación de la auditoría:** A-07 verificó nueve combinaciones de visitante, líder propio y ajeno sobre foto histórica, vigente y de huerto privado; todas devolvieron el estado previsto. Las fotos autorizadas respondieron image/png y no-store; los rechazos respondieron 404 sin contenido de imagen.

## P-065. Reversión después de guardar evidencia

**Descripción:**
Reversión después de guardar evidencia.
**Trazabilidad:** HU-014 / CA-09.
**Datos de prueba:** L1, K1, F1; PostgreSQL real con fallo controlado tras insertar la foto.

**Procedimiento de prueba:**

1. Provocar fallo al guardar la actividad y luego al confirmar la operación en pruebas separadas.
2. Consultar la base con una transacción independiente.
3. Retirar el fallo y reintentar la misma clave.

**Resultados esperados:**
Sin actividad parcial ni foto huérfana; MSG-27 solo cuando se confirmó la reversión. El reintento posterior crea una única siembra completa. La pérdida de respuesta se trata como resultado desconocido, no como reversión probada.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.reversionAlConfirmar; IntegridadEnviosPruebaIntegracion.reversionDespuesDeFoto.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-066. Carga y navegación 3D en el perfil Q1-I1

**Descripción:**
Carga y navegación 3D en el perfil Q1-I1.
**Trazabilidad:** Q-I1-03.
**Datos de prueba:** CR1 versionado; dispositivo y navegador registrados; perfil Q1-I1 del documento 05.

**Procedimiento de prueba:**

1. Realizar 10 aperturas con caché de escena vacía y medir hasta interacción.
2. Rotar, acercar, alejar y seleccionar Z1 y Z2; probar alternativa en lista con WebGL no disponible.

**Resultados esperados:**
Al menos 9 de 10 aperturas permiten interacción en 8 segundos o menos; las operaciones completan sin bloquearse y la selección muestra la zona correcta. Una ejecución sin dispositivo y navegador identificados no acredita el protocolo físico.

### Resultado registrado

**Estado:** ejecutado en el candidato local; véase documento 10. No se acredita la condición física o formal del protocolo.

## P-067. Tiempo de consulta de información

**Descripción:**
Tiempo de consulta de información.
**Trazabilidad:** Q-I1-02.
**Datos de prueba:** Q1-I1; entorno y conexión registrados.

**Procedimiento de prueba:**

1. Ejecutar 10 consultas de preparación y 100 consultas repartidas entre ficha, plantas y bitácora.
2. Medir desde la solicitud hasta el contenido textual utilizable y registrar errores.

**Resultados esperados:**
Percentil 95 no mayor de 3 segundos y cero errores funcionales en la muestra. El resultado solo aplica al perfil registrado.

### Resultado registrado

**Estado:** ejecutado en el candidato local; véase documento 10. No se acredita la condición física o formal del protocolo.

## P-068. Recuperación de datos y evidencias

**Descripción:**
Recuperación de datos y evidencias.
**Trazabilidad:** Q-I1-04.
**Datos de prueba:** Copia manual del piloto posterior a carga de datos; entorno separado de recuperación.

**Procedimiento de prueba:**

1. Crear una copia con fotos y revisiones, registrar su fecha y custodiarla fuera del repositorio.
2. Restaurarla en otra base y comprobar ficha, foto vigente, historial y nueva siembra.

**Resultados esperados:**
Datos y fotografías corresponden; se puede consultar y registrar. Se informa el tiempo medido. No se declara todavía un compromiso de recuperación de cuatro horas ni una frecuencia diaria de operación.

### Resultado registrado

**Estado:** ejecutado en el candidato local; véase documento 10. No se acredita la condición física o formal del protocolo.

## P-069. Reintento de corrección confirmada

**Descripción:**
Reintento de corrección confirmada.
**Trazabilidad:** HU-016 / CA-07.
**Datos de prueba:** A1 versión 1; corrección con clave única y versión base 1.

**Procedimiento de prueba:**

1. Confirmar la corrección y simular pérdida de su respuesta.
2. Reenviar exactamente la solicitud; luego reutilizar la clave cambiando un dato.

**Resultados esperados:**
El reintento devuelve la versión 2 existente, sin una tercera. El contenido distinto recibe 409 y MSG-24. La búsqueda del reintento ocurre antes de rechazar la versión base antigua.

### Resultado registrado

**Escenario automatizado:** aprobado en la revisión de `78d8797` del 11/11/2026.

**Evidencia de la suite:** IntegridadEnviosPruebaIntegracion.correccionRepetida.

**Cobertura del protocolo:** comprende las aserciones de la evidencia indicada; los pasos de pantalla descritos no se atribuyen a una prueba Java.

## P-070. Rechazo de zonas no cultivables

**Descripción:** Comprobar la regla del servidor.
**Trazabilidad:** HU-008; RF-025; RN-03.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Como L1, intentar registrar un cultivo de su planta en BODEGA, CAMINO y CERCA de H1.
2. Consultar el número de cultivos.

**Resultado esperado:** HTTP 400 y MSG-29 en todos los intentos; sin nuevas filas.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** CultivoPruebaIntegracion.rechazaZonasNoCultivables. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-071. Cierre de sesión interrumpido

**Descripción:** Evitar una confirmación falsa del cierre.
**Trazabilidad:** HU-001; RF-031.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Ingresar como L1.
2. Interrumpir DELETE /api/sesion y pulsar Cerrar sesión.
3. Restablecer conexión y reintentar.

**Resultado esperado:** Se informa que no se confirmó el cierre y se conserva el estado autenticado; tras el reintento confirmado, la sesión queda anónima en cliente y servidor.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-071. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-072. Respuestas de filtro desordenadas

**Descripción:** Conservar la última intención del usuario.
**Trazabilidad:** HU-020; RF-085.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Retrasar la respuesta para SIEMBRA.
2. Seleccionar COSECHA_SEMILLAS_RAICES y recibir su respuesta.
3. Entregar la respuesta atrasada.

**Resultado esperado:** La última selección permanece y los días resaltados corresponden a luna menguante.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-072. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-073. Campos institucionales disponibles en ficha

**Descripción:** Verificar la presentación de altitud y distribución.
**Trazabilidad:** HU-006 y HU-011; RF-024.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Simular una respuesta institucional que aporte ambos campos.
2. Abrir la ficha pública.

**Resultado esperado:** Ambos datos son visibles. Se verifica presentación mediante respuesta simulada; las cuatro fichas reales iniciales no incorporan esos campos.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-073. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-074. Hora de gestión de Galápagos

**Descripción:** Usar la zona horaria del huerto.
**Trazabilidad:** HU-009; RF-026; AD-10.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Preparar H1 con provincia Galápagos.
2. Abrir gestión de cultivos.
3. Contrastar el mismo instante con Pacific/Galapagos.

**Resultado esperado:** La actualización se presenta con Pacific/Galapagos y coincide con el instante persistido.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-074. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-075. Rechazo de nombre libre largo

**Descripción:** Evitar el antiguo error 500 bajo el contrato nuevo.
**Trazabilidad:** HU-006; RF-023; RN-11.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Enviar por la API un nombre común libre de 121 caracteres junto con catalogoCodigo.
2. Consultar el número de fichas.

**Resultado esperado:** HTTP 400 y MSG-28; no se guarda la petición. El líder selecciona un código: ya no existe editor de nombres libres.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.nombreLibreLargoNoProduce500. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-076. Indisponibilidad cartográfica

**Descripción:** Mantener consulta alternativa comprensible.
**Trazabilidad:** HU-010; RF-006.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Interrumpir la descarga de teselas.
2. Consultar la lista de huertos.
3. Abrir H1 desde la lista.

**Resultado esperado:** Se informa el fallo de mapa base y la ficha sigue accesible.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-076. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.

## P-077. Mapa sin huertos publicados

**Descripción:** Comunicar ausencia de resultados.
**Trazabilidad:** HU-010; RF-005.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Preparar una respuesta vacía de huertos publicados.
2. Abrir el mapa.

**Resultado esperado:** Se informa que todavía no hay huertos publicados y no se muestran marcadores.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** correcciones.spec.ts: P-077. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

**Antecedente:** los resultados del commit base del 11/11/2026 se conservan en evidencias/resultados_adicionales.json. El resultado vigente de este apartado es la ejecución local del candidato del 12/11/2026.


## P-078. Catálogo inexistente

**Descripción:** Validar identificadores del catálogo.
**Trazabilidad:** HU-006; RF-023.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Enviar catalogoCodigo inexistente.

**Resultado esperado:** HTTP 400 y MSG-28; sin nueva ficha.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.rechazaCatalogoInexistente. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-079. Contenido anterior sin referencia

**Descripción:** Evitar publicación de información no trazada.
**Trazabilidad:** HU-006; RF-023.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Preparar una planta anterior sin catalogoCodigo.
2. Consultar gestión, ficha pública y sus cultivos.

**Resultado esperado:** El registro permanece privado y no aparece en ficha pública ni en cultivos públicos.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.fichaAnteriorSinCatalogoNoSePublica. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-080. Catálogo sin escritura por líder

**Descripción:** Separar gestión del huerto y contenido institucional.
**Trazabilidad:** RN-11; RF-023.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Consultar el catálogo.
2. Intentar POST al catálogo autenticado.
3. Consultar el número de registros.

**Resultado esperado:** La consulta funciona y el intento no crea ni altera registros. La prueba verifica persistencia; no certifica el formato de respuesta de un método no soportado.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.catalogoNoTieneOperacionDeEscritura. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-081. Permiso sobre fotografía ajena

**Descripción:** Evitar que un líder modifique otra ficha.
**Trazabilidad:** HU-007; RN-02.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Como L1, intentar modificar una ficha de H2.

**Resultado esperado:** HTTP 403, MSG-03; sin cambios.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** PlantaPruebaIntegracion.fichaAjena. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

## P-082. Selección de ficha en interfaz

**Descripción:** Verificar el recorrido sin editor medicinal.
**Trazabilidad:** HU-006 y HU-007; RN-11.
**Datos:** cuentas L1 y L2, huertos H1 y H2 y catálogo sintético de pruebas; estos datos no son información medicinal del MSP.

**Procedimiento:**

1. Ingresar como L1.
2. Abrir Plantas e incorporar MSP-HIERBA-LUISA.
3. Revisar fuente y contenido de lectura y guardar.
4. Consultar H1.

**Resultado esperado:** No hay editor de usos ni campos de texto medicinales; se muestra la información institucional y se publica la ficha seleccionada.

**Resultado registrado:** aprobado en la copia local del candidato de corrección del 12/11/2026.
**Evidencia:** frontend/e2e/correcciones.spec.ts: El líder incorpora una ficha institucional y no tiene editor medicinal. El alcance de una prueba de API se limita a sus aserciones; no acredita ejecución manual en Android.

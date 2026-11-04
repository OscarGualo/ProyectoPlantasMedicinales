**ESCUELA POLITÉCNICA NACIONAL**
Ingeniería de Software
CALIDAD DE SOFTWARE (ISWD652) · GR2SW · Período 2026-B

# Casos de prueba

**Huertos Medicinales Comunitarios**
Iteración 1 · Versión 1.2

- **Equipo:** [Nombre del equipo]
- **Integrantes:**
  - [Integrante 1]
  - [Integrante 2]
  - [Integrante 3]
  - [Integrante 4]
  - [Integrante 5]
  - [Integrante 6]
- **Fecha:** 2 de noviembre de 2026

*Casos diseñados; ejecuciones por registrar*

---

## Presentación

Este documento contiene los 55 casos de prueba de la primera iteración en la plantilla del aula: uno por cada criterio de aceptación de las historias HU-001 a HU-020 y un recorrido integrado (P-055) que reproduce la demostración al cliente.

### Cómo ejecutar y registrar

- Antes de cada caso, restaurar el entorno de prueba con los datos de la sección 7 del documento 05 y fijar el reloj en 31/10/2026 12:00 (America/Guayaquil).
- **CAL-T** es un calendario controlado que sustituye al cálculo lunar solo en el entorno de prueba, para obtener fases conocidas. Los casos P-050 a P-054 usan el cálculo real; P-050 lo compara con efemérides oficiales (REF-LUNA).
- Cada ejecución se registra en una fila: fecha, responsable, versión del producto y navegador, resultado obtenido, aprobado o reprobado y captura. Una nueva ejecución agrega una fila; no reemplaza la anterior.
- Un caso reprobado conserva su resultado obtenido y el defecto encontrado. Un caso sin ejecutar no se marca como aprobado ni como reprobado.

### Resumen de casos

| ID | Historia | Criterio | Título |
|---|---|---|---|
| P-001 | HU-001 | CA-01 | Inicio de sesión con credenciales válidas |
| P-002 | HU-001 | CA-02 | Inicio de sesión con credenciales incorrectas |
| P-003 | HU-001 | CA-03 | Modificación de un huerto no asignado |
| P-004 | HU-002 | CA-01 | Cierre de sesión |
| P-005 | HU-002 | CA-02 | Modificación después de cerrar sesión |
| P-006 | HU-003 | CA-01 | Actualización de la ubicación del huerto |
| P-007 | HU-003 | CA-02 | Coordenadas fuera de rango |
| P-008 | HU-004 | CA-01 | Actualización de las características del huerto |
| P-009 | HU-004 | CA-02 | Huerto sin nombre |
| P-010 | HU-004 | CA-03 | Superficie negativa |
| P-011 | HU-005 | CA-01 | Publicación de un huerto con ubicación |
| P-012 | HU-005 | CA-02 | Publicación de un huerto sin ubicación |
| P-013 | HU-006 | CA-01 | Registro de una planta medicinal |
| P-014 | HU-006 | CA-02 | Planta con un dato obligatorio faltante |
| P-015 | HU-006 | CA-03 | Planta con fotografía no admitida |
| P-016 | HU-007 | CA-01 | Modificación de una planta |
| P-017 | HU-007 | CA-02 | Independencia de fichas entre huertos |
| P-018 | HU-007 | CA-03 | Borrado de un dato obligatorio |
| P-019 | HU-008 | CA-01 | Registro de cultivos de una misma planta |
| P-020 | HU-008 | CA-02 | Cultivo en una zona de otro huerto |
| P-021 | HU-009 | CA-01 | Cambio de estado de un cultivo |
| P-022 | HU-009 | CA-02 | Estado fuera del catálogo |
| P-023 | HU-010 | CA-01 | Marcadores de los huertos publicados |
| P-024 | HU-010 | CA-02 | Huerto no publicado en el mapa |
| P-025 | HU-011 | CA-01 | Consulta de la ficha pública de un huerto |
| P-026 | HU-011 | CA-02 | Enlace directo a un huerto no publicado |
| P-027 | HU-012 | CA-01 | Recorrido de la escena 3D |
| P-028 | HU-012 | CA-02 | Fallo de carga de la escena |
| P-029 | HU-013 | CA-01 | Cultivos de una zona |
| P-030 | HU-013 | CA-02 | Señalética caliente y fría |
| P-031 | HU-013 | CA-03 | Zona sin cultivos |
| P-032 | HU-014 | CA-01 | Registro de una siembra con fotografía |
| P-033 | HU-014 | CA-02 | Envío repetido de una siembra |
| P-034 | HU-014 | CA-03 | Siembra con fotografía no válida |
| P-035 | HU-014 | CA-04 | Siembra con fecha futura |
| P-036 | HU-014 | CA-05 | Siembra en un cultivo de otro huerto |
| P-037 | HU-014 | CA-06 | Falla al guardar la fotografía |
| P-038 | HU-015 | CA-01 | Siembra conforme con la condición lunar |
| P-039 | HU-015 | CA-02 | Siembra no conforme con la condición lunar |
| P-040 | HU-015 | CA-03 | Siembra sin información de fase |
| P-041 | HU-016 | CA-01 | Corrección de una siembra con motivo |
| P-042 | HU-016 | CA-02 | Corrección sin motivo |
| P-043 | HU-016 | CA-03 | Corrección por el líder de otro huerto |
| P-044 | HU-016 | CA-04 | Fotografía no válida en una corrección |
| P-045 | HU-017 | CA-01 | Historial de una siembra corregida |
| P-046 | HU-017 | CA-02 | Historial de una siembra sin correcciones |
| P-047 | HU-018 | CA-01 | Insignia obtenida por la comunidad |
| P-048 | HU-018 | CA-02 | Insignia única con varias siembras elegibles |
| P-049 | HU-018 | CA-03 | Comunidad sin siembras elegibles |
| P-050 | HU-019 | CA-01 | Calendario lunar de los próximos 30 días |
| P-051 | HU-019 | CA-02 | Fecha fuera del rango del calendario |
| P-052 | HU-020 | CA-01 | Filtro de días de siembra |
| P-053 | HU-020 | CA-02 | Filtro de días de cosecha de raíces |
| P-054 | HU-020 | CA-03 | Retiro del filtro |
| P-055 | HU-001 a HU-020 | Recorrido | Recorrido integrado de la primera iteración |

---

## Casos de prueba · Iteración 1

### P-001 · Inicio de sesión con credenciales válidas

**Descripción:** Comprobar que un líder con cuenta activa inicia sesión y accede solo a la gestión del huerto que tiene asignado.

**Trazabilidad:** HU-001 / CA-01 · RN-01.

**Datos de prueba:** L1, H1, H2.

**Procedimiento de prueba:**

1. Abrir la aplicación sin sesión.
2. Seleccionar "Iniciar sesión" e ingresar el usuario y la contraseña de L1.
3. Revisar los huertos que se ofrecen para gestionar.

**Resultados esperados:** Se inicia la sesión de L1 y se muestra la gestión de H1. No se ofrece la gestión de H2.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-002 · Inicio de sesión con credenciales incorrectas

**Descripción:** Comprobar que el sistema no inicia sesión con credenciales incorrectas y no revela cuál dato falló.

**Trazabilidad:** HU-001 / CA-02 · RN-01.

**Datos de prueba:** L1 y el usuario inexistente "usuario.inexistente".

**Procedimiento de prueba:**

1. Ingresar el usuario de L1 con una contraseña incorrecta.
2. Ingresar el usuario "usuario.inexistente" con cualquier contraseña.

**Resultados esperados:** En ambos intentos se muestra exactamente MSG-01 «Usuario o contraseña incorrectos.» y no se inicia sesión.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-003 · Modificación de un huerto no asignado

**Descripción:** Comprobar que un líder no puede modificar un huerto que no le fue asignado, ni desde la pantalla ni con solicitudes directas al servidor.

**Trazabilidad:** HU-001 / CA-03 · RN-01.

**Datos de prueba:** L2 (líder de H2), H1. Cliente HTTP o herramientas de desarrollador.

**Procedimiento de prueba:**

1. Anotar el nombre actual de H1.
2. Ingresar como L2.
3. Intentar abrir la gestión de H1 escribiendo su dirección en el navegador.
4. Enviar directamente al servidor una solicitud de cambio del nombre de H1.
5. Consultar H1 sin sesión.

**Resultados esperados:** Los pasos 3 y 4 se rechazan con MSG-03 «No tienes permiso para modificar la información de este huerto.». H1 conserva el nombre anotado.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-004 · Cierre de sesión

**Descripción:** Comprobar que el líder finaliza su sesión y vuelve a la consulta pública.

**Trazabilidad:** HU-002 / CA-01 · RN-01.

**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Ingresar como L1.
2. Seleccionar "Cerrar sesión".
3. Intentar abrir la gestión de H1.

**Resultados esperados:** Tras el paso 2 se muestra la consulta pública. En el paso 3 el sistema solicita iniciar sesión.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-005 · Modificación después de cerrar sesión

**Descripción:** Comprobar que el servidor rechaza una modificación enviada con los datos de una sesión ya cerrada.

**Trazabilidad:** HU-002 / CA-02 · RN-01.

**Datos de prueba:** L1, H1. Cliente HTTP o herramientas de desarrollador.

**Procedimiento de prueba:**

1. Ingresar como L1 y anotar el nombre de H1.
2. Cambiar el nombre de H1, capturar la solicitud enviada y restaurar el nombre original.
3. Cerrar sesión.
4. Reenviar al servidor la solicitud capturada.
5. Consultar H1 sin sesión.

**Resultados esperados:** La solicitud del paso 4 se rechaza con MSG-02 «Tu sesión ha finalizado. Inicia sesión para continuar.». H1 conserva el nombre anotado.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-006 · Actualización de la ubicación del huerto

**Descripción:** Comprobar que la ubicación se guarda y se recupera, incluidos los valores límite de las coordenadas.

**Trazabilidad:** HU-003 / CA-01 · RN-01.

**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Como L1, guardar: provincia Pichincha; parroquia "Parroquia de prueba"; dirección "Dirección de prueba"; latitud -0.2; longitud -78.5.
2. Cerrar sesión, volver a ingresar y abrir la ubicación de H1.
3. Guardar latitud 90 y longitud -180; recargar; restaurar los valores del paso 1.

**Resultados esperados:** Los pasos 2 y 3 muestran exactamente los valores guardados.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-007 · Coordenadas fuera de rango

**Descripción:** Comprobar que no se guardan coordenadas fuera de rango.

**Trazabilidad:** HU-003 / CA-02 · RN-01.

**Datos de prueba:** L1, H1 con la ubicación de P-006.

**Procedimiento de prueba:**

1. Guardar latitud 91 y recargar.
2. Guardar longitud 181 y recargar.

**Resultados esperados:** Ambos intentos muestran MSG-06 «Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180.» y H1 conserva latitud -0.2 y longitud -78.5.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-008 · Actualización de las características del huerto

**Descripción:** Comprobar que las características se guardan y se recuperan, incluida la superficie 0.

**Trazabilidad:** HU-004 / CA-01 · RN-01, RN-02.

**Datos de prueba:** L1, H1.

**Procedimiento de prueba:**

1. Como L1, guardar: nombre "Huerto Demostrativo A"; descripción "Huerto de prueba"; diseño "en tierra: camellones"; cerca "cerca viva"; superficie 25 m²; suelo "margoso"; fuente de agua "canal"; técnica de riego "regadera"; abono "compost".
2. Cerrar sesión, volver a ingresar y abrir las características.
3. Guardar superficie 0, recargar y restaurar 25 m².

**Resultados esperados:** Los pasos 2 y 3 muestran exactamente los valores guardados.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-009 · Huerto sin nombre

**Descripción:** Comprobar que el nombre es obligatorio.

**Trazabilidad:** HU-004 / CA-02 · RN-01.

**Datos de prueba:** L1, H1 con nombre "Huerto Demostrativo A".

**Procedimiento de prueba:**

1. Borrar el nombre y guardar.
2. Recargar las características.

**Resultados esperados:** Se muestra MSG-04 «Completa el campo obligatorio: {campo}.» con el campo "Nombre". H1 conserva el nombre "Huerto Demostrativo A".

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-010 · Superficie negativa

**Descripción:** Comprobar que la superficie no puede ser menor que 0.

**Trazabilidad:** HU-004 / CA-03 · RN-01.

**Datos de prueba:** L1, H1 con superficie 25 m².

**Procedimiento de prueba:**

1. Guardar superficie -1.
2. Recargar las características.

**Resultados esperados:** Se muestra MSG-05 «La superficie debe ser un número mayor o igual a 0.». H1 conserva la superficie de 25 m².

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-011 · Publicación de un huerto con ubicación

**Descripción:** Comprobar que un huerto con nombre y coordenadas se publica y aparece en el mapa.

**Trazabilidad:** HU-005 / CA-01 · RN-02.

**Datos de prueba:** L4 (líder de H5), H5 no publicado con nombre y coordenadas.

**Procedimiento de prueba:**

1. Ingresar como L4 y publicar H5.
2. Cerrar sesión y abrir el mapa.

**Resultados esperados:** H5 aparece en el mapa en sus coordenadas registradas.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-012 · Publicación de un huerto sin ubicación

**Descripción:** Comprobar que un huerto sin coordenadas no puede publicarse.

**Trazabilidad:** HU-005 / CA-02 · RN-02.

**Datos de prueba:** L3 (líder de H4), H4 no publicado y sin coordenadas.

**Procedimiento de prueba:**

1. Ingresar como L3 y solicitar la publicación de H4.
2. Cerrar sesión y abrir el mapa.

**Resultados esperados:** Se muestra MSG-21 «El huerto no tiene ubicación registrada y no puede publicarse en el mapa.». H4 continúa no publicado y no tiene marcador en el mapa.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-013 · Registro de una planta medicinal

**Descripción:** Comprobar que el líder registra una ficha completa y que el público la consulta con todos sus datos.

**Trazabilidad:** HU-006 / CA-01 · RN-02, RN-03.

**Datos de prueba:** L1, H1, F1.

**Procedimiento de prueba:**

1. Como L1, registrar E1: nombre común "Planta de prueba"; nombre científico "Plantae probatoria"; familia "Familia de prueba"; descripción "Ficha sintética sin uso medicinal"; fuente "Catálogo de prueba del equipo"; clasificación "fría"; fotografía F1.
2. Cerrar sesión y consultar las plantas de H1.

**Resultados esperados:** E1 aparece en las plantas de H1 con todos los datos ingresados y la fotografía F1.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-014 · Planta con un dato obligatorio faltante

**Descripción:** Comprobar que no se registra una planta sin nombre común, nombre científico, familia o fuente.

**Trazabilidad:** HU-006 / CA-02 · RN-02.

**Datos de prueba:** L1, H1, F1.

**Procedimiento de prueba:**

1. Registrar una planta sin nombre común.
2. Repetir dejando vacío, uno a la vez, el nombre científico, la familia y la fuente.
3. Consultar las plantas de H1.

**Resultados esperados:** Cada intento muestra MSG-04 «Completa el campo obligatorio: {campo}.» con el campo faltante. Ninguna de esas plantas se registra.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-015 · Planta con fotografía no admitida

**Descripción:** Comprobar el formato y el tamaño máximo de la fotografía, incluido el límite exacto de 5 MB.

**Trazabilidad:** HU-006 / CA-03 · RN-05.

**Datos de prueba:** L1, H1, F2, F3, F4.

**Procedimiento de prueba:**

1. Registrar una planta completa con F2.
2. Registrar una planta completa con F3.
3. Registrar una planta completa con F4.

**Resultados esperados:** Pasos 1 y 2: MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.» y no se registran. Paso 3: la planta se registra porque F4 mide exactamente 5 MB.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-016 · Modificación de una planta

**Descripción:** Comprobar que la consulta pública muestra la ficha modificada.

**Trazabilidad:** HU-007 / CA-01 · RN-02.

**Datos de prueba:** L1, E1 publicada.

**Procedimiento de prueba:**

1. Como L1, cambiar la descripción de E1 a "Descripción corregida".
2. Cerrar sesión y consultar E1.

**Resultados esperados:** E1 muestra "Descripción corregida".

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-017 · Independencia de fichas entre huertos

**Descripción:** Comprobar que modificar una ficha no altera la ficha de la misma planta en otro huerto.

**Trazabilidad:** HU-007 / CA-02 · RN-03.

**Datos de prueba:** L1, E1 en H1, E2 en H2 con los mismos nombres.

**Procedimiento de prueba:**

1. Como L1, cambiar la descripción de E1.
2. Consultar E2 sin sesión.

**Resultados esperados:** E2 conserva su descripción original.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-018 · Borrado de un dato obligatorio

**Descripción:** Comprobar que no se publica una ficha modificada sin un dato obligatorio.

**Trazabilidad:** HU-007 / CA-03 · RN-02.

**Datos de prueba:** L1, E1 publicada.

**Procedimiento de prueba:**

1. Como L1, borrar el nombre común de E1 y guardar.
2. Consultar E1 sin sesión.

**Resultados esperados:** Se muestra MSG-04 «Completa el campo obligatorio: {campo}.» con el campo "Nombre común". La versión pública de E1 no cambia.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-019 · Registro de cultivos de una misma planta

**Descripción:** Comprobar que una planta puede tener cultivos distintos en zonas distintas.

**Trazabilidad:** HU-008 / CA-01 · RN-03.

**Datos de prueba:** L1, E1, Z1, Z2. Reloj fijado.

**Procedimiento de prueba:**

1. Como L1, registrar K1: planta E1, zona Z1.
2. Registrar K2: planta E1, zona Z2.
3. Consultar K1 y K2.

**Resultados esperados:** K1 y K2 muestran planta E1, su zona, estado "sembrado" y fecha 31/10/2026 12:00.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-020 · Cultivo en una zona de otro huerto

**Descripción:** Comprobar que no se registra un cultivo en una zona de otro huerto.

**Trazabilidad:** HU-008 / CA-02 · RN-03.

**Datos de prueba:** L1, E1, Z3 (de H2).

**Procedimiento de prueba:**

1. Como L1, enviar directamente al servidor el registro de un cultivo de E1 en Z3.
2. Consultar los cultivos de H1 y H2.

**Resultados esperados:** Se muestra MSG-09 «La zona seleccionada no pertenece a este huerto.». No se registra el cultivo en ningún huerto.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-021 · Cambio de estado de un cultivo

**Descripción:** Comprobar que el estado pertenece al cultivo y no a la planta.

**Trazabilidad:** HU-009 / CA-01 · RN-03.

**Datos de prueba:** L1, K1 y K2 en estado "sembrado".

**Procedimiento de prueba:**

1. Como L1, cambiar K1 a "en crecimiento".
2. Consultar K1, K2 y la ficha de E1.

**Resultados esperados:** K1 muestra "en crecimiento" y una nueva fecha de actualización. K2 sigue "sembrado". La ficha de E1 no muestra un estado.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-022 · Estado fuera del catálogo

**Descripción:** Comprobar que solo se aceptan estados del catálogo.

**Trazabilidad:** HU-009 / CA-02 · RN-03.

**Datos de prueba:** L1, K1.

**Procedimiento de prueba:**

1. Como L1, enviar directamente al servidor el estado "gigante" para K1.
2. Consultar K1.

**Resultados esperados:** Se muestra MSG-10 «Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido.». K1 conserva su estado anterior.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-023 · Marcadores de los huertos publicados

**Descripción:** Comprobar que el mapa muestra un marcador por cada huerto publicado en su ubicación registrada.

**Trazabilidad:** HU-010 / CA-01 · RN-02.

**Datos de prueba:** H1 y H2 publicados con coordenadas.

**Procedimiento de prueba:**

1. Sin sesión, abrir el mapa.
2. Comparar la posición de cada marcador con las coordenadas registradas de H1 y H2.

**Resultados esperados:** Aparecen marcadores de H1 y H2 en sus coordenadas registradas.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-024 · Huerto no publicado en el mapa

**Descripción:** Comprobar que un huerto no publicado no aparece en el mapa.

**Trazabilidad:** HU-010 / CA-02 · RN-02.

**Datos de prueba:** H3 no publicado.

**Procedimiento de prueba:**

1. Sin sesión, abrir el mapa y buscar H3.

**Resultados esperados:** H3 no tiene marcador.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-025 · Consulta de la ficha pública de un huerto

**Descripción:** Comprobar que cada marcador abre la ficha correcta y que no muestra datos privados.

**Trazabilidad:** HU-011 / CA-01 · RN-02.

**Datos de prueba:** H1 con contacto del responsable registrado, H2.

**Procedimiento de prueba:**

1. Sin sesión, seleccionar el marcador de H1.
2. Revisar los datos mostrados y abrir sus plantas, cultivos y vista 3D.
3. Volver al mapa y seleccionar H2.

**Resultados esperados:** Cada marcador abre la ficha de su huerto con sus plantas, cultivos y acceso a la vista 3D. No se muestran el contacto del responsable ni datos de la cuenta.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-026 · Enlace directo a un huerto no publicado

**Descripción:** Comprobar que no se pueden consultar los datos de un huerto no publicado mediante su enlace.

**Trazabilidad:** HU-011 / CA-02 · RN-02.

**Datos de prueba:** H3 no publicado.

**Procedimiento de prueba:**

1. Sin sesión, abrir el enlace directo de H3.

**Resultados esperados:** Se muestra MSG-14 «El huerto solicitado no existe o no está publicado.» y ningún dato de H3.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-027 · Recorrido de la escena 3D

**Descripción:** Comprobar que la escena representa las partes del croquis aprobado y que la navegación funciona en los dispositivos acordados.

**Trazabilidad:** HU-012 / CA-01 · RN-02.

**Datos de prueba:** H1, CR1. Un computador con navegador actualizado y un celular Android.

**Procedimiento de prueba:**

1. Abrir la vista 3D de H1.
2. Rotar, acercar y alejar la vista.
3. Comparar con CR1 las zonas Z1 y Z2, los caminos, la cerca y la bodega.
4. Repetir en el otro dispositivo.

**Resultados esperados:** Las cuatro categorías presentes en CR1 se identifican en posiciones correspondientes. La navegación funciona en ambos dispositivos.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-028 · Fallo de carga de la escena

**Descripción:** Comprobar que, si la escena no carga, el visitante consulta el mismo huerto en forma de lista.

**Trazabilidad:** HU-012 / CA-02 · RN-02.

**Datos de prueba:** H1. Entorno de prueba con el recurso de la escena bloqueado.

**Procedimiento de prueba:**

1. Bloquear el recurso de la escena de H1.
2. Abrir la vista 3D de H1.
3. Restaurar el recurso y recargar.

**Resultados esperados:** Se muestra MSG-13 «No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.» y la lista de Z1 y Z2 con sus cultivos, sin la escena de otro huerto. Al restaurar, la escena de H1 se muestra.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-029 · Cultivos de una zona

**Descripción:** Comprobar que al seleccionar una zona se muestran sus cultivos con planta y estado.

**Trazabilidad:** HU-013 / CA-01 · RN-03.

**Datos de prueba:** H1; K1 en Z1; K2, K4 y K5 en Z2.

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z1.
2. Seleccionar Z2.

**Resultados esperados:** Z1 muestra K1. Z2 muestra K2, K4 y K5. Cada cultivo indica su planta y estado; no aparecen cultivos de H2.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-030 · Señalética caliente y fría

**Descripción:** Comprobar el color de cada cultivo según la clasificación de su planta.

**Trazabilidad:** HU-013 / CA-02 · RN-03.

**Datos de prueba:** Z2 con K2 (E1, fría), K4 (E3, caliente) y K5 (E4, sin clasificación).

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z2.

**Resultados esperados:** K2 se señala en verde, K4 en rojo y K5 en gris.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-031 · Zona sin cultivos

**Descripción:** Comprobar el mensaje de una zona sin cultivos.

**Trazabilidad:** HU-013 / CA-03 · RN-03.

**Datos de prueba:** H1 con la zona Z4 sin cultivos.

**Procedimiento de prueba:**

1. En la vista 3D de H1, seleccionar Z4.

**Resultados esperados:** Se muestra MSG-22 «Esta zona no tiene cultivos registrados.».

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-032 · Registro de una siembra con fotografía

**Descripción:** Comprobar que la siembra se guarda con fecha del hecho, fecha de registro y autor separados, y que persiste.

**Trazabilidad:** HU-014 / CA-01 · RN-04, RN-05.

**Datos de prueba:** L1, K1, F1. Reloj fijado.

**Procedimiento de prueba:**

1. Como L1, registrar una siembra de K1: fecha y hora 10/10/2026 08:00; técnica semillas; descripción "Siembra de prueba"; fotografía F1.
2. Consultar la bitácora de K1.
3. Cerrar sesión, volver a ingresar y consultar la bitácora.

**Resultados esperados:** La bitácora muestra la siembra A1 con fecha del hecho 10/10/2026 08:00, fecha de registro 31/10/2026 12:00, autor L1, técnica, descripción y F1, también después de volver a ingresar. El estado de K1 no cambia automáticamente.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-033 · Envío repetido de una siembra

**Descripción:** Comprobar que un reenvío por falla de conexión no duplica la siembra y que una siembra distinta sí se registra.

**Trazabilidad:** HU-014 / CA-02 · RN-04.

**Datos de prueba:** L1, K1, F1, F4. Cliente HTTP o simulación de red.

**Procedimiento de prueba:**

1. Registrar A1 como en P-032 y reenviar la misma solicitud con el mismo identificador de envío.
2. Registrar otra siembra A2 de K1 con fecha 13/10/2026 09:00 y fotografía F4.
3. Consultar la bitácora de K1.

**Resultados esperados:** La bitácora contiene exactamente dos siembras: una A1 y una A2.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-034 · Siembra con fotografía no válida

**Descripción:** Comprobar que no se registra una siembra con evidencia no admitida.

**Trazabilidad:** HU-014 / CA-03 · RN-05.

**Datos de prueba:** L1, K1, F2, F3.

**Procedimiento de prueba:**

1. Registrar una siembra de K1 con F2.
2. Registrar una siembra de K1 con F3.
3. Consultar la bitácora.

**Resultados esperados:** Ambos intentos muestran MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.». La bitácora no contiene esas siembras.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-035 · Siembra con fecha futura

**Descripción:** Comprobar que la fecha del hecho no puede ser posterior al momento actual, incluido su valor límite.

**Trazabilidad:** HU-014 / CA-04 · RN-05.

**Datos de prueba:** L1, K1, F1. Reloj fijado en 31/10/2026 12:00.

**Procedimiento de prueba:**

1. Registrar una siembra de K1 con fecha 01/11/2026 08:00.
2. Registrar una siembra de K1 con fecha 31/10/2026 11:59.

**Resultados esperados:** Paso 1: MSG-08 «La fecha y hora de la actividad no pueden ser posteriores al momento actual.» y no se registra. Paso 2: la siembra se registra.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-036 · Siembra en un cultivo de otro huerto

**Descripción:** Comprobar que un líder no registra actividades en cultivos ajenos.

**Trazabilidad:** HU-014 / CA-05 · RN-01, RN-04.

**Datos de prueba:** L1, K3 (de H2).

**Procedimiento de prueba:**

1. Como L1, enviar directamente al servidor una siembra para K3.
2. Consultar la bitácora de K3.

**Resultados esperados:** Se muestra MSG-19 «El cultivo seleccionado no pertenece a tu huerto.». La bitácora de K3 no cambia.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-037 · Falla al guardar la fotografía

**Descripción:** Comprobar que una falla de almacenamiento no deja una siembra sin evidencia.

**Trazabilidad:** HU-014 / CA-06 · RN-05.

**Datos de prueba:** L1, K1, F1. Entorno de prueba con el almacenamiento de imágenes desactivado.

**Procedimiento de prueba:**

1. Desactivar el almacenamiento de imágenes.
2. Registrar una siembra de K1 con F1.
3. Consultar la bitácora.
4. Reactivar el almacenamiento y repetir el registro.

**Resultados esperados:** Paso 2: MSG-11 «No se pudo guardar la fotografía. La actividad no se registró; inténtalo nuevamente.» y la bitácora no contiene la siembra. Paso 4: la siembra se registra con su fotografía.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-038 · Siembra conforme con la condición lunar

**Descripción:** Comprobar que una siembra en luna creciente o llena se evalúa conforme usando la fecha del hecho.

**Trazabilidad:** HU-015 / CA-01 · RN-06, RN-09.

**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Configurar CAL-T en el entorno de prueba.
2. Registrar A1 con fecha del hecho 10/10/2026 08:00 y consultar su condición lunar.
3. Registrar A3 con fecha del hecho 13/10/2026 08:00 y consultar su condición lunar.

**Resultados esperados:** A1 muestra fase "luna creciente", la regla RN-06 y MSG-15 «Conforme con la condición lunar: la siembra se realizó en {fase}.». A3 muestra fase "luna llena" y MSG-15 «Conforme con la condición lunar: la siembra se realizó en {fase}.». La fecha de registro (31/10/2026) no influye en la fase.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-039 · Siembra no conforme con la condición lunar

**Descripción:** Comprobar que una siembra en luna menguante u oscura se evalúa no conforme y se conserva.

**Trazabilidad:** HU-015 / CA-02 · RN-06.

**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Registrar una siembra con fecha 11/10/2026 08:00 y consultar su condición lunar.
2. Registrar una siembra con fecha 14/10/2026 08:00 y consultar su condición lunar.
3. Consultar la bitácora.

**Resultados esperados:** La primera muestra "luna menguante" y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.»; la segunda "luna oscura" y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.». Ambas permanecen en la bitácora.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-040 · Siembra sin información de fase

**Descripción:** Comprobar que, sin información de fase, la siembra queda pendiente de comprobación.

**Trazabilidad:** HU-015 / CA-03 · RN-06.

**Datos de prueba:** L1, K1, F1, CAL-T.

**Procedimiento de prueba:**

1. Registrar una siembra con fecha 12/10/2026 08:00 y consultar su condición lunar.
2. Consultar la bitácora.

**Resultados esperados:** La siembra muestra MSG-17 «Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra.» y aparece en la bitácora como pendiente de comprobación.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-041 · Corrección de una siembra con motivo

**Descripción:** Comprobar que la corrección crea una versión nueva, conserva la anterior y evalúa la condición lunar con la versión vigente.

**Trazabilidad:** HU-016 / CA-01 · RN-06, RN-07.

**Datos de prueba:** L1, A1 conforme (10/10/2026), CAL-T.

**Procedimiento de prueba:**

1. Como L1, corregir la fecha de A1 a 11/10/2026 08:00 con el motivo "Fecha registrada por error".
2. Consultar A1 y su condición lunar.
3. Consultar en la base de datos de prueba las versiones de A1.

**Resultados esperados:** A1 muestra la fecha 11/10/2026 y MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.». Existen dos versiones: la vigente del 11/10/2026 con su motivo y autor, y la anterior del 10/10/2026 sin modificar.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-042 · Corrección sin motivo

**Descripción:** Comprobar que no se acepta una corrección sin motivo.

**Trazabilidad:** HU-016 / CA-02 · RN-07.

**Datos de prueba:** L1, A1 conforme.

**Procedimiento de prueba:**

1. Como L1, cambiar la fecha de A1 sin escribir motivo y guardar.
2. Consultar A1 y su condición lunar.

**Resultados esperados:** Se muestra MSG-12 «Indica el motivo de la corrección.». A1 conserva su fecha y su condición lunar.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-043 · Corrección por el líder de otro huerto

**Descripción:** Comprobar que solo el líder del huerto puede corregir sus siembras.

**Trazabilidad:** HU-016 / CA-03 · RN-01, RN-07.

**Datos de prueba:** L2, A1 de H1.

**Procedimiento de prueba:**

1. Como L2, enviar directamente al servidor una corrección de A1 con motivo.
2. Consultar A1.

**Resultados esperados:** Se muestra MSG-03 «No tienes permiso para modificar la información de este huerto.». A1 no cambia.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-044 · Fotografía no válida en una corrección

**Descripción:** Comprobar que una corrección con evidencia no admitida no reemplaza la fotografía vigente.

**Trazabilidad:** HU-016 / CA-04 · RN-05, RN-07.

**Datos de prueba:** L1, A1 con F1, F2.

**Procedimiento de prueba:**

1. Como L1, reemplazar la fotografía de A1 por F2 con el motivo "Cambio de foto".
2. Consultar A1.

**Resultados esperados:** Se muestra MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.». A1 conserva F1.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-045 · Historial de una siembra corregida

**Descripción:** Comprobar que el historial muestra todas las versiones en orden con autor, fecha y motivo.

**Trazabilidad:** HU-017 / CA-01 · RN-07.

**Datos de prueba:** L1, A1 corregida como en P-041.

**Procedimiento de prueba:**

1. Como L1, consultar el historial de A1.

**Resultados esperados:** Se muestran dos versiones: primero la vigente (11/10/2026, motivo "Fecha registrada por error", autor L1, fecha de corrección) y después la original (10/10/2026, autor L1, fecha de registro).

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-046 · Historial de una siembra sin correcciones

**Descripción:** Comprobar el mensaje de una siembra que nunca se corrigió.

**Trazabilidad:** HU-017 / CA-02 · RN-07.

**Datos de prueba:** L1, A3 sin correcciones.

**Procedimiento de prueba:**

1. Como L1, consultar el historial de A3.

**Resultados esperados:** Se muestra MSG-23 «Esta siembra no tiene correcciones registradas.».

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-047 · Insignia obtenida por la comunidad

**Descripción:** Comprobar que la insignia se muestra con la siembra que la sustenta y la explicación de la práctica.

**Trazabilidad:** HU-018 / CA-01 · RN-08.

**Datos de prueba:** H1 con A1 conforme y fotografía F1.

**Procedimiento de prueba:**

1. Sin sesión, consultar el reto Documentación lunar de H1.

**Resultados esperados:** Se muestra la insignia Documentación lunar de la comunidad de H1, la siembra A1 como sustento y la explicación: la siembra se realiza en luna creciente o luna llena según la práctica.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-048 · Insignia única con varias siembras elegibles

**Descripción:** Comprobar que varias siembras elegibles no duplican la insignia y que corregir una no la retira si queda otra.

**Trazabilidad:** HU-018 / CA-02 · RN-07, RN-08.

**Datos de prueba:** L1, H1 con A1 (10/10) y A3 (13/10) conformes, CAL-T.

**Procedimiento de prueba:**

1. Consultar el reto de H1 y recargar la página.
2. Como L1, corregir A1 a 11/10/2026 08:00 con motivo.
3. Consultar el reto de H1.

**Resultados esperados:** En los pasos 1 y 3 se muestra una sola insignia. Después de la corrección sigue activa, sustentada por A3.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-049 · Comunidad sin siembras elegibles

**Descripción:** Comprobar que las siembras no conformes o pendientes no otorgan la insignia.

**Trazabilidad:** HU-018 / CA-03 · RN-08.

**Datos de prueba:** L1, H1 sin siembras, CAL-T.

**Procedimiento de prueba:**

1. Consultar el reto de H1.
2. Registrar una siembra con fecha 11/10/2026 y otra con fecha 12/10/2026.
3. Consultar el reto de H1.

**Resultados esperados:** En los pasos 1 y 3 no hay insignia y se muestra MSG-18 «Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena.». No se otorgan puntos, niveles ni posiciones de ranking.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-050 · Calendario lunar de los próximos 30 días

**Descripción:** Comprobar que el calendario muestra fases correctas para Ecuador continental y las actividades recomendadas con su fuente.

**Trazabilidad:** HU-019 / CA-01 · RN-09, RN-10.

**Datos de prueba:** Reloj fijado en 31/10/2026 12:00. REF-LUNA.

**Procedimiento de prueba:**

1. Sin sesión, abrir el calendario lunar.
2. Comparar la fecha y hora de cada fase principal mostrada con REF-LUNA.
3. Revisar las actividades y la fuente indicadas para cada fase.

**Resultados esperados:** Se muestran 30 días a partir del 31/10/2026, cada uno con una fase. Las horas de las fases principales coinciden con REF-LUNA con una diferencia máxima de una hora. Las actividades corresponden a RN-10 e indican su fuente.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-051 · Fecha fuera del rango del calendario

**Descripción:** Comprobar el rango disponible del calendario.

**Trazabilidad:** HU-019 / CA-02 · RN-09.

**Datos de prueba:** Reloj fijado en 31/10/2026 12:00.

**Procedimiento de prueba:**

1. Solicitar el calendario de diciembre de 2027.
2. Solicitar el calendario de septiembre de 2025.

**Resultados esperados:** En ambos casos se muestra MSG-20 «El calendario lunar está disponible desde {fecha inicial} hasta {fecha final}.» con el rango 31/10/2025 a 31/10/2027 y se conserva la vista anterior.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-052 · Filtro de días de siembra

**Descripción:** Comprobar que el filtro de siembra resalta exactamente los días recomendados.

**Trazabilidad:** HU-020 / CA-01 · RN-10.

**Datos de prueba:** Calendario de P-050.

**Procedimiento de prueba:**

1. Elegir la actividad "Siembra".

**Resultados esperados:** Se resaltan solo los días en luna creciente o luna llena.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-053 · Filtro de días de cosecha de raíces

**Descripción:** Comprobar que el filtro de cosecha de semillas y raíces resalta exactamente los días recomendados.

**Trazabilidad:** HU-020 / CA-02 · RN-10.

**Datos de prueba:** Calendario de P-050.

**Procedimiento de prueba:**

1. Elegir la actividad "Cosecha de semillas y raíces".

**Resultados esperados:** Se resaltan solo los días en luna menguante.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-054 · Retiro del filtro

**Descripción:** Comprobar que quitar el filtro restablece la vista completa.

**Trazabilidad:** HU-020 / CA-03 · RN-10.

**Datos de prueba:** Calendario filtrado por "Siembra".

**Procedimiento de prueba:**

1. Quitar el filtro.

**Resultados esperados:** Se muestran todos los días sin resaltar.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

---

### P-055 · Recorrido integrado de la primera iteración

**Descripción:** Comprobar el recorrido completo que se presentará en la demostración, sobre la versión entregada.

**Trazabilidad:** HU-001 a HU-020 / Recorrido · RN-01 a RN-10.

**Datos de prueba:** Todos los datos de la sección 7 del documento 05. Versión del producto identificada.

**Procedimiento de prueba:**

1. Como visitante, ubicar H1 en el mapa, abrir su ficha, recorrer la escena 3D y seleccionar Z2.
2. Consultar el calendario lunar y filtrar los días de siembra.
3. Como L1, actualizar las características de H1 y el estado de K1; verificar que K2 no cambia.
4. Registrar una siembra de K1 con fecha 10/10/2026 08:00 y F1; consultar su condición lunar y el reto.
5. Corregir la fecha a 11/10/2026 con motivo; consultar el historial y el reto.
6. Como L2, intentar modificar H1; cerrar sesión y revisar la consulta pública.

**Resultados esperados:** Cada paso produce el resultado de su caso individual: la siembra se registra una vez, la condición lunar y la insignia cambian según la versión vigente, el historial conserva ambas versiones, el acceso ajeno se rechaza y la consulta pública no muestra datos privados.

**Ejecuciones del caso de prueba:**

| Fecha | Responsable | Versión y navegador | Resultados obtenidos | Aprobado / Reprobado | Capturas |
|---|---|---|---|---|---|
|  |  |  |  |  |  |
|  |  |  |  |  |  |

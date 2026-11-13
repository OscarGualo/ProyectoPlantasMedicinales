# Historias de usuario de la primera iteración

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

Esta edición conserva las 20 historias y los 64 criterios funcionales de la línea base. Las condiciones de integridad, presentación y recuperación se complementan con los requisitos de la ERS y sus protocolos asociados. Una prueba complementaria no constituye una historia nueva.

| ID | Historia | Prioridad | Horas | Capacidad | Reglas | Pruebas |
|---|---|---|---|---|---|---|
| HU-001 | Iniciar sesión | Alta | 8 | C4 | RN-01 | P-001 a P-003; P-056 |
| HU-002 | Cerrar sesión | Alta | 3 | C4 | RN-01 | P-004 a P-005 |
| HU-003 | Actualizar la ubicación del huerto | Alta | 4 | C1, C3 | RN-01, RN-02 | P-006 a P-007 |
| HU-004 | Actualizar las características del huerto | Alta | 4 | C3 | RN-01, RN-02 | P-008 a P-010 |
| HU-005 | Publicar el huerto | Alta | 3 | C1 | RN-02 | P-011 a P-012 |
| HU-006 | Incorporar una planta del catálogo MSP | Alta | 8 | C3 | RN-02, RN-03, RN-05 | P-013 a P-015 |
| HU-007 | Gestionar la fotografía de una planta | Alta | 4 | C3 | RN-02, RN-03 | P-016 a P-018 |
| HU-008 | Registrar un cultivo | Alta | 6 | C3, C5 | RN-03 | P-019 a P-020 |
| HU-009 | Cambiar el estado de un cultivo | Alta | 4 | C3, C5 | RN-03 | P-021 a P-022 |
| HU-010 | Ver los huertos en el mapa | Alta | 6 | C1 | RN-02 | P-023 a P-024 |
| HU-011 | Consultar la ficha de un huerto | Alta | 5 | C1, C3 | RN-02 | P-025 a P-026; P-064 |
| HU-012 | Recorrer el huerto en 3D | Alta | 12 | C2 | RN-02 | P-027 a P-028 |
| HU-013 | Consultar los cultivos de una zona en 3D | Alta | 8 | C2, C5 | RN-03 | P-029 a P-031 |
| HU-014 | Registrar una siembra | Alta | 20 | C5 | RN-04, RN-05 | P-032 a P-037; P-058, P-059, P-065 |
| HU-015 | Consultar la condición lunar de una siembra | Alta | 14 | C5 | RN-06, RN-09 | P-038 a P-040; P-062 |
| HU-016 | Corregir una siembra | Alta | 12 | C5 | RN-07 | P-041 a P-044; P-057, P-060, P-069 |
| HU-017 | Consultar el historial de una siembra | Alta | 4 | C5 | RN-07 | P-045 a P-046 |
| HU-018 | Consultar el reto Documentación lunar | Media | 10 | C7 | RN-08 | P-047 a P-049; P-061 |
| HU-019 | Consultar el calendario lunar | Alta | 9 | C5 | RN-09, RN-10 | P-050 a P-051 |
| HU-020 | Filtrar el calendario lunar por actividad | Media | 4 | C5 | RN-10 | P-052 a P-054 |

Los criterios CA se identifican dentro de su historia; por ejemplo, HU-014/CA-01. Las horas de las tareas son estimaciones históricas de planificación y suman 148 horas-persona. La implementación y su aceptación se distinguen mediante el registro de pruebas.

## HU-001. Iniciar sesión

Prioridad: Alta  
Estimación: 8 horas

### Historia de usuario

Como líder de huerto quiero iniciar sesión con mi usuario y contraseña, para gestionar el huerto que el Ministerio me asignó.
Trazabilidad: capacidad C4 · reglas RN-01.

### Criterios de aceptación

1. Credenciales válidas (CA-01): Dado un líder con cuenta activa y un huerto asignado, cuando ingresa su usuario y contraseña correctos, entonces el sistema inicia la sesión y muestra la gestión de su huerto. [ESCENARIO IDEAL]
2. Credenciales incorrectas (CA-02): Dado un usuario registrado, cuando ingresa una contraseña incorrecta, entonces el sistema no inicia sesión y muestra MSG-01 «Usuario o contraseña incorrectos.», sin indicar cuál de los dos datos es incorrecto. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
3. Huerto no asignado (CA-03): Dado un líder con sesión iniciada, cuando intenta modificar un huerto que no le fue asignado, desde la pantalla o con una solicitud directa al servidor, entonces el sistema rechaza la operación, muestra MSG-03 «No tienes permiso para modificar la información de este huerto.» y los datos del huerto no cambian. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
4. Consulta de gestión (CA-04): Dado un líder con sesión válida y un huerto no publicado asignado, cuando recarga la gestión, entonces recupera su identidad, la ficha, las zonas, las plantas y los cultivos autorizados; otro líder no puede consultar esos datos.

### Tareas y estimación

1. Diseñar el acceso y la relación entre líder y huerto (1 hora).
2. Codificar el inicio de sesión (2 horas).
3. Incluir validaciones de credenciales y de asignación en el servidor (1 hora).
4. Probar ingresos válidos, incorrectos y a huertos ajenos (1 hora).
5. Completar consultas privadas, recuperación de sesión y flujo CSRF (3 horas, incluidas sus pruebas).
Total: 8 horas. Casos de prueba: P-001, P-002, P-003.
Verificaciones adicionales: P-056.

## HU-002. Cerrar sesión

Prioridad: Alta  
Estimación: 3 horas

### Historia de usuario

Como líder de huerto quiero cerrar mi sesión, para impedir que otra persona modifique mi huerto desde el mismo dispositivo.
Trazabilidad: capacidad C4 · reglas RN-01.

### Criterios de aceptación

1. Cierre de sesión (CA-01): Dado un líder con sesión iniciada, cuando selecciona "Cerrar sesión", entonces el sistema finaliza la sesión y muestra la consulta pública. [ESCENARIO IDEAL]
2. Modificación después del cierre (CA-02): Dada una sesión cerrada, cuando se envía una solicitud de modificación del huerto con los datos de esa sesión, entonces el sistema la rechaza, muestra MSG-02 «Tu sesión ha finalizado. Inicia sesión para continuar.» y los datos no cambian. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Codificar el cierre e invalidación de la sesión en el servidor (1 hora).
2. Incluir la validación de sesión vigente en las operaciones de modificación (1 hora).
3. Probar el cierre y el rechazo de solicitudes posteriores (1 hora).
Total: 3 horas. Casos de prueba: P-004, P-005.

**Verificación complementaria de los requisitos asociados:** P-071. Los resultados completos se encuentran en el documento 04.

## HU-003. Actualizar la ubicación del huerto

Prioridad: Alta  
Estimación: 4 horas

### Historia de usuario

Como líder de huerto quiero actualizar la provincia, la parroquia, la dirección y las coordenadas de mi huerto, para ubicarlo correctamente en el mapa.
Trazabilidad: capacidad C1, C3 · reglas RN-01, RN-02.

### Criterios de aceptación

1. Ubicación válida (CA-01): Dado un líder en la gestión de su huerto, cuando guarda una provincia, una parroquia, una dirección y coordenadas dentro de rango, entonces al volver a ingresar encuentra los mismos datos. [ESCENARIO IDEAL]
2. Coordenadas fuera de rango (CA-02): Dada la ubicación del huerto, cuando el líder guarda una latitud fuera de -90 a 90 o una longitud fuera de -180 a 180, entonces el sistema muestra MSG-06 «Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180.» y conserva la ubicación anterior. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar los campos de ubicación del huerto (1 hora).
2. Codificar la actualización de la ubicación (1 hora).
3. Incluir validaciones de rango de coordenadas (1 hora).
4. Probar valores válidos, límites y fuera de rango (1 hora).
Total: 4 horas. Casos de prueba: P-006, P-007.

## HU-004. Actualizar las características del huerto

Prioridad: Alta  
Estimación: 4 horas

### Historia de usuario

Como líder de huerto quiero actualizar el nombre, la descripción, el diseño, el tipo de cerca, la superficie, el tipo de suelo, la fuente de agua, la técnica de riego y el tipo de abono de mi huerto, para describir su organización a los visitantes.
Trazabilidad: capacidad C3 · reglas RN-01, RN-02.

### Criterios de aceptación

1. Características válidas (CA-01): Dado un líder en la gestión de su huerto, cuando guarda todas las características con valores válidos, entonces al volver a ingresar encuentra los mismos datos. [ESCENARIO IDEAL]
2. Nombre vacío (CA-02): Dadas las características del huerto, cuando el líder guarda sin nombre, entonces el sistema muestra MSG-04 «Completa el campo obligatorio: {campo}.» con el campo "Nombre" y conserva los datos anteriores. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
3. Superficie negativa (CA-03): Dadas las características del huerto, cuando el líder guarda una superficie menor que 0, entonces el sistema muestra MSG-05 «La superficie debe ser un número mayor o igual a 0.» y conserva los datos anteriores. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar los campos de características y sus catálogos (1 hora).
2. Codificar la actualización de características (1 hora).
3. Incluir validaciones de nombre y superficie (1 hora).
4. Probar valores válidos e inválidos (1 hora).
Total: 4 horas. Casos de prueba: P-008, P-009, P-010.

## HU-005. Publicar el huerto

Prioridad: Alta  
Estimación: 3 horas

### Historia de usuario

Como líder de huerto quiero publicar mi huerto, para darlo a conocer a los visitantes del mapa.
Trazabilidad: capacidad C1 · reglas RN-02.

### Criterios de aceptación

1. Publicación con ubicación (CA-01): Dado un huerto no publicado con nombre y coordenadas, cuando el líder lo publica, entonces el huerto aparece en el mapa público en su ubicación. [ESCENARIO IDEAL]
2. Publicación sin ubicación (CA-02): Dado un huerto sin coordenadas registradas, cuando el líder intenta publicarlo, entonces el sistema muestra MSG-21 «El huerto no tiene ubicación registrada y no puede publicarse en el mapa.» y el huerto no aparece en el mapa. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Codificar la publicación del huerto (1 hora).
2. Incluir la validación de nombre y coordenadas para publicar (1 hora).
3. Probar la publicación con y sin ubicación (1 hora).
Total: 3 horas. Casos de prueba: P-011, P-012.

## HU-006. Incorporar una planta del catálogo MSP

Prioridad: Alta  
Estimación base de la historia: 8 horas

### Historia de usuario

Como líder de huerto quiero seleccionar una ficha del catálogo suministrado por el MSP y asociarla a mi huerto con una fotografía propia, para publicar información educativa con una fuente identificable.

Trazabilidad: C3; RN-01, RN-02, RN-03 y RN-11; RF-021, RF-023 y RF-024.

### Criterios de aceptación

1. Selección institucional (CA-01): Dado un líder autorizado para H1, cuando selecciona una ficha activa del catálogo y guarda, con o sin fotografía admitida, entonces la planta queda vinculada a H1 con los datos educativos obtenidos por el servidor, su fuente y versión. Si H1 está publicado, la ficha es consultable públicamente. Los datos ausentes en la fuente permanecen ausentes.
2. Selección faltante (CA-02): Dado el formulario, cuando se guarda sin elegir ficha, entonces se informa MSG-04 en catalogoCodigo y no se registra la planta. Un código inexistente se rechaza con MSG-28.
3. Imagen no admitida (CA-03): Cuando se adjunta un archivo que no es JPEG o PNG o que supera 5 MB, se informa MSG-07 y no se guarda ni la planta ni una fotografía huérfana.

### Tareas de implementación

Consultar el catálogo de lectura; seleccionar su código; copiar los campos institucionales en el servidor; validar la imagen y la pertenencia; verificar consulta pública y privacidad. La estimación pertenece a la planificación base y no representa horas reales medidas de la corrección.

Casos: P-013, P-014, P-015, P-073, P-075 y P-078 a P-082.

## HU-007. Gestionar la fotografía de una planta

Prioridad: Alta  
Estimación base de la historia: 4 horas

### Historia de usuario

Como líder de huerto quiero actualizar la fotografía de una planta incorporada desde el catálogo MSP, para mostrar el ejemplar de mi huerto conservando la información institucional.

Trazabilidad: C3; RN-01, RN-02, RN-03 y RN-11; RF-022 y RF-023.

### Criterios de aceptación

1. Fotografía propia (CA-01): Dada una ficha institucional del huerto, cuando su líder guarda una fotografía admitida, entonces la consulta posterior muestra esa fotografía y conserva la descripción, clasificación, fuente y demás datos del catálogo.
2. Independencia (CA-02): Dadas fichas de la misma especie en H1 y H2, cuando el líder de H1 cambia su fotografía, entonces la ficha y fotografía de H2 permanecen sin cambios. Un líder ajeno recibe MSG-03.
3. Contenido protegido (CA-03): Cuando se intenta enviar nombre, descripción, usos, dosis, fuente u otros campos educativos por la API, el sistema responde HTTP 400 y MSG-28 sin alterar la ficha. Cambiar el código institucional de una ficha existente también se rechaza.

### Tareas de implementación

Separar datos institucionales y fotografía; impedir edición libre en interfaz y servidor; validar permisos, archivo e independencia entre huertos. La estimación pertenece a la planificación base y no representa horas reales medidas de la corrección.

Casos: P-016, P-017, P-018 y P-078 a P-082.

## HU-008. Registrar un cultivo

Prioridad: Alta  
Estimación: 6 horas

### Historia de usuario

Como líder de huerto quiero registrar un cultivo de una planta en una zona de mi huerto, para identificar dónde está sembrada cada planta.
Trazabilidad: capacidad C3, C5 · reglas RN-03.

### Criterios de aceptación

1. Registro en una zona (CA-01): Dada una planta con ficha y dos zonas del huerto, cuando el líder registra un cultivo de esa planta en cada zona, entonces cada cultivo muestra su planta, su zona, el estado "sembrado" y la fecha de registro. [ESCENARIO IDEAL]
2. Zona de otro huerto (CA-02): Dado el registro de un cultivo, cuando se indica una zona que pertenece a otro huerto, entonces el sistema muestra MSG-09 «La zona seleccionada no pertenece a este huerto.» y no registra el cultivo. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar planta, zona y cultivo como conceptos distintos (2 horas).
2. Codificar el registro de cultivos (2 horas).
3. Incluir la validación de pertenencia de la zona y la planta (1 hora).
4. Probar el registro y el rechazo de zonas ajenas (1 hora).
Total: 6 horas. Casos de prueba: P-019, P-020.

**Verificación complementaria de los requisitos asociados:** P-070. Los resultados completos se encuentran en el documento 04.

## HU-009. Cambiar el estado de un cultivo

Prioridad: Alta  
Estimación: 4 horas

### Historia de usuario

Como líder de huerto quiero cambiar el estado de un cultivo a sembrado, en crecimiento, cosechado o perdido, para mostrar a los visitantes el avance de cada plantación.
Trazabilidad: capacidad C3, C5 · reglas RN-03.

### Criterios de aceptación

1. Cambio de estado (CA-01): Dados dos cultivos de la misma planta, cuando el líder cambia uno de ellos a "en crecimiento", entonces solo ese cultivo cambia de estado y de fecha de actualización, y la ficha de la planta no muestra un estado. [ESCENARIO IDEAL]
2. Estado no válido (CA-02): Dado un cultivo del huerto, cuando se envía un estado que no pertenece al catálogo, entonces el sistema muestra MSG-10 «Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido.» y el cultivo conserva su estado. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Codificar el cambio de estado del cultivo (2 horas).
2. Incluir la validación del catálogo de estados (1 hora).
3. Probar la independencia de cultivos de la misma planta (1 hora).
Total: 4 horas. Casos de prueba: P-021, P-022.

**Verificación complementaria de los requisitos asociados:** P-074. Los resultados completos se encuentran en el documento 04.

## HU-010. Ver los huertos en el mapa

Prioridad: Alta  
Estimación: 6 horas

### Historia de usuario

Como visitante quiero ver en el mapa del Ecuador los huertos publicados, para conocer dónde existen huertos medicinales comunitarios.
Trazabilidad: capacidad C1 · reglas RN-02.

### Criterios de aceptación

1. Marcadores de huertos publicados (CA-01): Dados varios huertos publicados con ubicación, cuando un visitante abre el mapa sin iniciar sesión, entonces ve un marcador por cada huerto publicado en su ubicación registrada. [ESCENARIO IDEAL]
2. Huerto no publicado (CA-02): Dado un huerto no publicado, cuando el visitante abre el mapa, entonces no se muestra un marcador para ese huerto. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar la vista pública del mapa (1 hora).
2. Codificar el mapa y los marcadores (3 horas).
3. Incluir la validación de huertos publicados y con ubicación (1 hora).
4. Probar los marcadores mostrados y omitidos (1 hora).
Total: 6 horas. Casos de prueba: P-023, P-024.

**Verificación complementaria de los requisitos asociados:** P-076, P-077. Los resultados completos se encuentran en el documento 04.

## HU-011. Consultar la ficha de un huerto

Prioridad: Alta  
Estimación: 5 horas

### Historia de usuario

Como visitante quiero consultar la ficha pública de un huerto, para conocer sus características, sus plantas y sus cultivos.
Trazabilidad: capacidad C1, C3 · reglas RN-02.

### Criterios de aceptación

1. Ficha pública (CA-01): Dado el mapa con huertos publicados, cuando el visitante selecciona un marcador, entonces ve la ficha pública de ese huerto con sus plantas, sus cultivos y el acceso a su vista 3D, sin el contacto del responsable ni datos de la cuenta. [ESCENARIO IDEAL]
2. Enlace a un huerto no publicado (CA-02): Dado un huerto no publicado, cuando el visitante abre un enlace directo a ese huerto, entonces el sistema muestra MSG-14 «El huerto solicitado no existe o no está publicado.» y no muestra sus datos. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
3. Fotografías protegidas (CA-03): Dado un visitante, cuando solicita directamente una fotografía de un huerto no publicado o de una versión anterior de una siembra, entonces recibe 404 sin datos de la imagen; el líder autorizado sí puede consultarla.

### Tareas y estimación

1. Diseñar la ficha pública del huerto (1 hora).
2. Codificar la ficha y sus enlaces a plantas, cultivos y vista 3D (2 horas).
3. Incluir la separación de campos públicos y privados (1 hora).
4. Probar la consulta y el rechazo de huertos no publicados (1 hora).
Total: 5 horas. Casos de prueba: P-025, P-026.
Verificaciones adicionales: P-064.

**Verificación complementaria de los requisitos asociados:** P-073. Los resultados completos se encuentran en el documento 04.

## HU-012. Recorrer el huerto en 3D

Prioridad: Alta  
Estimación: 12 horas

### Historia de usuario

Como visitante quiero recorrer la representación 3D de un huerto, para conocer cómo están organizadas sus partes.
Trazabilidad: capacidad C2 · reglas RN-02.

### Criterios de aceptación

1. Recorrido de la escena (CA-01): Dado un huerto con su escena 3D preparada a partir del croquis aprobado, cuando el visitante rota, acerca y aleja la vista, entonces identifica las zonas de cultivo, los caminos, la cerca y la bodega presentes en el croquis. [ESCENARIO IDEAL]
2. Fallo de carga de la escena (CA-02): Dado un huerto cuya escena no puede cargarse, cuando el visitante abre la vista 3D, entonces el sistema muestra MSG-13 «No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.» y la lista de zonas y cultivos de ese mismo huerto. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar la escena a partir del croquis y su formato de datos (3 horas).
2. Codificar la escena y la navegación (rotar, acercar y alejar) (6 horas).
3. Incluir la alternativa en lista ante un fallo de carga (1 hora).
4. Probar la navegación en computador y celular, y el fallo de carga (2 horas).
Total: 12 horas. Casos de prueba: P-027, P-028.

## HU-013. Consultar los cultivos de una zona en 3D

Prioridad: Alta  
Estimación: 8 horas

### Historia de usuario

Como visitante quiero consultar los cultivos de una zona en la vista 3D del huerto, para saber qué se cultiva en cada parte.
Trazabilidad: capacidad C2, C5 · reglas RN-03.

### Criterios de aceptación

1. Cultivos de la zona (CA-01): Dada la escena de un huerto, cuando el visitante selecciona una zona con cultivos, entonces ve cada cultivo de esa zona con su planta, su estado informado y su fecha de actualización. [ESCENARIO IDEAL]
2. Señalética caliente y fría (CA-02): Dada una zona con cultivos seleccionada, cuando se muestran sus cultivos, entonces cada uno se señala en rojo si la planta es caliente, en verde si es fría y en gris si no tiene clasificación. [ESCENARIO IDEAL]
3. Zona sin cultivos (CA-03): Dada una zona sin cultivos registrados, cuando el visitante la selecciona, entonces el sistema muestra MSG-22 «Esta zona no tiene cultivos registrados.». [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar la correspondencia entre zonas de la escena y cultivos (2 horas).
2. Codificar la selección de zonas y la señalética de colores (4 horas).
3. Incluir la validación de zonas sin cultivos (1 hora).
4. Probar la selección, los colores y las zonas vacías (1 hora).
Total: 8 horas. Casos de prueba: P-029, P-030, P-031.

## HU-014. Registrar una siembra

Prioridad: Alta  
Estimación: 20 horas

### Historia de usuario

Como líder de huerto quiero registrar la siembra de un cultivo con su fecha, hora, técnica, descripción y una fotografía, para documentar lo realizado en el huerto.
Trazabilidad: capacidad C5 · reglas RN-04, RN-05.

### Criterios de aceptación

1. Registro de la siembra (CA-01): Dado un cultivo del huerto, cuando el líder registra la fecha y hora de la siembra, la técnica (semillas, estacas, esquejes o plántulas), una descripción y una fotografía JPEG o PNG de hasta 5 MB, entonces la bitácora muestra la siembra con la fecha del hecho, la fecha de registro y el autor. [ESCENARIO IDEAL]
2. Envío repetido (CA-02): Dada una siembra enviada dos veces por una falla de conexión, cuando el sistema recibe el segundo envío, entonces la bitácora conserva una sola siembra. [ESCENARIO IDEAL]
3. Fotografía no válida (CA-03): Dado el registro de una siembra, cuando el líder adjunta un archivo que no es JPEG o PNG o que supera 5 MB, entonces el sistema muestra MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.» y no registra la siembra. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
4. Fecha futura (CA-04): Dado el registro de una siembra, cuando el líder indica una fecha y hora posteriores al momento actual, entonces el sistema muestra MSG-08 «La fecha y hora de la actividad no pueden ser posteriores al momento actual.» y no registra la siembra. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
5. Cultivo de otro huerto (CA-05): Dado un líder con sesión iniciada, cuando intenta registrar una siembra en un cultivo de otro huerto, entonces el sistema muestra MSG-19 «El cultivo seleccionado no pertenece a tu huerto.» y no registra la siembra. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
6. Falla al guardar la fotografía (CA-06): Dado un error al almacenar la fotografía, cuando el líder registra la siembra, entonces el sistema muestra MSG-11 «No se pudo guardar la fotografía. La actividad no se registró; inténtalo nuevamente.» y no queda registrada una siembra sin evidencia. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
7. Reintentos simultáneos (CA-07): Dadas dos solicitudes simultáneas del mismo envío y contenido, cuando se procesan, entonces ambas identifican la misma siembra y quedan una sola actividad, su versión inicial y una evidencia asociada.
8. Clave con otros datos (CA-08): Dado un envío ya registrado, cuando se reutiliza su clave con contenido distinto, entonces se muestra MSG-24, no se modifica el registro existente y no se crea otro.
9. Fallo después de la foto (CA-09): Dada una foto escrita dentro de una operación, cuando falla el guardado de la actividad o su confirmación, entonces no queda persistida ni esa actividad ni una foto huérfana de ese intento; solo se informa un fallo definitivo si se conoce que la transacción se revirtió.

### Tareas y estimación

1. Diseñar el registro de la actividad y su evidencia (2 horas).
2. Codificar el guardado, la bitácora y el control de envíos repetidos (6 horas).
3. Incluir validaciones de archivo, fecha y pertenencia del cultivo (3 horas).
4. Probar persistencia, errores y reintentos (3 horas).
5. Resolver concurrencia, contenido de reintentos y reversión después de guardar la foto (6 horas, incluidas sus pruebas).
Total: 20 horas. Casos de prueba: P-032, P-033, P-034, P-035, P-036, P-037.
Verificaciones adicionales: P-058, P-059, P-065.

## HU-015. Consultar la condición lunar de una siembra

Prioridad: Alta  
Estimación: 14 horas

### Historia de usuario

Como líder de huerto quiero consultar la condición lunar de una siembra, para comprobar que se realizó en la fase que indica la práctica de mi comunidad.
Trazabilidad: capacidad C5 · reglas RN-06, RN-09.

### Criterios de aceptación

1. Siembra conforme (CA-01): Dada una siembra cuya fecha y hora del hecho cae en luna creciente o luna llena, cuando el líder consulta su condición lunar, entonces ve la fase calculada para la zona horaria del huerto, la regla aplicada y MSG-15 «Conforme con la condición lunar: la siembra se realizó en {fase}.». La evaluación usa la fecha del hecho: una siembra del 10/10/2026 registrada el 31/10/2026 se evalúa con la fase del 10/10/2026. [ESCENARIO IDEAL]
2. Siembra no conforme (CA-02): Dada una siembra cuya fecha y hora del hecho cae en luna oscura o luna menguante, cuando el líder consulta su condición lunar, entonces ve MSG-16 «No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.» y la siembra se conserva en la bitácora. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
3. Fase no disponible (CA-03): Dada una siembra para cuya fecha no hay información de fase lunar, cuando el líder consulta su condición lunar, entonces ve MSG-17 «Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra.» y la siembra queda pendiente de comprobación. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
4. Cobertura de intervalos (CA-04): Dados eventos astronómicos suficientes en el intervalo admitido, cuando se consulta cualquier instante, incluidos los límites de RN-09, entonces se obtiene exactamente una fase; el límite compartido corresponde al intervalo que comienza y no se generan pendientes por una ventana insuficiente.

### Tareas y estimación

1. Diseñar el cálculo de la fase y la regla de siembra (2 horas).
2. Codificar el cálculo astronómico de la fase y la evaluación (4 horas).
3. Incluir validaciones de fecha del hecho y zona horaria (2 horas).
4. Probar con el calendario controlado y las efemérides de referencia (2 horas).
5. Corregir cobertura de intervalos y verificar límites y fechas locales (4 horas, incluidas sus pruebas).
Total: 14 horas. Casos de prueba: P-038, P-039, P-040.
Verificaciones adicionales: P-062.

## HU-016. Corregir una siembra

Prioridad: Alta  
Estimación: 12 horas

### Historia de usuario

Como líder de huerto quiero corregir la fecha o la fotografía de una siembra con un motivo, para mantener fiel el registro de mi huerto.
Trazabilidad: capacidad C5 · reglas RN-07.

### Criterios de aceptación

1. Corrección con motivo (CA-01): Dada una siembra registrada, cuando el líder corrige su fecha e indica el motivo, entonces se guarda una versión nueva, se conserva la anterior y la condición lunar se evalúa con la versión vigente. [ESCENARIO IDEAL]
2. Corrección sin motivo (CA-02): Dada una siembra registrada, cuando el líder intenta guardar una corrección sin motivo, entonces el sistema muestra MSG-12 «Indica el motivo de la corrección.» y la siembra no cambia. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
3. Corrección por otro líder (CA-03): Dada una siembra de un huerto, cuando el líder de otro huerto intenta corregirla, entonces el sistema muestra MSG-03 «No tienes permiso para modificar la información de este huerto.» y la siembra no cambia. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
4. Fotografía no válida en la corrección (CA-04): Dada una siembra registrada, cuando el líder intenta reemplazar su fotografía por un archivo no admitido, entonces el sistema muestra MSG-07 «La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.» y se conserva la fotografía vigente. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
5. Fecha futura corregida (CA-05): Dada una siembra existente, cuando el líder intenta corregirla con fecha y hora posteriores al reloj del servidor, entonces se muestra MSG-08 y no cambian la versión vigente, su foto ni sus evaluaciones.
6. Correcciones simultáneas (CA-06): Dadas dos correcciones distintas sobre la misma versión, cuando se procesan simultáneamente, entonces se acepta una y la otra recibe MSG-25; no se pierde ninguna versión confirmada y se solicita revisar la versión vigente antes de corregir otra vez.
7. Reintento de corrección (CA-07): Dada una corrección confirmada cuya respuesta se perdió, cuando se reenvía la misma clave y contenido, entonces se devuelve la versión ya creada sin agregar otra; la misma clave con contenido distinto se rechaza con MSG-24.

### Tareas y estimación

1. Diseñar las versiones de una actividad (2 horas).
2. Codificar la corrección y la nueva evaluación lunar (3 horas).
3. Incluir validaciones de motivo, evidencia y autoridad (2 horas).
4. Probar la corrección y sus efectos (1 hora).
5. Validar fecha corregida, conflictos y reintentos de correcciones (4 horas, incluidas sus pruebas).
Total: 12 horas. Casos de prueba: P-041, P-042, P-043, P-044.
Verificaciones adicionales: P-057, P-060, P-069.

## HU-017. Consultar el historial de una siembra

Prioridad: Alta  
Estimación: 4 horas

### Historia de usuario

Como líder de huerto quiero consultar el historial de correcciones de una siembra, para conocer qué cambió y por qué.
Trazabilidad: capacidad C5 · reglas RN-07.

### Criterios de aceptación

1. Historial con correcciones (CA-01): Dada una siembra corregida, cuando el líder consulta su historial, entonces ve todas sus versiones de la más reciente a la más antigua, cada una con su fecha del hecho, fotografía, autor, fecha de la corrección y motivo. [ESCENARIO IDEAL]
2. Siembra sin correcciones (CA-02): Dada una siembra que nunca se corrigió, cuando el líder consulta su historial, entonces el sistema muestra MSG-23 «Esta siembra no tiene correcciones registradas.». [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Codificar la consulta del historial (2 horas).
2. Incluir el orden de versiones y el caso sin correcciones (1 hora).
3. Probar el historial con y sin correcciones (1 hora).
Total: 4 horas. Casos de prueba: P-045, P-046.

## HU-018. Consultar el reto Documentación lunar

Prioridad: Media  
Estimación: 10 horas

### Historia de usuario

Como integrante de una comunidad quiero consultar el reto Documentación lunar, para saber si mi comunidad obtuvo su insignia.
Trazabilidad: capacidad C7 · reglas RN-08.

### Criterios de aceptación

1. Insignia obtenida (CA-01): Dado un huerto publicado con al menos una siembra vigente con fotografía y conforme con la condición lunar, cuando se consulta el reto, entonces se muestra la insignia Documentación lunar de la comunidad, la siembra que la sustenta y la explicación de la práctica. [ESCENARIO IDEAL]
2. Varias siembras elegibles (CA-02): Dado un huerto con dos siembras elegibles, cuando se consulta el reto o se corrige una de ellas a no conforme, entonces se muestra una sola insignia, que sigue activa mientras quede al menos una siembra elegible. [ESCENARIO IDEAL]
3. Sin siembras elegibles (CA-03): Dado un huerto sin siembras elegibles, incluso si tiene siembras no conformes o pendientes, cuando se consulta el reto, entonces no se muestra la insignia y aparece MSG-18 «Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena.». [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]
4. Sustento público de la insignia (CA-04): Dada una comunidad con un huerto publicado y otro no publicado, cuando un visitante consulta su reto, entonces solo las siembras elegibles de huertos publicados sustentan la insignia pública; la respuesta no revela identificadores ni evidencias del huerto privado.

### Tareas y estimación

1. Diseñar el reto, su explicación y los estados de la insignia (1 hora).
2. Codificar la elegibilidad y la consulta del reto (3 horas).
3. Incluir validaciones de unicidad y recálculo (2 horas).
4. Probar ausencia, multiplicidad y correcciones (2 horas).
5. Limitar el sustento público de la insignia y probar huertos privados (2 horas, incluidas sus pruebas).
Total: 10 horas. Casos de prueba: P-047, P-048, P-049.
Verificaciones adicionales: P-061.

## HU-019. Consultar el calendario lunar

Prioridad: Alta  
Estimación: 9 horas

### Historia de usuario

Como integrante de una comunidad quiero consultar el calendario lunar de los próximos 30 días, con fechas, fases y actividades recomendadas según las prácticas documentadas, para elegir fechas de siembra y planificar las labores del huerto.
Trazabilidad: capacidad C5 · reglas RN-09, RN-10.

### Criterios de aceptación

1. Calendario de los próximos días (CA-01): Dado cualquier usuario, con o sin sesión, cuando abre el calendario lunar, entonces ve los próximos 30 días con la fase de cada día en hora del Ecuador continental, la fecha y hora de cada fase principal y las actividades recomendadas para cada fase con su fuente. [ESCENARIO IDEAL]
2. Fecha fuera de rango (CA-02): Dado el calendario abierto, cuando el usuario solicita una fecha anterior o posterior al rango disponible de 12 meses antes o después de la fecha actual, entonces el sistema muestra MSG-20 «El calendario lunar está disponible desde {fecha inicial} hasta {fecha final}.» y conserva la vista anterior. [ESCENARIO EXCEPCIONAL O PROBLEMÁTICO]

### Tareas y estimación

1. Diseñar la vista del calendario y la tabla de actividades por fase (2 horas).
2. Codificar el calendario reutilizando el cálculo de fases de HU-015 (3 horas).
3. Incluir validaciones de rango y zona horaria (1 hora).
4. Probar fases, actividades y rango con las efemérides de referencia (1 hora).
5. Separar eventos astronómicos e interpretación; preparar la referencia USNO y verificar el calendario (2 horas, incluidas sus pruebas).
Total: 9 horas. Casos de prueba: P-050, P-051.

## HU-020. Filtrar el calendario lunar por actividad

Prioridad: Media  
Estimación: 4 horas

### Historia de usuario

Como integrante de una comunidad quiero filtrar el calendario lunar por una actividad, para encontrar los días recomendados para realizarla.
Trazabilidad: capacidad C5 · reglas RN-10.

### Criterios de aceptación

1. Filtro de siembra (CA-01): Dado el calendario abierto, cuando el usuario elige la actividad "Siembra", entonces se resaltan solo los días en luna creciente o luna llena. [ESCENARIO IDEAL]
2. Filtro de cosecha de raíces (CA-02): Dado el calendario abierto, cuando el usuario elige la actividad "Cosecha de semillas y raíces", entonces se resaltan solo los días en luna menguante. [ESCENARIO IDEAL]
3. Quitar el filtro (CA-03): Dado el calendario filtrado por una actividad, cuando el usuario quita el filtro, entonces se muestran todos los días sin resaltar. [ESCENARIO IDEAL]

### Tareas y estimación

1. Codificar el filtro de actividades sobre el calendario (2 horas).
2. Incluir la correspondencia de actividades por fase según RN-10 (1 hora).
3. Probar cada filtro y su retiro (1 hora).
Total: 4 horas. Casos de prueba: P-052, P-053, P-054.

**Verificación complementaria de los requisitos asociados:** P-072. Los resultados completos se encuentran en el documento 04.

## Estado de aceptación

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

Las pruebas P-070 a P-077 cubren la restricción de tipo de zona, el cierre de sesión interrumpido, las respuestas desordenadas del calendario, los campos públicos, la hora de Galápagos, las longitudes y los estados alternativos del mapa. El detalle conserva la correspondencia entre requisito, historia, evidencia e incidencia.

[Índice de documentación](README.md)

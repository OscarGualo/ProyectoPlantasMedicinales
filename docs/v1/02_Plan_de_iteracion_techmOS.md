# Plan y seguimiento de la primera iteración

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Objetivo de la iteración

Entregar el viernes 13 de noviembre de 2026 una versión operativa que permita recorrer de principio a fin el valor central del producto: un visitante encuentra un huerto en el mapa del Ecuador, recorre su estructura en 3D y consulta sus plantas y cultivos; el líder del huerto mantiene esa información, documenta una siembra con fotografía, comprueba si respetó la fase lunar, la corrige conservando el historial y su comunidad obtiene el reconocimiento Documentación lunar. El calendario lunar orienta la elección de fechas; no guarda actividades planificadas ni envía notificaciones en I1.

El incremento distingue planta, cultivo y actividad. No promete crecimiento biológico en dos semanas ni certifica aprendizaje mediante una insignia: la aceptación funcional se comprueba con los casos de prueba y la utilidad para el cliente se recoge en la revisión.

## 2. Historias seleccionadas

| Historia | Resultado para el usuario | Horas | Prioridad | Casos de prueba |
|---|---|---|---|---|
| HU-001 | Iniciar sesión | 8 | Alta | P-001 a P-003; P-056 |
| HU-002 | Cerrar sesión | 3 | Alta | P-004 a P-005 |
| HU-003 | Actualizar la ubicación del huerto | 4 | Alta | P-006 a P-007 |
| HU-004 | Actualizar las características del huerto | 4 | Alta | P-008 a P-010 |
| HU-005 | Publicar el huerto | 3 | Alta | P-011 a P-012 |
| HU-006 | Incorporar una planta del catálogo MSP | 8 | Alta | P-013 a P-015 |
| HU-007 | Gestionar la fotografía de una planta | 4 | Alta | P-016 a P-018 |
| HU-008 | Registrar un cultivo | 6 | Alta | P-019 a P-020 |
| HU-009 | Cambiar el estado de un cultivo | 4 | Alta | P-021 a P-022 |
| HU-010 | Ver los huertos en el mapa | 6 | Alta | P-023 a P-024 |
| HU-011 | Consultar la ficha de un huerto | 5 | Alta | P-025 a P-026; P-064 |
| HU-012 | Recorrer el huerto en 3D | 12 | Alta | P-027 a P-028 |
| HU-013 | Consultar los cultivos de una zona en 3D | 8 | Alta | P-029 a P-031 |
| HU-014 | Registrar una siembra | 20 | Alta | P-032 a P-037; P-058, P-059, P-065 |
| HU-015 | Consultar la condición lunar de una siembra | 14 | Alta | P-038 a P-040; P-062 |
| HU-016 | Corregir una siembra | 12 | Alta | P-041 a P-044; P-057, P-060, P-069 |
| HU-017 | Consultar el historial de una siembra | 4 | Alta | P-045 a P-046 |
| HU-018 | Consultar el reto Documentación lunar | 10 | Media | P-047 a P-049; P-061 |
| HU-019 | Consultar el calendario lunar | 9 | Alta | P-050 a P-051 |
| HU-020 | Filtrar el calendario lunar por actividad | 4 | Media | P-052 a P-054 |

Total de historias: 148 horas-persona. Cubren parcialmente las capacidades C1 a C5 y C7. Almacén (C6), foro (C8), miembros, puntos, ranking, niveles y talentos permanecen en el alcance global para los siguientes incrementos (D-09).

### 2.1 Decisiones que enmarcan la iteración

- Equipo y fecha (D-07): 6 integrantes; entrega del informe y demostración el 13/11/2026 a las 09:00.

- Gamificación (D-09): solo el reto e insignia Documentación lunar.

- Fichas de plantas (D-10): catálogo institucional común de lectura y fotografías por huerto. El líder incorpora fichas existentes; no redacta información medicinal.

- Calendario lunar (D-11): consulta y filtro para planificar (HU-019 y HU-020) y verificación de cada siembra (HU-015), con fases calculadas.

- 3D (D-01): escena preparada por el equipo a partir del croquis aprobado; el líder edita información y estado de los cultivos, no la geometría.

- Prioridades: alta para el recorrido central y media para el reto y filtro del calendario. Las 20 historias están presentes en el candidato documentado; HU-006 y HU-007 aplican el contrato institucional actualizado.

### 2.2 Dependencias entre historias

Se registran solo las dependencias necesarias. Las que pueden resolverse con datos preparados no obligan a terminar antes otra historia.

| Historia | Depende de | Tipo y consecuencia |
|---|---|---|
| HU-002 a HU-009, HU-014 y HU-016 | HU-001 | Necesaria: toda modificación requiere un líder autenticado y autorizado. |
| HU-005 | HU-003 | Necesaria para publicar desde la interfaz; en pruebas, las coordenadas pueden prepararse como datos. |
| HU-007 | HU-006 | Necesaria: solo se modifica una ficha registrada. |
| HU-008 y HU-009 | HU-006; zonas preparadas (D-01) | Necesaria: un cultivo requiere una planta y una zona del huerto. |
| HU-013 | HU-012 y HU-008 | Necesaria: la zona se selecciona en la escena y muestra cultivos registrados. |
| HU-015, HU-016 | HU-014 | Necesaria: se evalúa o corrige una siembra existente. |
| HU-017 | HU-016 | Necesaria: el historial muestra versiones creadas por correcciones. |
| HU-018 | HU-015 | Necesaria: la insignia usa la condición lunar de las siembras. |
| HU-019 | HU-015 (cálculo de fases) | Necesaria: el cálculo se construye una sola vez en HU-015 y el calendario lo reutiliza; sus horas no se cuentan dos veces. |
| HU-020 | HU-019 | Necesaria: el filtro opera sobre el calendario. |

Ninguna historia de prioridad alta depende de una de prioridad media (HU-018 y HU-020). Si fuera necesario aplazar alguna de ellas, el resto del recorrido se mantiene coherente.

## 3. Esfuerzo y capacidad

Estimación de planificación del incremento completo. Las estimaciones están en horas-persona de trabajo efectivo: tiempo de trabajo concentrado, sin contar interrupciones ni reuniones generales. La capacidad del equipo debe expresarse en la misma unidad.

| Concepto | Horas-persona | Contenido |
|---|---|---|
| Historias HU-001 a HU-020 | 148 | Diseño, codificación, validaciones y pruebas de cada historia. |
| Coordinación y refinamiento | 6 | Revisiones diarias breves del equipo de 6 personas, resolución de decisiones y consultas al cliente. |
| Entorno, repositorio y arquitectura | 12 | Selección de tecnologías, prueba de viabilidad del mapa y el 3D, estructura del repositorio y despliegue. |
| Integración y recorrido completo | 10 | Integración entre historias y ejecución de P-055, distinta de las pruebas de cada historia. |
| Documentación de la versión | 8 | Arquitectura y diseño, listado de herramientas y tecnologías, README e informe Proyecto-Version1-techmOS.pdf. |
| Preparación de la demostración | 3 | Datos de demostración, guion del recorrido y ensayo. |
| Subtotal | 187 | Trabajo base previsto. |
| Reserva para corrección e incertidumbre | 38 | 20 % del subtotal, redondeado hacia arriba. Se usa para corregir defectos, no para agregar funciones. |
| Esfuerzo total | 225 | Estimación total del incremento, incluida la reserva. |

La estimación histórica equivale a 37,5 horas-persona por integrante. Se utiliza para explicar la planificación del incremento. El avance comprobado corresponde a la versión construida, a sus 89 pruebas Java y a sus 14 recorridos de navegador.

El seguimiento del incremento considera el código integrado, los casos ejecutados y los entregables publicados. La estimación se conserva como antecedente y no se presenta como registro de horas reales trabajadas.

## 4. Calendario de la iteración

| Fecha | Trabajo | Resultado revisable |
|---|---|---|
| Lun 2/11 | Cierre inicial de la documentación de definición y planificación. | Paquete de documentos actualizado. |
| Mar 3/11 | Confirmar capacidad individual. Elegir tecnologías y arquitectura. Crear repositorio, datos de prueba y prueba de viabilidad de mapa y 3D. Iniciar HU-001 y HU-002. | Stack y arquitectura decididos; repositorio con README inicial. |
| Mié 4/11 – Jue 5/11 | HU-003 a HU-009: ubicación, características y publicación del huerto; plantas y cultivos. Cálculo de fases lunares (base de HU-015 y HU-019). | Líder mantiene huerto, plantas y cultivos con sus pruebas. |
| Vie 6/11 | Revisión intermedia del equipo. Iniciar HU-010 a HU-013 (mapa y 3D). | Demostración interna: ficha, plantas y cultivos; avance del mapa y 3D. |
| Lun 9/11 – Mar 10/11 | Terminar HU-010 a HU-013; HU-014, HU-015, HU-019 y HU-020. | Consulta pública completa; siembra con evaluación lunar; calendario. |
| Mié 11/11 | HU-016, HU-017 y HU-018. Integración del recorrido. | Correcciones con historial e insignia funcionando. |
| Jue 12/11 | Ejecutar P-055 y los casos pendientes; corregir defectos; desplegar; completar arquitectura, tecnologías, README e informe. | Versión candidata con resultados reales de pruebas. |
| Vie 13/11, 09:00 | El gestor sube el informe Proyecto-Version1-techmOS.pdf. Demostración en clase. | Entrega y observaciones del cliente. |

Las pruebas de cada historia se ejecutan mientras se construye, no al final. Una historia está terminada solo cuando cumple su definición de terminado (sección 6), no cuando termina el día previsto.

## 5. Organización del equipo

| Rol | Personas | Responsabilidad |
|---|---|---|
| Gestor del proyecto | 1 | Coordina la capacidad, las consultas al cliente y el informe; sube la entrega. |
| Análisis y pruebas | 1 | Mantiene historias, reglas y casos; ejecuta y registra las pruebas con evidencia. |
| Desarrollo backend | 2 | Datos, autorización, actividades, cálculo lunar, insignia y API. |
| Desarrollo frontend, mapa y 3D | 2 | Pantallas públicas y de gestión, mapa, escena 3D y calendario. |

La responsabilidad técnica se distribuye entre Mauricio López, Dhalin Avila, Oscar Gualotuña, Christian Yanchatipan, Jean Espinosa y Gabriel Vinueza. El documento 11 detalla los archivos y los casos de cada integrante.

## 6. Entregables y definición de terminado

| Entregable | Contenido esperado el 13/11/2026 |
|---|---|
| Producto operativo | Recorrido completo desplegado y accesible para la demostración, con los datos ficticios de demostración. |
| Historias de usuario | Documento 03 actualizado con lo realmente entregado. |
| Arquitectura y diseño | Contexto, componentes, modelo de datos y pantallas, coherentes con el código. |
| Herramientas y tecnologías | Listado con versiones y justificación de cada elección. |
| Código fuente y README | Repositorio GitHub con instrucciones de instalación y ejecución reproducibles, sin credenciales. |
| Casos de prueba con resultados | Documento 04 con las ejecuciones reales registradas, incluidas las reprobadas y su corrección. |
| Informe | Proyecto-Version1-techmOS.pdf en la plantilla del curso, con el enlace al repositorio. |

Definición de terminado de una historia: todos sus criterios de aceptación cumplidos; sus casos de prueba ejecutados y registrados; sin defectos críticos abiertos; código integrado en la rama de aceptación del incremento, `develop`, e identificado por su commit; y documentación actualizada. Son defectos críticos los que permiten leer información privada o modificar huertos ajenos, pierden registros, producen evaluaciones lunares erróneas sistemáticas o bloquean el recorrido principal.

## 7. Demostración al cliente

- Un visitante abre el mapa, selecciona H1, recorre la escena 3D y consulta una zona con su señalética caliente y fría.

- Consulta el calendario lunar y resalta los días recomendados para la siembra.

- El líder actualiza la ficha y el estado de un cultivo sin alterar otro de la misma planta.

- Registra una siembra con fotografía en el 18/10/2026 a las 08:00 de America/Guayaquil; revisa su condición lunar y la insignia de su comunidad.

- Corrige la fecha con un motivo y muestra el historial y el recálculo de la insignia.

- Se muestran un acceso rechazado y una fotografía no admitida.

Se usan datos de demostración identificados como tales: la espera de 15 días de la preparación real no puede comprimirse al calendario académico. Tras la demostración se recogen las observaciones del cliente y las respuestas a las decisiones pendientes.

## 8. Riesgos de la iteración

| Riesgo | Respuesta |
|---|---|
| La capacidad real es menor que el esfuerzo estimado | El control de cierre utiliza trabajo restante, capacidad disponible y resultados de verificación para priorizar correcciones del incremento. Primera opción a negociar: HU-020. Mantener HU-017 junto con la corrección por su aporte a la confianza. HU-018 solo se aplaza con acuerdo explícito porque elimina la gamificación del incremento. Estas medidas no garantizan por sí solas cerrar la diferencia de capacidad. Retirar HU-018 deja la insignia fuera de la versión 1 y debe acordarse con el cliente. |
| El 3D o el mapa resultan inviables en celulares | Prueba de viabilidad el 3/11; la lista de zonas y cultivos (HU-012, CA-02) mantiene la consulta mientras se ajusta el detalle. |
| El cálculo de fases no coincide con las efemérides | Verificarlo temprano con REF-LUNA; mantener CAL-T para probar la lógica de evaluación por separado. |
| Las pruebas y la documentación se acumulan al final | Ejecutarlas por historia; reservar el 12/11 solo para el recorrido integrado y las correcciones. |
| Las correcciones alteran la insignia de forma incorrecta | Calcular la insignia con la versión vigente de todas las siembras; casos P-041 y P-048. |
| El croquis de H1 no está disponible a tiempo | El producto utiliza un croquis esquemático identificado como dato de demostración; P-027 conserva separado el contraste físico con un croquis real. |

El resto del proyecto conserva almacén, foro, miembros, editor del huerto, funciones gubernamentales, ciclo agrícola completo, puntos, ranking, niveles y talentos. Su planificación se acuerda con el cliente después de esta entrega.

## 9. Seguimiento y cierre del incremento

El cierre del incremento se controla por incidencia, responsable técnico, resultado de prueba y revisión de código. La estimación histórica de 225 horas-persona no se utiliza como una medición de disponibilidad ni como un registro de trabajo ejecutado.

El candidato construido contiene implementación para las 20 historias seleccionadas, HU-001 a HU-020; su aceptación depende de criterios y verificaciones, no de su presencia en el código. El contenido de la versión se identifica por el commit de develop consignado en el informe de entrega.

Cerrar primero una prueba vertical: sesión y consulta privada de huerto no publicado, guardado de una siembra con foto, consulta pública autorizada y despliegue. Verificar en paralelo el cálculo lunar y CR1 en el perfil físico de P-027.

Las 12 horas de entorno cubren repositorio, versiones, configuración de sesión, despliegue y prueba técnica inicial; las 10 de integración incluyen los casos transversales y recuperación piloto. Las verificaciones propias de cada historia permanecen dentro de sus horas. No duplicar esos esfuerzos.

Plan de respuesta: si la capacidad restante no cubre el compromiso revisado, acordar con el cliente un nuevo contenido o dedicación antes de prometer la entrega. No sustituir el 3D por una lista ni retirar la evidencia, autorización o prácticas lunares de forma unilateral.

La trazabilidad conserva 64 criterios funcionales, con HU-006 y HU-007 actualizadas por la aclaración del cliente, y 82 protocolos identificados. Los 94 resultados Java y 21 de navegador del candidato constan en los documentos 04 y 10. Las pruebas físicas de dispositivo, rendimiento y recuperación mantienen su cobertura pendiente.


## 10. Cierre documental del 12 de noviembre de 2026

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

| ID | Prioridad | Incidencia | Resultado observado | Prueba | Responsable técnico | Estado |
|---|---|---|---|---|---|---|
| INC-01 | Alta | Cierre de sesión interrumpido | La interfaz oculta la cuenta mientras la sesión del servidor sigue activa; reaparece al recargar. | P-071 | Christian Yanchatipan | Corregida en candidato local; pendiente de integración |
| INC-02 | Alta | Zona no cultivable | El servidor acepta cultivos en zonas de tipo BODEGA, CAMINO y CERCA con HTTP 201. | P-070 | Gabriel Vinueza | Corregida en candidato local; pendiente de integración |
| INC-03 | Alta | Orden de respuestas del filtro lunar | Una respuesta antigua puede reemplazar la última actividad seleccionada. | P-072 | Dhalin Avila | Corregida en candidato local; pendiente de integración |
| INC-04 | Media | Hora de actualización de cultivos en Galápagos | La pantalla de gestión usa America/Guayaquil aunque el huerto pertenece a Pacific/Galapagos. | P-074 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |
| INC-05 | Media | Campos públicos de plantas | Altitud y distribución se guardan y se devuelven en la API, pero se omiten en la ficha pública. | P-073 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |
| INC-06 | Media | Longitud de nombre común | Un nombre común de 121 caracteres produce HTTP 500; la columna admite 120. | P-075 | Mauricio López | Corregida en candidato local; pendiente de integración |
| INC-07 | Media | Fallo de cartografía | La lista continúa disponible, pero no se informa que las teselas del mapa no pudieron cargarse. | P-076 | Oscar Gualotuña | Corregida en candidato local; pendiente de integración |

El plan de cierre mantiene las 20 historias y sus 64 criterios. P-070 a P-077 verifican las siete correcciones y el estado vacío del mapa, con resultado local aprobado. P-078 a P-082 verifican la procedencia institucional y sus permisos; el catálogo reúne 82 protocolos. La siguiente acción de integración es aplicar el candidato completo en la rama del equipo y verificar su CI antes de la demostración.

| Fecha | Actividad de cierre | Resultado documental exigido |
|---|---|---|
| 12/11/2026 | Tratar las incidencias abiertas, verificar los cambios y ejecutar los protocolos físicos y de operación disponibles. | Commit de cierre, evidencias y estados de cada prueba. |
| 13/11/2026, antes de las 09:00 | Revisar el entorno de presentación y entregar el informe del curso. | Informe y demostración con el alcance, los resultados y las limitaciones reales. |

P-027 no cuenta con ejecución en Android físico y contraste con un croquis real aprobado. P-066, P-067 y P-068 carecen de medición formal de rendimiento y recuperación. P-063 conserva cobertura parcial del entorno HTTPS de entrega. No se dispone de evidencia de validación comunitaria de los intervalos lunares ni de alojamiento externo operativo. La construcción de la imagen Docker sí fue satisfactoria en GitHub Actions del último PR integrado.

La ejecución de CI verificada está disponible en [GitHub Actions](https://github.com/OscarGualo/ProyectoPlantasMedicinales/actions/runs/43140337802). Una incidencia solo cambia a corregida cuando existe un cambio identificado y una nueva ejecución satisfactoria; esta edición no registra ese cierre.

[Índice de documentación](README.md)

## Precisión de planificación y registro

El calendario lunar es la referencia de fechas para planificar las siembras según las prácticas documentadas. HU-019 permite consultarlo y HU-020 filtrar por actividad. HU-014 registra una siembra ya realizada; HU-015 comprueba su fecha y hora reales con la regla aplicable. Consultar el calendario no crea una siembra ni demuestra que se haya realizado una labor. La aclaración no añade agenda persistente ni altera las estimaciones, las 20 historias o sus dependencias.

**ESCUELA POLITÉCNICA NACIONAL**
Ingeniería de Software
CALIDAD DE SOFTWARE (ISWD652) · GR2SW · Período 2026-B

# Plan de la primera iteración

**Huertos Medicinales Comunitarios**
Versión 1.3 · del 3 al 13 de noviembre de 2026

- **Equipo:** [Nombre del equipo]
- **Integrantes:**
  - [Integrante 1]
  - [Integrante 2]
  - [Integrante 3]
  - [Integrante 4]
  - [Integrante 5]
  - [Integrante 6]
- **Fecha:** 2 de noviembre de 2026

*Documento para revisión con el cliente*

---

## 1. Objetivo de la iteración

Entregar el viernes 13 de noviembre de 2026 una versión operativa que permita recorrer de principio a fin el valor central del producto: un visitante encuentra un huerto en el mapa del Ecuador, recorre su estructura en 3D y consulta sus plantas y cultivos; el líder del huerto mantiene esa información, documenta una siembra con fotografía, comprueba si respetó la fase lunar, la corrige conservando el historial y su comunidad obtiene el reconocimiento *Documentación lunar*. El calendario lunar permite además planificar las actividades según la práctica de las comunidades.

El incremento distingue planta, cultivo y actividad. No promete crecimiento biológico en dos semanas ni certifica aprendizaje mediante una insignia: la aceptación funcional se comprueba con los casos de prueba y la utilidad para el cliente se recoge en la revisión.

## 2. Historias seleccionadas

| Historia | Resultado para el usuario | Horas | Prioridad | Casos de prueba |
|---|---|---:|---|---|
| HU-001 | Iniciar sesión | 5 | Alta | P-001 a P-003 |
| HU-002 | Cerrar sesión | 3 | Alta | P-004 a P-005 |
| HU-003 | Actualizar la ubicación del huerto | 4 | Alta | P-006 a P-007 |
| HU-004 | Actualizar las características del huerto | 4 | Alta | P-008 a P-010 |
| HU-005 | Publicar el huerto | 3 | Alta | P-011 a P-012 |
| HU-006 | Registrar una planta medicinal | 8 | Alta | P-013 a P-015 |
| HU-007 | Modificar una planta medicinal | 4 | Alta | P-016 a P-018 |
| HU-008 | Registrar un cultivo | 6 | Alta | P-019 a P-020 |
| HU-009 | Cambiar el estado de un cultivo | 4 | Alta | P-021 a P-022 |
| HU-010 | Ver los huertos en el mapa | 6 | Alta | P-023 a P-024 |
| HU-011 | Consultar la ficha de un huerto | 5 | Alta | P-025 a P-026 |
| HU-012 | Recorrer el huerto en 3D | 12 | Alta | P-027 a P-028 |
| HU-013 | Consultar los cultivos de una zona en 3D | 8 | Alta | P-029 a P-031 |
| HU-014 | Registrar una siembra | 14 | Alta | P-032 a P-037 |
| HU-015 | Consultar la condición lunar de una siembra | 10 | Alta | P-038 a P-040 |
| HU-016 | Corregir una siembra | 8 | Alta | P-041 a P-044 |
| HU-017 | Consultar el historial de una siembra | 4 | Media | P-045 a P-046 |
| HU-018 | Consultar el reto Documentación lunar | 8 | Media | P-047 a P-049 |
| HU-019 | Consultar el calendario lunar | 7 | Alta | P-050 a P-051 |
| HU-020 | Filtrar el calendario lunar por actividad | 4 | Media | P-052 a P-054 |

**Total de historias: 127 horas-persona.** Cubren parcialmente las capacidades C1 a C5 y C7. Almacén (C6), foro (C8), miembros, puntos, ranking, niveles y talentos permanecen en el alcance global para los siguientes incrementos (D-09).

### 2.1 Decisiones que enmarcan la iteración

- **Equipo y fecha (D-07):** 6 integrantes; entrega del informe y demostración el 13/11/2026 a las 09:00.
- **Gamificación (D-09):** solo el reto e insignia *Documentación lunar*.
- **Fichas de plantas (D-10):** cada huerto mantiene sus propias fichas.
- **Calendario lunar (D-11):** consulta y filtro para planificar (HU-019 y HU-020) y verificación de cada siembra (HU-015), con fases calculadas.
- **3D (D-01):** escena preparada por el equipo a partir del croquis aprobado; el líder edita información y estado de los cultivos, no la geometría.
- **Prioridades:** son una propuesta del equipo; el cliente las confirma o ajusta en la revisión.

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

Ninguna historia de prioridad alta depende de una de prioridad media (HU-017, HU-018 y HU-020). Si fuera necesario aplazar alguna de ellas, el resto del recorrido se mantiene coherente.

## 3. Esfuerzo y capacidad

Las estimaciones están en horas-persona de trabajo efectivo: tiempo de trabajo concentrado, sin contar interrupciones ni reuniones generales. La capacidad del equipo debe expresarse en la misma unidad.

| Concepto | Horas-persona | Contenido |
|---|---:|---|
| Historias HU-001 a HU-020 | 127 | Diseño, codificación, validaciones y pruebas de cada historia. |
| Coordinación y refinamiento | 6 | Revisiones diarias breves del equipo de 6 personas, resolución de decisiones y consultas al cliente. |
| Entorno, repositorio y arquitectura | 6 | Selección de tecnologías, prueba de viabilidad del mapa y el 3D, estructura del repositorio y despliegue. |
| Integración y recorrido completo | 6 | Integración entre historias y ejecución de P-055, distinta de las pruebas de cada historia. |
| Documentación de la versión | 8 | Arquitectura y diseño, listado de herramientas y tecnologías, README e informe `Proyecto-Version1-nombreEquipo.pdf`. |
| Preparación de la demostración | 2 | Datos de demostración, guion del recorrido y ensayo. |
| **Subtotal** | **155** | Trabajo base previsto. |
| Reserva para corrección e incertidumbre | 31 | Aproximadamente 20 % del subtotal. Se usa para corregir defectos, no para agregar funciones. |
| **Esfuerzo total** | **186** | Capacidad que el equipo debe confirmar. |

Con 6 integrantes, el esfuerzo total equivale a unas 31,0 horas efectivas por persona, es decir, alrededor de 3,9 horas por persona en cada uno de los 8 días hábiles entre el 3 y el 12 de noviembre. El fin de semana del 7 y 8 de noviembre queda como margen y no se cuenta como capacidad.

Al iniciar la iteración, cada integrante declara sus horas efectivas disponibles. Si la suma es menor que el esfuerzo total, se negocia con el cliente el contenido del incremento antes de comprometerlo; las historias que salgan permanecen en el backlog global. Una persona puede asumir varios roles, pero su capacidad no se multiplica por ello.

## 4. Calendario

| Fecha | Trabajo | Resultado revisable |
|---|---|---|
| Lun 2/11 | Cierre de la documentación de definición y planificación (esta versión). | Paquete de documentos actualizado. |
| Mar 3/11 | Confirmar capacidad individual. Elegir tecnologías y arquitectura. Crear repositorio, datos de prueba y prueba de viabilidad de mapa y 3D. Iniciar HU-001 y HU-002. | Stack y arquitectura decididos; repositorio con README inicial. |
| Mié 4/11 – Jue 5/11 | HU-003 a HU-009: ubicación, características y publicación del huerto; plantas y cultivos. Cálculo de fases lunares (base de HU-015 y HU-019). | Líder mantiene huerto, plantas y cultivos con sus pruebas. |
| Vie 6/11 | Revisión intermedia del equipo. Iniciar HU-010 a HU-013 (mapa y 3D). | Demostración interna: ficha, plantas y cultivos; avance del mapa y 3D. |
| Lun 9/11 – Mar 10/11 | Terminar HU-010 a HU-013; HU-014, HU-015, HU-019 y HU-020. | Consulta pública completa; siembra con evaluación lunar; calendario. |
| Mié 11/11 | HU-016, HU-017 y HU-018. Integración del recorrido. | Correcciones con historial e insignia funcionando. |
| Jue 12/11 | Ejecutar P-055 y los casos pendientes; corregir defectos; desplegar; completar arquitectura, tecnologías, README e informe. | Versión candidata con resultados reales de pruebas. |
| Vie 13/11, 09:00 | El gestor sube el informe `Proyecto-Version1-nombreEquipo.pdf`. Demostración en clase. | Entrega y observaciones del cliente. |

Las pruebas de cada historia se ejecutan mientras se construye, no al final. Una historia está terminada solo cuando cumple su definición de terminado (sección 6), no cuando termina el día previsto.

## 5. Organización del equipo

| Rol | Personas | Responsabilidad |
|---|---:|---|
| Gestor del proyecto | 1 | Coordina la capacidad, las consultas al cliente y el informe; sube la entrega. |
| Análisis y pruebas | 1 | Mantiene historias, reglas y casos; ejecuta y registra las pruebas con evidencia. |
| Desarrollo backend | 2 | Datos, autorización, actividades, cálculo lunar, insignia y API. |
| Desarrollo frontend, mapa y 3D | 2 | Pantallas públicas y de gestión, mapa, escena 3D y calendario. |

Los nombres se asignan el 3/11. La distribución es orientativa: el gestor y el responsable de pruebas también pueden desarrollar si su capacidad lo permite.

## 6. Entregables y definición de terminado

| Entregable | Contenido esperado el 13/11/2026 |
|---|---|
| Producto operativo | Recorrido completo desplegado y accesible para la demostración, con los datos acordados. |
| Historias de usuario | Documento 03 actualizado con lo realmente entregado. |
| Arquitectura y diseño | Contexto, componentes, modelo de datos y pantallas, coherentes con el código. |
| Herramientas y tecnologías | Listado con versiones y justificación de cada elección. |
| Código fuente y README | Repositorio GitHub con instrucciones de instalación y ejecución reproducibles, sin credenciales. |
| Casos de prueba con resultados | Documento 04 con las ejecuciones reales registradas, incluidas las reprobadas y su corrección. |
| Informe | `Proyecto-Version1-nombreEquipo.pdf` en la plantilla del curso, con el enlace al repositorio. |

**Definición de terminado de una historia:** todos sus criterios de aceptación cumplidos; sus casos de prueba ejecutados y registrados; sin defectos críticos abiertos; código integrado en la rama principal; y documentación actualizada. Son defectos críticos los que permiten modificar un huerto ajeno, pierden registros o bloquean el recorrido principal.

## 7. Demostración al cliente

1. Un visitante abre el mapa, selecciona H1, recorre la escena 3D y consulta una zona con su señalética caliente y fría.
2. Consulta el calendario lunar y resalta los días recomendados para la siembra.
3. El líder actualiza la ficha y el estado de un cultivo sin alterar otro de la misma planta.
4. Registra una siembra con fotografía, revisa su condición lunar y la insignia de su comunidad.
5. Corrige la fecha con un motivo y muestra el historial y el recálculo de la insignia.
6. Se muestran un acceso rechazado y una fotografía no admitida.

Se usan datos de demostración identificados como tales: la espera de 15 días de la preparación real no puede comprimirse al calendario académico. Tras la demostración se recogen las observaciones del cliente y las respuestas a las decisiones pendientes.

## 8. Riesgos de la iteración

| Riesgo | Respuesta |
|---|---|
| La capacidad real es menor que el esfuerzo estimado | Confirmarla el 3/11; si falta, negociar el contenido antes de comprometerlo. Orden propuesto de salida: HU-020, HU-017 y HU-018. Retirar HU-018 deja la insignia fuera de la versión 1 y debe acordarse con el cliente. |
| El 3D o el mapa resultan inviables en celulares | Prueba de viabilidad el 3/11; la lista de zonas y cultivos (HU-012, CA-02) mantiene la consulta mientras se ajusta el detalle. |
| El cálculo de fases no coincide con las efemérides | Verificarlo temprano con REF-LUNA; mantener CAL-T para probar la lógica de evaluación por separado. |
| Las pruebas y la documentación se acumulan al final | Ejecutarlas por historia; reservar el 12/11 solo para el recorrido integrado y las correcciones. |
| Las correcciones alteran la insignia de forma incorrecta | Calcular la insignia con la versión vigente de todas las siembras; casos P-041 y P-048. |
| El croquis de H1 no está disponible a tiempo | Solicitarlo el 3/11; mientras tanto, construir la escena con un croquis provisional identificado como tal. |

El resto del proyecto conserva almacén, foro, miembros, editor del huerto, funciones gubernamentales, ciclo agrícola completo, puntos, ranking, niveles y talentos. Su planificación se acuerda con el cliente después de esta entrega.

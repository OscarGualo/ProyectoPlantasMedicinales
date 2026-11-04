**ESCUELA POLITÉCNICA NACIONAL**
Ingeniería de Software
CALIDAD DE SOFTWARE (ISWD652) · GR2SW · Período 2026-B

# Reglas, datos y trazabilidad

**Huertos Medicinales Comunitarios**
Referencia común · Versión 1.2

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

## 1. Propósito y estado

Este documento es la referencia común de los documentos 01 a 04. Reúne el glosario, las decisiones del proyecto, las diferencias entre las fuentes, las reglas de negocio, el catálogo de mensajes, los datos de prueba y las matrices de cobertura y trazabilidad. Debe leerse antes de usar las historias y los casos de prueba.

**Estado:** las reglas marcadas como *Confirmada* provienen del cliente o de la definición del proyecto. Las marcadas como *Propuesta* son la interpretación del equipo para poder construir y probar; se presentan al cliente en la revisión de la iteración y, si cambian, se actualizan las historias y los casos afectados antes de ejecutarlos.

## 2. Glosario

### 2.1 Términos del dominio

| Término | Significado en este proyecto |
|---|---|
| Comunidad de siembra | Grupo que cultiva un huerto: puede ser una persona, una familia, un condominio o un barrio (definición del proyecto). |
| Líder de huerto | Persona designada por el MSP como responsable de un huerto. Es la única que registra y corrige su información, evidencias y avance. |
| Visitante | Cualquier persona que consulta la información pública sin iniciar sesión. |
| Integrante de la comunidad | Persona que participa en una comunidad de siembra. En la iteración 1 no tiene cuenta propia y usa la consulta pública (reto y calendario lunar). |
| Huerto publicado | Huerto con nombre y coordenadas que el líder hizo visible en el mapa y en la consulta pública. |
| Huerto / zona | Huerto real registrado. Una zona es una parte identificable de su estructura (por ejemplo, un camellón o una terraza). |
| Planta (ficha) | Ficha educativa de una especie medicinal dentro de un huerto: nombres, familia, descripción, fuente, clasificación caliente o fría y fotografía. No tiene estado de crecimiento. |
| Cultivo | Plantación concreta de una planta en una zona del huerto, con su propio estado (sembrado, en crecimiento, cosechado o perdido). |
| Actividad / siembra | Hecho del ciclo de cultivo informado por el líder, con fecha y hora del hecho, fecha y hora de registro, autor y evidencia. La siembra es la actividad implementada en la iteración 1. |
| Versión de una actividad | Cada corrección crea una versión nueva; la anterior se conserva en el historial con su autor, fecha y motivo. |
| Fase lunar | Luna oscura (luna nueva), luna creciente, luna llena o luna menguante, calculada según RN-09. |
| Condición lunar | Resultado de comparar la fase de una actividad con la práctica (RN-06): conforme, no conforme o pendiente. No certifica ritual, hora ni técnica. |
| Planta caliente / fría | Clasificación de la medicina ancestral andina según el efecto de la planta. Se señala en rojo (caliente) o en verde (fría). |
| Reto e insignia | El reto describe una práctica que se quiere reconocer; la insignia es el reconocimiento que obtiene la comunidad al cumplirlo (RN-08). |
| Escena 3D | Representación esquemática del huerto preparada a partir de su croquis aprobado. Muestra estructura y cultivos; no simula el crecimiento de las plantas. |

### 2.2 Abreviaturas e identificadores

| Sigla | Significado |
|---|---|
| C1–C8 | Capacidades globales del producto (documento 01, apartado 2.1). |
| HU | Historia de usuario (documento 03). |
| CA | Criterio de aceptación de una historia, en formato Dado / Cuando / Entonces. |
| P | Caso de prueba (documento 04). |
| RN | Regla de negocio (sección 5). |
| D | Decisión del proyecto (sección 3). |
| DF | Diferencia entre fuentes (sección 4). |
| MSG | Mensaje que el sistema muestra al usuario (sección 6). |
| I1 | Iteración 1, que se entrega como versión 1 el 13/11/2026. |
| MSP | Ministerio de Salud Pública del Ecuador. |
| LOPDP | Ley Orgánica de Protección de Datos Personales del Ecuador (2021). |
| CAL-T | Calendario lunar controlado que solo se usa en el entorno de prueba. |
| REF-LUNA | Tabla de efemérides lunares oficiales usada para verificar el cálculo de fases. |
| CR1 | Croquis aprobado del huerto de prueba H1. |
| L, H, Z, E, K, A, F | Prefijos de los datos de prueba: líder, huerto, zona, planta, cultivo, actividad y fotografía (sección 7). |

## 3. Registro de decisiones

| ID | Tema | Estado | Decisión o propuesta |
|---|---|---|---|
| D-01 | Representación 3D | Propuesta | En I1 el equipo prepara la escena a partir del croquis aprobado; la edición de la estructura por el líder queda para un incremento posterior. El cliente confirmó el 3D, no esta profundidad. |
| D-02 | Datos de demostración | Propuesta | Se usan los huertos H1 a H4 y los datos de la sección 7. Esas cantidades no limitan la cobertura nacional del producto. |
| D-03 | Cardinalidades | Pendiente | Cuántos huertos tiene una comunidad, a cuántas comunidades pertenece una persona y cuántos huertos tiene un líder. I1 asigna un huerto por líder sin fijarlo como regla. |
| D-04 | Rol gubernamental y moderación | Pendiente | Facultades del representante gubernamental y quién modera el foro. |
| D-05 | Almacén | Parcial | Confirmado: espacios, elementos almacenados y condiciones de conservación. Pendiente: cantidades, movimientos y lotes. Fuera de I1. |
| D-06 | Reglas lunares | Obligatoriedad confirmada; mecanismo propuesto | Las prácticas lunares son obligatorias según las aclaraciones del cliente registradas por el equipo el 29/10/2026. Los intervalos de cada fase (RN-09) y la tabla de actividades (RN-10) se validan con un líder antes de publicar resultados reales. |
| D-07 | Equipo y fechas | Resuelta | Equipo de 6 integrantes. Entrega del informe y demostración: viernes 13/11/2026, 09:00. Las horas disponibles por persona se confirman al iniciar la iteración. |
| D-08 | Talentos y competencia | Pendiente | Evidencia para acreditar talentos, ascenso de nivel, puntuación y comparación justa entre comunidades de distinto tamaño. |
| D-09 | Gamificación en I1 | Resuelta (equipo, 02/11/2026) | I1 incluye solo el reto e insignia *Documentación lunar*. Puntos, misiones, niveles, talentos y ranking siguen en el alcance global para incrementos posteriores. |
| D-10 | Fichas de plantas | Resuelta (equipo, 02/11/2026) | Cada huerto mantiene sus propias fichas, aunque la especie se repita en otros huertos. Un catálogo común queda como posible mejora. |
| D-11 | Calendario lunar | Resuelta (equipo, 02/11/2026) | I1 permite consultar y filtrar el calendario lunar para planificar (HU-019 y HU-020) y verificar la condición lunar de cada siembra (HU-015). Las fases se calculan; no dependen de una fuente externa en línea. |

Las decisiones resueltas por el equipo se presentan al cliente en la revisión del 13/11/2026. Para cerrar una decisión se registran respuesta, responsable, fecha y documentos afectados.

## 4. Diferencias entre fuentes

La definición del proyecto (DEF) y los lineamientos del MSP (2017) describen las mismas prácticas con algunas diferencias. Se registran para que ninguna historia futura convierta una de ellas en obligación sin acuerdo.

| ID | Tema | Definición del proyecto | Lineamientos MSP | Tratamiento propuesto |
|---|---|---|---|---|
| DF-01 | Hora de siembra | A las 8 de la mañana, después del ritual. | Ritual a la salida del sol; siembra desde las 8 o 9 de la mañana. | No se valida la hora en I1. Al implementar el ciclo completo, aceptar el rango del MSP si el cliente lo confirma. |
| DF-02 | Baño previo a la cosecha | Dos días antes de la cosecha. | Uno o dos días antes de la cosecha. | Confirmar con el cliente antes de implementar el ciclo de cosecha. |
| DF-03 | Caducidad de plantas secas | Sierra: 12 meses en fundas herméticas. | Sierra: 12 meses. Costa y Amazonía: 6 meses. | Calcular la caducidad según la región del huerto, con los valores del MSP. |
| DF-04 | Riego | Agua de riego, manguera (no aspersor) o regadera de aluminio. | Regadera; plantas que requieran riego semanal. | Ofrecer las técnicas de la definición; el MSP se muestra como orientación. |
| DF-05 | Agua para riego | Mezcla fuentes y técnicas: río, canal, por goteo. | Toma de agua (grifo) cercana. | Separar fuente de agua (río, canal, red, lluvia) de técnica de riego (goteo, manguera, regadera). |
| DF-06 | Inventario y notificaciones | Registrar espacios de bodega; no menciona notificaciones. | No aplica. La presentación del proyecto sí los menciona. | Tratar como pendientes de confirmación (D-05); fuera de I1. |

## 5. Reglas de negocio

Las reglas se clasifican según su tipo: **hecho** (algo verdadero del negocio), **restricción** (lo que se permite o no), **desencadenador** (una acción que se produce ante un evento), **inferencia** (conocimiento derivado de condiciones) y **cálculo** (una fórmula o algoritmo).

| ID | Regla | Tipo | Estado | Enunciado | Fuente |
|---|---|---|---|---|---|
| RN-01 | Autoridad del líder | Restricción | Confirmada en su principio | Solo el líder designado por el MSP para un huerto puede modificar su ficha, plantas, cultivos y actividades. En I1 el equipo crea las cuentas y asignaciones con la lista que entregue el cliente. | Aclaraciones del cliente registradas el 29/10/2026 |
| RN-02 | Información pública | Restricción | Propuesta | Son públicos el nombre, la ubicación y las características de un huerto publicado; sus plantas, cultivos y escena 3D; sus siembras con su condición lunar; y su insignia. Son privados las credenciales, el contacto del responsable y los datos personales de los miembros. Un huerto solo se publica si tiene nombre y coordenadas. | Aclaraciones del cliente (consulta pública) y LOPDP |
| RN-03 | Planta, cultivo y estado | Hecho y restricción | Concepto confirmado; estados propuestos | Una ficha de planta pertenece a un huerto (D-10). Un cultivo es una planta en una zona del mismo huerto y tiene uno de estos estados: sembrado, en crecimiento, cosechado o perdido. Cambiar el estado de un cultivo no cambia otros cultivos ni la ficha de la planta. Registrar una actividad no cambia el estado automáticamente. | Definición; análisis |
| RN-04 | Registro de una siembra | Restricción | Propuesta | Cada siembra pertenece a un cultivo del huerto del líder y guarda fecha y hora del hecho, fecha y hora de registro, autor, técnica (semillas, estacas, esquejes o plántulas), descripción y fotografía. Un envío repetido con el mismo identificador no crea otra siembra. Las siembras no se eliminan desde la interfaz: se corrigen (RN-07). | Definición |
| RN-05 | Evidencia y fechas | Restricción | Propuesta | La fotografía es JPEG o PNG, con contenido coherente con su formato y un tamaño máximo de 5 MB (5 242 880 bytes). La fecha y hora del hecho no pueden ser posteriores al momento del registro, en la zona horaria del huerto. Si la fotografía no se guarda, la siembra no se registra. | Aclaraciones del cliente (evidencia por fotos) |
| RN-06 | Condición lunar de la siembra | Inferencia | Obligatoriedad confirmada; mecanismo propuesto | Una siembra es conforme si la fecha y hora del hecho caen en luna creciente o luna llena; no conforme si caen en luna oscura o luna menguante; pendiente si no hay fase disponible. Se usa la versión vigente de la siembra. El resultado no impide registrar el hecho. | Definición; MSP |
| RN-07 | Corrección de una actividad | Restricción y desencadenador | Propuesta | Solo el líder del huerto corrige una actividad e indica un motivo. Cada corrección crea una versión nueva y conserva la anterior con autor, fecha y motivo. Las evaluaciones que dependen de la actividad (RN-06 y RN-08) usan siempre la versión vigente. | Análisis |
| RN-08 | Insignia Documentación lunar | Inferencia | Propuesta | La comunidad tiene la insignia activa si su huerto tiene al menos una siembra vigente, con fotografía y conforme. Es única por comunidad; se desactiva cuando deja de haber siembras elegibles y se reactiva si vuelve a haberlas. Se calcula al consultarla, con la versión vigente de cada siembra, de modo que una corrección se refleja sin un recálculo aparte. No otorga puntos ni certifica talentos ni aprendizaje (D-09). | Definición (gamificación) |
| RN-09 | Fase lunar de una fecha | Cálculo | Propuesta (D-06) | Los instantes de luna nueva, cuarto creciente, luna llena y cuarto menguante se calculan con un algoritmo astronómico (Meeus, *Astronomical Algorithms*). Cada fase abarca desde el punto medio con la fase principal anterior hasta el punto medio con la siguiente; así, la luna llena incluye los días alrededor del plenilunio. Se usa la zona horaria del huerto: America/Guayaquil (UTC-5) o Pacific/Galapagos (UTC-6). En el calendario, la fase de un día es la vigente a las 12:00. El calendario está disponible de 12 meses antes a 12 meses después de la fecha actual. | Análisis; validar con un líder |
| RN-10 | Actividades recomendadas por fase | Hecho | Confirmada en las fuentes; validar variantes locales | **Luna oscura:** preparación de la tierra (retiro y desmenuzado de maleza), elaboración de abono, deshierbe y control de plagas. **Luna creciente:** siembra y ritual de siembra, trasplante y resiembra, cosecha de hojas, flores y frutos. **Luna llena:** siembra y ritual de siembra, cosecha de hojas, flores y frutos, baños rituales. **Luna menguante:** cosecha de semillas y raíces. | Definición; MSP, Anexo 4 |

## 6. Catálogo de mensajes

Los criterios de aceptación y los casos de prueba citan estos mensajes por su identificador. Los valores entre llaves se reemplazan por el dato correspondiente. Los mensajes no revelan información privada ni detalles internos del sistema.

| ID | Texto | Historias |
|---|---|---|
| MSG-01 | Usuario o contraseña incorrectos. | HU-001 |

## 7. Datos de prueba

Todos los datos son ficticios y exclusivos del entorno de prueba. CAL-T no describe fases reales: solo permite obtener resultados conocidos. Las credenciales se entregan en el entorno, no en este documento.

| Dato | Preparación |
|---|---|
| Reloj | Fijado en 31/10/2026 12:00, zona America/Guayaquil. |
| Líderes | L1 asignado a H1; L2 asignado a H2; L3 asignado a H4; L4 asignado a H5. Todas las cuentas activas. |
| Huertos | H1 y H2 publicados con coordenadas. H3 no publicado. H4 no publicado y sin coordenadas. H5 no publicado, con nombre y coordenadas. |
| Zonas | Z1, Z2 y Z4 (sin cultivos) en H1; Z3 en H2. |
| Plantas | E1 "Planta de prueba" (fría) en H1; E2 con los mismos nombres que E1 en H2; E3 "Planta caliente de prueba" (caliente) y E4 "Planta sin clasificar" en H1. Datos sintéticos sin uso medicinal. |
| Cultivos | K1 (E1 en Z1) y K2 (E1 en Z2); K4 (E3 en Z2) y K5 (E4 en Z2); K3 en Z3 de H2. |
| Fotografías | F1: JPEG válido de 1 MB. F2: archivo de texto con extensión .jpg. F3: PNG válido de 6 MB. F4: JPEG válido de exactamente 5 MB (5 242 880 bytes). |
| Actividades | A1: siembra de K1 del 10/10/2026 08:00 con F1. A3: siembra de K1 del 13/10/2026 08:00 con F1. Cada caso indica si parte con ellas o sin siembras. |
| CAL-T | 10/10/2026: luna creciente. 11/10/2026: luna menguante. 12/10/2026: sin dato. 13/10/2026: luna llena. 14/10/2026: luna oscura. Cada valor abarca el día completo, de 00:00 a 24:00. |
| REF-LUNA | Fechas y horas oficiales de las fases principales entre el 31/10/2026 y el 30/11/2026, obtenidas de un observatorio o servicio astronómico reconocido antes de ejecutar P-050. Se registra la fuente y la fecha de consulta. |
| CR1 | Croquis de H1 aprobado por el cliente, con Z1, Z2, caminos, cerca y bodega. |

### 7.1 Campos de la iteración 1

- **Huerto:** nombre y coordenadas (obligatorios para publicar); descripción, provincia, parroquia, dirección, superficie (≥ 0 m²), diseño, tipo de cerca, tipo de suelo, fuente de agua, técnica de riego, tipo de abono y contacto del responsable (privado).
- **Planta:** nombre común, nombre científico, familia y fuente (obligatorios); otros nombres, descripción, altitud, distribución por provincias, ciclo corto o largo, clasificación caliente o fría, usos y fotografía (opcionales; nunca se inventan si no hay información validada).
- **Cultivo:** planta, zona y estado. **Siembra:** cultivo, fecha y hora del hecho, técnica, descripción, fotografía, autor y fecha y hora de registro.

## 8. Cobertura del enunciado

La matriz relaciona cada elemento de la definición del proyecto con su capacidad y su destino. Que un elemento no esté en I1 no significa que se elimine del proyecto.

| Elemento de la definición | Cap. | Destino | Tratamiento |
|---|---|---|---|
| Comunidad: nombre, responsable y contacto | C3 | HU-004 | Ficha básica en I1; el contacto es privado (RN-02). |
| Miembros: nombre, nacimiento, género, talentos y nivel | C3, C7 | Posterior | Requiere decidir finalidad y protección de datos (LOPDP) y las reglas de talentos (D-08). |
| Ubicación: provincia, parroquia, dirección y georreferencia | C1, C3 | HU-003, HU-010 | Incluido en I1. |
| Diseño, cerca, superficie, suelo, riego y abono | C2, C3 | HU-004, HU-012 | Incluido en I1; fuente de agua separada de técnica de riego (DF-05). |
| Plantas: nombres, familia, altitud, distribución, ciclo, caliente o fría, usos y fotografías | C3 | HU-006, HU-007 | Incluido en I1; obligatorios y opcionales según 7.1. Sin dosis ni prescripción personalizada. |
| Bodega: herramientas, material de siembra y abonos | C6 | HU-012 / posterior | La bodega se ve en la escena 3D; su gestión es posterior (D-05). |
| Preparación de la tierra y espera de 15 días | C5 | HU-019, HU-020 / posterior | El calendario indica la fase recomendada; el registro de la preparación es posterior. |
| Ritual, hora y técnica de siembra | C5 | HU-014, HU-015 | Se registra la técnica y se verifica la fase; ritual y hora no se validan (DF-01). |
| Señalética roja (caliente) y verde o blanca (fría) | C2, C5 | HU-013 | Incluido en I1 como color de los cultivos en la escena. |
| Cuidados: deshierbe, replante, aporque y riego | C5 | Posterior | Ciclo de cultivo completo. |
| Baño, ritual y cosecha según la parte de la planta | C5 | HU-020 / posterior | El calendario orienta la cosecha por fase (RN-10); su registro es posterior (DF-02). |
| Limpieza, secado, empaque, etiquetado y caducidad | C5, C6 | Posterior | Etiqueta con los campos del MSP; caducidad por región (DF-03). |
| Calendario de actividades e historia visual (fotos y videos) | C5 | HU-014, HU-017, HU-019 | Bitácora con fotografías y calendario lunar en I1. Videos: postergación por acordar con el cliente. |
| Huerto virtual vinculado al real | C2, C5 | HU-009, HU-012, HU-013 | Escena con cultivos y su estado informado; edición por el líder posterior (D-01). |
| Misiones, puntos, insignias, niveles, talentos y ranking | C7 | HU-018 / posterior | I1: reto e insignia *Documentación lunar* (D-09). |
| Foro con moderador y reglas de participación | C8 | Posterior | Incluido en el alcance global (D-04). |
| Mapa de Ecuador, líder autorizado, rol gubernamental, español | C1, C4 | HU-001, HU-002, HU-010 | Confirmados en las aclaraciones del cliente; permisos gubernamentales pendientes (D-04). |

## 9. Trazabilidad de la iteración 1

| Historia | Capacidad | Reglas | Criterios y pruebas | Horas |
|---|---|---|---|---:|
| HU-001 | C4 | RN-01 | CA-01 → P-001; CA-02 → P-002; CA-03 → P-003 | 5 |
| HU-002 | C4 | RN-01 | CA-01 → P-004; CA-02 → P-005 | 3 |
| HU-003 | C1, C3 | RN-01, RN-02 | CA-01 → P-006; CA-02 → P-007 | 4 |
| HU-004 | C3 | RN-01, RN-02 | CA-01 → P-008; CA-02 → P-009; CA-03 → P-010 | 4 |
| HU-005 | C1 | RN-02 | CA-01 → P-011; CA-02 → P-012 | 3 |
| HU-006 | C3 | RN-02, RN-03, RN-05 | CA-01 → P-013; CA-02 → P-014; CA-03 → P-015 | 8 |
| HU-007 | C3 | RN-02, RN-03 | CA-01 → P-016; CA-02 → P-017; CA-03 → P-018 | 4 |
| HU-008 | C3, C5 | RN-03 | CA-01 → P-019; CA-02 → P-020 | 6 |
| HU-009 | C3, C5 | RN-03 | CA-01 → P-021; CA-02 → P-022 | 4 |
| HU-010 | C1 | RN-02 | CA-01 → P-023; CA-02 → P-024 | 6 |
| HU-011 | C1, C3 | RN-02 | CA-01 → P-025; CA-02 → P-026 | 5 |
| HU-012 | C2 | RN-02 | CA-01 → P-027; CA-02 → P-028 | 12 |
| HU-013 | C2, C5 | RN-03 | CA-01 → P-029; CA-02 → P-030; CA-03 → P-031 | 8 |
| HU-014 | C5 | RN-04, RN-05 | CA-01 → P-032; CA-02 → P-033; CA-03 → P-034; CA-04 → P-035; CA-05 → P-036; CA-06 → P-037 | 14 |
| HU-015 | C5 | RN-06, RN-09 | CA-01 → P-038; CA-02 → P-039; CA-03 → P-040 | 10 |
| HU-016 | C5 | RN-07 | CA-01 → P-041; CA-02 → P-042; CA-03 → P-043; CA-04 → P-044 | 8 |
| HU-017 | C5 | RN-07 | CA-01 → P-045; CA-02 → P-046 | 4 |
| HU-018 | C7 | RN-08 | CA-01 → P-047; CA-02 → P-048; CA-03 → P-049 | 8 |
| HU-019 | C5 | RN-09, RN-10 | CA-01 → P-050; CA-02 → P-051 | 7 |
| HU-020 | C5 | RN-10 | CA-01 → P-052; CA-02 → P-053; CA-03 → P-054 | 4 |

P-055 recorre la integración de las 20 historias. **Total: 54 criterios de aceptación, 55 casos de prueba y 127 horas-persona de historias.**

### 9.1 Cambios respecto de la versión 1.1

- Las historias se reescriben con una sola acción central y a un nivel de abstracción más bajo: de 11 a 20 historias. La equivalencia está en la sección 9.2. La numeración de historias y casos cambia; no mezclarla con la de versiones anteriores.
- La corrección de una siembra (HU-016) y su condición lunar (HU-015) ya no prometen recalcular la insignia: esa verificación pertenece a HU-018. Así, aplazar HU-018 no deja criterios incumplibles en otras historias.
- Se agregan HU-017 (historial de una siembra), los mensajes MSG-22 y MSG-23 y los datos de prueba L4, H5 y Z4.
- Las fuentes de las reglas confirmadas se citan como aclaraciones del cliente registradas por el equipo, sin atribuirles una fecha de taller.

### 9.2 Equivalencia con las historias de la versión 1.1

| Historia v1.2 | Título | Proviene de |
|---|---|---|
| HU-001 | Iniciar sesión | HU-001 (v1.1) |
| HU-002 | Cerrar sesión | HU-001 (v1.1) |
| HU-003 | Actualizar la ubicación del huerto | HU-002 (v1.1) |
| HU-004 | Actualizar las características del huerto | HU-002 (v1.1) |
| HU-005 | Publicar el huerto | HU-002 (v1.1) |
| HU-006 | Registrar una planta medicinal | HU-003 (v1.1) |
| HU-007 | Modificar una planta medicinal | HU-003 (v1.1) |
| HU-008 | Registrar un cultivo | HU-004 (v1.1) |
| HU-009 | Cambiar el estado de un cultivo | HU-004 (v1.1) |
| HU-010 | Ver los huertos en el mapa | HU-005 (v1.1) |
| HU-011 | Consultar la ficha de un huerto | HU-005 (v1.1) |
| HU-012 | Recorrer el huerto en 3D | HU-006 (v1.1) |
| HU-013 | Consultar los cultivos de una zona en 3D | HU-006 (v1.1) |
| HU-014 | Registrar una siembra | HU-007 (v1.1) |
| HU-015 | Consultar la condición lunar de una siembra | HU-008 (v1.1) |
| HU-016 | Corregir una siembra | HU-009 (v1.1) |
| HU-017 | Consultar el historial de una siembra | HU-009 (v1.1) |
| HU-018 | Consultar el reto Documentación lunar | HU-010 (v1.1) |
| HU-019 | Consultar el calendario lunar | HU-011 (v1.1) |
| HU-020 | Filtrar el calendario lunar por actividad | HU-011 (v1.1) |

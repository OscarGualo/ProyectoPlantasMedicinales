# Reglas de negocio, glosario, datos y trazabilidad

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Propósito y estado

Este documento es la referencia común de los documentos 01 a 04. Reúne el glosario, las decisiones del proyecto, las diferencias entre las fuentes, las reglas de negocio, el catálogo de mensajes, los datos de prueba y las matrices de cobertura y trazabilidad. Debe leerse antes de usar las historias y los casos de prueba.

Estado: las reglas marcadas como Confirmada provienen del cliente o de la definición del proyecto. Las marcadas como Implementada en el piloto son la interpretación del equipo para poder construir y probar; describen la interpretación utilizada por la implementación con datos sintéticos. La validación cultural de esa interpretación no consta en la evidencia de la revisión.

## 2. Glosario

### 2.1 Términos del dominio

| Término | Significado en este proyecto |
|---|---|
| Comunidad de siembra | Grupo que cultiva un huerto: puede ser una persona, una familia, un condominio o un barrio (definición del proyecto). |
| Líder de huerto | Persona escogida por su comunidad y autorizada por el MSP como responsable de un huerto. Gestiona los datos operativos, evidencias y avance de su huerto; selecciona las fichas institucionales sin editar su contenido educativo. |
| Visitante | Cualquier persona que consulta la información pública sin iniciar sesión. |
| Integrante de la comunidad | Persona que participa en una comunidad de siembra. En la iteración 1 no tiene cuenta propia y usa la consulta pública (reto y calendario lunar). |
| Huerto publicado | Huerto con nombre y coordenadas que el líder hizo visible en el mapa y en la consulta pública. |
| Huerto / zona | Huerto real registrado. Una zona es una parte identificable de su estructura (por ejemplo, un camellón o una terraza). |
| Planta (ficha) | Ficha educativa de una especie medicinal dentro de un huerto: nombres, familia, descripción, fuente, clasificación caliente o fría y fotografía. No tiene estado de crecimiento. |
| Cultivo | Plantación concreta de una planta en una zona del huerto, con su propio estado (sembrado, en crecimiento, cosechado o perdido). |
| Actividad / siembra | Hecho del ciclo de cultivo informado por el líder, con fecha y hora del hecho, fecha y hora de registro, autor y evidencia. La siembra es la actividad implementada en la iteración 1. |
| Versión de una actividad | Cada corrección crea una versión nueva; la anterior se conserva en el historial con su autor, fecha y motivo. |
| Calendario lunar | Referencia organizada por fechas que relaciona fases lunares y actividades de cultivo según la práctica documentada. Permite elegir fechas de siembra; en I1 se consulta y filtra, sin guardar una agenda de trabajos futuros. |
| Ciclo agrícola o de cultivo | Etapas de preparación, siembra, cuidado, cosecha y poscosecha. Su significado es distinto del calendario lunar y del ciclo astronómico de la Luna. |
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
| CR1 | Croquis esquemático de demostración de H1. |
| L, H, Z, E, K, A, F | Prefijos de los datos de prueba: líder, huerto, zona, planta, cultivo, actividad y fotografía (sección 7). |
| RF / RNF | Requisito funcional / requisito no funcional (documento 07). |
| AD | Decisión de arquitectura (documento 06). |
| API / REST | Interfaz de programación; estilo de comunicación de la API web. |
| HTTP / HTTPS | Protocolo web; HTTPS protege el transporte mediante cifrado TLS. |
| SPA | Aplicación web de una sola página, servida con su API en el mismo origen. |
| CSRF | Falsificación de solicitudes entre sitios; se controla mediante un token por sesión. |
| UUID | Identificador universal utilizado como clave de reintento. |
| JSON / JSONB | Formato de intercambio de datos; JSONB es su representación binaria en PostgreSQL. |
| JPA / JDBC | Persistencia de entidades Java / conexión Java con la base de datos. |
| SQL / DDL | Lenguaje de consultas / instrucciones que definen el esquema de datos. |
| JAR / JVM | Archivo ejecutable Java / máquina virtual que lo ejecuta. |
| CI / E2E | Integración continua / prueba del recorrido completo de extremo a extremo. |
| P95 | Percentil 95 de los tiempos de respuesta medidos. |
| 3D / WebGL | Representación tridimensional / interfaz gráfica usada en el navegador. |
| UTC / USNO | Tiempo Universal Coordinado / Observatorio Naval de Estados Unidos, fuente de referencia. |
| SHA-256 / BCrypt | Función de huella de archivos / función de derivación para contraseñas. |
| MB / MiB | Megabyte decimal / mebibyte de 1 048 576 bytes. La aplicación admite hasta 5 242 880 bytes por foto. |
| PR / README | Solicitud de integración de cambios / archivo de instrucciones del repositorio. |
| Q1-I1 / Q-I1 | Perfil de carga de prueba / condiciones transversales de calidad de I1. |

## 3. Registro de decisiones

| ID | Tema | Estado | Decisión documentada |
|---|---|---|---|
| D-01 | Representación 3D | Implementada en el piloto | I1 genera la escena desde las geometrías esquemáticas de los datos de demostración. La edición de geometría por el líder pertenece a un incremento posterior. |
| D-02 | Datos de demostración | Implementada en el piloto | Se usan los huertos ficticios definidos por cada caso, incluidos H1 a H6, y los datos de las secciones 7 y 11. Esas cantidades no limitan la cobertura nacional del producto. |
| D-03 | Cardinalidades | Modelo implementado | El modelo admite varios huertos por comunidad y líder. Cada huerto tiene una comunidad y un líder. La demostración de I1 utiliza una cuenta por líder con un solo huerto; la interfaz de gestión selecciona la primera asignación y no incorpora un selector multihuerto. El registro de membresías de participantes pertenece al alcance posterior a I1. |
| D-04 | Rol gubernamental y moderación | Alcance posterior a I1 | El rol gubernamental y la moderación del foro pertenecen al alcance global posterior a I1. En el incremento actual intervienen el visitante y el líder de huerto. |
| D-05 | Almacén | Alcance posterior a I1 | El almacén pertenece al alcance global posterior a I1. La escena representa una bodega como estructura, sin gestión de existencias, movimientos o lotes. |
| D-06 | Reglas lunares | Práctica obligatoria; interpretación del piloto | Las prácticas lunares son obligatorias según las aclaraciones del cliente registradas por el equipo el 29/10/2026. Los intervalos de cada fase (RN-09) y la tabla de actividades (RN-10) se validan con un líder antes de publicar resultados reales. |
| D-07 | Equipo y fechas | Resuelta | Equipo techmOS con seis integrantes. La fecha de entrega del informe y demostración es el viernes 13/11/2026 a las 09:00. Este paquete documenta el código auditado el 11/11/2026 y se edita el 12/11/2026. |
| D-08 | Talentos y competencia | Alcance posterior a I1 | Evidencia para acreditar talentos, ascenso de nivel, puntuación y comparación justa entre comunidades de distinto tamaño. |
| D-09 | Gamificación en I1 | Resuelta (equipo, 02/11/2026) | I1 incluye solo el reto e insignia Documentación lunar. Puntos, misiones, niveles, talentos y ranking siguen en el alcance global para incrementos posteriores. |
| D-10 | Fichas de plantas | Resuelta (equipo, 02/11/2026) | El catálogo institucional es común y de lectura. Cada huerto incorpora una referencia a una ficha de ese catálogo y mantiene su fotografía propia. Los datos educativos se copian desde el servidor con fuente y versión; no se editan por el líder. Decisión actualizada por aclaración del cliente del 12/11/2026. |
| D-11 | Calendario lunar | Resuelta (equipo, 02/11/2026) | I1 permite consultar y filtrar el calendario lunar para planificar (HU-019 y HU-020) y verificar la condición lunar de cada siembra (HU-015). Las fases se calculan; no dependen de una fuente externa en línea. |

Las decisiones técnicas describen el comportamiento del candidato documentado. La regla del piloto implementa la interpretación documental de las fases. El corte de esta entrega no contiene un acta de validación comunitaria de esa interpretación.

## 4. Diferencias entre fuentes

La definición del proyecto (DEF) y los lineamientos del MSP (2017) describen las mismas prácticas con algunas diferencias. Se registran para que ninguna historia futura convierta una de ellas en obligación sin acuerdo.

| ID | Tema | Definición del proyecto | Lineamientos MSP | Tratamiento de la fuente en I1 |
|---|---|---|---|---|
| DF-01 | Hora de siembra | A las 8 de la mañana, después del ritual. | Ritual a la salida del sol; siembra desde las 8 o 9 de la mañana. | No se valida la hora en I1. Al implementar el ciclo completo, aceptar el rango del MSP si el cliente lo confirma. |
| DF-02 | Baño previo a la cosecha | Dos días antes de la cosecha. | Uno o dos días antes de la cosecha. | Confirmar con el cliente antes de implementar el ciclo de cosecha. |
| DF-03 | Caducidad de plantas secas | Sierra: 12 meses en fundas herméticas. | Sierra: 12 meses. Costa y Amazonía: 6 meses. | Para el incremento que gestione poscosecha, validar región, condiciones de conservación y criterio aplicable antes de calcular una fecha. Conservar fuente y aprobación; los valores del MSP no constituyen una garantía automática de aptitud del producto. |
| DF-04 | Riego | Agua de riego, manguera (no aspersor) o regadera de aluminio. | Regadera; plantas que requieran riego semanal. | Ofrecer las técnicas de la definición; el MSP se muestra como orientación. |
| DF-05 | Agua para riego | Mezcla fuentes y técnicas: río, canal, por goteo. | Toma de agua (grifo) cercana. | Separar fuente de agua (río, canal, red, lluvia) de técnica de riego (goteo, manguera, regadera). |
| DF-06 | Inventario y notificaciones | Registrar espacios de bodega; no menciona notificaciones. | No aplica. La presentación del proyecto sí los menciona. | El control cuantitativo de cantidades, movimientos y lotes es una ampliación posterior al registro básico del almacén; fuera de I1 (D-05). |

## 5. Reglas de negocio

Las reglas se clasifican según su tipo: hecho (algo verdadero del negocio), restricción (lo que se permite o no), desencadenador (una acción que se produce ante un evento), inferencia (conocimiento derivado de condiciones) y cálculo (una fórmula o algoritmo).

| ID | Regla | Tipo | Estado | Enunciado | Fuente |
|---|---|---|---|---|---|
| RN-01 | Autoridad del líder | Restricción | Confirmada en su principio | Solo el líder escogido por la comunidad y autorizado por el MSP para un huerto puede modificar su ficha, plantas, cultivos y actividades. En I1 las cuentas y asignaciones se preparan mediante datos de demostración y de prueba. | Aclaraciones del cliente registradas el 29/10/2026 |
| RN-02 | Información pública | Restricción | Implementada en el piloto | Son públicos el nombre, la ubicación, las características, las plantas, los cultivos y la escena 3D de un huerto publicado, además de su reto e insignia. La bitácora completa se consulta mediante una sesión de su líder. El reto expone el sustento vigente de la insignia. Son privados las credenciales, el contacto del responsable y los datos personales de los miembros. Un huerto solo se publica si tiene nombre y coordenadas. | Aclaraciones del cliente (consulta pública) y LOPDP |
| RN-03 | Planta, cultivo y estado | Hecho y restricción | Concepto y estados implementados en I1 | Una ficha de planta pertenece a un huerto y referencia un registro del catálogo institucional común (D-10). Un cultivo es una planta en una zona de tipo CULTIVO del mismo huerto y tiene uno de estos estados: sembrado, en crecimiento, cosechado o perdido. Cambiar el estado de un cultivo no cambia otros cultivos ni la ficha de la planta. Registrar una actividad no cambia el estado automáticamente. | Definición; análisis |
| RN-04 | Registro de una siembra | Restricción | Implementada en el piloto | Cada siembra pertenece a un cultivo del huerto del líder y guarda fecha y hora del hecho, fecha y hora de registro, autor, técnica (semillas, estacas, esquejes o plántulas), descripción y fotografía. Un envío repetido con el mismo identificador y contenido devuelve la misma siembra, incluso con solicitudes simultáneas. Con contenido distinto se rechaza con MSG-24. La clave se conserva mientras el resultado es desconocido y se renueva para una operación nueva deliberada. Las siembras no se eliminan desde la interfaz: se corrigen (RN-07). | Definición |
| RN-05 | Evidencia y fechas | Restricción | Implementada en el piloto | La fotografía es JPEG o PNG, con contenido coherente con su formato y un tamaño máximo de 5 MB (5 242 880 bytes). La fecha y hora del hecho no pueden ser posteriores al momento del registro, en la zona horaria del huerto. Si la fotografía no se guarda, la siembra no se registra. | Aclaraciones del cliente (evidencia por fotos) |
| RN-06 | Condición lunar de la siembra | Inferencia | Práctica obligatoria; interpretación del piloto | Una siembra es conforme si la fecha y hora del hecho caen en luna creciente o luna llena; no conforme si caen en luna oscura o luna menguante; pendiente si no hay fase disponible. Se usa la versión vigente de la siembra. El resultado no impide registrar el hecho. | Definición; MSP |
| RN-07 | Corrección de una actividad | Restricción y desencadenador | Implementada en el piloto | Solo el líder del huerto corrige una actividad e indica un motivo. Cada corrección válida crea una versión nueva y conserva la anterior con autor, fecha y motivo. Se rechazan fechas futuras e imágenes inválidas. La solicitud indica la versión base y una clave de envío: un reintento idéntico devuelve la revisión existente; una corrección distinta sobre una versión ya superada se rechaza con MSG-25. Nunca se sobrescribe una versión confirmada. Las evaluaciones que dependen de la actividad (RN-06 y RN-08) usan siempre la versión vigente. | Análisis |
| RN-08 | Insignia Documentación lunar | Inferencia | Implementada en el piloto | La comunidad tiene la insignia pública activa si al menos uno de sus huertos publicados tiene una siembra vigente, con fotografía y conforme. Solo se devuelve un sustento procedente de un huerto publicado; las siembras de huertos no publicados no cuentan para esta insignia pública. Esta es la política implementada en la versión documentada. Es única por comunidad; se desactiva cuando deja de haber siembras elegibles y se reactiva si vuelve a haberlas. Se calcula al consultarla, con la versión vigente de cada siembra, de modo que una corrección se refleja sin un recálculo aparte. No otorga puntos ni certifica talentos ni aprendizaje (D-09). | Definición (gamificación) |
| RN-09 | Fase lunar de una fecha | Cálculo | Implementada en el piloto (D-06) | Los instantes de luna nueva, cuarto creciente, luna llena y cuarto menguante se calculan mediante una biblioteca astronómica de versión identificada y se contrastan con REF-LUNA. No se atribuye un algoritmo concreto sin verificar el implementado por la versión elegida. La convención implementada establece que cada fase abarca desde el punto medio con el evento anterior, incluido, hasta el punto medio con el siguiente, excluido. La búsqueda debe obtener ambos vecinos y cubrir el instante sin huecos. Esta división cultural no es una propiedad certificada por la biblioteca astronómica. La fecha local del hecho se convierte a un instante usando la zona horaria del huerto: America/Guayaquil (UTC-5) o Pacific/Galapagos (UTC-6). En el calendario general se usa America/Guayaquil y se identifica la zona; la fase diaria es la vigente a las 12:00. Es una orientación diaria: para una siembra se evalúa su instante real, que puede estar en otra fase dentro del mismo día. La respuesta identifica la regla mediante su texto. La biblioteca utilizada es Commons SunCalc 3.11. La versión de regla no se almacena como atributo de cada evaluación. El calendario está disponible de 12 meses antes a 12 meses después de la fecha actual. | Análisis; validar con un líder |
| RN-10 | Actividades recomendadas por fase | Hecho | Confirmada en las fuentes; validar variantes locales | Luna oscura: preparación de la tierra (retiro y desmenuzado de maleza), elaboración de abono, deshierbe y control de plagas. Luna creciente: siembra y ritual de siembra, trasplante y resiembra, cosecha de hojas, flores y frutos. Luna llena: siembra y ritual de siembra, cosecha de hojas, flores y frutos, baños rituales. Luna menguante: cosecha de semillas y raíces. | Definición; MSP, Anexo 4 |

## 6. Catálogo de mensajes

Los criterios de aceptación y los casos de prueba citan estos mensajes por su identificador. Los valores entre llaves se reemplazan por el dato correspondiente. Los mensajes no revelan información privada ni detalles internos del sistema.

| ID | Texto | Historias |
|---|---|---|
| MSG-01 | Usuario o contraseña incorrectos. | HU-001 |
| MSG-02 | Tu sesión ha finalizado. Inicia sesión para continuar. | HU-002 |
| MSG-03 | No tienes permiso para modificar la información de este huerto. | HU-001, HU-016 |
| MSG-04 | Completa el campo obligatorio: {campo}. | HU-004, HU-006, HU-007 |
| MSG-05 | La superficie debe ser un número mayor o igual a 0. | HU-004 |
| MSG-06 | Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180. | HU-003 |
| MSG-07 | La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB. | HU-006, HU-014, HU-016 |
| MSG-08 | La fecha y hora de la actividad no pueden ser posteriores al momento actual. | HU-014, HU-016 |
| MSG-09 | La zona seleccionada no pertenece a este huerto. | HU-008 |
| MSG-10 | Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido. | HU-009 |
| MSG-11 | No se pudo guardar la fotografía. La actividad no se registró; inténtalo nuevamente. | HU-014 |
| MSG-12 | Indica el motivo de la corrección. | HU-016 |
| MSG-13 | No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista. | HU-012 |
| MSG-14 | El huerto solicitado no existe o no está publicado. | HU-011 |
| MSG-15 | Conforme con la condición lunar: la siembra se realizó en {fase}. | HU-015 |
| MSG-16 | No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena. | HU-015 |
| MSG-17 | Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra. | HU-015 |
| MSG-18 | Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena. | HU-018 |
| MSG-19 | El cultivo seleccionado no pertenece a tu huerto. | HU-014 |
| MSG-20 | El calendario lunar está disponible desde {fecha inicial} hasta {fecha final}. | HU-019 |
| MSG-21 | El huerto no tiene ubicación registrada y no puede publicarse en el mapa. | HU-005 |
| MSG-22 | Esta zona no tiene cultivos registrados. | HU-013 |
| MSG-23 | Esta siembra no tiene correcciones registradas. | HU-017 |
| MSG-24 | Este envío ya se registró con otros datos. Revisa el resultado antes de realizar un nuevo envío. | HU-014, HU-016 |
| MSG-25 | La siembra cambió desde que abriste la corrección. Revisa la versión vigente e inténtalo nuevamente. | HU-016 |
| MSG-26 | No se pudo validar la solicitud. Actualiza la página e inténtalo nuevamente. | Q-I1-01 |
| MSG-27 | No se pudo guardar la actividad. Los cambios no se registraron; inténtalo nuevamente. | HU-014, HU-016 |

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
| REF-LUNA | Efemérides del U.S. Naval Observatory para 2026, en Tiempo Universal, consultadas el 10/11/2026. El documento 09 contiene los eventos de septiembre a diciembre que sirven de vecinos para el cálculo de intervalos. |
| CR1 | Croquis esquemático de H1 en los datos de demostración, con Z1, Z2, Z4, camino, cerca y bodega. La concordancia con un huerto físico se evalúa en P-027. |

### 7.1 Campos de la iteración 1

- Huerto: nombre y coordenadas (obligatorios para publicar); descripción, provincia, parroquia, dirección, superficie (≥ 0 m²), diseño, tipo de cerca, tipo de suelo, fuente de agua, técnica de riego, tipo de abono y contacto del responsable (privado).

- Planta: catalogoCodigo obligatorio al incorporarla; nombre común, referencia y versión obtenidos del catálogo. Nombre científico, familia, clasificación y contenido educativo solo cuando constan en la fuente. Fotografía opcional por huerto. No existe edición libre de nombres, fuente, usos o dosis por el líder. Altitud y distribución se muestran cuando existen como datos institucionales; las cuatro fichas iniciales no los incorporan.

- Cultivo: planta, zona y estado. Siembra: cultivo, fecha y hora del hecho, técnica, descripción, fotografía, autor y fecha y hora de registro.

## 8. Cobertura del enunciado

La matriz relaciona cada elemento de la definición del proyecto con su capacidad y su destino. Que un elemento no esté en I1 no significa que se elimine del proyecto.

| Elemento de la definición | Cap. | Destino | Tratamiento |
|---|---|---|---|
| Comunidad: nombre, responsable y contacto | C3 | HU-004 | Comunidad y asignación se cargan como datos iniciales; HU-004 edita la ficha del huerto, no la comunidad. La gestión de la comunidad queda en alcance posterior; el contacto del huerto es privado (RN-02). |
| Miembros: nombre, nacimiento, género, talentos y nivel | C3, C7 | Posterior | Requiere decidir finalidad y protección de datos (LOPDP) y las reglas de talentos (D-08). |
| Ubicación: provincia, parroquia, dirección y georreferencia | C1, C3 | HU-003, HU-010 | Incluido en I1. |
| Diseño, cerca, superficie, suelo, riego y abono | C2, C3 | HU-004, HU-012 | Incluido en I1; fuente de agua separada de técnica de riego (DF-05). |
| Plantas: nombres, familia, altitud, distribución, ciclo, caliente o fría, usos y fotografías | C3 | HU-006, HU-007 | Incluido en I1 mediante selección del catálogo, fotografía y lectura de datos disponibles. Las cuatro fichas iniciales no contienen dosis, usos terapéuticos, altitud ni distribución; su ausencia no se rellena con datos supuestos. |
| Bodega: herramientas, material de siembra y abonos | C6 | HU-012 / posterior | La bodega se ve en la escena 3D; su gestión es posterior (D-05). |
| Preparación de la tierra y espera de 15 días | C5 | HU-019, HU-020 / posterior | El calendario indica la fase recomendada; el registro de la preparación es posterior. |
| Ritual, hora y técnica de siembra | C5 | HU-014, HU-015 | Se registra la técnica y se verifica la fase; ritual y hora no se validan (DF-01). |
| Señalética roja (caliente) y verde o blanca (fría) | C2, C5 | HU-013 | Incluido en I1 como color de los cultivos en la escena. |
| Cuidados: deshierbe, replante, aporque y riego | C5 | Posterior | Ciclo de cultivo completo. |
| Baño, ritual y cosecha según la parte de la planta | C5 | HU-020 / posterior | El calendario orienta la cosecha por fase (RN-10); su registro es posterior (DF-02). |
| Limpieza, secado, empaque, etiquetado y caducidad | C5, C6 | Posterior | Etiqueta con los campos del MSP; caducidad por región (DF-03). |
| Calendario de actividades e historia visual (fotos y videos) | C5 | HU-014, HU-017, HU-019 | Bitácora con fotografías y calendario lunar en I1. La gestión de videos pertenece al alcance posterior a este incremento. |
| Huerto virtual vinculado al real | C2, C5 | HU-009, HU-012, HU-013 | Escena con cultivos y su estado informado; edición por el líder posterior (D-01). |
| Misiones, puntos, insignias, niveles, talentos y ranking | C7 | HU-018 / posterior | I1: reto e insignia Documentación lunar (D-09). |
| Foro con moderador y reglas de participación | C8 | Posterior | Incluido en el alcance global (D-04). |
| Mapa de Ecuador, líder autorizado, rol gubernamental, español | C1, C4 | HU-001, HU-002, HU-010 | Confirmados en las aclaraciones del cliente; gestión gubernamental fuera de I1 (D-04). |

## 9. Trazabilidad de la iteración 1

| Historia | Capacidad | Reglas | Criterios y pruebas | Horas |
|---|---|---|---|---|
| HU-001 | C4 | RN-01 | CA-01 → P-001; CA-02 → P-002; CA-03 → P-003; CA-04 → P-056 | 8 |
| HU-002 | C4 | RN-01 | CA-01 → P-004; CA-02 → P-005 | 3 |
| HU-003 | C1, C3 | RN-01, RN-02 | CA-01 → P-006; CA-02 → P-007 | 4 |
| HU-004 | C3 | RN-01, RN-02 | CA-01 → P-008; CA-02 → P-009; CA-03 → P-010 | 4 |
| HU-005 | C1 | RN-02 | CA-01 → P-011; CA-02 → P-012 | 3 |
| HU-006 | C3 | RN-02, RN-03, RN-05 | CA-01 → P-013; CA-02 → P-014; CA-03 → P-015 | 8 |
| HU-007 | C3 | RN-02, RN-03 | CA-01 → P-016; CA-02 → P-017; CA-03 → P-018 | 4 |
| HU-008 | C3, C5 | RN-03 | CA-01 → P-019; CA-02 → P-020 | 6 |
| HU-009 | C3, C5 | RN-03 | CA-01 → P-021; CA-02 → P-022 | 4 |
| HU-010 | C1 | RN-02 | CA-01 → P-023; CA-02 → P-024 | 6 |
| HU-011 | C1, C3 | RN-02 | CA-01 → P-025; CA-02 → P-026; CA-03 → P-064 | 5 |
| HU-012 | C2 | RN-02 | CA-01 → P-027; CA-02 → P-028 | 12 |
| HU-013 | C2, C5 | RN-03 | CA-01 → P-029; CA-02 → P-030; CA-03 → P-031 | 8 |
| HU-014 | C5 | RN-04, RN-05 | CA-01 → P-032; CA-02 → P-033; CA-03 → P-034; CA-04 → P-035; CA-05 → P-036; CA-06 → P-037; CA-07 → P-058; CA-08 → P-059; CA-09 → P-065 | 20 |
| HU-015 | C5 | RN-06, RN-09 | CA-01 → P-038; CA-02 → P-039; CA-03 → P-040; CA-04 → P-062 | 14 |
| HU-016 | C5 | RN-07 | CA-01 → P-041; CA-02 → P-042; CA-03 → P-043; CA-04 → P-044; CA-05 → P-057; CA-06 → P-060; CA-07 → P-069 | 12 |
| HU-017 | C5 | RN-07 | CA-01 → P-045; CA-02 → P-046 | 4 |
| HU-018 | C7 | RN-08 | CA-01 → P-047; CA-02 → P-048; CA-03 → P-049; CA-04 → P-061 | 10 |
| HU-019 | C5 | RN-09, RN-10 | CA-01 → P-050; CA-02 → P-051 | 9 |
| HU-020 | C5 | RN-10 | CA-01 → P-052; CA-02 → P-053; CA-03 → P-054 | 4 |

P-055 verifica un recorrido representativo, sin sustituir las pruebas individuales de las 20 historias. La línea base conserva 64 criterios y 69 protocolos; la auditoría añade ocho protocolos, para un catálogo actual de 77. Las historias conservan 148 horas-persona de estimación histórica.

### 9.1 Antecedentes del cambio de numeración de v1.1 a v1.2

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
| HU-006 | Incorporar una planta del catálogo MSP | HU-003 (v1.1) |
| HU-007 | Gestionar la fotografía de una planta | HU-003 (v1.1) |
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

## 10. Condiciones transversales de verificación de I1

Q1-I1 define el perfil de evaluación formal de rendimiento: 5 huertos, 50 cultivos, 100 actividades sintéticas, 5 sesiones concurrentes, 5 Mbps y 100 ms de latencia. Al 11/11/2026 no se registró la ejecución de P-066 y P-067 sobre ese perfil. Las pruebas automatizadas existentes se ejecutaron en Windows 11 con Edge 157 en la revisión integrada.

Q-I1-01 Seguridad de sesión: transporte HTTPS; cookie de sesión HttpOnly, Secure y SameSite=Lax; protección CSRF en modificaciones; adquisición y renovación del token; separación de rutas privadas y públicas. P-063 y P-064. El token CSRF no se confunde con la cookie de sesión.

Q-I1-02 Consulta textual: percentil 95 de 100 consultas después de 10 de preparación, no mayor de 3 segundos en Q1-I1. Medir hasta contenido utilizable y registrar errores. P-067. Sustituye la frase sin contexto de menos de 2 segundos en arquitectura.

Q-I1-03 Escena: al menos 9 de 10 aperturas con caché vacía permiten interacción en 8 segundos o menos en Q1-I1 y CR1. Rotar, acercar, alejar y seleccionar deben funcionar en los dispositivos registrados. Mostrar clasificación con texto además del color y ofrecer lista si falla WebGL. P-027, P-028 y P-066.

Q-I1-04 Recuperación piloto: realizar y restaurar una copia que incluya fotos e historial; comprobar lectura y nueva escritura en otro entorno y registrar el tiempo. P-068. El corte documental no incluye una ejecución de restauración ni una medición del tiempo de recuperación. La periodicidad operativa y el compromiso de recuperación se establecen en una etapa de operación posterior al piloto académico.

Q-I1-01 a Q-I1-04 definen los protocolos transversales. La seguridad de sesión cuenta con cobertura automatizada. El rendimiento, la recuperación y el uso físico en Android tienen el estado de ejecución descrito en los documentos 04 y 10.

## 11. Datos y decisiones complementarias

D-12 Entorno documentado: Java 21.0.13.0.2, Maven 3.10.0, PostgreSQL 17.11, Windows 11 y Edge 157 en la revisión integrada. El JAR se verificó en una base independiente. La estimación histórica es de 225 horas-persona. El corte no contiene evidencia de alojamiento externo operativo.

D-13 La insignia pública usa siembras vigentes de huertos publicados de la comunidad. P-061 verificó que un huerto privado de la misma comunidad no aporta sustento público. El modelo admite varios huertos por comunidad y líder.

D-14 I1 utiliza una tabla común de recomendaciones y una convención de intervalos por puntos medios. La validación con un referente comunitario no figura en la evidencia de este corte. Las variantes de reglas por comunidad pertenecen al alcance de un incremento posterior.

Las cuentas de demostración no acreditan una designación real por el MSP. Identificar como sintéticos los nombres, huertos, croquis y evidencias que no hayan sido aportados y autorizados por el cliente.

Fixture ampliado: H6 no publicado pertenece a la misma comunidad C1 de H1; contiene una siembra elegible y un líder autorizado definido por cada prueba. No aparece en consultas públicas. C1, líderes, asignaciones y versiones se preparan explícitamente por caso.

P-055 se verificó también con cálculo real y reloj fijo al 31/10/2026. El recorrido usa H1 sin siembras iniciales: 18/10/2026 a las 08:00 y corrección al 03/10/2026 a las 08:00, ambas en America/Guayaquil. Las dos fechas están fuera del tramo de CAL-T y representan condiciones conforme y no conforme respectivamente.

Los registros iniciales E1, K1 o A1 se omiten en los casos cuyo objetivo es crearlos. Cada caso parte de un estado independiente y enumera qué registros existen. Los identificadores lógicos de prueba no son valores de clave primaria obligatorios.


## 12. Precisiones del incremento auditado

- **D-15. Identidad de entrega:** esta edición usa el commit `78d87974152092c2d07125060021d0afdae9dac9` de `develop`; los candidatos locales anteriores son antecedentes y no el identificador actual.
- **D-16. Cuenta desactivada:** el código consulta activo al autenticar; una sesión previamente creada conserva sus permisos hasta su finalización o expiración. La administración y revocación inmediata de sesiones pertenecen a RF-033 y RF-034 del alcance posterior. Esta restricción se aplica al piloto y no se presenta como control de revocación ya implementado.
- **D-17. Insignia:** I1 calcula el estado actual. El motivo de una corrección se conserva en la versión de la siembra. No existe un registro independiente de eventos de otorgamiento y retirada de insignias.
- **D-18. Calendario:** la fase diaria corresponde a las 12:00 continentales y orienta la consulta. La evaluación de un hecho usa su instante real. La pantalla no explica todavía esa diferencia con suficiente precisión; el cambio de comunicación forma parte de la mejora documentada, sin alterar el algoritmo en esta edición.
- **D-19. Campo de ubicación:** el código valida rangos globales de latitud y longitud; no comprueba pertenencia territorial a Ecuador. El ámbito nacional es el del producto y de los datos autorizados del piloto, sin afirmar una validación geográfica no implementada.

## 13. Trazabilidad complementaria

| Prueba | Historia | Requisito | Evidencia | Estado |
|---|---|---|---|---|
| P-070 | HU-008 | RF-025 y RF-027 | A-01 | Aprobado en candidato local |
| P-071 | HU-002 | RF-031 y RNF-018 | A-06 | Aprobado en candidato local |
| P-072 | HU-020 | RF-085 | A-05 | Aprobado en candidato local |
| P-073 | HU-006 y HU-011 | RF-021 y RF-024 | A-03 | Aprobado en candidato local |
| P-074 | HU-009 | RF-026 y AD-10 | A-08 | Aprobado en candidato local |
| P-075 | HU-006 | RF-021, RF-023 y RNF-002 | A-02 | Aprobado en candidato local |
| P-076 | HU-010 | RF-006 | A-04 | Aprobado en candidato local |
| P-077 | HU-010 | RF-005 | Aprobado en candidato local | Aprobado en candidato local |

Los siete fallos se describen en los documentos 04 y 08. El documento 07 conserva los 21 requisitos globales de calidad y su relación con los diez requisitos del perfil de I1.

## 14. Identificadores de fuente conservados en la ERS

| Identificador | Significado |
|---|---|
| U | Aclaraciones del cliente registradas por el equipo. |
| DER | Requisito derivado del análisis de negocio y calidad. |
| BASE | Documentación base de definición del proyecto y aclaraciones. |
| VIS | Documento 01 de visión y alcance. |
| DEF | Definición académica del proyecto Huertos Medicinales Comunitarios. |
| INC | Incidencia reproducida en el código auditado. |
| A-01 a A-09 | Identificadores de los ensayos de la auditoría complementaria; no sustituyen la numeración P de los protocolos. |

[Índice de documentación](README.md)

## 15. Aclaración del cliente del 12/11/2026

| ID | Regla | Enunciado y aplicación |
|---|---|---|
| RN-11 | Autoría de información institucional | El MSP suministra el contenido educativo y su fuente. El líder escogido por la comunidad y autorizado por el MSP selecciona fichas para su huerto y gestiona fotos, cultivos y evidencias. No redacta ni aprueba clínicamente usos, dosis o contraindicaciones. Aplica a HU-006 y HU-007 y al etiquetado futuro RF-055. |
| D-12 | Catálogo inicial | Cuatro fichas de los anexos MSP 2017, versión MSP-2017-I1-1. Se incorporan únicamente datos localizados. No se presupone adaptación a cada región ni seguridad de un tratamiento. |

| Mensaje | Texto y condición |
|---|---|
| MSG-28 | La información educativa proviene del catálogo del MSP. Selecciona una ficha institucional; esos campos no se pueden editar. HTTP 400 ante código inválido o intento de editar contenido institucional. |
| MSG-29 | La zona seleccionada no es una zona de cultivo. HTTP 400 al registrar cultivo en BODEGA, CAMINO o CERCA. |

El catálogo común no concede al líder permisos de administración institucional. Las cuentas de demostración no son prueba de una autorización real del MSP. Una fotografía acredita un registro aportado por el líder, no demuestra eficacia medicinal ni autenticidad automática del hecho.

## 16. Aclaración terminológica del cliente: calendario lunar

La planificación de las siembras toma como referencia el calendario lunar. Las fases calculadas son datos usados para construir esa referencia y comprobar posteriormente hechos registrados. Esta precisión no añade una obligación de usar una agenda persistente ni de demostrar que una persona consultó la pantalla antes de sembrar. Tampoco cambia RN-06, RN-09, RN-10, la política de conservar hechos no conformes o la condición vigente del reto. Esas reglas no se modifican por una sustitución de palabras.

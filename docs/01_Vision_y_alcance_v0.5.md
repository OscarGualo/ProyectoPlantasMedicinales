**ESCUELA POLITÉCNICA NACIONAL**
Ingeniería de Software
CALIDAD DE SOFTWARE (ISWD652) · GR2SW · Período 2026-B

# Documento de visión y alcance

**Huertos Medicinales Comunitarios**
Versión 0.5

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

## 1. Requisitos comerciales

### 1.1 Antecedentes

El Ministerio de Salud Pública del Ecuador (MSP), a través de su enfoque de salud intercultural, promueve desde 2017 la implementación de huertos medicinales con base en los lineamientos de la Dirección Nacional de Salud Intercultural. Esos lineamientos describen cómo preparar la tierra, sembrar, cuidar, cosechar, secar y almacenar las plantas medicinales siguiendo las fases lunares y los conocimientos ancestrales de los pueblos y nacionalidades.

El MSP ha contratado a nuestra startup para construir una aplicación web educativa con gamificación que fomente la creación y sostenibilidad de huertos medicinales comunitarios. Una comunidad de siembra puede ser una persona, una familia, un condominio o un barrio. Cada huerto tiene un líder designado por el Ministerio, responsable de su información y de las evidencias de su avance. Actualmente no existe un medio común donde estas comunidades registren sus prácticas, den a conocer su huerto y se reconozca su participación.

### 1.2 Oportunidad de negocio

- **Marco institucional existente:** los lineamientos del MSP ya promueven los huertos medicinales en el primer nivel de atención. Una aplicación que muestre y dé seguimiento a esos huertos aprovecha una política vigente sin necesidad de crear una nueva.
- **Saberes en riesgo de perderse:** el propio MSP señala que el saber leer las fases lunares para las actividades agrícolas está en decadencia. Registrar y enseñar esas prácticas ayuda a conservarlas.
- **Participación con pocos recursos:** la definición contempla huertos en tierra, verticales, colgantes y en maceteros, botellas o llantas. Personas y familias en zonas urbanas y rurales pueden participar sin gran infraestructura.
- **Motivación por reconocimiento:** retos, insignias y una competencia positiva entre comunidades pueden sostener la participación en el tiempo, que es la principal dificultad de cualquier huerto comunitario.
- **Visibilidad territorial:** un mapa nacional de huertos le permite al MSP y a la ciudadanía conocer dónde existen y cómo están organizados.

### 1.3 Objetivos comerciales

Las metas siguientes son propuestas del equipo para acordar con el MSP. Los plazos se cuentan desde la puesta en marcha del piloto con huertos reales.

| ID | Objetivo |
|---|---|
| OC-1 | Publicar en el mapa al menos 10 huertos comunitarios de al menos 3 provincias durante los primeros 6 meses de operación. |
| OC-2 | Lograr que, en cada uno de los primeros 6 meses de operación, al menos el 70 % de los huertos publicados registre como mínimo una actividad con evidencia fotográfica. |
| OC-3 | Lograr que al menos el 60 % de las siembras registradas en los primeros 6 meses sean conformes con la condición lunar de la práctica. |
| OC-4 | Alcanzar al menos 50 publicaciones de intercambio de saberes entre comunidades distintas en el foro durante los 6 meses posteriores a su habilitación. |

Las metas suponen que el MSP designa a los líderes y entrega la lista de huertos (S-01 y S-02); deben ajustarse cuando confirme cuántos huertos participarán en el piloto.

### 1.4 Métricas de éxito

| Obj. | Métrica | Cómo se mide |
|---|---|---|
| OC-1 | Huertos publicados | Número de huertos con estado publicado y coordenadas válidas al cierre de cada mes, contados por provincia. Fuente: registros del sistema. |
| OC-2 | Huertos activos mensuales | Huertos publicados con al menos una actividad con fotografía registrada en el mes, divididos para los huertos publicados al inicio de ese mes. Fuente: bitácoras del sistema. |
| OC-3 | Conformidad lunar de las siembras | Siembras vigentes conformes divididas para las siembras vigentes evaluadas en el período; las pendientes de comprobación no se cuentan. Fuente: evaluaciones del sistema. |
| OC-4 | Intercambio entre comunidades | Publicaciones y respuestas del foro cuyos autores pertenecen a al menos dos comunidades distintas, por mes. Fuente: registros del foro. |

Las métricas se obtienen del propio sistema y requieren usuarios reales; no se pueden demostrar con la entrega académica. La sostenibilidad de un huerto exige seguimiento posterior y no se considera demostrada por el número de cuentas, fotografías o insignias.

### 1.5 Declaración de visión

Para las comunidades de siembra del Ecuador y los líderes designados por el Ministerio de Salud Pública, quienes necesitan documentar el cuidado de sus huertos medicinales, dar a conocer su trabajo y mantener vivas las prácticas ancestrales del ciclo lunar, **Huertos Medicinales Comunitarios** es una aplicación web educativa con gamificación que permite ubicar los huertos en un mapa del Ecuador, recorrer su estructura en 3D, consultar sus plantas, registrar las actividades del cultivo con evidencia fotográfica conforme al calendario lunar y reconocer la participación de cada comunidad.

A diferencia de los registros en papel, de las redes sociales de uso general y de los materiales impresos de los lineamientos, nuestro producto vincula cada actividad real con el huerto, el cultivo y la práctica ancestral que la sustenta, la hace consultable públicamente y convierte su seguimiento en retos e intercambio de saberes entre comunidades.

### 1.6 Riesgos comerciales

| Riesgo | Impacto y tratamiento |
|---|---|
| Baja adopción por brecha digital o conectividad limitada | Comunidades rurales con poca cobertura podrían no usar la aplicación. Se diseña una web liviana, usable desde celulares y con fotografías de tamaño limitado. |
| Carga excesiva para el líder | Si registrar actividades toma mucho tiempo, el líder abandona el seguimiento. Los registros deben completarse en pocos pasos y validarse con líderes reales. |
| Información medicinal errónea o mal interpretada | Usos o dosis incorrectos podrían afectar la salud de terceros y la reputación del MSP. Toda ficha indica su fuente y la aplicación no genera recomendaciones ni dosis personalizadas. |
| Registros falsos para obtener reconocimientos | La gamificación podría incentivar evidencias inventadas. Solo el líder designado registra, cada evidencia queda en un historial y los reconocimientos se basan en hechos verificables. |
| Imposición de una sola versión de las prácticas | Las prácticas varían entre pueblos y nacionalidades. Se atribuye la fuente de cada regla y se validan las variantes con los líderes de cada comunidad. |
| Exposición de datos personales | Datos de miembros (incluidos menores), fotografías y ubicación podrían usarse indebidamente. Se aplican la LOPDP y la separación entre información pública y privada. |
| Competencia desmotivadora | Un ranking que compare una familia con un barrio puede desalentar a las comunidades pequeñas. Las reglas de puntuación deben ser equitativas antes de publicar el ranking. |
| Falta de continuidad institucional | Sin un responsable después del proyecto, la información queda desactualizada. Se debe acordar con el MSP quién administra la plataforma. |

### 1.7 Supuestos y dependencias

#### Supuestos

- **S-01:** Cada huerto tiene un líder designado por el MSP con acceso, al menos periódico, a un celular con cámara e internet.
- **S-02:** El MSP entrega la lista de líderes y huertos autorizados para crear las cuentas iniciales.
- **S-03:** Cada líder puede proporcionar un croquis de su huerto para preparar su representación 3D.
- **S-04:** Las prácticas lunares de la definición del proyecto y de los lineamientos del MSP representan las de las comunidades participantes, salvo las variantes que indique cada líder.
- **S-05:** El contenido de las fichas de plantas lo aporta y aprueba el líder, citando su fuente.

#### Dependencias

- Lineamientos del MSP «Implementación de huertos medicinales en los establecimientos de salud del primer nivel de atención» (2017): fuente de las prácticas de cultivo, cosecha, secado y almacenaje.
- Ley Orgánica de Protección de Datos Personales (2021): tratamiento de datos de miembros, como fecha de nacimiento y género, fotografías y ubicación; consentimiento y protección de datos de menores.
- Servicio de mapas base con cobertura del Ecuador y su política de uso.
- Algoritmo astronómico de cálculo de fases lunares y zonas horarias del Ecuador continental (UTC-5) y Galápagos (UTC-6).
- Servicio de alojamiento web con almacenamiento para fotografías.

## 2. Alcance y limitaciones

### 2.1 Características principales

| ID | Capacidad | Descripción |
|---|---|---|
| C1 | Localización y consulta pública | Mapa del Ecuador con los huertos publicados y acceso sin cuenta a su información, plantas y estructura. |
| C2 | Representación 3D del huerto | Escena con las partes del huerto (zonas, caminos, cerca, bodega) vinculadas a sus cultivos, con señalética roja para plantas calientes y verde para frías. |
| C3 | Comunidades, huertos, plantas y cultivos | Datos de la comunidad, sus miembros y su huerto real: ubicación, diseño, cerca, superficie, suelo, agua, riego, abono, fichas de plantas y cultivos con su estado. |
| C4 | Roles y autoridad | Líder designado por el MSP, integrante de la comunidad, representante gubernamental y visitante, cada uno con permisos definidos. |
| C5 | Ciclo de cultivo y calendario lunar | Registro de preparación, siembra, cuidados, cosecha y poscosecha con evidencia, conforme al calendario lunar y a los conocimientos ancestrales; historial visual. |
| C6 | Bodega y almacenamiento | Espacios para herramientas, material de siembra y abonos; empaque, etiquetado y conservación de las plantas secas. |
| C7 | Gamificación | Misiones o retos, puntos, insignias, niveles (aprendiz, oficial y maestro), talentos certificados y ranking de comunidades. |
| C8 | Foro de saberes | Publicaciones y respuestas sobre prácticas, saberes y sentires, con moderador y reglas de participación. |

### 2.2 Alcance de la primera versión

La versión 1 se entrega el 13 de noviembre de 2026 y permite recorrer de extremo a extremo el valor central del producto. El detalle está en el plan de la iteración 1 (documento 02) y en sus veinte historias de usuario (documento 03).

- Acceso del líder a su huerto y protección de la información frente a otros usuarios (C4).
- Ficha del huerto con ubicación y características, y fichas de plantas con su fuente y clasificación caliente o fría (C3).
- Cultivos por zona con su estado (C3).
- Mapa público del Ecuador con los huertos publicados (C1).
- Recorrido 3D del huerto preparado por el equipo a partir de su croquis, con selección de zonas y señalética (C2).
- Registro de siembras con fotografía, verificación de la condición lunar y corrección con historial (C5).
- Calendario lunar con las actividades recomendadas para cada fase (C5).
- Reto e insignia *Documentación lunar* para la comunidad (C7).

### 2.3 Alcance de las versiones posteriores

- Miembros de la comunidad con sus datos, talentos certificados y niveles (C3, C7).
- Edición de la estructura 3D por el líder (C2).
- Ciclo de cultivo completo: preparación con espera de 15 días, cuidados, riego, baño, ritual y cosecha según la parte de la planta, limpieza, secado, empaque y etiquetado (C5).
- Bodega y almacenamiento (C6).
- Puntos, misiones, niveles, talentos y ranking de comunidades (C7).
- Foro moderado (C8) y funciones del representante gubernamental (C4).
- Videos en la historia visual y notificaciones, por acordar con el cliente.

### 2.4 Limitaciones y exclusiones

- La aplicación es web y en español. No incluye aplicaciones móviles nativas, funcionamiento sin conexión, sensores ni integración automática con sistemas del MSP.
- El contenido medicinal es educativo y atribuido a su fuente. La aplicación no genera diagnósticos, recetas ni dosis personalizadas; los usos y dosificaciones de las etiquetas se registran tal como los aprueba el líder.
- La escena 3D es esquemática: no se genera a partir de fotografías ni simula el crecimiento biológico de las plantas.
- El estado de un cultivo lo informa el líder; el sistema no lo deduce de fechas ni de fotografías. Una fotografía no prueba por sí sola la autenticidad de un hecho.
- No se incluyen compras, proveedores, préstamos de herramientas, chat ni mensajería privada.

## 3. Contexto comercial

### 3.1 Perfiles de los stakeholders

| Stakeholder | Interés y participación |
|---|---|
| MSP – Dirección Nacional de Salud Intercultural (cliente) | Patrocina el producto, define prioridades y acepta las entregas. En el curso lo representa el docente. |
| Líder de huerto | Designado por el MSP. Mantiene la información de su huerto, registra actividades y evidencias, aprueba el contenido de sus plantas y valida las prácticas de su comunidad. |
| Integrantes de la comunidad | Participan en el cultivo, en los retos y en el foro; consultan los reconocimientos de su comunidad. |
| Visitante y ciudadanía | Descubren los huertos, comprenden su organización y aprenden sobre plantas medicinales y prácticas lunares. |
| Representante gubernamental | Consulta la información de los huertos de su territorio y ejerce las facultades que se acuerden (D-04). |
| Moderador del foro | Hace cumplir las reglas de participación; su designación está pendiente (D-04). |
| Equipo de desarrollo (6 integrantes) | Analiza, estima, construye, prueba y documenta cada incremento; el gestor coordina con el cliente. |

### 3.2 Prioridades del proyecto

| Dimensión | Tipo | Tratamiento |
|---|---|---|
| Fecha | Restricción | Entrega fija el 13/11/2026 a las 09:00. |
| Equipo | Restricción | Seis integrantes con dedicación parcial. |
| Calidad | Impulsor | Pruebas ejecutadas con resultados, integridad de los datos y respeto de la autoridad del líder no se sacrifican. |
| Funcionalidad | Grado de libertad | Se negocia la profundidad de cada capacidad, no su permanencia en el proyecto. |
| Costo | No aplica | Proyecto académico sin presupuesto monetario; se usan herramientas gratuitas. |

En caso de conflicto se prioriza, en este orden: respeto de las prácticas lunares y de la autoridad del líder; consulta pública comprensible (mapa y 3D); registro de actividades con evidencia; y, después, gamificación, foro y almacén. Si el esfuerzo supera la capacidad, se reduce el detalle visual o la automatización antes que retirar una capacidad, y cualquier retiro se acuerda con el cliente.

### 3.3 Consideraciones de implementación

- Los usuarios acceden desde navegadores actualizados en computadores y celulares Android, con conexiones móviles o Wi-Fi de calidad variable.
- La consulta pública no requiere cuenta. El líder accede con usuario y contraseña; las operaciones se autorizan en el servidor.
- Las fotografías se almacenan en el servidor con un límite de 5 MB por archivo.
- El mapa usa un servicio de mapas base con cobertura del Ecuador. La escena 3D debe funcionar en celulares de gama media y ofrecer una lista de zonas y cultivos si no puede cargarse.
- Las fases lunares se calculan en el sistema con la zona horaria de cada huerto.
- Para la entrega, la aplicación se publica en un servicio de alojamiento accesible por internet. El código, la documentación y el README se mantienen en un repositorio GitHub.
- Las tecnologías se eligen al inicio de la iteración 1 y se justifican en el listado de herramientas de la entrega.

## Referencias

- Definición del proyecto «Huertos Medicinales Comunitarios», ISWD652 Calidad de Software, período 2026-B.
- Ministerio de Salud Pública del Ecuador (2017). *Implementación de huertos medicinales en los establecimientos de salud del primer nivel de atención. Lineamientos.* Dirección Nacional de Salud Intercultural.
- Presentación «Huertos Medicinales Comunitarios» y aclaraciones del cliente registradas por el equipo el 29/10/2026.

*Esta versión 0.5 sustituye a las versiones 0.1 a 0.4.*

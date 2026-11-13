# Especificación de requisitos de software

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

Esta especificación consolida la terminología y la relación entre el alcance global del proyecto y el primer incremento construido. Los requisitos describen comportamientos del sistema. Las decisiones de clases, rutas, almacenamiento y bloqueo se documentan en arquitectura.

## 1 Propósito usuarios y alcance

La aplicación educativa permite consultar huertos medicinales del Ecuador y mantener información de un huerto mediante un líder autorizado. I1 cubre las historias HU-001 a HU-020. El proyecto global incluye almacén, comunidad y participantes, ciclo agrícola completo, foro y gamificación ampliada.

La interfaz utiliza español. El visitante accede a consultas públicas y el líder accede a la gestión y a la bitácora de los huertos asignados. El rol gubernamental se conserva en el alcance posterior a I1. Las cuentas del piloto son ficticias y su uso no acredita una autorización real del MSP.

## 2 Identificadores y fuentes

RF identifica requisito funcional, RNF requisito no funcional, HU historia, CA criterio de aceptación, RN regla de negocio, P caso de prueba y AD decisión de arquitectura. Los RF-001 a RF-083 conservan su identidad desde la ERS inicial. Los tres requisitos adicionales RF-084 a RF-086 explicitan el calendario, su filtro y la consulta privada del incremento actual.

Fuentes de dominio: Definición del proyecto del aula, lineamientos MSP de huertos medicinales de 2017, presentación del dominio y aclaraciones del cliente registradas en el proyecto. Los documentos 03, 04 y 05 definen los criterios, los casos y las reglas operativas de I1.

## 3 Requisitos funcionales

La etiqueta I1 identifica el comportamiento incluido en el incremento. Alcance global posterior identifica capacidades del proyecto completo que se planifican en otras entregas. La aceptación de un requisito exige verificar todas sus obligaciones aplicables al incremento. Una referencia a una historia o prueba no acredita por sí sola cobertura completa.

### RF-001 Mostrar los huertos publicados

Al abrir el mapa de Ecuador, el sistema debe mostrar un marcador por cada huerto publicado que tenga una ubicación válida, asociado a su identificador y nombre.

**Aplicación** Iteración 1. **Trazabilidad** HU-010; P-023, P-024.

### RF-002 Consultar la ficha desde el mapa

Cuando una persona seleccione un huerto en el mapa, el sistema debe mostrar la ficha pública de ese huerto y permitir acceder a su representación 3D y a sus cultivos conservando la identidad del huerto seleccionado.

**Aplicación** Iteración 1. **Trazabilidad** HU-011; P-025.

### RF-003 Restringir la consulta de contenido no publicado

Cuando una persona sin autorización solicite una ficha no publicada, incluso mediante su dirección directa, el sistema debe rechazar la consulta sin revelar sus campos privados.

**Aplicación** Iteración 1. **Trazabilidad** HU-011; P-026, P-064.

### RF-004 Tratar las ubicaciones incompletas

Cuando un registro carezca de coordenadas publicables, el sistema debe excluirlo de los marcadores del mapa y comunicar al líder qué información falta para publicar su ubicación.

**Aplicación** Iteración 1. **Trazabilidad** HU-003, HU-005; P-006, P-007, P-011, P-012.

### RF-005 Informar ausencia de huertos

Cuando no existan huertos publicados disponibles para la consulta, el sistema debe mostrar un estado sin resultados que explique esa ausencia.

**Aplicación** Iteración 1. **Trazabilidad** HU-010; P-077; ejecutado en el candidato local; véase documento 10.

### RF-006 Mantener una consulta alternativa al mapa

Cuando el recurso cartográfico no pueda cargarse pero la información del producto esté disponible, el sistema debe ofrecer una lista de los huertos publicados con acceso a sus fichas e informar la indisponibilidad del mapa.

**Aplicación** Iteración 1. **Trazabilidad** HU-010; P-076; corrección local verificada; véase documento 10.

### RF-007 Representar las partes del huerto

Al consultar un huerto con una escena publicada, el sistema debe mostrar su estructura 3D con las partes presentes en el croquis de referencia aprobado, identificando zonas de cultivo, circulación, cerca y bodega cuando existan.

**Aplicación** Iteración 1. **Trazabilidad** HU-012; P-027.

### RF-008 Explorar la escena

Cuando una persona explore una escena 3D disponible, el sistema debe permitir cambiar el punto de vista y acercar o alejar la vista en los dispositivos del perfil de prueba.

**Aplicación** Iteración 1. **Trazabilidad** HU-012; P-027.

### RF-009 Relacionar zonas y cultivos

Cuando una persona seleccione una zona de cultivo de la escena, el sistema debe mostrar los cultivos asociados a esa zona y permitir consultar su especie, estado informado y fecha de actualización.

**Aplicación** Iteración 1. **Trazabilidad** HU-009, HU-013; P-021, P-029.

### RF-010 Conservar la consulta sin escena disponible

Cuando la escena no esté disponible, el sistema debe informar esa condición y mantener una consulta textual de las zonas y cultivos publicados del huerto.

**Aplicación** Iteración 1. **Trazabilidad** HU-012; P-028.

### RF-011 Configurar la estructura del huerto

Cuando el líder autorizado modifique la estructura de su huerto, el sistema debe permitir incorporar, ubicar y editar sus zonas y partes mediante la edición de geometrías de ese incremento, conservando las asociaciones con cultivos que no hayan cambiado.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-012 Impedir referencias espaciales huérfanas

Cuando una modificación de la estructura elimine una zona que tenga cultivos asociados, el sistema debe impedir su confirmación hasta que el líder reasigne esos cultivos o cancele la modificación.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-013 Registrar una comunidad y su huerto

Cuando se dé de alta una comunidad y su huerto, el sistema debe crear identificadores propios y vincular cada huerto con una comunidad y un líder autorizado, sin impedir que una comunidad o un líder tengan varios huertos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-014 Actualizar los datos de la comunidad

Cuando el líder guarde cambios válidos en el nombre, descripción y contacto responsable de su comunidad, el sistema debe conservarlos y mostrarlos al volver a consultar la ficha.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-015 Publicar la ficha del huerto

Cuando el líder confirme la publicación de un huerto con nombre y coordenadas válidos, el sistema debe habilitar la consulta de su ficha, zonas, plantas, cultivos, escena y reto. Debe excluir credenciales, contacto responsable de la comunidad y versiones históricas de fotos.

**Aplicación** Iteración 1. **Trazabilidad** HU-005; P-011, P-012.

### RF-016 Rechazar datos inválidos de la ficha

Cuando el líder intente guardar un nombre vacío, una superficie negativa, una latitud fuera de −90 a 90 o una longitud fuera de −180 a 180, el sistema debe rechazar el cambio, identificar el campo inválido y conservar los valores anteriores.

**Aplicación** Iteración 1. **Trazabilidad** HU-003, HU-004; P-007, P-009, P-010.

### RF-017 Describir las características físicas

Cuando el líder actualice su huerto, el sistema debe conservar nombre, descripción, provincia, parroquia, dirección, coordenadas, superficie, diseño, cerca, suelo, fuente de agua, técnica de riego y abono. La superficie informada debe ser no negativa.

**Aplicación** Iteración 1. **Trazabilidad** HU-004; P-008.

### RF-018 Registrar participantes

Cuando el líder registre una persona participante de su comunidad, el sistema debe conservar su nombre y vínculo comunitario sin exigir una cuenta de acceso. El tratamiento de fecha de nacimiento y género debe documentar finalidad, base de legitimación, visibilidad y protección de menores antes de recopilar esos datos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-019 Actualizar información del participante

Cuando el líder corrija los datos de un participante bajo su responsabilidad, el sistema debe mantener el identificador de la persona y sus relaciones con actividades y reconocimientos, registrando autor y fecha de la modificación.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-020 Finalizar una participación

Cuando el líder dé por terminada una participación, el sistema debe conservar los hechos históricos atribuidos a esa persona e impedir su selección para nuevas actividades de la comunidad desde la fecha de baja.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-021 Incorporar una especie institucional al huerto

Cuando el líder autorizado incorpore una planta a su huerto, el sistema debe exigir la selección de una ficha activa del catálogo suministrado por el MSP, vincular su código y conservar los datos institucionales obtenidos por el servidor con fuente y versión. La fotografía propia del huerto es opcional. El líder no debe ingresar información educativa libre.

**Aplicación** Iteración 1. **Trazabilidad** HU-006 y HU-007; RN-11; P-013 a P-018 y P-078 a P-082.

### RF-022 Actualizar la fotografía propia de una especie

Cuando el líder autorizado actualice una ficha institucional de su huerto, el sistema debe permitir sustituir su fotografía por otra admitida y conservar los datos educativos de su referencia institucional, sin modificar las fotografías de otros huertos ni el estado de los cultivos.

**Aplicación** Iteración 1. **Trazabilidad** HU-006 y HU-007; RN-11; P-013 a P-018 y P-078 a P-082.

### RF-023 Proteger la publicación de información institucional

El sistema debe rechazar los códigos institucionales inexistentes y los intentos del líder de escribir nombres, descripción, usos, dosis, fuente u otros campos educativos. Solo deben publicarse fichas con referencia institucional dentro de un huerto publicado. Los registros anteriores sin referencia deben conservarse en gestión privada, excluidos de la consulta pública y de nuevas asociaciones de cultivos.

**Aplicación** Iteración 1. **Trazabilidad** HU-006 y HU-007; RN-11; P-013 a P-018 y P-078 a P-082.

### RF-024 Consultar información educativa atribuida

Cuando una persona consulte una planta institucional de un huerto publicado, el sistema debe mostrar los datos disponibles con su fuente y versión, fotografía del huerto y enlace al documento de referencia. Debe presentar la clasificación como información tradicional atribuida y mantener ausentes los datos que no constan en la fuente. I1 no proporciona dosificaciones ni recomendaciones médicas personalizadas.

**Aplicación** Iteración 1. **Trazabilidad** HU-006 y HU-007; RN-11; P-013 a P-018 y P-078 a P-082.

### RF-025 Registrar un cultivo concreto

Cuando el líder registre un cultivo, el sistema debe vincularlo con una planta y una zona de tipo CULTIVO de su huerto, asignarle una identidad propia y el estado inicial Sembrado, y conservar las fechas de registro y actualización.

**Aplicación** Iteración 1. **Trazabilidad** HU-008; P-019.

### RF-026 Actualizar el estado observado del cultivo

Cuando el líder informe un estado admitido para un cultivo, el sistema debe conservar ese estado y su fecha de actualización exclusivamente para el cultivo seleccionado.

**Aplicación** Iteración 1. **Trazabilidad** HU-009; P-021, P-022.

### RF-027 Validar la pertenencia del cultivo

Cuando se intente asociar un cultivo con una zona o ficha de especie de otro huerto, o asignarle un estado ajeno al catálogo de RN-03, el sistema debe rechazar la modificación y conservar la asociación válida anterior.

**Aplicación** Iteración 1. **Trazabilidad** HU-008, HU-009; P-020, P-022.

### RF-028 Atribuir la participación en actividades

Cuando el líder asocie participantes con una actividad, el sistema debe registrar cuáles participaron y el papel declarado de cada uno, manteniendo separada esa atribución de la identidad del líder que registró la evidencia.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-029 Iniciar una sesión autorizada

Cuando una persona con cuenta activa entregue credenciales válidas, el sistema debe iniciar su sesión y habilitar exclusivamente las funciones correspondientes a sus roles y asignaciones vigentes.

**Aplicación** Iteración 1. **Trazabilidad** HU-001; P-001, P-056.

### RF-030 Rechazar el acceso inválido

Cuando las credenciales sean incorrectas o la cuenta esté inactiva, el sistema debe rechazar el inicio de sesión y mostrar un mensaje de acceso no válido sin revelar cuál de esos datos causó el rechazo.

**Aplicación** Iteración 1. **Trazabilidad** HU-001; P-002, P-003.

### RF-031 Cerrar la sesión

Cuando una persona cierre su sesión, el sistema debe invalidar su autorización de sesión e impedir que solicitudes posteriores efectuadas con esa sesión ejecuten operaciones protegidas.

**Aplicación** Iteración 1. **Trazabilidad** HU-002; P-004, P-005.

### RF-032 Comprobar permisos por operación y huerto

Ante cada solicitud de gestión de un huerto o sus registros, el sistema debe comprobar el rol y la asignación vigentes de la persona y rechazar toda operación fuera de ese ámbito, aunque se solicite sin utilizar la pantalla prevista.

**Aplicación** Iteración 1. **Trazabilidad** HU-001, HU-016; P-003, P-036, P-043, P-056, P-064.

### RF-033 Registrar la habilitación del líder

Cuando la persona habilitada para administrar designaciones registre una autorización del Ministerio, el sistema debe vincular la cuenta del líder con el ámbito autorizado y conservar la referencia de designación, su vigencia y quién realizó el registro.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-034 Revocar una asignación de liderazgo

Cuando una asignación de liderazgo deje de estar vigente, el sistema debe impedir nuevas operaciones de gestión mediante esa asignación y conservar la autoría histórica de sus registros.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-035 Vincular una cuenta con un participante

Cuando se habilite el acceso de un participante mediante el procedimiento de habilitación de cuentas, el sistema debe vincular su cuenta con su registro de persona sin otorgarle permisos de líder ni atribuirle nuevamente los reconocimientos que ya posee.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-036 Habilitar las consultas gubernamentales del alcance global

Cuando una cuenta con rol gubernamental solicite información restringida en el incremento que habilite ese rol, el sistema debe comprobar un permiso explícito para la operación y el territorio solicitado. Debe rechazar las consultas sin permiso y registrar los accesos a información restringida.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-037 Registrar una siembra con evidencia

Cuando el líder confirme una siembra válida, el sistema debe crear una actividad vinculada a un cultivo de su huerto con fecha y hora del hecho, técnica, descripción, fotografía, autor y fecha y hora de registro.

**Aplicación** Iteración 1. **Trazabilidad** HU-014; P-032.

### RF-038 Validar una siembra y su fotografía

Cuando una siembra tenga una fecha futura, una fotografía que incumpla RN-05 o un cultivo ajeno, el sistema debe rechazar el registro e informar la causa sin alterar el cultivo.

**Aplicación** Iteración 1. **Trazabilidad** HU-014; P-034 a P-037.

### RF-039 Evitar duplicación por reintento

Cuando se reenvíe la misma solicitud de registro de una siembra, el sistema debe devolver el resultado de esa operación sin crear otra actividad; una nueva siembra legítima debe poder registrarse aunque coincida en fecha y especie.

**Aplicación** Iteración 1. **Trazabilidad** HU-014; P-033, P-058, P-059.

### RF-040 Confirmar únicamente registros completos

Cuando falle la conservación de los datos o de la fotografía de una siembra, el sistema debe informar que el registro no se completó y evitar presentarlo como una actividad completa o elegible para reconocimiento.

**Aplicación** Iteración 1. **Trazabilidad** HU-014; P-037, P-065.

### RF-041 Consultar la bitácora del huerto

Cuando el líder consulte las actividades de su huerto, el sistema debe mostrarlas ordenadas por fecha del hecho, con acceso a la evidencia, fecha de registro y revisión vigente de cada actividad.

**Aplicación** Iteración 1. **Trazabilidad** HU-014, HU-017; P-032, P-045, P-046.

### RF-042 Corregir una actividad

Cuando el líder corrija una actividad de su huerto con un motivo informado, el sistema debe guardar una nueva revisión con el contenido corregido, el motivo, autor y fecha, conservando la revisión anterior.

**Aplicación** Iteración 1. **Trazabilidad** HU-016; P-041, P-060, P-069.

### RF-043 Rechazar correcciones inválidas

Cuando una corrección carezca de motivo, incluya una imagen inválida o provenga de una persona no autorizada, el sistema debe rechazarla sin cambiar la revisión vigente, su evaluación lunar ni su reconocimiento.

**Aplicación** Iteración 1. **Trazabilidad** HU-016; P-042 a P-044, P-057.

### RF-044 Determinar la fase aplicable a la siembra

Cuando se consulte una siembra, el sistema debe determinar la fase de la fecha y hora del hecho usando la zona horaria del huerto y los intervalos definidos en RN-09. Debe mostrar la fase y la regla aplicada. La fuente astronómica y su versión se identifican en los documentos 06 y 09.

**Aplicación** Iteración 1. **Trazabilidad** HU-015; P-038 a P-040, P-050, P-062.

### RF-045 Explicar el resultado de la condición lunar

Al evaluar la siembra, el sistema debe mostrar conforme con la condición lunar, no conforme o pendiente de comprobación según RN-06, junto con la regla y la razón del resultado, conservando el hecho registrado en los tres casos.

**Aplicación** Iteración 1. **Trazabilidad** HU-015; P-038 a P-040.

### RF-046 Reevaluar después de una corrección

Cuando cambie la fecha o evidencia de una actividad mediante una corrección válida, el sistema debe actualizar su evaluación y la elegibilidad de los reconocimientos dependientes usando la revisión vigente.

**Aplicación** Iteración 1. **Trazabilidad** HU-016, HU-018; P-041, P-048, P-055.

### RF-047 Evaluar las condiciones del ciclo de cultivo

Cuando se evalúe una actividad del ciclo de cultivo, el sistema debe aplicar las condiciones lunares y culturales aprobadas para su tipo y comunidad, mostrando por condición su resultado, fuente y versión. Las condiciones sin datos suficientes deben quedar pendientes y las basadas en declaración deben identificarse como tales.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-048 Documentar la preparación del terreno

Cuando el líder registre una preparación, el sistema debe conservar zona, fecha, fase, deshierbe, tratamiento de maleza, abono y remoción informados. Al vincularla con la siembra posterior debe comprobar la espera de 15 días prevista en la definición del proyecto y mostrar el intervalo observado.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-049 Documentar el ritual y la técnica de siembra

Cuando el líder documente una siembra completa, el sistema debe conservar la realización declarada y horario del ritual, el horario de siembra y el tipo de propagación. Debe distinguir las condiciones calculadas de las declaradas por el líder y mostrar el criterio cultural y su fuente.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-050 Registrar la señalética del cultivo

Cuando el líder registre la señalética de una zona o cultivo, el sistema debe conservar sus nombres, usos educativos autorizados y clasificación tradicional, incluido el color asociado por la comunidad y una descripción textual equivalente.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-051 Registrar los cuidados del cultivo

Cuando el líder documente un deshierbe, replante, aporque o riego, el sistema debe conservar el tipo de actividad, cultivo, fecha, descripción, técnica y evidencia asociada, distinguiendo la fuente de agua del medio de riego cuando corresponda.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-052 Documentar las actividades previas a la cosecha

Cuando el líder registre el baño previo o el ritual de gratitud, el sistema debe conservar la actividad, fecha, cultivo y evidencia y relacionarla con la cosecha correspondiente para contrastar la secuencia cultural documentada.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-053 Registrar y evaluar una cosecha

Cuando el líder documente una cosecha, el sistema debe conservar cultivo, parte recolectada, fecha, evidencia y condiciones observadas. Debe evaluar creciente o llena para hojas, flores y frutos, y menguante para semillas y raíces, mostrando la regla cultural y su fuente.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-054 Documentar limpieza y secado

Cuando el líder registre limpieza o secado de una cosecha, el sistema debe conservar el vínculo con la cosecha, procedimiento, espacio, fechas de inicio y finalización informadas y condiciones observadas, mostrando el intervalo de secado como referencia y no como garantía automática de aptitud.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-055 Registrar empaquetado y etiqueta

Cuando el líder documente un empaquetado, el sistema debe conservar cosecha de origen, ubicación, fechas informadas de empaquetado y caducidad y etiqueta. La etiqueta debe usar nombres, familia, clasificación, usos, dosificación y contraindicaciones exclusivamente de la referencia autorizada suministrada por el MSP, como contenido de lectura. El líder registra los datos propios del lote, peso y unidad, sin editar contenido medicinal. Si la fuente no aporta un dato, el sistema no debe inventarlo. El sistema no debe calcular dosis personalizadas ni inventar la caducidad.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-056 Consultar el calendario de actividades

Cuando una persona autorizada seleccione un periodo y un huerto, el sistema debe mostrar sus actividades realizadas en las fechas del hecho, permitiendo abrir el detalle y la evidencia autorizada de cada una.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-057 Consultar la historia visual publicada

Cuando una persona consulte la historia visual pública de un huerto, el sistema debe mostrar las evidencias aprobadas para publicación vinculadas a sus actividades y fechas, excluyendo imágenes y datos que mantengan carácter privado.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-058 Adjuntar videos al seguimiento

Cuando el líder adjunte un video admitido por los límites de carga definidos para el incremento de videos, el sistema debe vincularlo con una actividad y someter su consulta a las mismas autorizaciones de publicación que las fotografías.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-059 Describir los espacios de almacenamiento

Cuando el líder registre un espacio de almacenamiento, el sistema debe conservar su ubicación dentro del huerto, finalidad y categorías admitidas: herramientas e implementos, material de propagación, abonos o plantas secas empacadas.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-060 Registrar los recursos guardados

Cuando el líder incorpore un recurso al almacén, el sistema debe conservar su nombre, categoría, descripción y espacio asignado, distinguiendo herramientas, semillas, estacas, esquejes, plántulas y abonos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-061 Registrar condiciones de conservación

Cuando el líder describa un espacio, el sistema debe conservar las condiciones declaradas de seguridad, humedad, luz y ventilación según su finalidad, diferenciando bodega, secado y conservación de plantas empacadas.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-062 Relacionar el material empacado con su ubicación

Cuando el líder asigne una ubicación a un registro de empaquetado, el sistema debe permitir recuperarlo desde el espacio de almacenamiento y consultar su origen y etiqueta.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-063 Consultar existencias por unidad

En una ampliación de inventario cuantitativo, al consultar un recurso el sistema debe mostrar existencia y unidad a partir de sus movimientos válidos, sin sumar cantidades expresadas en unidades incompatibles. Esta ampliación no forma parte del registro básico de espacios y recursos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-064 Registrar entradas y salidas del almacén

En una ampliación de inventario cuantitativo, cuando el líder registre un movimiento el sistema debe conservar recurso, cantidad positiva, unidad, entrada o salida, fecha y motivo. Debe rechazar una salida superior a la existencia disponible.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-065 Consultar caducidad registrada

Cuando el líder consulte material empacado, el sistema debe mostrar su fecha de caducidad registrada y señalar si esa fecha ya transcurrió; si no existe una fecha validada, debe indicar que no está informada.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-066 Consultar el reto inicial

Cuando el líder consulte el reto Documentación lunar, el sistema debe mostrar su objetivo educativo y las condiciones de elegibilidad de RN-08, junto con la explicación de la práctica de siembra que representa.

**Aplicación** Iteración 1. **Trazabilidad** HU-018; P-047 a P-049.

### RF-067 Otorgar la insignia comunitaria inicial

Cuando exista al menos una siembra vigente de la comunidad que cumpla RN-08, el sistema debe mostrar una única insignia activa Documentación lunar, vinculada con la evidencia que sustenta su elegibilidad.

**Aplicación** Iteración 1. **Trazabilidad** HU-018; P-047, P-061.

### RF-068 Actualizar la vigencia de la insignia

Cuando una corrección cambie la elegibilidad de una siembra, el sistema debe mantener activa la insignia si queda otra siembra elegible de la comunidad y desactivarla únicamente si no queda ninguna, conservando el motivo de la corrección en el historial de la siembra. En I1 la insignia se calcula al consultar; no se registra un evento independiente de otorgamiento o retirada.

**Aplicación** Iteración 1. **Trazabilidad** HU-016, HU-018; P-048, P-055.

### RF-069 Presentar misiones y sus condiciones

Cuando un participante consulte una misión vigente, el sistema debe mostrar su objetivo, destinatarios, periodo, condiciones de cumplimiento, evidencia requerida y recompensa según la versión de reglas autorizada.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-070 Asignar puntos trazables

Cuando una actividad cumpla una condición de puntuación aprobada, el sistema debe registrar los puntos para el destinatario previsto con referencia a la actividad y versión de regla, aplicando los límites de repetición definidos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-071 Determinar el nivel del participante

Cuando cambie la información que acredita la progresión de un participante, el sistema debe determinar su nivel aprendiz, oficial o maestro mediante las condiciones aprobadas y permitir consultar la razón del nivel alcanzado.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-072 Registrar el reconocimiento de un talento

Cuando el responsable autorizado de evaluar un talento confirme su cumplimiento, el sistema debe registrar talento, evidencias, evaluador y fecha. Debe distinguir el reconocimiento interno de una certificación profesional u oficial externa.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-073 Mostrar la clasificación entre comunidades

Cuando se consulte el ranking, el sistema debe ordenar las comunidades elegibles conforme a una regla aprobada, mostrando periodo, criterio de comparación, posición y tratamiento de empates.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-074 Rectificar efectos de una evidencia corregida

Cuando una corrección invalide una evidencia usada en reconocimientos, el sistema debe recalcular los puntos, nivel, talento y posición afectados conforme a la regla aplicable, conservando la explicación del ajuste.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-075 Consultar el progreso propio

Cuando un participante autenticado consulte su progreso, el sistema debe mostrar sus misiones, puntos, insignias, nivel y talentos con los hechos que los sustentan, distinguiéndolos del estado biológico de los cultivos.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-076 Consultar el foro

Cuando una persona con permiso de lectura acceda al foro, el sistema debe mostrar los temas y respuestas publicados dentro del ámbito de publicación definido para el foro, identificando autor visible, comunidad y fecha conforme a la política de publicación.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-077 Proponer una publicación

Cuando un participante habilitado envíe una publicación, el sistema debe conservar título, contenido, autor y comunidad y mantenerla sin publicación hasta que el líder de esa comunidad modere su publicación. Esa moderación no autoriza a sustituir las fichas institucionales ni a validar clínicamente indicaciones medicinales; el contenido medicinal de referencia procede del MSP.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-078 Proponer una respuesta

Cuando un participante habilitado responda a un tema disponible, el sistema debe vincular la respuesta con ese tema y conservar su autoría, sometiéndola al mismo proceso de aprobación del contenido comunitario.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-079 Aprobar el contenido comunitario del foro

Cuando el líder revise una contribución de su comunidad, el sistema debe permitir aprobarla para publicación o rechazarla con un motivo, conservando quién tomó la decisión y cuándo.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-080 Moderar una contribución publicada

Cuando la persona designada como moderadora actúe sobre una contribución dentro de su ámbito, el sistema debe permitir retirarla de consulta pública registrando motivo, regla aplicada, autor de la moderación y fecha.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-081 Consultar las reglas de participación

Antes de enviar contenido al foro, el sistema debe permitir consultar las reglas de participación vigentes y la identificación del rol responsable de moderarlas.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-082 Revisar cambios en contenido aprobado

Cuando el autor modifique una contribución ya aprobada, el sistema debe conservar la versión publicada y someter la modificación a una nueva aprobación, sin reemplazar públicamente el contenido hasta que se apruebe.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-083 Emitir avisos de actividades

En el incremento que incorpore actividades planificadas y notificaciones, el sistema debe emitir avisos vinculados con actividad, destinatario autorizado y fecha prevista.

**Aplicación** Alcance global posterior a I1. **Trazabilidad** Capacidad global descrita en visión y alcance. Su implementación y sus casos corresponden a otra entrega.

### RF-084 Consultar el calendario lunar

Para apoyar la elección de fechas de siembra y otras labores según las prácticas documentadas, el sistema debe mostrar un calendario lunar de 30 días por defecto con la fase al mediodía de Ecuador continental, las recomendaciones por fase y los eventos astronómicos del rango. Debe rechazar consultas fuera de los doce meses anteriores o posteriores a la fecha actual. Trazabilidad HU-019, P-050 y P-051.

### RF-085 Filtrar el calendario por actividad

El sistema debe permitir seleccionar una actividad agrícola y resaltar los días recomendados para ella. Al retirar el filtro debe mostrar el calendario sin resaltados de actividad. Trazabilidad HU-020, P-052 a P-054.

### RF-086 Consultar la gestión privada

El sistema debe permitir al líder consultar su sesión y los datos, plantas, zonas y cultivos de su huerto de demostración, incluso si está sin publicar. La API autoriza las asignaciones del modelo; la interfaz de I1 utiliza una sola asignación por cuenta. Debe rechazar la consulta privada de un huerto ajeno. Trazabilidad HU-001, P-056.

## 4 Requisitos no funcionales de I1

| ID | Requisito | Verificación |
|---|---|---|
| RNF-I1-01 | Interfaz, instrucciones y errores en español. | Recorridos de consulta y gestión. |
| RNF-I1-02 | Autorización de huerto en el servidor y protección CSRF de modificaciones. | P-003, P-005, P-056, P-063. |
| RNF-I1-03 | Datos privados y fotografías históricas con visibilidad restringida. | P-026, P-061 y P-064. |
| RNF-I1-04 | Guardado atómico de actividad, versión y evidencia. | P-037 y P-065. |
| RNF-I1-05 | Reintento idéntico sin duplicados y corrección sin sobrescribir el historial. | P-033, P-058 a P-060 y P-069. |
| RNF-I1-06 | Consulta alternativa textual cuando la escena no pueda cargarse. | P-028. |
| RNF-I1-07 | Evidencia JPEG o PNG válida con tamaño máximo de 5 242 880 bytes. | P-015 y P-034. |
| RNF-I1-08 | Percentil 95 de 100 consultas textuales, después de diez de preparación, de hasta tres segundos en Q1-I1. | P-067, sin ejecución formal registrada. |
| RNF-I1-09 | Nueve de diez aperturas frías con interacción 3D en hasta ocho segundos en Q1-I1. | P-066, sin ejecución formal registrada. |
| RNF-I1-10 | Recuperar datos, fotos e historial desde una copia en un entorno independiente. | P-068, ejecutado en el candidato local; véase documento 10. |

Q1-I1 define cinco huertos, cincuenta cultivos, cien actividades, cinco sesiones concurrentes, cinco Mbps y cien milisegundos de latencia. Los umbrales describen los protocolos de evaluación. Los resultados registrados se encuentran en los documentos 04 y 10.

## 5 Interfaces datos y restricciones

La API usa JSON y multipart para fotografías. Las fechas de hechos se ingresan en la zona del huerto y se convierten a un instante. La base conserva PostgreSQL y migraciones Flyway. El sistema recibe un UUID por envío y una versión base por corrección. Las fotografías y el historial forman parte del mismo modelo de persistencia.

## 6 Verificación y alcance de aceptación

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

P-027 no cuenta con ejecución en Android físico y contraste con un croquis real aprobado. P-066, P-067 y P-068 carecen de medición formal de rendimiento y recuperación. P-063 conserva cobertura parcial del entorno HTTPS de entrega. No se dispone de evidencia de validación comunitaria de los intervalos lunares ni de alojamiento externo operativo. La construcción de la imagen Docker sí fue satisfactoria en GitHub Actions del último PR integrado.

## 7 Requisitos globales de calidad conservados

Esta sección mantiene los identificadores RNF-001 a RNF-021 de la ERS global. Español es una condición confirmada. Los umbrales y políticas globales conservan su naturaleza de propuestas de calidad; su presencia no atribuye una aprobación del cliente ni cumplimiento por el piloto. El perfil Q1-I1 y los diez RNF-I1 describen específicamente la verificación de esta iteración.

### RNF-001. Idioma de la interfaz

El sistema debe presentar en español la navegación, instrucciones, confirmaciones y errores dirigidos a las personas usuarias. Los nombres botánicos y tradicionales se conservarán como datos de contenido.
Verificación: inspeccionar los recorridos de cada capacidad entregada, incluidos errores. Fuente: U. Aplicación: todas las entregas.

### RNF-002. Errores comprensibles

Cuando rechace un formulario, el sistema debe identificar los campos afectados y explicar qué debe corregirse, conservando los valores válidos que no sean secretos. Los mensajes públicos deben omitir trazas, nombres internos y credenciales.
Verificación: provocar cada validación del incremento y comprobar causa, acción de corrección y conservación de datos. Fuente: DER y BASE. Propuesta transversal.

### RNF-003. Acceso mediante teclado

Las funciones de consulta textual, formularios, inicio y cierre de sesión deben poder operarse mediante teclado, con foco visible y sin quedar atrapado en un control. Las zonas y cultivos de la escena deben tener acceso textual equivalente.
Verificación: completar los recorridos sin ratón y comprobar el orden de foco. Fuente: propuesta de calidad. La alternativa textual no elimina RF-007.

### RNF-004. Ampliación del contenido

Al ampliar al 200 % el texto o la vista del navegador de la ejecución, el contenido textual y los controles deben conservarse legibles y operables, sin superponerse ni ocultar acciones necesarias.
Verificación: revisar fichas, formularios, resultados y errores con ampliación. Fuente: propuesta de calidad.

### RNF-005. Adaptación al ancho de pantalla

Las vistas textuales y formularios deben permitir completar sus tareas con anchos de 360 y 1280 píxeles de vista sin perder campos ni acciones por recorte horizontal. La escena y el mapa deben ofrecer controles utilizables dentro del espacio disponible.
Verificación: ejecutar los recorridos en ambos anchos de la matriz de dispositivos registrada en la evidencia. Fuente: propuesta de calidad.

### RNF-006. Validación de comprensión de las tareas

En una sesión acordada con cinco personas de referencia, al menos cuatro deben localizar un huerto y abrir la información de un cultivo sin ayuda del desarrollador. Dos líderes de referencia deben completar registro y recuperación de una siembra sin errores que les impidan finalizar.
Verificación: observar tareas, ayudas, errores y resultado individual. Fuente: metas propuestas en VIS. Es una validación de uso con una muestra, no prueba de adopción nacional ni de aprendizaje sostenido.

### RNF-007. Tiempo de consultas textuales

Con el perfil Q1, el percentil 95 del tiempo de las consultas de fichas, especies y bitácora debe ser como máximo 3 segundos, medido desde la solicitud de la persona hasta que el contenido textual queda disponible para interacción.
Verificación: medir al menos 100 consultas válidas distribuidas entre esas funciones, después de 10 consultas de preparación; registrar también errores y tiempos máximos. Fuente: umbral propuesto. No incluye la descarga inicial de una escena ni la carga de una fotografía.

### RNF-008. Carga inicial de una escena

Con el perfil Q1 y la escena de referencia identificada por versión y dispositivo, el percentil 95 del tiempo desde la apertura hasta disponer de una vista 3D interactiva debe ser como máximo 8 segundos.
Verificación: realizar 30 aperturas con caché de recursos de escena vacía, registrando escena, tamaño, dispositivo y errores. Fuente: umbral propuesto. No se extiende el resultado a escenas de complejidad desconocida.

### RNF-009. Protección de la comunicación

Las operaciones de acceso, consulta y modificación en el entorno publicado deben transmitirse por HTTPS con certificado válido para el dominio utilizado.
Verificación: inspeccionar el canal de cada operación y comprobar que la transmisión de credenciales no ocurre por una conexión sin protección. Fuente: propuesta de calidad.

### RNF-010. Protección de secretos

El producto debe impedir recuperar contraseñas en texto legible desde su almacenamiento y omitir contraseñas, credenciales de operación y datos completos de sesión en pantallas públicas, registros de diagnóstico y repositorio distribuido.
Verificación: revisar almacenamiento y salidas de prueba con secretos ficticios, incluida una búsqueda de esos valores en registros y archivos distribuidos. La técnica concreta se documentará en arquitectura. Fuente: propuesta de calidad.

### RNF-011. Inactividad de sesión

El sistema debe invalidar una sesión protegida tras 30 minutos continuos sin actividad de la persona y exigir autenticación para la siguiente operación protegida. La consulta pública debe continuar disponible.
Verificación: probar una operación antes del límite y otra después, con reloj controlado. Fuente: parámetro propuesto. No equivale a limitar toda sesión a 30 minutos desde su inicio.

### RNF-012. Limitación de intentos fallidos

Después de cinco intentos fallidos de autenticación de una misma cuenta en una ventana de 15 minutos, el sistema debe impedir nuevos intentos de esa cuenta durante 15 minutos, sin revelar públicamente si la cuenta existe.
Verificación: probar el quinto intento, un intento durante la restricción y otro al terminar el periodo. Fuente: política propuesta. Debe acordarse recuperación y tratamiento del bloqueo indebido antes de habilitar cuentas reales.

### RNF-013. Separación de información pública y privada

La consulta sin autorización debe omitir contactos privados, datos personales de miembros, evidencias no publicadas e historial privado, incluso cuando se soliciten por sus identificadores o direcciones directas.
Verificación: comparar respuestas públicas y autorizadas con datos de prueba distinguibles. Fuente: RN-02 y BASE. Complementa la comprobación de permisos de RF-032.

### RNF-014. Registro de operaciones relevantes

El sistema debe conservar autor o identidad de intento, instante, objeto afectado, operación y resultado de cambios de fichas, actividades, revisiones, asignaciones y moderaciones que estén implementados, así como de inicios de sesión fallidos, sin incorporar secretos al registro.
Verificación: ejecutar una operación válida y otra rechazada por cada tipo entregado y contrastar sus registros. Fuente: propuesta derivada de trazabilidad; conservación y acceso sujetos a las autorizaciones del producto.

### RNF-015. Consistencia de una modificación

Cada operación de guardado debe conservar conjuntamente los datos necesarios para su resultado o indicar que no se completó, manteniendo la última versión íntegra. Un reintento de la misma operación no debe duplicar actividades ni recompensas.
Verificación: interrumpir el guardado de datos y evidencias en puntos controlados y consultar el resultado; comprobar RF-039 y RF-040. Fuente: BASE y regla propuesta transversal.

### RNF-016. Copias de recuperación

En el entorno operativo definido para la ejecución se propone conservar una copia recuperable de los datos y evidencias con una antigüedad máxima de 24 horas. La copia debe incluir las relaciones necesarias para recuperar revisiones y reconocimientos.
Verificación: revisar antigüedad y contenido de la copia seleccionada. Fuente: objetivo propuesto. La periodicidad técnica y los recursos de almacenamiento se decidirán en diseño.

### RNF-017. Restauración comprobable

Desde la declaración de un incidente simulado y con la copia válida disponible, el producto debe permitir restablecer su consulta y gestión en el entorno de recuperación acordado en un máximo de cuatro horas, conservando la correspondencia entre registros y fotografías.
Verificación: realizar una restauración en un entorno separado, medir el tiempo y ejecutar una consulta y un registro representativos. Fuente: objetivo propuesto. Este requisito exige un procedimiento documentado y probado.

### RNF-018. Resultado incierto por pérdida de conexión

Cuando se pierda la respuesta de una operación enviada, el sistema debe informar que su resultado no se ha confirmado y permitir recuperar el resultado mediante consulta o reintento de la misma operación, sin presentar automáticamente éxito o fracaso definitivo.
Verificación: perder la respuesta después de conservar una siembra y comprobar su recuperación sin duplicación. Fuente: análisis de RF-039. Un fallo confirmado de almacenamiento sigue el tratamiento de RF-040.

### RNF-019. Compatibilidad acordada

Cada incremento debe permitir completar sus recorridos aceptados en dos navegadores de escritorio y un navegador móvil de la matriz de dispositivos identificada en la evidencia, con sus versiones registradas, incluidos los controles 3D previstos en RF-008.
Verificación: ejecutar el recorrido integrado y los casos de presentación pertinentes en cada combinación. Fuente: propuesta de calidad. Una alternativa textual no permite declarar superada la prueba del 3D en un dispositivo incompatible.

### RNF-020. Configuración reproducible

Las reglas, fuentes lunares y parámetros operativos usados en una entrega deben estar identificados por versión y acompañados de instrucciones que permitan preparar el mismo entorno de demostración a partir del repositorio y los recursos autorizados.
Verificación: una persona del equipo distinta de quien preparó el entorno debe reconstruirlo siguiendo las instrucciones y comprobar la configuración activa. Fuente: necesidad académica y propuesta de mantenimiento.

### RNF-021. Alcance de la información presentada

La aplicación debe identificar la procedencia del contenido medicinal, el carácter informado del estado del cultivo y el alcance de cada evaluación o reconocimiento, evitando presentar una condición lunar o una insignia como certificación de eficacia sanitaria, aprendizaje o realización física de todo el procedimiento.
Verificación: revisar fichas, evaluación lunar, estados y reconocimientos con ejemplos que muestren sus límites. Fuente: visión y alcance, RN-03, RN-06, RN-08 y criterios de información educativa.

La versión académica no compromete un porcentaje de disponibilidad ni soporte nacional permanente. Las metas globales propuestas de recuperación no constituyen un acuerdo de disponibilidad.

### Perfil global Q1

El perfil global propuesto utiliza 100 huertos, 1 000 cultivos, 5 000 actividades, 20 sesiones concurrentes, 5 Mbps y 100 ms de latencia. No se ha medido en esta entrega. El piloto Q1-I1 utiliza 5 huertos, 50 cultivos, 100 actividades y 5 sesiones; ambos perfiles se mantienen diferenciados. El objetivo global de carga 3D P95 sobre 30 aperturas no se considera equivalente al protocolo piloto de 9 aperturas satisfactorias sobre 10.

## 8 Control de continuidad de requisitos de calidad

| Requisito global | Relación actual | Tema | Estado documentado |
|---|---|---|---|
| RNF-001 | RNF-I1-01 | Idioma | Conservado en I1 |
| RNF-002 | P-075 e INC-06 | Errores de validación | Incidencia abierta |
| RNF-003 a RNF-006 | P-027 y recorridos de uso | Accesibilidad y usabilidad | Validación global sin ejecución completa |
| RNF-007 | RNF-I1-08 / P-067 | Consulta textual | Perfil piloto separado; sin medición formal |
| RNF-008 | RNF-I1-09 / P-066 | Carga de escena | Umbral piloto distinto; sin medición formal |
| RNF-009 | P-063 | HTTPS | Sin evidencia del dominio publicado |
| RNF-010 | Arquitectura AD-03 | Protección de secretos | Hash de contraseña implementado; no equivale a una auditoría integral |
| RNF-011 | Configuración de sesión | Inactividad | Sin prueba formal del límite global |
| RNF-012 | Alcance operativo posterior | Intentos fallidos | Política global no implementada en I1 |
| RNF-013 | RNF-I1-03 / P-064 | Privacidad | Matriz API ampliada satisfactoria |
| RNF-014 | Historial y alcance posterior | Auditoría de operaciones | I1 conserva versiones de siembra; no auditoría general |
| RNF-015 | RNF-I1-04 y RNF-I1-05 | Integridad y reintento | Escenarios de escritura aprobados |
| RNF-016 y RNF-017 | RNF-I1-10 / P-068 | Copia y restauración | Protocolo piloto sin ejecución; metas globales no comprometidas |
| RNF-018 | P-071 e INC-01 | Resultado incierto | Incidencia abierta en cierre de sesión |
| RNF-019 | P-027 | Compatibilidad | Escritorio probado; Android físico sin ejecución |
| RNF-020 | Manual y evidencias | Reproducción | Revisión de código y entorno identificados |
| RNF-021 | Fichas, condición e insignia | Alcance educativo | No acredita eficacia sanitaria ni aprendizaje |

## 9 Verificación complementaria de los requisitos funcionales

| Protocolo | RF o RNF relacionado | Historia | Estado |
|---|---|---|---|
| P-070 | RF-025 y RF-027 | HU-008 | Aprobado en candidato local |
| P-071 | RF-031 y RNF-018 | HU-002 | Aprobado en candidato local |
| P-072 | RF-085 | HU-020 | Aprobado en candidato local |
| P-073 | RF-021 y RF-024 | HU-006 y HU-011 | Aprobado en candidato local |
| P-074 | RF-026 y AD-10 | HU-009 | Aprobado en candidato local |
| P-075 | RF-021, RF-023 y RNF-002 | HU-006 | Aprobado en candidato local |
| P-076 | RF-006 | HU-010 | Aprobado en candidato local |
| P-077 | RF-005 | HU-010 | Aprobado en candidato local |

## 10 Interfaces y límites de datos de I1

La API intercambia JSON y utiliza multipart para fotografías. Los errores de negocio tienen codigo, mensaje y campo cuando corresponde. Las consultas públicas excluyen credenciales y contacto responsable. Las fechas del hecho se reciben como fecha local del huerto y se conservan como instantes; los reintentos utilizan una clave UUID y las correcciones incluyen versión base.

| Dato | Límite del modelo | Observación de verificación |
|---|---|---|
| Nombre común | 120 caracteres | Dato del catálogo; el líder no lo escribe. P-075 verifica rechazo HTTP 400 de nombre libre |
| Nombre científico | 150 caracteres | Longitud definida en el modelo |
| Familia | 120 caracteres | Solo cuando consta en la fuente; lectura |
| Fuente | 300 caracteres | Obligatoria |
| Descripción de planta o siembra | 2 000 caracteres | Conservación según el formulario y modelo |
| Distribución | 500 caracteres | Lectura cuando existe; presentación verificada en P-073 con respuesta simulada |
| Altitud | 120 caracteres | Lectura cuando existe; presentación verificada en P-073 con respuesta simulada |
| Motivo de corrección | 500 caracteres | Obligatorio |
| Superficie | NUMERIC(10, 2), no negativa | Modelo y validaciones de huerto |
| Foto | JPEG o PNG; hasta 5 242 880 bytes | Formato validado por contenido |
| Coordenadas | Latitud -90 a 90 y longitud -180 a 180 | No constituye validación territorial de Ecuador |

## 11 Estado de reglas y autoridad en el piloto

La cuenta debe estar activa para iniciar sesión. Cada operación de gestión comprueba la asignación del huerto en el servidor. La desactivación administrativa de una cuenta no revoca sus sesiones abiertas en la implementación auditada; RF-033 y RF-034 conservan la administración y revocación en el alcance posterior. La regla lunar común del piloto no se presenta como una variante comunitaria aprobada.

[Índice de documentación](README.md)

## 10. Contrato de incorporación institucional de I1

POST /api/huertos/{huertoId}/plantas y PUT /api/plantas/{plantaId} reciben multipart: catalogoCodigo y foto opcional. El servidor verifica el huerto y copia la información del catálogo; la interfaz no determina su contenido. Los parámetros educativos libres se rechazan con MSG-28. GET /api/publico/catalogo-plantas solo consulta. No se ofrece operación de escritura del catálogo para el líder.

La documentación conserva 86 RF y 20 HU. RF-021 a RF-024 y HU-006/007 cambian su contrato por la aclaración expresa del cliente; no se conserva el antiguo editor libre como comportamiento aceptable. Los detalles físicos de V4 y la carga inicial se describen en arquitectura y en el documento 13.

## 12. Separación entre planificar y registrar

RF-084 y RF-085 apoyan la planificación mediante consulta y filtro del calendario lunar. RF-037 conserva una siembra ya realizada y RF-044 y RF-045 comprueban su fecha y hora del hecho. El calendario no representa el crecimiento de las plantas y una consulta no equivale a una actividad ejecutada. La referencia diaria al mediodía continental no sustituye la comprobación del instante real de una siembra. La precisión del cliente no modifica los intervalos culturales implementados, cuya validación comunitaria mantiene el estado declarado en RN-09.

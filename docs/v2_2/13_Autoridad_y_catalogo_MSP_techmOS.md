# Autoridad y procedencia de la información institucional

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión:** 2.2.1 · 12/11/2026  
**Base:** develop, 78d87974152092c2d07125060021d0afdae9dac9; candidato local de corrección.

## 1. Regla confirmada por el cliente

La comunidad escoge al líder del huerto y el Ministerio de Salud Pública lo autoriza. El líder mantiene la información operativa del huerto, selecciona y publica la información institucional que le corresponde y registra fotografías y avances. El MSP suministra el contenido verificado de las plantas y sus referencias. La autorización del líder para gestionar el huerto no lo convierte en autor ni revisor clínico de ese contenido.

## 2. Permisos de I1

| Información | Quién la suministra | Acción del líder |
|---|---|---|
| Nombre y datos operativos del huerto | Comunidad y líder | Registrar y actualizar su propio huerto |
| Especie, clasificación y contenido educativo | Catálogo trazado a anexos MSP | Seleccionar y consultar; no editar |
| Fotografía de la planta en el huerto | Líder | Incorporar o sustituir imagen admitida |
| Cultivo, zona y estado informado | Líder | Registrar en zona CULTIVO y actualizar estado |
| Siembra, fecha, técnica y evidencia | Líder | Registrar y corregir con motivo e historial |
| Usos, dosis y contraindicaciones | Fuente autorizada que suministre el MSP | Lectura si se incorporan en una entrega posterior; no redactar ni adaptar |

El alta administrativa y la autorización real de las cuentas no se tramitan en I1. Las cuentas de demostración son ficticias. La interfaz no permite que un visitante se autoproclame líder ni se autorice por sí mismo.

## 3. Catálogo inicial trazado

| Código | Planta | Información incorporada | Referencia de los lineamientos 2017 |
|---|---|---|---|
| MSP-MANZANILLA | Manzanilla | Presencia en anexo 3; clasificación tradicional CALIENTE en anexo 5 | Páginas impresas 66 y 74 |
| MSP-TORONJIL | Toronjil | Presencia en anexo 3; clasificación tradicional CALIENTE en anexo 5 | Páginas impresas 66 y 74 |
| MSP-RUDA | Ruda | Listado de plantas calientes del anexo 5 | Página impresa 74 |
| MSP-HIERBA-LUISA | Hierba luisa | Cymbopogon citratus; familia Poaceae | Anexo 2.1, página impresa 63 |

Fuente: [MSP, Lineamientos de implementación de huertos medicinales en establecimientos del primer nivel de atención, 2017](fuentes/MSP-Lineamientos-2017.pdf). La numeración impresa difiere en una página del índice físico del PDF. La versión de carga es MSP-2017-I1-1.

Para manzanilla, toronjil y ruda, la carga inicial no atribuye nombres científicos o familias que no constan en las páginas incorporadas. No se copian usos terapéuticos, dosis ni contraindicaciones. El conjunto inicial es limitado; no se presenta como transcripción completa de todos los anexos, comprobación clínica del equipo ni garantía de que una especie pueda cultivarse en cualquier provincia.

## 4. Preservación de registros anteriores

V4 conserva identificadores y relaciones de plantas, cultivos, fotos, actividades e historial. Las plantas de demostración cuyo nombre coincide con las cuatro fichas se vinculan y reciben su contenido institucional. Esa correspondencia limitada no sustituye una revisión taxonómica de datos reales. Los registros sin correspondencia permanecen en gestión privada y se excluyen de publicación y de nuevas asociaciones hasta una conciliación institucional.

La migración sustituye textos educativos anteriores sin fuente por el contenido trazado del catálogo. Antes de aplicarla en una base con datos que deban conservarse se requiere un respaldo: la igualdad comprobada en fotos, cultivos e historial no significa que esos textos anteriores permanezcan publicados. V1, V2 y V3 conservan su contenido y checksums.

## 5. Alcance y arquitectura

El monolito modular React–Spring Boot–PostgreSQL se conserva. CatalogoMsp se agrega al paquete plantas, V4 añade el catálogo y su clave en planta, y las solicitudes envían catalogoCodigo en lugar de textos libres. No se añade un microservicio ni un editor clínico. La interfaz muestra fuente, versión y documento de referencia. Las instantáneas educativas se actualizan mediante cambios institucionales controlados; no hay propagación automática ni editor del catálogo en I1.

HU-006 y HU-007 cambian por la aclaración del cliente. RF-021 a RF-024, RN-11 y P-013 a P-018 expresan ese contrato. RF-055 mantiene el futuro etiquetado con datos de lote propios y contenido medicinal institucional de lectura. Mapa, 3D, luna, registro de siembras, evidencia, correcciones e insignia permanecen en el alcance de I1.

[Índice](README.md)

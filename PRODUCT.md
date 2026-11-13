# Product

<!-- impeccable:product-schema 1 -->

## Platform

web

## Users

- **Visitante y ciudadanía** (prioridad visual del producto, confirmada el 03/11/2026): cualquier persona,
  sin cuenta, que quiere descubrir dónde existen huertos medicinales comunitarios en el Ecuador, entender
  cómo está organizado un huerto y aprender sobre plantas medicinales y prácticas lunares. Usa sobre todo el
  celular, en conexiones móviles o Wi-Fi de calidad variable.
- **Líder de huerto**: persona escogida por su comunidad y autorizada por el Ministerio de Salud Pública (MSP) como responsable de un
  huerto. Mantiene la ficha del huerto, sus plantas y cultivos, y registra las actividades con evidencia
  fotográfica. Trabaja desde un celular Android de gama media, a veces en el propio huerto. Registrar debe
  tomar pocos pasos o abandona el seguimiento (doc 01 §1.6).
- **Integrante de la comunidad**: en la versión 1 no tiene cuenta; consulta el calendario lunar y el reto de
  su comunidad como visitante.
- **MSP – Dirección Nacional de Salud Intercultural**: cliente; acepta las entregas (en el curso lo
  representa el docente).

## Product Purpose

Aplicación web educativa con gamificación que fomenta la creación y sostenibilidad de huertos medicinales
comunitarios: ubica los huertos en un mapa del Ecuador, permite recorrer su estructura en 3D, consultar sus
plantas, registrar las actividades del cultivo con evidencia fotográfica conforme al calendario lunar y
reconocer la participación de cada comunidad. El éxito se mide con huertos publicados, huertos activos cada
mes, siembras conformes con la condición lunar e intercambio entre comunidades (doc 01 §1.3–1.4).

## Positioning

Vincula cada actividad real con el huerto, el cultivo y la práctica ancestral que la sustenta (fase lunar,
clasificación caliente o fría), la hace consultable públicamente y convierte su seguimiento en retos. A
diferencia de registros en papel, redes sociales generales o los materiales impresos del MSP, conserva
saberes del ciclo lunar que el propio MSP señala en decadencia.

## Operating Context

- Lineamientos del MSP «Implementación de huertos medicinales en los establecimientos de salud del primer
  nivel de atención» (2017): preparar, sembrar, cuidar, cosechar, secar y almacenar según las fases lunares.
- Comunidades de siembra: persona, familia, condominio o barrio; huertos en tierra, verticales, colgantes, en
  maceteros, botellas o llantas; zonas urbanas y rurales de las tres regiones y Galápagos.
- Prácticas: luna creciente o llena para sembrar; menguante para cosechar semillas y raíces; luna oscura para
  preparar la tierra (RN-10). Clasificación andina caliente (rojo) y fría (verde).
- Versión 1 entregada el 13/11/2026; equipo académico de 6 personas (ISWD652, EPN).

## Capabilities and Constraints

- Versión 1: acceso del líder, ficha y publicación del huerto, fichas de plantas, cultivos con estado, mapa
  público, recorrido 3D esquemático, registro y corrección de siembras con fotografía, condición lunar,
  calendario lunar y reto *Documentación lunar* (HU-001 a HU-020).
- Fuera de la versión 1: almacén, foro, miembros, puntos, ranking, niveles, talentos, editor 3D y funciones
  del representante gubernamental.
- Web en español; sin app nativa ni funcionamiento sin conexión. Fotos JPEG o PNG de hasta 5 MB.
- Los mensajes al usuario son los del catálogo MSG-01 a MSG-23 (doc 05 §6 y doc 03), con texto exacto.
- El contenido medicinal es educativo y atribuido a su fuente: la aplicación nunca genera diagnósticos,
  recetas ni dosis.

## Brand Commitments

- Nombre: «Huertos Medicinales Comunitarios».
- El MSP no exige identidad visual propia (confirmado el 03/11/2026): hay libertad para la identidad del
  producto. La paleta verde vigente (primario `#15803D`) y las tipografías Lora y Noto Sans, elegidas con
  ui-ux-pro-max, son la marca actual del producto.

## Evidence on Hand

- Datos de demostración ficticios e identificados como tales (`backend/src/main/resources/db/migration/V2__demo.sql`).
- No existen fotografías propias de huertos, plantas ni personas. Decisión confirmada: usar láminas
  botánicas y fotografías de colecciones abiertas con licencia libre y crédito visible, más dibujos propios
  (por ejemplo, el croquis del huerto). Nunca se presentan como fotos del huerto, la planta o la comunidad
  de un líder.
- No existen testimonios, cifras de uso, prensa ni logos de aliados: no se inventan.

## Product Principles

1. El saber ancestral se muestra con su fuente: nada medicinal sin atribución.
2. Lo público se entiende en cinco segundos desde un celular, con una mano y mala conexión.
3. Registrar un hecho del huerto toma pocos pasos; el sistema nunca bloquea el registro por la condición lunar.
4. La autoridad es del líder escogido por la comunidad y autorizado por el MSP: la interfaz nunca sugiere que otros puedan modificar un huerto.
5. Reconocer sin competir de forma injusta: los retos celebran hechos verificables.

## Accessibility & Inclusion

- WCAG 2.2 AA como mínimo: contraste 4.5:1, objetivos táctiles de 44 px, foco visible, errores junto al campo.
- Público con alfabetización digital variable, en zonas urbanas y rurales: lenguaje simple, sin jerga técnica.
- Celulares de gama media y conectividad variable: páginas livianas, imágenes de tamaño limitado,
  alternativa en lista cuando la escena 3D no carga.
- Respetar `prefers-reduced-motion`.

## Procedencia de las fichas

La información educativa se selecciona desde el catálogo MSP de lectura. El líder gestiona la fotografía, los cultivos y las evidencias de su huerto, sin editar nombres, clasificación, usos, dosis ni contraindicaciones. El contrato vigente se encuentra en [la documentación 2.2](docs/v2_2/README.md).

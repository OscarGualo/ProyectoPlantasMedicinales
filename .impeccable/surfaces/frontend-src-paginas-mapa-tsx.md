---
version: 1
slug: "frontend-src-paginas-mapa-tsx"
primary_target: "frontend/src/paginas/Mapa.tsx"
related_targets: ["frontend/src/paginas/FichaHuerto.tsx"]
---

# Consulta pública: mapa y ficha de huerto

Modo: Read. El visitante quiere saber dónde hay huertos medicinales y qué se cultiva en uno. Prioridad visual del
producto (confirmada). La gestión del líder (Operate) hereda este mundo con sobriedad.

Audiencia y tarea: ciudadanía sin cuenta, en celular y conexión variable; encontrar un huerto y entender su
organización, sus plantas (con fuente y clasificación caliente o fría) y sus cultivos por zona.

Restricciones: ui-ux-pro-max manda en accesibilidad (4.5:1, 44 px, foco, errores junto al campo, movimiento
reducido) y en la paleta verde de marca; taste-skill fija reglas sin ornamento (sin degradados, sombras en reposo,
vidrio, antetítulos ni etiquetas versales espaciadas); emil-design-eng fija el movimiento. Imágenes: láminas
botánicas de colecciones abiertas con crédito; nunca presentadas como fotos de un huerto.

## Direction contract

THESIS: Cada huerto y cada planta se presentan como la etiqueta oficial de la planta seca de los lineamientos del
MSP: datos verificables primero, compuestos en campos apretados. Rechaza la app de mapas genérica de tarjetas con
sombra, íconos en cuadros teñidos y degradados suaves.

OWN-WORLD: Campo verde profundo de la bolsa (#14532d) que ocupa el primer tercio de cada pantalla pública;
etiquetas de papel claro verdoso (#f4f8f3) troqueladas con esquinas recortadas; tinta verde casi negra (#0f2417);
sellos circulares de tinta para estados: caliente (#c2410c), fría (#047857), sin clasificar (gris), y ámbar para
fechas de cosecha. Archivo variable: ancho 62 en títulos y nombres, ancho 100 en texto. Campos agrupados por bandas
de tono, nunca por líneas finas. Botones de esquina recta.

STORY: El visitante ve primero cuántos huertos hay y dónde; toca uno y recibe su etiqueta: quién lo cuida, cómo está
hecho, qué plantas guarda con su fuente y qué crece en cada zona. Cree que es información verificable y atribuida.

FIRST VIEWPORT: Mapa (inicio): barra plana con la marca y tres enlaces; debajo, el campo verde a todo el ancho con
«Huertos medicinales del Ecuador» en Archivo condensado de 48 a 112 px en dos líneas, una frase de apoyo y los
sellos de conteo (huertos, provincias); el mapa entra como la ventana de la etiqueta, solapando el borde inferior
del campo verde. Ficha: el nombre del huerto en el campo verde, la etiqueta de datos en dos columnas y la acción
«Recorrer en 3D» como botón principal a la vista.

FORM: Etiqueta de empaque de planta seca, candidato 6 de 7 de la lista propia (herbario, almanaque lunar,
calendario agrícola andino, puesto del mercado, lámina escolar, etiqueta del MSP, faja tejida). Subidas: el estado es
una marca (mesa de corte); sin cajas ni divisores (cátodo); densidad honesta en dos columnas (hoja volante); una sola
retícula para todas las pantallas (retícula suiza). seed:274c09eaa12365bc

FINISH: unreviewed and undocumented is unfinished; this build ends with the finish review, the verdict, DESIGN.md, and every shipping raster carrying its provenance

## Movimiento (emil-design-eng)

Curva de salida fuerte cubic-bezier(0.23, 1, 0.32, 1); presión 0.97 en 160 ms; hover solo con puntero fino;
entrada escalonada de etiquetas 50 ms; firma: el sello «cae» una vez al entrar su etiqueta (escala 1.08 a 1 con
opacidad, 220 ms). Con movimiento reducido se conservan los fundidos de opacidad y se elimina el desplazamiento.

## Decisiones abiertas

- Láminas botánicas: catálogo por nombre científico para las especies de demostración; las demás plantas sin foto
  muestran su sello y sus datos.

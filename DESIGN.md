---
name: Huertos Medicinales Comunitarios
description: Cada huerto y cada planta, compuestos como la etiqueta de la planta seca del MSP.
colors:
  bolsa: "#14532d"
  bolsa-profunda: "#0e3f22"
  sobre-bolsa: "#f4f8f3"
  sobre-bolsa-suave: "#c9dccd"
  papel: "#f4f8f3"
  fondo: "#e7efe8"
  banda: "#dde8de"
  banda-fuerte: "#cbdccd"
  tinta: "#0f2417"
  tinta-suave: "#2c4334"
  accion: "#15803d"
  accion-hover: "#116c33"
  enlace: "#0f5c2d"
  sello-caliente: "#b4320f"
  sello-fria: "#046c4e"
  sello-neutro: "#56605a"
  sello-ambar: "#8a4b06"
  error: "#a3200f"
  error-fondo: "#fbe9e5"
  exito: "#0f5c2d"
  exito-fondo: "#dcefe0"
  info: "#1d3d5c"
  info-fondo: "#e2ecf5"
  campo-borde: "#8fa996"
  papel-lamina: "#f1f5f1"
typography:
  display:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "clamp(2.375rem, 8.6vw, 6rem)"
    fontWeight: 800
    lineHeight: 0.98
    letterSpacing: "-0.02em"
    fontVariation: "'wdth' 62"
  headline:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "clamp(1.75rem, 3.4vw, 2.25rem)"
    fontWeight: 800
    lineHeight: 0.98
    letterSpacing: "-0.02em"
    fontVariation: "'wdth' 62"
  title:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "1.75rem"
    fontWeight: 800
    lineHeight: 1.02
    fontVariation: "'wdth' 62"
  lead:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "1.125rem"
    fontWeight: 400
    lineHeight: 1.55
  body:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "1rem"
    fontWeight: 400
    lineHeight: 1.55
    fontFeature: "'tnum' 1"
  label:
    fontFamily: "Archivo Variable, system-ui, sans-serif"
    fontSize: "0.9375rem"
    fontWeight: 600
    lineHeight: 1.3
rounded:
  control: "2px"
  sello: "4px"
  chaflan: "12px"
spacing:
  "1": "4px"
  "2": "8px"
  "3": "12px"
  "4": "16px"
  "5": "24px"
  "6": "32px"
  "7": "48px"
  "8": "72px"
components:
  button-primary:
    backgroundColor: "{colors.accion}"
    textColor: "#ffffff"
    typography: "{typography.label}"
    rounded: "{rounded.control}"
    padding: "8px 24px"
    height: "48px"
  button-primary-hover:
    backgroundColor: "{colors.accion-hover}"
  button-on-bolsa:
    backgroundColor: "{colors.sobre-bolsa}"
    textColor: "{colors.bolsa}"
    rounded: "{rounded.control}"
    height: "48px"
  button-secondary:
    backgroundColor: "transparent"
    textColor: "{colors.accion}"
    rounded: "{rounded.control}"
    height: "48px"
  input:
    backgroundColor: "{colors.papel}"
    textColor: "{colors.tinta}"
    rounded: "{rounded.control}"
    height: "48px"
    padding: "8px 12px"
  etiqueta:
    backgroundColor: "{colors.papel}"
    textColor: "{colors.tinta}"
  campo-de-etiqueta:
    backgroundColor: "{colors.banda}"
    textColor: "{colors.tinta}"
    padding: "12px 16px"
---

# Design System: Huertos Medicinales Comunitarios

## Overview

**Creative North Star: «La etiqueta de la planta seca».** Cada huerto y cada planta se presentan como la etiqueta
oficial de empaque que piden los lineamientos del MSP (2017): datos verificables primero, compuestos en campos
apretados sobre papel claro, dentro del campo verde de la bolsa. La identidad está en la escala de la tipografía
condensada, en el verde que ocupa el primer tercio de cada pantalla pública y en los sellos de tinta que marcan
estados.

El sistema rechaza la app de mapas genérica: nada de tarjetas con sombra, íconos en cuadros teñidos, degradados
suaves, antetítulos ni vidrio. Las pantallas públicas (mapa y ficha) son de lectura; las de gestión del líder son
de trabajo y heredan el mundo con sobriedad.

Origen: dirección asignada por `impeccable concept-seed` (seed:274c09eaa12365bc) y elegida por el usuario el
03/11/2026; reglas de oficio de taste-skill; piso de accesibilidad de ui-ux-pro-max; movimiento de emil-design-eng.

## Colors

Estrategia **Committed**: el verde de la bolsa domina el primer tercio de las pantallas públicas, la cabecera de
gestión y el pie; el resto es papel y bandas de tono.

### Primary
- **Verde bolsa** (`bolsa`): campo de la bolsa, barra, pie. Texto encima siempre en `sobre-bolsa` (8.9:1).
- **Verde acción** (`accion`): botones primarios. **Verde enlace** (`enlace`) para enlaces y estados activos de
  navegación, porque `accion` sobre `banda` no alcanza 4.5:1.

### Neutral
- **Papel de etiqueta** (`papel`) sobre la **mesa** (`fondo`); los campos se agrupan con **bandas** (`banda`).
- **Tinta** (`tinta`) para todo texto corrido; **tinta suave** (`tinta-suave`, 8.4:1 sobre banda) solo para nombres
  de campo y notas.

### Named Rules
- **La regla del sello:** caliente `sello-caliente`, fría `sello-fria`, sin clasificar `sello-neutro`, sembrado
  `sello-ambar`; siempre con su texto, nunca solo color.
- **La regla de la tinta llena:** el texto corrido nunca va en gris ni en `sobre-bolsa-suave`.
- **Modo oscuro diseñado aparte:** la bolsa se vuelve la noche del huerto (`#0b2a17`); cada par se verificó por
  separado, no se invierte.

## Typography

Una sola familia, **Archivo Variable**, con dos anchos: 62 % (condensado de etiqueta impresa) para títulos,
nombres y cifras; 100 % para leer.

### Hierarchy
- **Display** (96 px máx.): título de la bolsa; dos líneas como máximo a todo ancho.
- **Headline** (36 px): encabezados de sección y cifras de conteo.
- **Title** (28 px): nombre de un huerto o una planta en su etiqueta.
- **Lead** (18 px): bajada bajo el título; marca.
- **Body** (16 px, cifras tabulares): texto y valores de campo.
- **Label** (15 px, 600): nombres de campo, notas, pies de lámina.

### Named Rules
- Seis pasos y no más. Sin versalitas espaciadas: los nombres de campo van en tipo oración.

## Layout

Una retícula y un par de márgenes (`clamp(16px, 4vw, 40px)`, ancho máximo 1180 px) para barra, contenido y pie.
El campo de la bolsa sangra a todo el ancho con unidades de contenedor (`cqw`) para no contar la barra de
desplazamiento. En el inicio, el mapa entra como ventana de la etiqueta y solapa 120 px el borde del campo verde.
Más espacio sobre un encabezado (72 px) que debajo (16 px). Celular primero; barra inferior de navegación por debajo
de 768 px; la barra superior solo es fija en escritorio.

## Elevation & Depth

Plano. Las superficies se separan por **pasos de tono** (fondo → papel → banda), nunca por sombras ni líneas finas.
La única sombra es la del globo del mapa, porque flota de verdad.

## Shapes

- **Etiqueta troquelada:** rectángulo con las cuatro esquinas recortadas 12 px (`clip-path` de ocho vértices).
- **Controles de esquina recta** (2 px). Sellos con borde de 2 px y esquina de 4 px, girados −2°; el sello redondo
  (76 px, doble anillo) marca la clasificación de cada planta, girado −12°.

## Components

### Buttons
Planos, de un color, 48 px de alto, presión `scale(0.97)` en 160 ms. Sobre la bolsa se invierten: papel con texto
verde. El secundario es transparente con borde de 2 px.

### Etiqueta (firma)
Papel troquelado con cabeza (nombre en Title y una línea de contexto) y **campos** en dos columnas (cuatro en
escritorio) separados por bandas de 2 px. En la lista de huertos, toda la etiqueta es el enlace.

### Sellos
Marcas de estado con texto: clasificación caliente o fría y estado del cultivo. Al entrar su etiqueta, el sello cae
una vez (escala 1.12 → 1, 220 ms).

### Inputs / Fields
Los campos de formulario sí llevan borde (2 px, `campo-borde`): es la convención del control. Error debajo del campo
con `aria-describedby`, foco en el primer campo inválido, fondo `error-fondo`.

### Navigation
Barra plana en el verde de la bolsa: marca, tres destinos con ícono y texto, indicador inferior de 4 px. En
celular, barra inferior en papel con el destino activo en banda y franja superior. Cerrar sesión es un botón aparte.

### Lámina botánica
Lámina de colección abierta que ilustra la **especie**, con pie que lo dice y enlace a la fuente; nunca se presenta
como foto del huerto. La fotografía propia del líder siempre tiene prioridad.

### Ilustración generada (ampliación del 12/11/2026)
Para que las pantallas no sean solo texto, el mundo admite **ilustraciones grabadas generadas con IA** (Higgsfield)
con estas reglas:
- **Un solo estilo:** grabado en cobre coloreado a mano, al estilo de Köhler (1887), sobre el papel claro
  `papel-lamina` (`#f1f5f1`, igual en ambos modos: la lámina es un objeto físico). Cada generación usa la lámina real
  de *Matricaria* como referencia de estilo.
- **Honestidad:** cada imagen dice en su pie «ilustración generada con IA» (o se acredita en el pie de página si es
  decorativa) y figura en `frontend/public/laminas/credits.json` con `ia: true` y el trabajo de origen. Nunca se
  presenta como lámina histórica ni como foto de un huerto; la foto del líder sigue teniendo prioridad.
- **Revisión botánica a mano** antes de publicar: nombre científico bien escrito y sin familia, firma ni texto
  inventado. Las láminas que fallan se regeneran, no se retocan.
- **Dónde:** láminas de especie (catálogo `laminaDe`), lámina del inicio (solo escritorio), lámina del ingreso (solo
  ≥ 768 px), cabecera del calendario, grabados de las cuatro fases lunares, viñetas de los estados vacíos (`Vacio`
  con `vineta`).
- **Forma:** siempre sobre papel troquelado (chaflán de 12 px, 8 px en viñetas) o recortadas en círculo (lunas).
  Sin sombras, sin degradados, sin vidrio.
- **Movimiento:** la «lámina viva» (video corto en bucle, mudo) solo con póster fijo, botón de pausa de 44 px y sin
  reproducción con `prefers-reduced-motion`.
- **Peso:** WebP, ≤ 180 KB a tamaño completo, con versión de la mitad del ancho en `srcset`, `width`/`height`
  declarados.

## Do's and Don'ts

### Do:
- Componer cada huerto, planta o registro como su etiqueta: datos completos en campos.
- Marcar estados con sellos de texto y color.
- Acreditar cada lámina en su pie y en el pie de página.
- Usar movimiento corto y con propósito: curva `cubic-bezier(0.23, 1, 0.32, 1)`, bajo 300 ms, hover solo con puntero
  fino, y fundidos breves en lugar de desplazamientos con `prefers-reduced-motion`.

### Don't:
- Antetítulos o etiquetas pequeñas sobre un título.
- Sombras en reposo, degradados, vidrio o desenfoque decorativo.
- Líneas finas o cajas de 1 px para separar; usar bandas de tono.
- Íconos dentro de cuadros teñidos, emoji o tarjetas de ícono, título y texto como estructura.
- Texto corrido en gris o en verde suave.

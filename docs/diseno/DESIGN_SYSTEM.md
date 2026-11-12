# Sistema de diseño propuesto (A y B)

> Fase 5 de `DESIGN_WORKFLOW.md`. **Propuesta:** no reemplaza `DESIGN.md` hasta que el usuario elija una dirección.
> Los tokens se escriben como CSS custom properties, igual que hoy (`frontend/src/estilos.css:14-120`). Todos los
> pares de contraste se calcularon con la fórmula WCAG 2.x el 12/11/2026.

## Lo común a A y B (no cambia con la dirección)

- **Espacio:** ritmo de 4/8 (`--e-1` 4 px … `--e-8` 72 px). Márgenes de `clamp(16px, 4vw, 40px)` y ancho máximo de
  1180 px.
- **Objetivos táctiles:** ≥ 44 px, con 8 px entre controles.
- **Foco visible:** anillo de 3 px con contraste ≥ 3:1 contra su fondo.
- **Sellos de clasificación:** caliente y fría siempre con su texto, nunca solo color (`DESIGN.md`, «La regla del
  sello»).
- **Estados obligatorios** en cada componente de datos: carga (esqueleto), vacío (con viñeta), error (texto MSG
  exacto) y éxito.
- **Movimiento:** curvas y duraciones de `MOTION_GUIDELINES.md`; `prefers-reduced-motion` es obligatorio.
- **Imágenes:** WebP con `srcset`, `width`/`height` y un pie que dice qué es (y si es IA).

---

## A: «La etiqueta de la planta seca», evolucionada

### A.1 Tokens de color

Los tokens actuales se mantienen. Se añaden dos y se verifican así:

| Token | Claro | Oscuro | Par verificado | Contraste |
|---|---|---|---|---|
| `--bolsa` / `--sobre-bolsa` | `#14532d` / `#f4f8f3` | `#0b2a17` / `#e9f2ea` | Texto sobre bolsa | 8,49:1 · 13,52:1 |
| `--tinta` / `--papel` | `#0f2417` / `#f4f8f3` | `#e9f2ea` / `#13241a` | Texto corrido | 15,23:1 · 14,17:1 |
| `--accion` / blanco | `#15803d` / `#ffffff` | `#5fd18a` / `#04210f` | Botón primario | 5,02:1 · 8,92:1 |
| `--enlace` / `--banda` | `#0f5c2d` / `#dde8de` | — | Enlace sobre banda | 6,44:1 |
| `--sello-caliente` / `--papel` | `#b4320f` | `#ff9b7a` | Sello | 5,75:1 |
| `--sello-fria` / `--papel` | `#046c4e` | `#6fdcb2` | Sello | 6,00:1 |
| **`--papel-lamina`** (ayer) | `#f1f5f1` | igual | Fondo de las láminas | Tinta: 14,84:1 |
| **`--ocre`** (nuevo) | `#7a4a0c` | `#e0b56a` | Notas de herbario sobre `--papel-lamina` | 6,78:1 |

### A.2 Tipografía

| Rol | Familia | Medida | Nota |
|---|---|---|---|
| Display, encabezado, nombre | Archivo Variable al 62 % de ancho, 800 | 96 / 36 / 28 px | Sin cambios |
| Entrada, cuerpo, nota | Archivo Variable al 100 %, 400/600 | 18 / 16 / 15 px | Cifras tabulares |
| **Latín y pie de lámina** (nuevo) | **Newsreader**, itálica 400/500 | 16–18 px | Solo nombres científicos, pies y citas de fuente. `@fontsource/newsreader`, subconjunto latino |

La escala se mantiene en **seis pasos**: Newsreader no agrega pasos, solo cambia la voz del latín.

### A.3 Forma y superficie

- La etiqueta troquelada (chaflán de 12 px), los controles de 2 px y los sellos de 4 px se mantienen.
- **Pliego de herbario** (nuevo): papel de lámina, con la lámina alineada arriba a la izquierda y un **rótulo**
  abajo a la derecha (borde de tinta de 2 px, fondo de papel) que lleva el nombre, el latín, la familia, el ciclo y
  la fuente. El sello redondo va arriba a la derecha.
- **Grano de papel:** opcional y ≤ 4 % de opacidad, solo sobre `--papel` y la bolsa. Se apaga con
  `prefers-contrast: more`.

### A.4 Componentes clave

| Componente | Hoy | Propuesta A |
|---|---|---|
| Etiqueta de huerto (lista) | Solo texto y campos | + **tira de 3 láminas** de sus especies arriba. Si no tiene plantas, va la viñeta «semillas» y el texto «aún no registra plantas» |
| Etiqueta de planta | Lámina a la izquierda y campos | **Pliego de herbario** con su rótulo |
| Sello | Borde de 2 px girado | Sin cambios |
| Botón | Plano, 48 px, `scale(.97)` | Sin cambios |
| Campo | Borde de 2 px, error abajo | Sin cambios |
| Vacío | Viñeta grabada | Sin cambios |
| Pie de página | Título gigante en todas las rutas | **Compacto en `/gestion/*`** (solo créditos, en una línea) |
| Cajón de especímenes (nuevo) | — | Rejilla de láminas del mismo tamaño, con nombre y latín |

---

## B: «Minga: el cartel comunitario»

### B.1 Tokens de color

| Token | Claro | Oscuro | Par verificado | Contraste |
|---|---|---|---|---|
| `--crudo` (fondo) / `--tinta` | `#eadfc8` / `#1d1611` | `#16130f` / `#eadfc8` | Texto corrido | 13,52:1 · 14,01:1 |
| `--papel` (tarjeta) / `--tinta` | `#f6f0e3` / `#1d1611` | `#221d17` / `#eadfc8` | Texto en tarjeta | 15,74:1 |
| `--tinta-suave` / `--crudo` | `#4a3b2f` | `#cbbfa8` | Notas y nombres de campo | 8,12:1 |
| `--maiz` (capítulo) / `--tinta` | `#e1ac10` / `#1d1611` | igual | Texto sobre maíz | 8,60:1 |
| `--tierra` (capítulo) / `--crudo` | `#472913` / `#eadfc8` | igual | Texto sobre tierra | 9,96:1 |
| `--anil` (capítulo, enlace) / `--papel` | `#1f3a5f` / `#f6f0e3` | `#8fb3e0` sobre noche | Texto sobre añil; enlace sobre crudo 8,69:1 | 10,11:1 |
| `--chakra` (capítulo) / `--papel` | `#2f5a2e` / `#f6f0e3` | igual | Texto sobre chakra | 7,05:1 |
| `--caliente` / `--papel` | `#a12a0c` | `#ff9b7a` sobre noche | Sello (texto blanco sobre el sello: 6,48:1 invertido) | 6,48:1 · 9,01:1 |
| `--fria` / `--papel` | `#1d5c3a` | `#7fd6a4` sobre noche | Sello | 6,99:1 · 10,63:1 |
| Acción principal | `--tinta` sobre `--papel` (píldora negra) | `--maiz` con texto `--tinta` | Botón | 15,74:1 · 8,91:1 |

Pares oscuros verificados aparte (no se invierte el claro):
- crudo sobre papel oscuro `#221d17`: 12,65:1;
- tinta suave oscura `#cbbfa8` sobre noche `#16130f`: 10,19:1;
- añil de enlace oscuro `#8fb3e0` sobre noche: 8,55:1;
- texto papel sobre sello caliente: 6,48:1;
- texto papel sobre sello frío: 6,99:1.

En A, el ocre oscuro `#e0b56a` sobre papel oscuro `#13241a` da 8,48:1.

**Regla de capítulos.** Cada página tiene como máximo **tres** capítulos de color (por ejemplo: maíz, crudo y
tierra). La caliente y la fría **nunca** se usan como color de capítulo. En `/gestion/*` no hay capítulos: solo
crudo, papel y tinta (la variante sobria).

### B.2 Tipografía

| Rol | Familia | Medida | Nota |
|---|---|---|---|
| Display | **Bricolage Grotesque** variable, 800, `opsz` 96 | `clamp(3rem, 10vw, 7.5rem)` | Tracking de −0,03 em, interlínea de 0,95 |
| Encabezado de sección | Bricolage 800 | `clamp(2rem, 5vw, 3.5rem)` | — |
| Nombre (huerto, planta) | Bricolage 800 | 28–30 px | — |
| Entrada y cuerpo | **Public Sans** 400/600 | 19 / 16 px | Interlínea de 1,55 |
| Nota y nombre de campo | Public Sans 700 | 14 px | Tipo oración, sin versalitas |
| Latín | Public Sans itálica 400 | 16 px | — |

Ambas son gratuitas (SIL OFL) y se autoalojan con `@fontsource-variable/bricolage-grotesque` y
`@fontsource-variable/public-sans`. Archivo se retira.

### B.3 Forma y superficie

- **Borde de tinta de 3 px** en tarjetas, tablas y campos: es el filo del taco de grabado. Reemplaza las bandas de
  tono de A.
- **Píldoras** (radio de 999 px) para botones, filtros y sellos de estado.
- **Círculos de tinta** para las cifras (sello de goma, 64 px).
- **Tarjetas** de esquina recta y día del calendario con radio de 14 px.
- **Sin sombras, sin degradados, sin vidrio.** Las tintas son planas.

### B.4 Componentes clave

| Componente | Hoy | Propuesta B |
|---|---|---|
| Etiqueta de huerto (lista) | Solo texto y campos | **Cartel**: franja superior del color de su capítulo, nombre grande, especies como píldoras con borde y campos en tabla de tinta |
| Etiqueta de planta | Lámina y campos | Cartel con **linograbado** a la izquierda. Si falta, la **inicial** de la planta sobre maíz |
| Sello | Borde de 2 px girado | **Píldora llena** (caliente o fría) con texto en papel; el estado del cultivo, píldora con borde |
| Botón | Plano y recto | Píldora negra (o maíz en capítulos oscuros) |
| Campo | Borde de 2 px | Borde de tinta de 3 px, radio de 4 px |
| Cifra | Número condensado | Círculo de tinta de 64 px con el número en maíz |
| Vacío | Viñeta grabada | Viñeta de linograbado (B-05) sobre crudo |
| Filtro del calendario | `<select>` | Fila de píldoras (radio), accesible con teclado |
| Pie de página | Verde bolsa | Tinta negra con el nombre en maíz; compacto en `/gestion/*` |

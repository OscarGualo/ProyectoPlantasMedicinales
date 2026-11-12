# Experiencias Three.js / WebGL (A y B)

> Fase 7 de `DESIGN_WORKFLOW.md`. Regla: **no hay WebGL sin justificación**. Hoy existe una sola experiencia 3D, el
> recorrido del huerto (`/huertos/:id/3d`, HU-013, P-027 a P-031, P-066). Esa es la que se mejora. No se propone
> WebGL para el inicio en ninguna de las dos direcciones (la justificación está al final).

## Estado actual

- **Pila:** `three` 0.187 con `@react-three/fiber` y `OrbitControls` (`Escena3D.tsx:1-4`). Usa `frameloop="demand"`,
  `dpr` de 1 a 1,5 y `powerPreference: low-power`: buena base para la gama media.
- **Geometría** generada desde las zonas guardadas como datos (AD-04):
  - un terreno de caja;
  - una caja por zona;
  - cultivos como un cilindro más una esfera del color de la señalética;
  - etiquetas en `CanvasTexture`.
- **Problemas de la auditoría:**
  - P1: no se lee como un huerto.
  - P2: las etiquetas son diminutas a 390 px.
  - P2: la cámara no guía.
- **Presupuesto que hay que respetar:**
  - P-066: la escena abre en < 8 s en gama media (hoy la mediana preliminar en escritorio es unos 2,4 s).
  - Alternativa en lista si WebGL falla (P-028).
  - El módulo `Escena3D` pesa unos 943 KB sin comprimir (unos 249 KB gzip) y se descarga solo en esta ruta.

## Mejoras comunes a A y B (independientes de la estética)

| Mejora | Detalle técnico | Costo de rendimiento |
|---|---|---|
| **Cámara guiada** | Al elegir una zona (botón o toque), se interpola `controls.target` y `camera.position` hacia el centro de la zona en 600 ms con `--curva-movimiento`. Con reduced-motion, salto directo. «Ver todo» vuelve al encuadre inicial | Despreciable (solo cuadros a demanda durante la interpolación) |
| **Etiquetas legibles** | Escalar las etiquetas de zona por distancia de la cámara (tamaño mínimo en px) o usar `Html` de drei con texto del DOM (accesible) para la zona seleccionada | Bajo |
| **Luz de cielo** | Sumar `hemisphereLight` (cielo y suelo) a la `ambientLight` y la `directionalLight` actuales: da volumen sin sombras | Casi nulo |
| **Sombras falsas** | Un disco oscuro semitransparente bajo cada planta, en lugar de `shadowMap` | Casi nulo |
| **Plantas por especie** | Silueta de bajo polígono por familia (gramínea en manojo, roseta, arbusto). Instanciadas con `InstancedMesh` si hay más de 20 | Bajo (< 2000 triángulos por escena) |

No se usa post-procesado (bloom, SSAO): es caro en gama media y no aporta lectura.

## A: maqueta de herbario

- **Texturas** (A-02): tierra de camellón, césped, grava y cerca viva en grabado coloreado. WebP de 512 px sin
  costura, con `RepeatWrapping`, unos 60 KB cada una y unos 240 KB en total, cargadas con `TextureLoader` **después**
  de pintar la escena plana (carga progresiva).
- **Plantas:** silueta de bajo polígono con material del color de su lámina. La señalética caliente y fría se queda
  como un **anillo** en el suelo, alrededor de la planta.
- **Paleta:** verde bolsa para la cerca, papel para los caminos y ocre para la tierra.
- **Riesgo para P-066:** las texturas suman unos 240 KB, pero no bloquean la primera vista. Hay que medirlo de
  nuevo con P-066 en un Android real.

## B: maqueta de papel recortado

- **Material plano por capas** (`MeshToonMaterial` con un gradiente de 3 tonos en una textura de 3×1 px): la escena
  parece cartón recortado.
- **Colores de capítulo:** tierra para los camellones, maíz para los caminos y tinta para la cerca.
- **Plantas como recortes:** un plano que mira a la cámara con el linograbado de la especie (textura con alfa,
  unos 30 KB cada una). Es barato, pero las especies sin linograbado necesitan otra solución: un recorte genérico
  por familia.
- **Bordes de tinta:** contorno con el truco de la malla invertida (`BackSide`, escala de 1,03), que dibuja la línea
  de 3 px del sistema. Duplica los triángulos, que siguen siendo pocos.
- **Riesgo para P-066:** bajo en GPU. El costo está en preparar los recortes de cada especie (contenido y créditos).

## Por qué no hay WebGL en el inicio (ni en A ni en B)

- La primera visita es en el celular con mala conexión (`PRODUCT.md`, principio 2: «se entiende en cinco
  segundos»). Un lienzo 3D en el inicio sumaría unos 250 KB gzip de `three` a la ruta más visitada y retrasaría el
  mapa, que es la tarea principal.
- El mapa de Leaflet ya es la experiencia interactiva del inicio.
- **Alternativa sin WebGL:** A usa la lámina viva (video con póster); B, el entintado del cartel con CSS. Ninguna
  carga `three`.

## Verificación

- P-028 a P-031 y P-055 siguen en verde (la suite e2e no se amplía).
- Se repite P-066 con el procedimiento de `docs/evidencias/P-066_preliminar_escritorio.md` (en la rama
  `i1/oscar-cumplimiento`), con 10 aperturas y mediana y máximo, más una medición en Android de gama media antes del
  merge.
- Con WebGL desactivado, se muestra la lista alternativa (P-028).

# Manifiesto de recursos audiovisuales

> Fase 6 del flujo de diseño (`DESIGN_WORKFLOW.md`). Cada recurso tiene un **uso definido antes de generarse**:
> página, formato, peso máximo, origen o modelo, costo y crédito. No se genera nada que no esté en esta lista.

## Reglas comunes a A y B

- **Honestidad:** toda imagen generada con IA lo dice en su pie o en el pie de página y figura en
  `frontend/public/laminas/credits.json` con `ia: true` y el trabajo de origen de Higgsfield. Nunca se presenta como
  foto de un huerto, de una planta de un huerto ni de una comunidad (`PRODUCT.md`, «Evidence on Hand»).
- **Revisión botánica a mano:** nombre científico bien escrito, sin familia, firma ni texto inventado. Una lámina
  que falla se regenera, no se retoca. Ayer se regeneraron dos por este motivo (sábila con «Compositae» y una firma
  inventada; ortiga con «Urticceae»).
- **Formato y peso:** WebP con `srcset` de dos anchos, ≤ 180 KB a tamaño completo, con `width` y `height`
  declarados. Video: WebM y MP4 de ≤ 1,5 MB, con póster, mudo, pausable y sin reproducción con
  `prefers-reduced-motion`.
- **Personas:** no se generan rostros identificables de «comuneros». Si aparecen manos o figuras, son
  ilustración estilizada, nunca fotorrealismo.
- **Costos de referencia en Higgsfield**, medidos el 12/11/2026:

| Modelo | Uso | Créditos |
|---|---|---|
| `gpt_image_2_6`, calidad low, 1k | Imagen (basta para la web) | 0,25 |
| `gpt_image_2_6`, calidad high, 2k | Imagen de impresión | 2,75 |
| `grok_video`, 5 s | Video | 7,5 |
| `seedance_2_5`, 5 s, 720p | Video | 35 |

Saldo actual: 6,5 créditos.

---

## 1. Recursos ya hechos (commit `25ef1e0`, sirven a la propuesta A)

| Recurso | Ruta | Página | Peso | Trabajo Higgsfield |
|---|---|---|---|---|
| 7 láminas de especie (hierba luisa, sábila, menta, orégano, llantén, ortiga, cedrón) | `frontend/public/laminas/*.webp` (+ `-440`) | Ficha del huerto, catálogo `laminaDe` | 117–158 KB | ver `credits.json` |
| Herbario andino (ramillete) | `frontend/public/arte/heroe/herbario-andino.webp` | Inicio, escritorio | 136 KB | `2b946a8e…` |
| Kit de herbario | `frontend/public/arte/ingreso/kit-herbario.webp` | Ingreso, ≥ 768 px | 187 KB ⚠ | `d8dfcbdb…` |
| Huerto de noche | `frontend/public/arte/luna/huerto-de-noche.webp` | Cabecera del calendario | 170 KB | `28d57815…` |
| 4 fases lunares | `frontend/public/arte/luna/{nueva,creciente,llena,menguante}.webp` | Calendario, reto | ~17 KB c/u | `b3ddb5cf…` |
| 5 viñetas de estados vacíos | `frontend/public/arte/vacios/*.webp` | `Vacio` en 9 lugares | ~14 KB c/u | `c2c7a8ee…` |

⚠ El kit de herbario pasa del tope de 180 KB y hay que recomprimirlo si se elige A.

Todos los recursos ya hechos están en el estilo de grabado de Köhler. **Si se elige B, quedan fuera de las
pantallas públicas.** Las 7 láminas de especie podrían conservarse en la ficha como «lámina de referencia», pero
eso mezclaría dos lenguajes: hay que decidirlo en la fase 5.

## 2. Recursos propuestos para A (evolución de «La etiqueta de la planta seca»)

| ID | Recurso | Uso definido | Formato | Modelo | Costo | Prioridad |
|---|---|---|---|---|---|---|
| A-01 | **Lámina viva del inicio**: el ramillete con un leve vaivén de hojas, 5 s en bucle | Inicio, escritorio, dentro de `LaminaViva` (ya construido) | WebM/MP4 ≤ 1,5 MB, póster = el WebP actual | `grok_video` con la imagen `2b946a8e…` como `start_image` | 7,5 | Media |
| A-02 | **4 texturas 3D sin costura**: tierra de camellón, césped, grava, cerca viva, en grabado coloreado | Escena 3D (`Escena3D.tsx`), `map` con `RepeatWrapping` | WebP 512 px, ≤ 60 KB c/u | `gpt_image_2_6` low | 1,0 | Alta |
| A-03 | **Láminas de 6 especies más** (toronjil de monte, eucalipto, sangorache, matico, guayusa, borraja) | Catálogo `laminaDe` | Como las actuales | `gpt_image_2_6` low + referencia Köhler | 1,5 | Media |
| A-04 | **Grano de papel** | Fondo de `--papel` y de la bolsa, opacidad ≤ 4 % | WebP 256 px en mosaico, ≤ 8 KB | `gpt_image_2_6` low | 0,25 | Baja |
| A-05 | **Viñeta de «escena 3D no disponible»** | Alternativa en lista de P-028 | WebP 240 px | `gpt_image_2_6` low | 0,25 | Baja |

**Total A:** unos 10,5 créditos (3 sin el video).

## 3. Recursos propuestos para B («Minga: el cartel comunitario»)

Lenguaje: **xilografía y linograbado latinoamericano**, con tinta negra sobre papel crudo y una o dos tintas
planas de la paleta de B (`DESIGN_SYSTEM.md` §B). Bordes de corte de gubia, sin degradados y sin fotorrealismo.

| ID | Recurso | Uso definido | Formato | Modelo | Costo | Estado |
|---|---|---|---|---|---|---|
| B-01 | **Cartel del inicio**: manos que siembran en un camellón bajo la luna creciente, con montañas al fondo | Inicio: primer capítulo de color, a la derecha del título | WebP 4:5, ≤ 180 KB | `gpt_image_2_6` low | 0,25 | **Muestra generada para comparar** |
| B-02 | **Especie en linograbado**: manzanilla (*Matricaria chamomilla*), para compararla con la lámina de Köhler | Ficha: figura de la planta | WebP 3:4 | `gpt_image_2_6` low | 0,25 | **Muestra generada para comparar** |
| B-03 | **Cabecera del calendario**: las cuatro fases de la luna sobre surcos, en grabado de dos tintas | Calendario | WebP 21:9 | `gpt_image_2_6` low | 0,25 | **Muestra generada para comparar** |
| B-04 | Las 10 especies del catálogo en linograbado | Ficha, gestión | WebP 3:4 | `gpt_image_2_6` low | 2,5 | Solo si se elige B |
| B-05 | 5 viñetas de estados vacíos en linograbado | `Vacio` | WebP 1:1 | `gpt_image_2_6` low | 0,25 (una lámina con las 5, recortada) | Solo si se elige B |
| B-06 | Ingreso: manos con una libreta de campo y semillas | Ingreso | WebP 3:4 | `gpt_image_2_6` low | 0,25 | Solo si se elige B |
| B-07 | Animación de «prensa de grabado»: el cartel del inicio se entinta | Inicio, una sola vez | Se hace con CSS (`clip-path`), **sin video** | — | 0 | Solo si se elige B |
| B-08 | Texturas 3D de papel recortado y cartón (maqueta) | Escena 3D | WebP 512 px | `gpt_image_2_6` low | 0,75 | Solo si se elige B |

**Total B:** unos 4,5 créditos. Las muestras B-01 a B-03 cuestan 0,75 y se generan ahora para que la comparación
sea justa. Las capturas de las maquetas están en `docs/diseno/maquetas/`.

## 4. Lo que no se genera en ninguna propuesta

- Fotografías «de huertos» o «de comunidades» hechas con IA: chocan con `PRODUCT.md`.
- Testimonios, logotipos de aliados o cifras de uso.
- Audio o música: la app es de consulta y no lo necesita.
- Avatares o personajes con rostro.

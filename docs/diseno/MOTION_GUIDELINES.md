# Pautas de movimiento (A y B)

> Fase 9 de `DESIGN_WORKFLOW.md`, planificada con emil-design-eng. Regla de base: **cada animación expresa una
> causa y su efecto**, o no existe. La auditoría (`UI_UX_AUDIT.md` §5) concluyó que hoy no sobra ninguna; solo
> faltan dos con significado (la cámara 3D y la entrada de imágenes).

## Lo común a A y B

| Token | Valor | Uso |
|---|---|---|
| `--curva-salida` | `cubic-bezier(0.23, 1, 0.32, 1)` | Todo lo que llega (desacelera) |
| `--curva-movimiento` | `cubic-bezier(0.77, 0, 0.175, 1)` | Lo que se mueve de un lugar a otro |
| `--m-presion` | 160 ms | Presión de botones |
| `--m-rapido` | 150 ms | Cambios de contenido en el mismo sitio |
| `--m-entrada` | 280 ms | Entrada de etiquetas o tarjetas |
| `--escalon` | 50 ms (tope de 8 elementos) | Escalonado de listas |

**Reglas comunes:**
- Solo se anima `transform` y `opacity`; `clip-path` solo en la entrada única de B.
- La salida es más rápida que la entrada: alrededor del 65 %.
- **Nunca** se anima en atajos de teclado, en el filtro del calendario (se usa seguido) ni en datos que cambian.
- Con `prefers-reduced-motion: reduce`, los desplazamientos se vuelven un fundido de 150 ms o nada, el video se
  queda en su póster y la cámara 3D salta sin volar.
- El hover solo aplica con `(hover: hover) and (pointer: fine)`.
- Todo es interrumpible: un nuevo clic cancela la animación en curso y fija el estado final explícitamente.

## A: movimiento discreto de archivo

| Dónde | Qué | Duración y curva | Por qué |
|---|---|---|---|
| Listas y etiquetas | `revelar`: sube 8 px y aparece (ya existe) | 280 ms, salida, con escalón | Orienta al llegar |
| Sello de clasificación | `sellar`: escala 1,12 → 1 (ya existe) | 220 ms | Marca el estado con un gesto físico |
| **Láminas** (nuevo) | Fundido de opacidad 0 → 1 al `load` | 220 ms, salida | Evita el salto en conexiones lentas |
| **Lámina viva del inicio** (nuevo, A-01) | Video de 5 s en bucle, con botón de pausa | — | Única pieza ambiental; se pausa y respeta reduced-motion |
| **Cámara 3D** (nuevo) | Al elegir una zona, la cámara se acerca y la encuadra | 600 ms, `--curva-movimiento` | Conecta el botón con el lugar: causa y efecto |
| Panel de zona | `fundir` (ya existe) | 150 ms | Cambio de contenido en el mismo sitio |
| Pliego de herbario, al pasar el puntero | La lámina sube 2 px | 150 ms | Señala que es interactivo; solo con puntero fino |

**Descartado en A:** el paralaje de las láminas (marea y no aporta), los contadores animados de las cifras (datos
que cambian) y las transiciones de página completas (demoran la tarea).

## B: movimiento de imprenta, con más carácter

| Dónde | Qué | Duración y curva | Por qué |
|---|---|---|---|
| **Cartel del inicio** | «Entintado»: `clip-path: inset(0 0 100% 0)` → `inset(0)`, de arriba abajo, una sola vez por sesión | 600 ms, salida | Metáfora de la prensa; ocurre una vez |
| Capítulos de color | Al entrar en pantalla, el capítulo sube 16 px como una hoja que se apila | 320 ms, salida | Separa los capítulos al hacer scroll; solo la primera vez |
| Cifras en sello | Escala 0,9 → 1,04 → 1 | 260 ms (resorte) | El sello de goma golpea el papel |
| Días recomendados | Barrido de color maíz de izquierda a derecha, escalonado por semana | 200 ms + 30 ms por fila | Hace visible qué cambió el filtro. Se aplica **solo** al cambiar de actividad, no al navegar |
| Píldoras y botones | `scale(.96)` al presionar | 140 ms | Respuesta táctil |
| Cámara 3D | Igual que A | 600 ms | Igual que A |

**Descartado en B:** las texturas de papel en movimiento, el «temblor» tipográfico (cansa y daña la lectura),
cualquier animación en `/gestion/*` aparte de la presión de botones, y el cursor personalizado.

**Riesgo propio de B:** con más gesto hay más riesgo de que todo se mueva a la vez. Hay como máximo **dos**
elementos animados por pantalla al llegar.

## Verificación (para A y B)

- Prueba manual con la emulación de `prefers-reduced-motion` en DevTools, en cada página.
- `review-animations` de emil sobre el diff antes del merge.
- Rendimiento: grabar con Performance en un Android de gama media (o con la CPU 4× más lenta). Ningún frame de
  animación debe pasar de 16 ms.

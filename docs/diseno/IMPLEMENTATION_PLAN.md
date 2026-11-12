# Plan de implementación (fases 8 a 10)

> **Se ejecuta solo después de la aprobación 1** (`DESIGN_WORKFLOW.md`). Hay un plan «si eliges A» y otro «si
> eliges B». Cada página se cierra con su verificación antes de pasar a la siguiente.

## Dueños de los archivos (según las fichas `docs/0x_nombre.md` de la iteración 1)

| Archivo | Área | Dueño |
|---|---|---|
| `paginas/Mapa.tsx`, `paginas/FichaHuerto.tsx`, `paginas/Recorrido3D.tsx`, `paginas/escena/Escena3D.tsx` | HU-010 a HU-013 | **Oscar** |
| `paginas/Ingresar.tsx`, `sesion/*` | HU-001, HU-002 | Christian |
| `paginas/gestion/Bitacora.tsx` (alta) | HU-014 | Gabriel |
| `paginas/gestion/Bitacora.tsx` (corrección e historial), `paginas/Reto.tsx` | HU-016 a HU-018 | Piero |
| `paginas/Calendario.tsx` | HU-019, HU-020 | Dalin |
| `estilos.css`, `componentes/Diseno.tsx`, `componentes/Estado.tsx` | Transversal | Compartidos: coordinar con Mauricio (integración) |
| `paginas/gestion/Gestion*.tsx` | HU-003 a HU-009 | Base del equipo |
| `DESIGN.md`, `PRODUCT.md` | Diseño | Oscar (dirección elegida el 03/11) |

**Regla:** los cambios en archivos que no son de Oscar se limitan a lo visual (clases, marcado de presentación) y
se avisan a su dueño antes del merge. No se toca lógica, mensajes MSG ni pruebas.

## Verificación común (al cerrar cada página)

1. `npm run build` (incluye `tsc -b`) sin errores.
2. `npx playwright test`: **14/14** en verde, sin pruebas nuevas.
3. Capturas a 1440 y 390 px en modo claro y oscuro, con un script **fuera de `testDir`** (como los usados en esta
   auditoría), y comparación con las de `UI_UX_AUDIT.md`.
4. `impeccable detect http://localhost:5174/<ruta>` sin hallazgos nuevos.
5. Contraste de cada par nuevo ≥ 4,5:1, foco visible, 44 px y prueba con reduced-motion.
6. Peso: imágenes de ≤ 180 KB; LCP del inicio ≤ 2,5 s (Lighthouse escritorio, perfil móvil 4G).

---

## Si eliges A: «La etiqueta de la planta seca», evolucionada (unos 4 a 6 días por persona)

| Orden | Página o pieza | Cambios | Archivos | Dueño | Créditos |
|---|---|---|---|---|---|
| A0 | Sistema | Token `--ocre`, Newsreader itálica (`@fontsource/newsreader`), clase `.cientifico`, fundido de láminas al cargar, pie compacto en `/gestion`, actualizar `DESIGN.md` y corregir las fuentes de `PRODUCT.md` (dice Lora y Noto) | `estilos.css`, `Diseno.tsx`, `DESIGN.md`, `PRODUCT.md` | Compartido | 0 |
| A1 | Recorrido 3D (P1 de la auditoría) | Cámara guiada, `hemisphereLight`, plantas de bajo polígono, etiquetas legibles y texturas grabadas (A-02) con carga progresiva | `Escena3D.tsx`, `Recorrido3D.tsx` | Oscar | 1,0 |
| A2 | Ficha | Pliego de herbario con rótulo, latín en serif | `FichaHuerto.tsx`, `estilos.css` | Oscar | 0 |
| A3 | Inicio | Tira de láminas en cada etiqueta de huerto (requiere las especies en `ResumenHuerto`, que hoy solo trae el conteo, `api/tipos.ts:17-27`: **cambio de API** que hay que valorar; si no, la tira queda solo en la ficha), contraste de la atribución del mapa y lámina viva (A-01, opcional) | `Mapa.tsx`, `estilos.css`, `LaminaViva.tsx` | Oscar (API: coordinar) | 0 o 7,5 |
| A4 | Calendario | Grabado de la luna en los días de cambio de fase | `Calendario.tsx` | Dalin | 0 |
| A5 | Reto | Insignia pendiente con pasos concretos | `Reto.tsx` | Piero | 0 |
| A6 | Catálogo | 6 láminas más (A-03), con revisión botánica | `datos/laminas.ts`, `credits.json` | Oscar | 1,5 |
| A7 | Pulido | Recomprimir el kit de herbario y botón de cerrar sesión en el celular | `public/arte`, `Diseno.tsx` | Oscar | 0 |

**Validación específica de A:**
- P-066, 10 aperturas, tras A1.
- Revisión manual de cada lámina nueva.

---

## Si eliges B: «Minga, el cartel comunitario» (unos 12 a 18 días por persona)

| Orden | Página o pieza | Cambios | Archivos | Dueño | Créditos |
|---|---|---|---|---|---|
| B0 | Sistema | Tokens nuevos (crudo, papel, tinta, maíz, tierra, añil, chakra), Bricolage Grotesque y Public Sans (`@fontsource-variable/*`), quitar Archivo, bordes de tinta, píldoras, variante sobria para `/gestion`, reescribir `DESIGN.md` y actualizar `PRODUCT.md` | `estilos.css` (reescritura amplia), `Diseno.tsx`, `Estado.tsx`, `DESIGN.md`, `PRODUCT.md` | Compartido: **requiere acuerdo del equipo** | 0 |
| B0' | Validación cultural | Mostrar las muestras B-01 a B-03 al MSP (Salud Intercultural) o a una comunidad; ajustar o quitar los motivos textiles | — | Oscar | 0 |
| B1 | Inicio | Capítulo maíz con el cartel (B-01) entintado con CSS, cifras en sello, tarjetas de huerto como cartel. Las píldoras de especies requieren el mismo **cambio de API** que A3 (`ResumenHuerto` solo trae el conteo de plantas, `api/tipos.ts:17-27`) | `Mapa.tsx` | Oscar (API: coordinar) | 0 (B-01 ya existe; versión final en calidad baja: 0,25) |
| B2 | Ficha | Capítulo chakra, plantas con linograbado (B-04) o con su inicial | `FichaHuerto.tsx`, `datos/laminas.ts` | Oscar | 2,5 |
| B3 | Recorrido 3D | Cámara guiada (común) y maqueta de papel recortado: `MeshToonMaterial`, contornos y plantas como recorte | `Escena3D.tsx` | Oscar | 0,75 |
| B4 | Calendario | Capítulo añil, filtro en píldoras (con `role="radiogroup"` accesible) y barrido de días recomendados | `Calendario.tsx` | Dalin | 0 (B-03 ya existe) |
| B5 | Reto | Capítulo de celebración: la insignia como cartel | `Reto.tsx` | Piero | 0 |
| B6 | Ingreso y vacíos | Linograbado del ingreso (B-06) y viñetas (B-05) | `Ingresar.tsx`, `Estado.tsx`, `public/arte` | Christian / compartido | 0,5 |
| B7 | Gestión y bitácora | Variante sobria: crudo, papel, tinta y bordes de 3 px, **sin capítulos** | `gestion/*` | Gabriel, Piero, base | 0 |
| B8 | Retiro | Quitar o reubicar las 14 ilustraciones de Köhler y la `LaminaViva` si no se usan | `public/arte`, `componentes/LaminaViva.tsx` | Oscar | 0 |

**Validación específica de B:**
- Contraste de **cada** capítulo con su texto, en claro y oscuro.
- Medir el peso de las fuentes (objetivo ≤ 150 KB en total).
- P-066 tras B3.
- Prueba con 2 o 3 personas del público objetivo, para comprobar que se entiende en 5 segundos en el celular.

---

## Riesgos y mitigación

| Riesgo | A | B | Mitigación |
|---|---|---|---|
| Conflictos con las ramas del equipo | Bajo | Alto | Integrar después del 13/11; avisar a los dueños; PR por página |
| Las pruebas e2e dependen de textos o roles | Bajo | Medio (cambia el filtro del calendario) | Conservar textos, roles y nombres accesibles; correr las 14 tras cada página |
| Créditos de Higgsfield (saldo de 6,5 al 12/11) | Alcanza sin el video | Alcanza justo | Generar en calidad baja (0,25); recargar antes de un lote |
| Error botánico o cultural en el arte de IA | Bajo (ya hay proceso de revisión) | Medio | Revisión a mano obligatoria; validación cultural en B0' |
| P-066 empeora | Texturas | Contornos y recortes | Carga progresiva; medir tras la pieza 3D |

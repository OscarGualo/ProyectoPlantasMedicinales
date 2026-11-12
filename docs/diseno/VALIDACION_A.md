# Validación de la propuesta A (fase 10)

> El usuario eligió la propuesta A, «La etiqueta de la planta seca», evolucionada, el 12/11/2026, y se implementó
> según `IMPLEMENTATION_PLAN.md` (piezas A0 a A7). Rama `ui/experiencia-visual`.
>
> Las capturas finales están en `validacion/`, con datos de demostración reales y en tres variantes: escritorio
> claro, escritorio oscuro (`-oscuro`) y celular a 390 px (`-movil`).

## Resultado de las verificaciones

| Verificación | Resultado |
|---|---|
| `npm run build` (incluye `tsc -b`) | ✅ Sin errores |
| Pruebas Java (`mvnw verify`) | ✅ **89 de 89**, 0 fallas, con el cambio de API incluido |
| Pruebas de navegador (`npx playwright test`) | ✅ **14 de 14**, sin pruebas nuevas (se corrieron dos veces: tras A0–A6 y tras los ajustes finales) |
| Desborde horizontal a 390 px | ✅ Ninguno en inicio, ficha, calendario, reto ni 3D |
| `impeccable detect` en el inicio | ✅ Sin defectos. Antes: atribución del mapa con 4,49:1; un aviso de 4,3:1 en «Leer la etiqueta», corregido |
| `impeccable detect` en ficha, calendario, reto y 3D | ⚠ Solo «espaciado monótono» (unos 4 px en 15 de 17 medidas). Ya existía antes de esta rama; sigue como P3 por confirmar (`UI_UX_AUDIT.md` §4.2) |
| Contraste de los pares nuevos | ✅ Ocre sobre papel de lámina, 6,8:1; tinta de lámina, 14,8:1; enlace en tarjetas, 7,5:1 |
| Modo oscuro | ✅ El pliego conserva el papel claro con tinta fija; el rótulo usa los tokens oscuros |
| Movimiento reducido | ✅ El fundido de imágenes se apaga, la cámara 3D salta sin volar y el grano no se mueve |
| Peso | ✅ Láminas nuevas de 145 a 177 KB (≤ 180); versión reducida de 28 a 45 KB; texturas de 41 a 60 KB; kit de herbario de 187 a 140 KB; Newsreader itálica de 24 KB |

## Qué cambió, por pieza

| Pieza | Cambio | Archivos |
|---|---|---|
| A0 Sistema | Newsreader itálica para el latín (`--serif`, `.cientifico`); tokens `--ocre` y `--tinta-lamina`; grano de papel en SVG (desactivado con `prefers-contrast: more`); componente `Imagen` con fundido al cargar; pie compacto en `/gestion`; botón de cerrar sesión con su ícono completo en el celular; `DESIGN.md` y `PRODUCT.md` al día | `estilos.css`, `componentes/Imagen.tsx`, `componentes/Diseno.tsx`, `DESIGN.md`, `PRODUCT.md` |
| A1 Recorrido 3D | Maqueta de herbario: texturas grabadas con carga progresiva, luz de cielo, plantas de bajo polígono con sombra falsa, señalética como anillo, rótulos de tamaño constante, cámara guiada de 600 ms (interrumpible, sin vuelo con movimiento reducido), botón «Ver todo el huerto» y croquis cuando la escena no carga | `escena/Escena3D.tsx`, `Recorrido3D.tsx`, `public/arte/texturas/*` |
| A2 Ficha | Pliego de herbario: lámina montada, rótulo abajo a la derecha y crédito en serif ocre. La foto del líder conserva su etiqueta | `FichaHuerto.tsx`, `estilos.css` |
| A3 Inicio | Tira de láminas en cada etiqueta de huerto (viñeta de semillas si no hay plantas); cajón «Qué crece en los huertos»; contraste de la atribución del mapa | `Mapa.tsx`, `estilos.css`, **API**: `ResumenHuerto.java`, `ServicioConsultaPublica.java`, `api/tipos.ts` |
| A4 Calendario | El día en que empieza cada fase principal lleva su grabado y la hora | `Calendario.tsx`, `estilos.css` |
| A5 Reto | La insignia pendiente explica en 4 pasos cómo obtenerla | `Reto.tsx`, `estilos.css` |
| A6 Catálogo | 6 láminas nuevas (eucalipto, sangorache, guayusa, borraja, caléndula, romero), revisadas a mano; versiones reducidas en WebP de las 3 láminas de Köhler | `datos/laminas.ts`, `public/laminas/*`, `credits.json` |
| A7 Pulido | Viñetas recortadas de nuevo (el borde de una vecina asomaba); kit de herbario recomprimido | `public/arte/vacios/*`, `public/arte/ingreso/*` |

## Cambio de API (aditivo)

`GET /api/publico/huertos` agrega `especies: [{ nombreComun, nombreCientifico }]` a cada huerto, en el orden en que
se registraron. Son datos que la ficha pública ya publicaba (HU-011): no expone nada privado (RN-02). Los campos
anteriores no cambian. El frontend tolera un backend sin el campo (`especies ?? []`).

**Pendiente de documentar en el doc 06 (arquitectura).** Se avisa al integrador antes del merge.

## Pendientes

1. **A-01, lámina viva:** cuesta 7,5 créditos y el saldo es de unos 3. `LaminaViva` ya acepta `video`.
2. **P-066 en Android real:** las texturas suman unos 205 KB que no bloquean la primera vista. Falta repetir la
   medición formal en un equipo de gama media antes del merge.
3. **Avisar a los dueños** de los archivos que no son de Oscar, antes del merge:
   - Calendario: Dalin.
   - Reto: Piero.
   - `estilos.css`, `Diseno.tsx` y `Estado.tsx`: Mauricio, como integrador.
   - Las pantallas de gestión: solo los cambios de ayer (viñetas).
4. **«Espaciado monótono»:** confirmar si es real con la revisión en vivo de impeccable (P3).

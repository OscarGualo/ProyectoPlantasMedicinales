# Auditoría UI/UX: Huertos Medicinales Comunitarios

> Fases 1 y 2 de `DESIGN_WORKFLOW.md`, hechas el 12/11/2026 sobre la rama `ui/experiencia-visual` (commit
> `25ef1e0`, que ya incluye las ilustraciones de Higgsfield).
>
> **Cómo se auditó:**
> - Capturas reales con Playwright a 1440 y 390 px, en modo claro y oscuro. Incluyen `/huertos/1/3d`, `/gestion/*`
>   y la bitácora, con el usuario `l1` del perfil e2e.
> - `impeccable detect` sobre las páginas en marcha.
> - Revisión de código.
>
> **Reparto de skills:**
> - ui-ux-pro-max: piso de accesibilidad y usabilidad; gana los conflictos.
> - impeccable: calidad visual.
> - emil-design-eng: inventario de movimiento.
>
> **Severidad:**
> - P0: bloquea el uso o rompe WCAG AA.
> - P1: daña la tarea principal.
> - P2: resta calidad.
> - P3: pulido.

## 1. El producto en una página

- **Usuarios** (`PRODUCT.md`):
  - El visitante es la prioridad visual. Usa el celular, con mala conexión y alfabetización digital variable.
  - El líder de huerto usa un Android de gama media, a veces en el propio huerto y al sol.
- **Tareas públicas:** encontrar un huerto en el mapa, leer su etiqueta, recorrerlo en 3D, consultar el
  calendario lunar y ver el reto de su comunidad.
- **Tareas del líder:** mantener la ficha, plantas y cultivos, y registrar siembras con foto. Debe tomar pocos
  pasos.
- **Restricciones:**
  - Mensajes MSG con texto exacto.
  - Nada medicinal sin fuente.
  - Ninguna imagen se presenta como foto real.
  - WCAG 2.2 AA.
  - 14 pruebas e2e y 89 Java que no se deben ampliar.

## 2. Lo que ya funciona (se conserva en cualquier dirección)

- **Arquitectura de información clara:** tres destinos (Mapa, Calendario, Ingresar o Mi huerto), barra inferior
  en el celular y un enlace directo a cada huerto.
- **Accesibilidad de base sólida:**
  - Foco al cambiar de ruta (`Diseno.tsx:19-26`), enlace para saltar al contenido.
  - Errores junto al campo con `aria-describedby`.
  - Objetivos de 44 px y `prefers-reduced-motion` respetado.
  - Contrastes verificados (todos los pares ≥ 4,5:1).
- **Estados completos:** carga con esqueletos que reservan espacio, vacío con viñeta, error con MSG y alternativa en
  lista si el 3D falla (P-028).
- **Identidad propia, no genérica:** la «etiqueta troquelada», los sellos girados y la tipografía condensada de
  Archivo.
- **Sin desborde horizontal a 390 px** en ninguna de las pantallas auditadas.
- **Rendimiento:** rutas con carga diferida; el 3D se descarga solo al abrir su vista (P-066 preliminar: mediana
  de unos 2,4 s).

## 3. Hallazgos por página

| Página | Funciona | Problemas (severidad, ubicación, regla) | Oportunidad |
|---|---|---|---|
| **Inicio** `/` | Cifras en vivo, mapa que solapa el campo verde, lámina del herbario (escritorio) | **P2:** la atribución de Leaflet queda pegada al borde y su contraste medido es 4,49:1, apenas bajo 4,5 (`impeccable detect`; `estilos.css:633-639`). **P2:** las tarjetas de huerto son solo texto; no muestran qué crece en cada huerto (`Mapa.tsx:120-150`). **P3:** en el celular no hay ilustración: el primer pantallazo es verde con texto | Mostrar en cada tarjeta las especies del huerto. Lámina viva (A) o cartel (B) |
| **Ficha** `/huertos/:id` | Etiqueta completa, láminas de especie con su fuente, sellos de clasificación con texto | **P2:** sin lámina, la planta queda solo con texto (`FichaHuerto.tsx:177`, `planta-sin-figura`). Ahora pasa con menos especies, pero cualquier especie fuera del catálogo vuelve a ese caso. **P3:** los sellos se capturaron a mitad de la animación de 220 ms (se ve en las capturas e2e); no es un defecto, pero confirma que la entrada se nota | Pliego de herbario (A) o cartel de especie (B) |
| **Recorrido 3D** `/huertos/:id/3d` | Lista alternativa accesible, botones por zona, señalética caliente/fría | **P1:** la escena son cajas de color plano sin luz ambiental de cielo ni textura (`Escena3D.tsx:82-84, 139-163`). No se lee como un huerto, sobre todo en el celular, donde el lienzo es pequeño. **P2:** las etiquetas de zona (`CanvasTexture`) son diminutas a 390 px. **P2:** la cámara no guía: al tocar una zona no se acerca a ella | Ver `WEBGL_EXPERIENCES.md` |
| **Calendario** `/calendario` | Filtro por actividad, días recomendados con sello y texto, fases grabadas | **P2:** en el celular la cuadrícula de 30 días es una lista muy larga sin agrupar por semana. **P3:** el glifo diario (círculo SVG) y el grabado conviven con dos lenguajes distintos | Marcar con el grabado solo los días de cambio de fase |
| **Reto** `/huertos/:id/reto` | Insignia como sello, evidencia con fecha y fase, explicación con fuente | **P3:** la insignia pendiente solo tiene la luna y el aviso; no muestra cuánto falta ni qué hacer | Pasos concretos («registra una siembra con foto en luna creciente») |
| **Ingreso** `/ingresar` | Gestor de contraseñas, mostrar y ocultar, MSG-01 sin revelar el dato | Sin problemas de severidad P0 a P2. **P3:** la lámina del kit pesa 187 KB, sobre el tope de 180 | — |
| **Gestión** `/gestion/*` | Formularios con ayuda bajo cada campo, pestañas Huerto, Plantas y Cultivos | **P2:** el pie gigante «Huertos Medicinales Comunitarios» (`Diseno.tsx:83-98`) se repite en las pantallas de trabajo y aleja el contenido. **P2:** la miniatura de planta sin foto es un ícono (`GestionPlantas.tsx:89-91`); se dejó así a propósito para que el líder note que falta su foto, pero puede mejorar con un texto «Sin foto propia». **P2, celular:** el botón de cerrar sesión en la barra superior muestra un glifo mínimo dentro de su caja de 44 px (`Diseno.tsx:63-73`, `estilos.css:416`). **P3:** el formulario de características es largo y no tiene agrupación visual por bloques | Pie compacto en las rutas `/gestion`. Agrupar los campos del formulario |
| **Bitácora** | Registro de siembra con foto, corrección con historial y conformidad lunar explicada | **P2:** «Registrada 31/10/26, 12:00 p. m.» usa un año de dos cifras y hora de 12 horas, distinto del resto (`docs/evidencias/resultados_oscar_I1.md:41-44`, en la rama `i1/oscar-cumplimiento`). **P2:** la corrección tarda unos 5,6 s y la interfaz no lo comunica (`cumplimiento_I1.md:55`, de Piero) | Componente `FechaHora` compartido |
| **404 y vacíos** | Viñeta grabada y acción para volver | **P3:** el `h1` de la 404 queda pegado a la caja del vacío | — |

## 4. Hallazgos transversales

1. **Incoherencia documental (P2).** `PRODUCT.md`, «Brand Commitments», dice que la marca usa Lora y Noto Sans con
   el primario `#15803D`, pero el sistema real usa **Archivo** (`DESIGN.md`, `estilos.css:44`). Hay que corregir
   `PRODUCT.md` cuando se decida la dirección.
2. **Ritmo de espacio (P3, por confirmar).** `impeccable detect` marca un espaciado de unos 4 px en 13 de 15 medidas
   en cuatro páginas. Es probable que mida la misma barra o pie compartido. Hay que verificarlo con su revisión en
   vivo antes de tratarlo como defecto.
3. **El pie de página pesa demasiado (P2).** El título «Huertos Medicinales Comunitarios» a 4,5–6 rem aparece en
   todas las rutas: en el celular ocupa casi una pantalla después de cada contenido.
4. **Móvil (pendiente acordado).** El pulido a 390 px y la prueba P-027 en un Android real quedan para el final
   (`SIGUIENTES_PASOS.md:89-111`).
5. **Captura de pantalla completa con barra fija.** En las capturas de página completa a 390 px, la barra inferior
   aparece a mitad de página. Es un artefacto de la captura, no de la app: la barra está fija al borde de la
   ventana.

## 5. Inventario de movimiento (emil-design-eng)

| Animación | Dónde | Duración y curva | Juicio |
|---|---|---|---|
| `revelar` | Entrada de listas y etiquetas, con escalón de 50 ms | 280 ms, `cubic-bezier(.23,1,.32,1)` | Útil: orienta al llegar. Se conserva |
| `sellar` | Sello que «cae» al entrar su etiqueta | 220 ms | Con propósito (marca un estado). Se conserva |
| `aparecer` | Avisos, paneles | 280 ms | Correcta |
| `fundir` | Cambio de zona en el panel 3D | 150 ms | Correcta |
| `pulso` | Esqueletos de carga | 1,2 s en bucle | Correcto; se apaga con reduced-motion |
| `girar` | Ícono de carga en botones | 0,7 s lineal | Correcto |
| Presión de botones | Todos los botones | `scale(.97)` en 160 ms | Correcta |

No sobra ninguna animación. Faltan transiciones con significado en dos lugares:
- **Recorrido 3D:** al elegir una zona, la cámara no se mueve.
- **Imágenes:** las láminas aparecen sin fundido; en conexiones lentas «saltan» al cargar.

## 6. Prioridades de la auditoría (valen para A y B)

1. **P1:** que la escena 3D se lea como un huerto.
2. **P2:**
   - pie compacto en gestión;
   - contraste de la atribución del mapa;
   - formato `FechaHora`;
   - la planta sin figura;
   - especies visibles en las tarjetas de huerto;
   - el botón de cerrar sesión en el celular.
3. **P3:**
   - pulido móvil;
   - insignia pendiente con pasos;
   - recomprimir el kit de herbario.

Capturas de esta auditoría: las de la app se regeneran con el script de capturas (ver `IMPLEMENTATION_PLAN.md`).
Las de las maquetas están en `docs/diseno/maquetas/capturas/`.

# Flujo de trabajo de diseño: Huertos Medicinales Comunitarios

> Coordinación de las herramientas de diseño para el rediseño. Rama `ui/experiencia-visual` (desde `develop`),
> worktree `HuertosMedicinales-ui`. Documentos en `docs/diseno/`.
>
> **Estado al 12/11/2026:** fases 1 a 7 hechas como **propuesta doble (A y B)**. **Esperando la decisión del
> usuario** para pasar a la fase 8.

## 1. Responsabilidades (una herramienta, una especialidad)

| Herramienta | Especialidad en este proyecto | No hace |
|---|---|---|
| Task (lista de tareas) | Ordena las tareas de cada fase y sus dependencias | Decidir diseño |
| **ui-ux-pro-max** (obligatoria) | Piso de accesibilidad y usabilidad: contraste, objetivos táctiles, foco, formularios, rendimiento. **Gana los conflictos** de accesibilidad | Elegir estética |
| **impeccable** | Proceso y calidad visual: `detect` sobre las páginas en marcha, revisión crítica y `DESIGN.md` como contrato | Proponer movimiento |
| **taste-skill** | Dirección de arte sin ornamento genérico: biblioteca de arquetipos y paletas, inspector `inspect.mjs` | Decidir accesibilidad |
| **emil-design-eng** | Movimiento y microinteracciones: qué se anima, curva y duración, y qué se quita | Generar imágenes |
| **Higgsfield MCP** | Imágenes y video originales, **solo** los de `ASSET_MANIFEST.md`, con revisión botánica y cultural a mano | Fotografías «reales» de huertos o comunidades |
| **Three.js / WebGL** | Solo el recorrido 3D (`WEBGL_EXPERIENCES.md`); nada en el inicio | Decoración |
| **Playwright** | Capturas a 1440 y 390 px, modo claro y oscuro; la suite e2e (14 pruebas) como red de seguridad | Sumar pruebas a `testDir` (el conjunto está fijado en 14) |

**Reglas:**
- Las herramientas se usan en secuencia, no todas a la vez.
- Si dos se contradicen, decide ui-ux-pro-max en accesibilidad, taste-skill en estética y emil en movimiento.

## 2. Fases, entregables y puntos de aprobación

| Fase | Qué | Herramientas | Entregable | Estado |
|---|---|---|---|---|
| 1. Comprender el producto | Usuarios, tareas y restricciones | — | `UI_UX_AUDIT.md` §1 | ✅ |
| 2. Auditar la interfaz | Capturas, `impeccable detect`, código | Playwright, impeccable, ui-ux-pro-max, emil | `UI_UX_AUDIT.md` | ✅ |
| 3. Investigar referencias | Web con fuentes citadas y biblioteca de taste-skill | WebSearch, taste-skill | `ART_DIRECTION.md` (A.1, B.1) | ✅ |
| 4. Dirección artística | **Dos propuestas comparables**, con matriz sin recomendación | taste-skill, ui-ux-pro-max | `ART_DIRECTION.md` + maquetas | ✅ |
| 5. Sistema de diseño | Tokens y componentes de A y B, con contraste verificado | ui-ux-pro-max, impeccable | `DESIGN_SYSTEM.md` | ✅ (propuesta) |
| 6. Recursos audiovisuales | Cada recurso con su uso, peso, modelo y costo | Higgsfield | `ASSET_MANIFEST.md` | ✅ (propuesta; 3 muestras de B generadas) |
| 7. Experiencias WebGL | Solo el recorrido 3D, con presupuesto P-066 | Three.js | `WEBGL_EXPERIENCES.md` | ✅ (propuesta) |
| **⏸ Aprobación 1** | **El usuario elige A, B o un híbrido** | — | — | **Pendiente** |
| 8. Implementar por páginas | Según `IMPLEMENTATION_PLAN.md` (la rama de plan elegida) | taste-skill, impeccable, Higgsfield, Three.js | Código en `frontend/` | Bloqueada |
| **⏸ Aprobación 2** | El usuario aprueba los recursos antes de gastar créditos (lote final) | Higgsfield | — | — |
| 9. Motion design | `MOTION_GUIDELINES.md` (la sección elegida) | emil | Código | Bloqueada |
| 10. Validar y optimizar | e2e 14/14, capturas, `impeccable detect`, P-066, contraste, reduced-motion | Playwright, impeccable, ui-ux-pro-max | Informe en `docs/diseno/` | Bloqueada |
| **⏸ Aprobación 3** | El usuario aprueba antes de commit o merge a `develop` (y antes de avisar a los dueños de los archivos) | — | — | — |

## 3. Dependencias

```
1 → 2 → 3 → 4 ─┬→ 5 → 8 → 9 → 10
               ├→ 6 ───┘
               └→ 7 ───┘
      (⏸ aprobación 1 entre 4–7 y 8)
```

- La fase 6 depende de la 4 (el estilo de las imágenes) y de la 5 (la paleta de tintas en B).
- La fase 8 necesita la 5, la 6 y la 7 aprobadas.
- La fase 9 va después de la 8 porque anima componentes que ya existen.
- La fase 10 vuelve a correr al final de cada página, no solo al final.

## 4. Restricciones que atraviesan todas las fases

- **No se pierde ninguna funcionalidad.** HU-001 a HU-020, mensajes MSG con texto exacto, 14 e2e y 89 Java en verde.
- **Accesibilidad:** WCAG 2.2 AA y `prefers-reduced-motion`.
- **Rendimiento:** WebP de ≤ 180 KB y P-066 < 8 s.
- **Sin dependencias redundantes:** Three.js y R3F ya están; no se suma drei si basta con `TextureLoader`, y no se
  suma una librería de animación si basta CSS o WAAPI.
- **Honestidad del arte:** nada se presenta como foto real.
- **Coordinación con el equipo:** esta rama toca archivos de otras personas (ver `IMPLEMENTATION_PLAN.md`). Se
  integra después de la entrega del 13/11 y con aviso previo.
- **Móvil:** el pulido a 390 px y la prueba P-027 en Android van al final (acordado el 04/11).

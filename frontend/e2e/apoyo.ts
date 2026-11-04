import { test as base, expect } from '@playwright/test'

/**
 * Antes de cada caso se restauran los datos del doc 05 §7 (doc 04, «Cómo ejecutar y registrar»).
 * El reloj del backend e2e está fijado en 31/10/2026 12:00 America/Guayaquil.
 * Además, cada caso falla si la consola del navegador registra un error no esperado.
 */

export interface Consola {
  /** Declara un error que el caso provoca a propósito; cualquier otro hace fallar la prueba. */
  permitir: (...patrones: RegExp[]) => void
}

export const test = base.extend<{ datos: void; consola: Consola }>({
  datos: [
    async ({ request }, usar) => {
      const r = await request.post('http://localhost:8081/api/publico/e2e/restaurar')
      expect(r.status(), 'restaurar datos de prueba').toBe(204)
      await usar()
    },
    { auto: true },
  ],
  consola: [
    async ({ page }, usar) => {
      const errores: string[] = []
      const permitidos: RegExp[] = []
      page.on('pageerror', (e) => errores.push(e.message))
      page.on('console', (m) => {
        if (m.type() === 'error') errores.push(m.text())
      })
      await usar({ permitir: (...p) => permitidos.push(...p) })
      const inesperados = errores.filter((e) => !permitidos.some((patron) => patron.test(e)))
      expect(inesperados, 'errores en la consola del navegador').toEqual([])
    },
    { auto: true },
  ],
})

export { expect }

/** Datos de prueba (doc 05 §7) usados en las pruebas. */
export const H1 = { id: 1, nombre: 'Huerto de prueba H1' }
export const H2 = { id: 2, nombre: 'Huerto de prueba H2' }
export const H3 = { id: 3, nombre: 'Huerto de prueba H3' }

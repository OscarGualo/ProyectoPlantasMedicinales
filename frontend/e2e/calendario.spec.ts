import type { Locator } from '@playwright/test'
import { expect, test } from './apoyo'

/** HU-020 (filtrar el calendario lunar por actividad): P-052 a P-054, con el cálculo real de fases. */

async function verificarResaltado(dias: Locator, fasesRecomendadas: string[]) {
  const total = await dias.count()
  expect(total).toBe(30)
  for (let i = 0; i < total; i++) {
    const dia = dias.nth(i)
    const fase = await dia.getAttribute('data-fase')
    const resaltado = await dia.evaluate((el) => el.classList.contains('dia-resaltado'))
    expect(resaltado, `día ${i + 1} (${fase})`).toBe(fasesRecomendadas.includes(fase ?? ''))
    // El resaltado nunca es solo color: lleva el sello «Recomendado».
    await expect(dia.getByText('Recomendado')).toHaveCount(resaltado ? 1 : 0)
  }
}

test('P-052 Filtro de días de siembra', async ({ page }) => {
  await page.goto('/calendario')
  await page.getByLabel('Actividad').selectOption({ label: 'Siembra' })
  await expect(page.locator('.dia-resaltado').first()).toBeVisible()
  await verificarResaltado(page.locator('.dias > li'), ['LUNA_CRECIENTE', 'LUNA_LLENA'])
})

test('P-053 Filtro de días de cosecha de raíces', async ({ page }) => {
  await page.goto('/calendario')
  await page.getByLabel('Actividad').selectOption({ label: 'Cosecha de semillas y raíces' })
  await expect(page.locator('.dia-resaltado').first()).toBeVisible()
  await verificarResaltado(page.locator('.dias > li'), ['LUNA_MENGUANTE'])
})

test('P-054 Retiro del filtro', async ({ page }) => {
  await page.goto('/calendario')
  await page.getByLabel('Actividad').selectOption({ label: 'Siembra' })
  await expect(page.locator('.dia-resaltado').first()).toBeVisible()

  await page.getByRole('button', { name: 'Quitar filtro' }).click()
  await expect(page.locator('.dia-resaltado')).toHaveCount(0)
  await expect(page.locator('.dias > li')).toHaveCount(30)
})

import { expect, H1, H2, H3, test } from './apoyo'

/** HU-010 (mapa) y HU-011 (ficha pública): P-023 a P-026. */

test('P-023 Marcadores de los huertos publicados', async ({ page }) => {
  await page.goto('/')
  await expect(page.locator(`.leaflet-marker-icon[title="${H1.nombre}"]`)).toBeVisible()
  await expect(page.locator(`.leaflet-marker-icon[title="${H2.nombre}"]`)).toBeVisible()
  await expect(page.locator('.leaflet-marker-icon')).toHaveCount(2)
})

test('P-024 Huerto no publicado en el mapa', async ({ page }) => {
  await page.goto('/')
  await expect(page.locator('.leaflet-marker-icon').first()).toBeVisible()
  await expect(page.locator(`.leaflet-marker-icon[title="${H3.nombre}"]`)).toHaveCount(0)
  await expect(page.getByRole('heading', { name: H3.nombre })).toHaveCount(0)
})

test('P-025 Consulta de la ficha pública de un huerto', async ({ page }) => {
  for (const huerto of [H1, H2]) {
    await page.goto('/')
    await page.locator(`.leaflet-marker-icon[title="${huerto.nombre}"]`).click()
    await page.getByRole('link', { name: 'Leer la etiqueta del huerto' }).click()
    await expect(page).toHaveURL(new RegExp(`/huertos/${huerto.id}$`))
    await expect(page.getByRole('heading', { level: 1, name: huerto.nombre })).toBeVisible()
    await expect(page.getByRole('heading', { name: 'Plantas medicinales' })).toBeVisible()
    await expect(page.getByRole('heading', { name: 'Qué crece en cada zona' })).toBeVisible()
    await expect(page.getByRole('link', { name: 'Recorrer en 3D' })).toBeVisible()
    // Sin el contacto del responsable ni datos de la cuenta (RN-02).
    await expect(page.getByText(/contacto-privado/)).toHaveCount(0)
  }
})

test('P-026 Enlace directo a un huerto no publicado', async ({ page, consola }) => {
  consola.permitir(/status of 404/) // el servidor responde 404 con MSG-14, a propósito
  await page.goto(`/huertos/${H3.id}`)
  await expect(page.getByRole('alert')).toHaveText('El huerto solicitado no existe o no está publicado.')
  await expect(page.getByText(H3.nombre)).toHaveCount(0)
})

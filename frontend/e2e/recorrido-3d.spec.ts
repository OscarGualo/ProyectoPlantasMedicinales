import { expect, H1, test } from './apoyo'

/** HU-012 (recorrer el huerto en 3D) y HU-013 (cultivos de una zona): P-028 a P-031. P-027 es manual. */

const MSG_13 = 'No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.'

test('P-028 Fallo de carga de la escena', async ({ page, consola }) => {
  // Paso 1: bloquear el recurso de la escena (el módulo que se descarga al abrir la vista).
  consola.permitir(/net::ERR_FAILED/, /Failed to fetch dynamically imported module/, /error occurred in one of your React components/)
  await page.route('**/Escena3D*', (ruta) => ruta.abort())
  await page.goto(`/huertos/${H1.id}/3d`)

  await expect(page.getByRole('alert')).toHaveText(MSG_13)
  const lista = page.getByRole('list', { name: 'Zonas y cultivos del huerto' })
  await expect(lista.getByRole('heading', { name: /Zona Z1/ })).toBeVisible()
  await expect(lista.getByRole('heading', { name: /Zona Z2/ })).toBeVisible()
  await expect(lista.getByText('Planta caliente de prueba')).toBeVisible()
  // Sin la escena ni las zonas de otro huerto.
  await expect(page.locator('canvas')).toHaveCount(0)
  await expect(lista.getByRole('heading', { name: /Zona Z3/ })).toHaveCount(0)

  // Paso 3: restaurar el recurso y recargar.
  await page.unroute('**/Escena3D*')
  await page.reload()
  await expect(page.locator('canvas')).toBeVisible({ timeout: 20_000 })
  await expect(page.getByText(MSG_13)).toHaveCount(0)
})

test('P-029 Cultivos de una zona', async ({ page }) => {
  await page.goto(`/huertos/${H1.id}/3d`)
  const panel = page.getByRole('region', { name: 'Cultivos de la zona elegida' })

  await page.getByRole('button', { name: /Z1/ }).click()
  await expect(panel.getByRole('heading', { name: /Zona Z1/ })).toBeVisible()
  await expect(panel.getByRole('listitem')).toHaveCount(1)
  await expect(panel.getByRole('listitem').first()).toContainText('Planta de prueba')
  await expect(panel.getByRole('listitem').first()).toContainText('sembrado')

  await page.getByRole('button', { name: /Z2/ }).click()
  await expect(panel.getByRole('heading', { name: /Zona Z2/ })).toBeVisible()
  await expect(panel.getByRole('listitem')).toHaveCount(3)
  for (const planta of ['Planta de prueba', 'Planta caliente de prueba', 'Planta sin clasificar']) {
    await expect(panel.getByRole('listitem').filter({ hasText: planta })).toContainText('sembrado')
  }
})

test('P-030 Señalética caliente y fría', async ({ page }) => {
  await page.goto(`/huertos/${H1.id}/3d`)
  await page.getByRole('button', { name: /Z2/ }).click()
  const panel = page.getByRole('region', { name: 'Cultivos de la zona elegida' })

  const sello = (planta: string) =>
    panel.getByRole('listitem').filter({ hasText: planta }).locator('.sello').first()
  await expect(sello('Planta caliente de prueba')).toHaveText('Caliente')
  await expect(sello('Planta caliente de prueba')).toHaveCSS('color', 'rgb(180, 50, 15)') // rojo
  await expect(sello('Planta sin clasificar')).toHaveText('Sin clasificar')
  await expect(sello('Planta sin clasificar')).toHaveCSS('color', 'rgb(86, 96, 90)') // gris
  const fria = panel.getByRole('listitem').filter({ hasText: /^Planta de prueba/ }).locator('.sello').first()
  await expect(fria).toHaveText('Fría')
  await expect(fria).toHaveCSS('color', 'rgb(4, 108, 78)') // verde
})

test('P-031 Zona sin cultivos', async ({ page }) => {
  await page.goto(`/huertos/${H1.id}/3d`)
  await page.getByRole('button', { name: /Z4/ }).click()
  await expect(page.getByRole('region', { name: 'Cultivos de la zona elegida' }))
    .toContainText('Esta zona no tiene cultivos registrados.')
})

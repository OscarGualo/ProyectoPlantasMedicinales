import type { Page } from '@playwright/test'
import { expect, H1, test } from './apoyo'

/**
 * P-055: recorrido integrado de la primera iteración, el mismo de la demostración. El backend e2e usa CAL-T para
 * los días 10 a 14/10/2026 (10/10 luna creciente, 11/10 luna menguante) y el cálculo real en el resto.
 */

const FOTO_F1 = 'public/laminas/melissa-officinalis.jpg' // JPEG válido de menos de 1 MB
const MSG_03 = 'No tienes permiso para modificar la información de este huerto.'
const MSG_18 =
  'Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena.'

async function ingresar(page: Page, usuario: string) {
  await page.goto('/ingresar')
  await page.getByLabel('Usuario').fill(usuario)
  await page.locator('input[name="contrasena"]').fill('clave-prueba')
  await page.getByRole('button', { name: 'Iniciar sesión' }).click()
  await expect(page).toHaveURL(/\/gestion$/)
  await expect(page.getByRole('form', { name: 'Características' })).toBeVisible()
}

async function salir(page: Page) {
  await page.getByRole('button', { name: 'Cerrar sesión' }).click()
  await expect(page.getByRole('link', { name: 'Ingresar' }).first()).toBeVisible()
}

for (const escenario of [
  { nombre: 'CAL-T', conforme: '2026-10-10', corregida: '2026-10-11' },
  { nombre: 'efemérides reales', conforme: '2026-10-18', corregida: '2026-10-03' },
]) {
test(`P-055 Recorrido integrado de la primera iteración · ${escenario.nombre}`, async ({ page, consola }) => {
  test.setTimeout(120_000)
  const fechaVisible = (iso: string) => iso.split('-').reverse().join('/')

  await test.step('1. Visitante: mapa, ficha, escena 3D y zona Z2', async () => {
    await page.goto('/')
    await page.locator(`.leaflet-marker-icon[title="${H1.nombre}"]`).click()
    await page.getByRole('link', { name: 'Leer la etiqueta del huerto' }).click()
    await expect(page.getByRole('heading', { level: 1, name: H1.nombre })).toBeVisible()
    await page.getByRole('link', { name: 'Recorrer en 3D' }).click()
    await expect(page.locator('canvas')).toBeVisible({ timeout: 20_000 })
    await page.getByRole('button', { name: /Z2/ }).click()
    const panel = page.getByRole('region', { name: 'Cultivos de la zona elegida' })
    await expect(panel.getByRole('heading', { name: /Zona Z2/ })).toBeVisible()
    await expect(panel.getByRole('listitem')).toHaveCount(3)
  })

  await test.step('2. Calendario lunar filtrado por días de siembra', async () => {
    await page.getByRole('link', { name: 'Calendario lunar' }).first().click()
    await page.getByLabel('Actividad').selectOption({ label: 'Siembra' })
    await expect(page.locator('.dia-resaltado').first()).toBeVisible()
    await expect(page.locator('.dia-resaltado').first().getByText('Recomendado')).toBeVisible()
  })

  await test.step('3. L1 actualiza H1 y el estado de K1; K2 no cambia', async () => {
    await ingresar(page, 'l1')
    const caracteristicas = page.getByRole('form', { name: 'Características' })
    await caracteristicas.getByLabel('Tipo de suelo').fill('Franco arcilloso')
    await caracteristicas.getByRole('button', { name: 'Guardar características' }).click()
    await expect(page.getByText('Características guardadas.')).toBeVisible()

    await page.getByRole('link', { name: 'Cultivos', exact: true }).click()
    const k1 = page.getByLabel('Estado de Planta de prueba en la zona Z1')
    const k2 = page.getByLabel('Estado de Planta de prueba en la zona Z2')
    await k1.selectOption({ label: 'en crecimiento' })
    await expect(page.getByText('Estado actualizado.')).toBeVisible()
    await expect(k1).toHaveValue('EN_CRECIMIENTO')
    await expect(k2).toHaveValue('SEMBRADO')
  })

  const siembras = page.locator('.siembras > li')

  await test.step('4. Siembra de K1 con foto, condición lunar y reto', async () => {
    await page.getByRole('listitem').filter({ hasText: 'Zona Z1' }).getByRole('link', { name: 'Bitácora de siembras' }).click()
    const formulario = page.getByRole('form', { name: 'Registrar una siembra' })
    await formulario.getByLabel('Fecha de la siembra').fill(escenario.conforme)
    await formulario.getByLabel('Hora').fill('08:00')
    await formulario.getByLabel('Técnica').selectOption({ label: 'Semillas' })
    consola.permitir(/status of 400/)
    await formulario.getByLabel('Fotografía').setInputFiles({
      name: 'invalida.jpg', mimeType: 'image/jpeg', buffer: Buffer.from('archivo de prueba inválido'),
    })
    await formulario.getByRole('button', { name: 'Registrar siembra' }).click()
    await expect(formulario.getByText('La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.'))
      .toBeVisible()
    await expect(siembras).toHaveCount(0)
    await formulario.getByLabel('Fotografía').setInputFiles(FOTO_F1)
    await formulario.getByRole('button', { name: 'Registrar siembra' }).click()

    await expect(siembras).toHaveCount(1) // se registra una sola vez
    await expect(siembras.first()).toContainText(`Siembra del ${fechaVisible(escenario.conforme)} a las 08:00`)
    await expect(siembras.first().locator('.sello-redondo')).toHaveText('Conforme')
    await expect(siembras.first()).toContainText('la siembra se realizó en luna creciente')

    await page.goto(`/huertos/${H1.id}/reto`)
    await expect(page.getByRole('heading', { name: 'Insignia obtenida' })).toBeVisible()
    await expect(page.getByText('La sustenta la siembra de Planta de prueba en la zona Z1')).toBeVisible()
    await page.goBack()
  })

  await test.step('5. Corrección con motivo, historial y recálculo de la insignia', async () => {
    await siembras.first().getByRole('button', { name: 'Corregir' }).click()
    const correccion = page.getByRole('form', { name: 'Corregir la siembra' })
    await correccion.getByLabel('Fecha de la siembra').fill(escenario.corregida)
    await correccion.getByLabel('Motivo de la corrección').fill('Fecha registrada por error')
    await correccion.getByRole('button', { name: 'Guardar corrección' }).click()

    await expect(siembras).toHaveCount(1)
    await expect(siembras.first()).toContainText(`Siembra del ${fechaVisible(escenario.corregida)} a las 08:00`)
    await expect(siembras.first().locator('.siembra-cuerpo .sello-redondo')).toHaveText('No conforme')

    // Tras guardar se abre el historial: la vigente primero, después la original.
    const historial = page.getByRole('region', { name: 'Historial de la siembra' })
    const versiones = historial.locator('.version')
    await expect(versiones).toHaveCount(2)
    await expect(versiones.nth(0)).toContainText('Corrección 1 · vigente')
    await expect(versiones.nth(0)).toContainText('Fecha registrada por error')
    await expect(versiones.nth(0)).toContainText(`${fechaVisible(escenario.corregida)} a las 08:00`)
    await expect(versiones.nth(1)).toContainText('Registro original')
    await expect(versiones.nth(1)).toContainText(`${fechaVisible(escenario.conforme)} a las 08:00`)

    await page.goto(`/huertos/${H1.id}/reto`)
    await expect(page.getByRole('heading', { name: 'Insignia pendiente' })).toBeVisible()
    await expect(page.getByText(MSG_18)).toBeVisible()
  })

  await test.step('6. L2 intenta modificar H1; sin sesión, la consulta pública no muestra datos privados', async () => {
    await salir(page)
    await ingresar(page, 'l2')
    // L2 no ve H1 en su gestión: el intento va directo al servidor, como en P-003.
    const token = (await page.context().cookies()).find((c) => c.name === 'XSRF-TOKEN')?.value ?? ''
    const r = await page.request.put(`/api/huertos/${H1.id}/caracteristicas`, {
      headers: { 'X-XSRF-TOKEN': token },
      data: { nombre: 'Intento ajeno' },
    })
    expect(r.status()).toBe(403)
    expect((await r.json()).mensaje).toBe(MSG_03)
    await salir(page)

    await page.goto(`/huertos/${H1.id}`)
    await expect(page.getByRole('heading', { level: 1, name: H1.nombre })).toBeVisible()
    await expect(page.getByText('Franco arcilloso')).toBeVisible()
    await expect(page.getByText(/contacto-privado/)).toHaveCount(0)
    await expect(page.getByText('Intento ajeno')).toHaveCount(0)
  })
})
}

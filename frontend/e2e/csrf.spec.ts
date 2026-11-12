import { expect, test } from './apoyo'

test('P-063 Sesión y token CSRF se renuevan y rechazan la cabecera anterior', async ({ page }) => {
  async function token() {
    const cookie = (await page.context().cookies()).find((c) => c.name === 'XSRF-TOKEN')
    expect(cookie?.value).toBeTruthy()
    return cookie!.value
  }
  const peticion = page.request
  expect((await peticion.get('/api/sesion')).ok()).toBeTruthy()
  const anonimo = await token()
  const ingreso = await peticion.post('/api/sesion', {
    headers: { 'X-XSRF-TOKEN': anonimo },
    data: { usuario: 'l1', contrasena: 'clave-prueba' },
  })
  expect(ingreso.ok()).toBeTruthy()
  const autenticado = await token()
  expect(autenticado).not.toBe(anonimo)
  const invalido = await peticion.delete('/api/sesion', { headers: { 'X-XSRF-TOKEN': anonimo } })
  expect(invalido.status()).toBe(403)
  expect((await invalido.json()).codigo).toBe('MSG-26')
  const cierre = await peticion.delete('/api/sesion', { headers: { 'X-XSRF-TOKEN': autenticado } })
  expect(cierre.status()).toBe(204)
  expect(await token()).not.toBe(autenticado)
  const sinSesion = await peticion.put('/api/huertos/1/caracteristicas', {
    headers: { 'X-XSRF-TOKEN': autenticado },
    data: { nombre: 'No se debe guardar' },
  })
  expect(sinSesion.status()).toBe(401)
  expect((await sinSesion.json()).codigo).toBe('MSG-02')
})

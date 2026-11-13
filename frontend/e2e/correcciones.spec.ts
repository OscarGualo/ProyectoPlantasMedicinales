import {expect,test} from './apoyo'

async function ingresar(page: import('@playwright/test').Page) {
  await page.goto('/ingresar')
  await expect(page.getByText(/escogidos por su comunidad y autorizados por el MSP/)).toBeVisible()
  await page.getByLabel('Usuario').fill('l1')
  await page.locator('input[name="contrasena"]').fill('clave-prueba')
  await page.getByRole('button',{name:'Iniciar sesión',exact:true}).click()
  await expect(page).toHaveURL(/\/gestion$/)
}

test('P-071 Cierre interrumpido conserva estado y permite reintentar',async({page,consola})=>{
  await ingresar(page)
  consola.permitir(/net::ERR_FAILED/)
  await page.route('**/api/sesion',r=>r.request().method()==='DELETE'?r.abort():r.continue())
  await page.getByRole('button',{name:'Cerrar sesión',exact:true}).click()
  await expect(page.getByRole('alert')).toHaveText('No se confirmó el cierre de sesión. Revisa tu conexión y vuelve a intentarlo.')
  await expect(page.getByRole('button',{name:'Cerrar sesión',exact:true})).toBeVisible()
  expect((await (await page.request.get('/api/sesion')).json()).autenticado).toBe(true)
  await page.unroute('**/api/sesion')
  await page.getByRole('button',{name:'Cerrar sesión',exact:true}).click()
  await expect(page.getByRole('link',{name:'Ingresar'}).first()).toBeVisible()
  expect((await (await page.request.get('/api/sesion')).json()).autenticado).toBe(false)
})

test('P-072 La última selección vence a respuestas anteriores',async({page})=>{
  await page.goto('/calendario')
  await page.getByLabel('Actividad',{exact:true}).waitFor()
  let recibida!:()=>void
  const primera=new Promise<void>(r=>{recibida=r})
  await page.route('**/api/publico/calendario-lunar*',async ruta=>{
    const r=await ruta.fetch()
    if(new URL(ruta.request().url()).searchParams.get('actividad')==='SIEMBRA'){
      recibida();await new Promise(r=>setTimeout(r,900))
    }
    await ruta.fulfill({response:r})
  })
  await page.getByLabel('Actividad',{exact:true}).selectOption('SIEMBRA')
  await primera
  await page.getByLabel('Actividad',{exact:true}).selectOption('COSECHA_SEMILLAS_RAICES')
  await expect(page.getByLabel('Actividad',{exact:true})).toHaveValue('COSECHA_SEMILLAS_RAICES')
  await page.waitForTimeout(1100)
  await expect(page.getByLabel('Actividad',{exact:true})).toHaveValue('COSECHA_SEMILLAS_RAICES')
  for(const dia of await page.locator('.dia-resaltado').all()) await expect(dia).toHaveAttribute('data-fase','LUNA_MENGUANTE')
})

test('P-076 El fallo cartográfico se informa y la ficha sigue accesible',async({page,consola})=>{
  consola.permitir(/net::ERR_FAILED/)
  await page.route('**/*.tile.openstreetmap.org/**',r=>r.abort())
  await page.goto('/')
  await expect(page.getByText('No se pudo cargar el mapa base. Puedes consultar los huertos en la lista.')).toBeVisible()
  await page.getByRole('heading',{name:'Huerto de prueba H1',exact:true}).click()
  await expect(page).toHaveURL(/\/huertos\/1$/)
})

test('P-077 El mapa sin huertos muestra estado vacío',async({page})=>{
  await page.route('**/api/publico/huertos',r=>r.fulfill({json:[]}))
  await page.goto('/')
  await expect(page.getByText('Todavía no hay huertos publicados')).toBeVisible()
  await expect(page.locator('.leaflet-marker-icon')).toHaveCount(0)
})

test('P-073 La ficha muestra altitud y distribución cuando la fuente las aporta',async({page})=>{
  await page.route('**/api/publico/huertos/1',async ruta=>{
    const r=await ruta.fetch();const ficha=await r.json()
    ficha.plantas[0].altitud='Altitud institucional de prueba'
    ficha.plantas[0].distribucion='Distribución institucional de prueba'
    await ruta.fulfill({json:ficha})
  })
  await page.goto('/huertos/1')
  await expect(page.getByText('Altitud institucional de prueba')).toBeVisible()
  await expect(page.getByText('Distribución institucional de prueba')).toBeVisible()
})

test('P-074 Gestión de Galápagos usa la hora del huerto',async({page})=>{
  await ingresar(page)
  const token=(await page.context().cookies()).find(c=>c.name==='XSRF-TOKEN')!.value
  const r=await page.request.put('/api/huertos/1/ubicacion',{headers:{'X-XSRF-TOKEN':token},data:{provincia:'Galápagos',latitud:-0.74,longitud:-90.31}})
  expect(r.status()).toBe(200)
  await page.goto('/gestion/cultivos')
  const esperado=await page.evaluate(()=>new Intl.DateTimeFormat('es-EC',{dateStyle:'medium',timeStyle:'short',timeZone:'Pacific/Galapagos'}).format(new Date('2026-10-30T14:00:00Z')))
  await expect(page.getByRole('listitem').filter({has:page.getByRole('link',{name:'Bitácora de siembras'})}).first()).toContainText(esperado)
})

test('El líder incorpora una ficha institucional y no tiene editor medicinal',async({page})=>{
  await ingresar(page)
  await page.getByRole('link',{name:'Plantas',exact:true}).click()
  await page.getByRole('button',{name:'Incorporar planta del catálogo MSP'}).click()
  await page.getByRole('combobox',{name:/Ficha del catálogo MSP/}).selectOption('MSP-HIERBA-LUISA')
  await expect(page.getByRole('region',{name:'Información institucional de lectura'})).toContainText('Cymbopogon citratus')
  await expect(page.getByLabel('Usos',{exact:true})).toHaveCount(0)
  await expect(page.locator('textarea')).toHaveCount(0)
  await page.getByRole('button',{name:'Guardar ficha',exact:true}).click()
  await expect(page.getByText('Ficha de Hierba luisa guardada.')).toBeVisible()
  await page.goto('/huertos/1')
  await expect(page.getByRole('heading',{name:'Hierba luisa',exact:true})).toBeVisible()
})

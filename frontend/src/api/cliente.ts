// Cliente HTTP común (doc 06 §8.2): envía la cookie de sesión, agrega el token CSRF y convierte las
// respuestas de error {codigo, mensaje, campo} en ErrorApi. La interfaz ayuda, pero el servidor valida.

export class ErrorApi extends Error {
  readonly codigo: string
  readonly campo: string | null
  readonly estado: number

  constructor(estado: number, codigo: string, mensaje: string, campo: string | null) {
    super(mensaje)
    this.name = 'ErrorApi'
    this.estado = estado
    this.codigo = codigo
    this.campo = campo
  }
}

/** Evento que se emite cuando el servidor responde MSG-02 (la sesión terminó). */
export const SESION_FINALIZADA = 'huertos:sesion-finalizada'

const MENSAJE_SIN_CONEXION = 'No se pudo conectar con el servidor. Revisa tu conexión e inténtalo nuevamente.'

function leerCookie(nombre: string): string | null {
  const par = document.cookie.split('; ').find((c) => c.startsWith(`${nombre}=`))
  return par ? decodeURIComponent(par.slice(nombre.length + 1)) : null
}

type Cuerpo = FormData | object | undefined

async function solicitar<T>(metodo: string, ruta: string, cuerpo?: Cuerpo): Promise<T> {
  const cabeceras: Record<string, string> = { Accept: 'application/json' }
  let body: BodyInit | undefined
  if (cuerpo instanceof FormData) {
    body = cuerpo // el navegador define el multipart boundary
  } else if (cuerpo !== undefined) {
    cabeceras['Content-Type'] = 'application/json'
    body = JSON.stringify(cuerpo)
  }
  if (metodo !== 'GET') {
    const token = leerCookie('XSRF-TOKEN')
    if (token) cabeceras['X-XSRF-TOKEN'] = token
  }

  let respuesta: Response
  try {
    respuesta = await fetch(ruta, { method: metodo, headers: cabeceras, body, credentials: 'same-origin' })
  } catch {
    throw new ErrorApi(0, 'SIN_CONEXION', MENSAJE_SIN_CONEXION, null)
  }

  if (respuesta.status === 204) return undefined as T
  const datos = await respuesta.json().catch(() => null)
  if (!respuesta.ok) {
    const error = new ErrorApi(
      respuesta.status,
      datos?.codigo ?? 'ERROR',
      datos?.mensaje ?? 'Ocurrió un error inesperado. Inténtalo nuevamente.',
      datos?.campo ?? null,
    )
    if (error.codigo === 'MSG-02') window.dispatchEvent(new CustomEvent(SESION_FINALIZADA))
    throw error
  }
  return datos as T
}

export const api = {
  get: <T>(ruta: string) => solicitar<T>('GET', ruta),
  post: <T>(ruta: string, cuerpo?: Cuerpo) => solicitar<T>('POST', ruta, cuerpo),
  put: <T>(ruta: string, cuerpo?: Cuerpo) => solicitar<T>('PUT', ruta, cuerpo),
  patch: <T>(ruta: string, cuerpo?: Cuerpo) => solicitar<T>('PATCH', ruta, cuerpo),
  delete: <T>(ruta: string) => solicitar<T>('DELETE', ruta),
}

/** Mensaje para mostrar al usuario a partir de cualquier error. */
export function mensajeDe(error: unknown): string {
  if (error instanceof ErrorApi) return error.message
  return 'Ocurrió un error inesperado. Inténtalo nuevamente.'
}

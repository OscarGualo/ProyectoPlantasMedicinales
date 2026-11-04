// Tipos de los DTO del backend (doc 06 §9). Mantenerlos alineados con los records de Java.

export interface HuertoGestionado {
  id: number
  nombre: string | null
}

export interface Sesion {
  autenticado: boolean
  id: number | null
  usuario: string | null
  nombre: string | null
  huertos: HuertoGestionado[]
}

/** Huerto publicado en el mapa y en la lista pública (GET /api/publico/huertos). */
export interface ResumenHuerto {
  id: number
  nombre: string
  comunidad: string
  provincia: string | null
  parroquia: string | null
  latitud: number
  longitud: number
  plantas: number
  cultivos: number
}

export interface HuertoGestion {
  id: number
  nombre: string | null
  descripcion: string | null
  provincia: string | null
  parroquia: string | null
  direccion: string | null
  latitud: number | null
  longitud: number | null
  zonaHoraria: string
  superficieM2: number | null
  diseno: string | null
  tipoCerca: string | null
  tipoSuelo: string | null
  fuenteAgua: string | null
  tecnicaRiego: string | null
  tipoAbono: string | null
  publicado: boolean
  comunidad: string
  contactoResponsable: string | null
}

export type Clasificacion = 'CALIENTE' | 'FRIA'

export interface Planta {
  id: number
  huertoId: number
  nombreComun: string
  nombreCientifico: string
  familia: string
  fuente: string
  otrosNombres: string | null
  descripcion: string | null
  altitud: string | null
  distribucion: string | null
  ciclo: 'CORTO' | 'LARGO' | null
  clasificacion: Clasificacion | null
  usos: string | null
  fotoUrl: string | null
}

export type EstadoCultivo = 'SEMBRADO' | 'EN_CRECIMIENTO' | 'COSECHADO' | 'PERDIDO'

export interface Cultivo {
  id: number
  plantaId: number
  planta: string
  clasificacion: Clasificacion | null
  zonaId: number
  zona: string
  estado: EstadoCultivo
  estadoEtiqueta: string
  registradoEn: string
  actualizadoEn: string
}

export interface Geometria {
  x: number
  z: number
  ancho: number
  largo: number
  alto: number
}

export interface Zona {
  id: number
  codigo: string
  nombre: string | null
  tipo: 'CULTIVO' | 'CAMINO' | 'CERCA' | 'BODEGA'
  geometria: Geometria
}

export interface FichaPublica {
  id: number
  nombre: string
  descripcion: string | null
  comunidad: string
  provincia: string | null
  parroquia: string | null
  direccion: string | null
  latitud: number
  longitud: number
  zonaHoraria: string
  superficieM2: number | null
  diseno: string | null
  tipoCerca: string | null
  tipoSuelo: string | null
  fuenteAgua: string | null
  tecnicaRiego: string | null
  tipoAbono: string | null
  plantas: Planta[]
  cultivos: Cultivo[]
  zonas: Zona[]
}

export const ESTADOS_CULTIVO: { valor: EstadoCultivo; etiqueta: string }[] = [
  { valor: 'SEMBRADO', etiqueta: 'sembrado' },
  { valor: 'EN_CRECIMIENTO', etiqueta: 'en crecimiento' },
  { valor: 'COSECHADO', etiqueta: 'cosechado' },
  { valor: 'PERDIDO', etiqueta: 'perdido' },
]

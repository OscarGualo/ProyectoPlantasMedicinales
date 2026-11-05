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

// ---------- Siembras y condición lunar (HU-014, HU-015) ----------

export type Tecnica = 'SEMILLAS' | 'ESTACAS' | 'ESQUEJES' | 'PLANTULAS'

export const TECNICAS: { valor: Tecnica; etiqueta: string }[] = [
  { valor: 'SEMILLAS', etiqueta: 'Semillas' },
  { valor: 'ESTACAS', etiqueta: 'Estacas' },
  { valor: 'ESQUEJES', etiqueta: 'Esquejes' },
  { valor: 'PLANTULAS', etiqueta: 'Plántulas' },
]

export type ResultadoLunar = 'CONFORME' | 'NO_CONFORME' | 'PENDIENTE'

export interface CondicionLunar {
  resultado: ResultadoLunar
  fase: string | null
  codigo: string
  mensaje: string
  regla: string
}

export interface Siembra {
  id: number
  cultivoId: number
  version: number
  /** Verdadero si la vigente es una corrección (versión mayor que 1). */
  corregida: boolean
  fechaHecho: string
  /** Fecha y hora del hecho en la zona del huerto, `yyyy-MM-ddTHH:mm`. */
  fechaHechoLocal: string
  registradaEn: string
  autor: string
  tecnica: Tecnica
  tecnicaEtiqueta: string
  descripcion: string | null
  fotoUrl: string
  condicion: CondicionLunar
}

export interface Bitacora {
  cultivo: Cultivo
  zonaHoraria: string
  siembras: Siembra[]
}

// ---------- Corrección e historial (HU-016, HU-017) ----------

export interface VersionSiembra {
  numero: number
  fechaHecho: string
  fechaHechoLocal: string
  tecnicaEtiqueta: string
  descripcion: string | null
  fotoUrl: string
  autor: string
  /** Fecha del registro (versión 1) o de la corrección. */
  creadaEn: string
  motivo: string | null
  condicion: CondicionLunar
}

export interface Historial {
  siembra: Siembra
  zonaHoraria: string
  versiones: VersionSiembra[]
  /** MSG-23 cuando la siembra nunca se corrigió. */
  mensaje: string | null
}

// ---------- Reto Documentación lunar (HU-018) ----------

export interface SustentoInsignia {
  siembraId: number
  planta: string
  zona: string
  fechaHechoLocal: string
  fotoUrl: string
  fase: string | null
  condicion: string
}

export interface Reto {
  huertoId: number
  huerto: string
  comunidad: string
  reto: string
  explicacion: string
  fuente: string
  alcance: string
  insignia: { nombre: string; sustento: SustentoInsignia } | null
  codigo: string | null
  mensaje: string | null
}

// ---------- Calendario lunar (HU-019, HU-020) ----------

export type FaseLunar = 'LUNA_OSCURA' | 'LUNA_CRECIENTE' | 'LUNA_LLENA' | 'LUNA_MENGUANTE'

export interface DiaLunar {
  fecha: string
  fase: FaseLunar | null
  faseEtiqueta: string | null
  resaltado: boolean
}

export interface EventoFase {
  fase: 'LUNA_NUEVA' | 'CUARTO_CRECIENTE' | 'LUNA_LLENA' | 'CUARTO_MENGUANTE'
  etiqueta: string
  instante: string
}

export interface CalendarioLunar {
  desde: string
  hasta: string
  disponibleDesde: string
  disponibleHasta: string
  zonaHoraria: string
  actividad: string | null
  dias: DiaLunar[]
  fasesPrincipales: EventoFase[]
  actividadesPorFase: Record<FaseLunar, string[]>
  opciones: { id: string; etiqueta: string }[]
  fuente: string
}

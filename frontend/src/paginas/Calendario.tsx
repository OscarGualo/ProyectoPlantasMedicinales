import { useCallback, useEffect, useRef, useState, type FormEvent } from 'react'
import { ArrowLeft, ArrowRight, X } from '@phosphor-icons/react'
import { api, mensajeDe } from '../api/cliente'
import type { CalendarioLunar, FaseLunar } from '../api/tipos'
import { AvisoError, Cargando } from '../componentes/Estado'
import { Campo } from '../componentes/Campo'

const FASES: FaseLunar[] = ['LUNA_OSCURA', 'LUNA_CRECIENTE', 'LUNA_LLENA', 'LUNA_MENGUANTE']
const NOMBRE_FASE: Record<FaseLunar, string> = {
  LUNA_OSCURA: 'Luna oscura',
  LUNA_CRECIENTE: 'Luna creciente',
  LUNA_LLENA: 'Luna llena',
  LUNA_MENGUANTE: 'Luna menguante',
}

const formatoDia = new Intl.DateTimeFormat('es-EC', { weekday: 'short', day: 'numeric', month: 'short', timeZone: 'UTC' })
const formatoLargo = new Intl.DateTimeFormat('es-EC', {
  weekday: 'long',
  day: 'numeric',
  month: 'long',
  hour: '2-digit',
  minute: '2-digit',
  hourCycle: 'h23',
  timeZone: 'America/Guayaquil',
})

/** Solo la primera letra en mayúscula, como se escribe en castellano («Lunes, 9 de noviembre»). */
const inicial = (t: string) => t.charAt(0).toUpperCase() + t.slice(1)

/** HU-019 (calendario lunar de 30 días) y HU-020 (filtro por actividad). Público, con o sin sesión. */
export function Calendario() {
  const [datos, setDatos] = useState<CalendarioLunar | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [actividad, setActividad] = useState('')
  const [irA, setIrA] = useState('')
  const ultimaSolicitud = useRef(0)

  /** Pide un rango; si el servidor responde MSG-20 se conserva la vista anterior (HU-019 CA-02). */
  const pedir = useCallback(async (desde: string | null, filtro: string) => {
    const solicitud = ++ultimaSolicitud.current
    const q = new URLSearchParams()
    if (desde) q.set('desde', desde)
    if (filtro) q.set('actividad', filtro)
    try {
      const r = await api.get<CalendarioLunar>(`/api/publico/calendario-lunar${q.size ? `?${q}` : ''}`)
      if (solicitud !== ultimaSolicitud.current) return false
      setDatos(r)
      setError(null)
      return true
    } catch (e) {
      if (solicitud !== ultimaSolicitud.current) return false
      setError(mensajeDe(e))
      return false
    }
  }, [])

  useEffect(() => {
    pedir(null, '')
  }, [pedir])

  async function filtrar(valor: string) {
    if (await pedir(datos?.desde ?? null, valor)) setActividad(valor)
  }

  function mover(dias: number) {
    if (!datos) return
    const d = new Date(`${datos.desde}T00:00:00Z`)
    d.setUTCDate(d.getUTCDate() + dias)
    pedir(d.toISOString().slice(0, 10), actividad)
  }

  function irAFecha(e: FormEvent) {
    e.preventDefault()
    if (irA) pedir(irA, actividad)
  }

  return (
    <>
      <section className="bolsa bolsa-compacta" aria-labelledby="titulo-calendario">
        <h1 id="titulo-calendario">Calendario lunar</h1>
        <p className="bajada">
          La fase de cada día y lo que la práctica recomienda hacer en el huerto. Elige una actividad para ver sus
          días. La fase diaria corresponde a las 12:00 de Ecuador continental; cada siembra se evalúa a su hora real.
        </p>
      </section>

      <AvisoError>{error}</AvisoError>
      {!datos && !error && <Cargando texto="Calculando las fases…" />}

      {datos && (
        <>
          <section className="seccion controles-calendario" aria-label="Filtro y navegación del calendario">
            <Campo etiqueta="Actividad" ayuda="Se resaltan los días recomendados para ella.">
              {(p) => (
                <select {...p} className="control" value={actividad} onChange={(e) => filtrar(e.target.value)}>
                  <option value="">Todas, sin resaltar</option>
                  {datos.opciones.map((o) => (
                    <option key={o.id} value={o.id}>
                      {o.etiqueta}
                    </option>
                  ))}
                </select>
              )}
            </Campo>
            {actividad && (
              <button type="button" className="boton boton-secundario" onClick={() => filtrar('')}>
                <X size={18} aria-hidden="true" />
                Quitar filtro
              </button>
            )}
            <form className="ir-a" onSubmit={irAFecha}>
              <Campo etiqueta="Ir a la fecha">
                {(p) => <input {...p} className="control" type="date" value={irA} onChange={(e) => setIrA(e.target.value)} />}
              </Campo>
              <button type="submit" className="boton boton-secundario">
                Ver
              </button>
            </form>
          </section>

          <div className="cabecera-seccion">
            <h2 style={{ margin: 0 }}>
              Del {formatoDia.format(new Date(`${datos.desde}T00:00:00Z`))} al{' '}
              {formatoDia.format(new Date(`${datos.hasta}T00:00:00Z`))}
            </h2>
            <div className="acciones" style={{ marginTop: 0 }}>
              <button type="button" className="boton boton-secundario boton-pequeno" onClick={() => mover(-30)}>
                <ArrowLeft size={18} aria-hidden="true" />
                30 días antes
              </button>
              <button type="button" className="boton boton-secundario boton-pequeno" onClick={() => mover(30)}>
                30 días después
                <ArrowRight size={18} aria-hidden="true" />
              </button>
            </div>
          </div>

          <ul className="dias" aria-label="Días del calendario">
            {datos.dias.map((d) => (
              <li
                key={d.fecha}
                className={`dia${d.resaltado ? ' dia-resaltado' : ''}`}
                data-fase={d.fase ?? 'SIN_DATO'}
                aria-current={d.resaltado ? 'true' : undefined}
              >
                <span className="dia-fecha">{inicial(formatoDia.format(new Date(`${d.fecha}T00:00:00Z`)))}</span>
                {d.fase && <GlifoLuna fase={d.fase} />}
                <span className="dia-fase">{d.faseEtiqueta ?? 'Sin dato'}</span>
                {d.resaltado && <span className="sello sello-recomendado">Recomendado</span>}
              </li>
            ))}
          </ul>

          <h2>Fases principales</h2>
          <ul className="eventos">
            {datos.fasesPrincipales.map((e) => (
              <li key={e.instante} className="etiqueta evento">
                <strong>{e.etiqueta.charAt(0).toUpperCase() + e.etiqueta.slice(1)}</strong>
                <span>{inicial(formatoLargo.format(new Date(e.instante)))}</span>
              </li>
            ))}
          </ul>

          <h2>Qué hacer en cada fase</h2>
          <div className="etiqueta fases-actividades">
            {FASES.map((f) => (
              <section key={f} aria-label={NOMBRE_FASE[f]}>
                <h3>
                  <GlifoLuna fase={f} />
                  {NOMBRE_FASE[f]}
                </h3>
                <ul>
                  {datos.actividadesPorFase[f].map((a) => (
                    <li key={a}>{a}</li>
                  ))}
                </ul>
              </section>
            ))}
          </div>
          <p className="fuente">Fuente: {datos.fuente}. Horas de Ecuador continental (UTC-5).</p>
        </>
      )}
    </>
  )
}

/** Glifo de la fase dibujado en SVG: la parte iluminada en papel, la oscura en tinta. */
function GlifoLuna({ fase }: { fase: FaseLunar }) {
  const luz = 'var(--papel)'
  const sombra = 'var(--tinta)'
  return (
    <svg className="glifo-luna" viewBox="0 0 32 32" width={28} height={28} aria-hidden="true">
      <circle cx="16" cy="16" r="14" fill={fase === 'LUNA_LLENA' ? luz : sombra} stroke={sombra} strokeWidth="2" />
      {fase === 'LUNA_CRECIENTE' && <path d="M16 2 A14 14 0 0 1 16 30 Z" fill={luz} />}
      {fase === 'LUNA_MENGUANTE' && <path d="M16 2 A14 14 0 0 0 16 30 Z" fill={luz} />}
    </svg>
  )
}

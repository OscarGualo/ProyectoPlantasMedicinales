import { useCallback, useEffect, useRef, useState, type FormEvent } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft, ClockCounterClockwise, PencilSimple, Plant } from '@phosphor-icons/react'
import { api, mensajeDe } from '../../api/cliente'
import { nuevaClave } from '../../api/clave'
import {
  TECNICAS,
  type Bitacora as DatosBitacora,
  type CondicionLunar,
  type Historial,
  type Siembra,
} from '../../api/tipos'
import { AvisoError, AvisoExito, AvisoInfo, Cargando, Vacio } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { SelloEstado, SenalClasificacion } from '../../componentes/Senal'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'

const TAMANO_MAXIMO = 5 * 1024 * 1024

type Panel = { siembraId: number; modo: 'corregir' | 'historial' } | null

/**
 * HU-014 (registrar una siembra), HU-015 (condición lunar), HU-016 (corregir) y HU-017 (historial). Solo un panel
 * de corrección o historial abierto a la vez.
 */
export function Bitacora() {
  const { id } = useParams()
  const [datos, setDatos] = useState<DatosBitacora | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [panel, setPanel] = useState<Panel>(null)

  const cargar = useCallback(() => {
    api
      .get<DatosBitacora>(`/api/cultivos/${id}/bitacora`)
      .then(setDatos)
      .catch((e) => setError(mensajeDe(e)))
  }, [id])

  useEffect(cargar, [cargar])

  if (error) return <AvisoError>{error}</AvisoError>
  if (!datos) return <Cargando texto="Cargando la bitácora…" />

  const { cultivo } = datos
  return (
    <>
      <section className="seccion">
        <div className="cabecera-seccion">
          <div>
            <h2 className="titulo-seccion" style={{ margin: 0 }}>
              <Plant size={24} aria-hidden="true" />
              Bitácora de {cultivo.planta}
            </h2>
            <p className="secundario" style={{ margin: '6px 0 0' }}>
              Zona {cultivo.zona} · <SenalClasificacion clasificacion={cultivo.clasificacion} />{' '}
              <SelloEstado estado={cultivo.estado} etiqueta={cultivo.estadoEtiqueta} />
            </p>
          </div>
          <Link className="boton boton-secundario boton-pequeno" to="/gestion/cultivos">
            <ArrowLeft size={18} aria-hidden="true" />
            Volver a cultivos
          </Link>
        </div>
      </section>

      <FormularioSiembra cultivoId={cultivo.id} alRegistrar={cargar} />

      <section className="seccion" aria-labelledby="titulo-siembras">
        <h2 id="titulo-siembras" style={{ marginTop: 0 }}>
          Siembras registradas
        </h2>
        {datos.siembras.length === 0 ? (
          <Vacio titulo="Aún no hay siembras registradas" vineta="regadera">
            Registra la primera con su fecha, técnica y una fotografía como evidencia.
          </Vacio>
        ) : (
          <ul className="siembras">
            {datos.siembras.map((s) => (
              <EtiquetaSiembra
                key={s.id}
                siembra={s}
                zonaHoraria={datos.zonaHoraria}
                modo={panel?.siembraId === s.id ? panel.modo : null}
                alCambiarModo={(modo) => setPanel(modo ? { siembraId: s.id, modo } : null)}
                alCorregir={() => {
                  setPanel({ siembraId: s.id, modo: 'historial' })
                  cargar()
                }}
              />
            ))}
          </ul>
        )}
      </section>
    </>
  )
}

function FormularioSiembra({ cultivoId, alRegistrar }: { cultivoId: number; alRegistrar: () => void }) {
  const ahora = new Date()
  const [fecha, setFecha] = useState(aFechaLocal(ahora))
  const [hora, setHora] = useState(aHoraLocal(ahora))
  const [tecnica, setTecnica] = useState('')
  const [descripcion, setDescripcion] = useState('')
  const [foto, setFoto] = useState<File | null>(null)
  const [ultima, setUltima] = useState<Siembra | null>(null)
  // La clave se crea al abrir el formulario y se reutiliza en cada reintento; se renueva tras un registro.
  const clave = useRef(nuevaClave())
  const entradaFoto = useRef<HTMLInputElement>(null)
  const formulario = useRef<HTMLFormElement>(null)
  const envio = useEnvio(formulario)
  const fotoGrande = foto !== null && foto.size > TAMANO_MAXIMO

  async function registrar(e: FormEvent) {
    e.preventDefault()
    const datos = new FormData()
    datos.append('fechaHecho', fecha && hora ? `${fecha}T${hora}` : '')
    datos.append('tecnica', tecnica)
    datos.append('descripcion', descripcion)
    datos.append('claveEnvio', clave.current)
    if (foto) datos.append('foto', foto)
    const r = await envio.enviar(
      () => api.post<Siembra>(`/api/cultivos/${cultivoId}/siembras`, datos),
      'Siembra registrada en la bitácora.',
    )
    if (r) {
      setUltima(r)
      clave.current = nuevaClave()
      setDescripcion('')
      setFoto(null)
      if (entradaFoto.current) entradaFoto.current.value = ''
      alRegistrar()
    }
  }

  return (
    <form
      ref={formulario}
      className="formulario seccion"
      onSubmit={registrar}
      noValidate
      aria-labelledby="titulo-nueva-siembra"
    >
      <h2 id="titulo-nueva-siembra" style={{ marginTop: 0 }}>
        Registrar una siembra
      </h2>
      <AvisoError>{envio.errorGeneral}</AvisoError>
      <div className="rejilla">
        <Campo etiqueta="Fecha de la siembra" obligatorio error={envio.errorDe('fechaHecho')}>
          {(p) => (
            <input
              {...p} data-campo="fechaHecho" className="control" type="date" value={fecha}
              max={aFechaLocal(new Date())} onChange={(e) => setFecha(e.target.value)}
            />
          )}
        </Campo>
        <Campo etiqueta="Hora" obligatorio ayuda="Hora del huerto en que se sembró.">
          {(p) => <input {...p} className="control" type="time" value={hora} onChange={(e) => setHora(e.target.value)} />}
        </Campo>
        <Campo etiqueta="Técnica" obligatorio error={envio.errorDe('tecnica')}>
          {(p) => (
            <select {...p} data-campo="tecnica" className="control" value={tecnica} onChange={(e) => setTecnica(e.target.value)}>
              <option value="">Selecciona…</option>
              {TECNICAS.map((t) => (
                <option key={t.valor} value={t.valor}>
                  {t.etiqueta}
                </option>
              ))}
            </select>
          )}
        </Campo>
        <Campo
          etiqueta="Fotografía"
          obligatorio
          error={envio.errorDe('foto') ?? (fotoGrande ? 'La imagen supera 5 MB: elige una más liviana.' : null)}
          ayuda="JPEG o PNG de hasta 5 MB. Es la evidencia de la siembra."
        >
          {(p) => (
            <input
              {...p} ref={entradaFoto} data-campo="foto" className="control control-archivo" type="file"
              accept="image/jpeg,image/png" onChange={(e) => setFoto(e.target.files?.[0] ?? null)}
            />
          )}
        </Campo>
        <Campo etiqueta="Descripción" className="ancho-completo">
          {(p) => (
            <textarea
              {...p} className="control" rows={2} value={descripcion} maxLength={2000}
              onChange={(e) => setDescripcion(e.target.value)}
            />
          )}
        </Campo>
      </div>
      {envio.exito && ultima && (
        <AvisoExito>
          {envio.exito} {ultima.condicion.mensaje}
        </AvisoExito>
      )}
      <div>
        <BotonEnviar enviando={envio.enviando} textoEnviando="Registrando…">
          Registrar siembra
        </BotonEnviar>
      </div>
    </form>
  )
}

const ETIQUETA_RESULTADO: Record<CondicionLunar['resultado'], string> = {
  CONFORME: 'Conforme',
  NO_CONFORME: 'No conforme',
  PENDIENTE: 'Pendiente',
}

function SelloLunar({ condicion }: { condicion: CondicionLunar }) {
  return (
    <span className={`sello sello-redondo sello-lunar-${condicion.resultado.toLowerCase()}`}>
      {ETIQUETA_RESULTADO[condicion.resultado]}
    </span>
  )
}

function EtiquetaSiembra({
  siembra,
  zonaHoraria,
  modo,
  alCambiarModo,
  alCorregir,
}: {
  siembra: Siembra
  zonaHoraria: string
  modo: 'corregir' | 'historial' | null
  alCambiarModo: (modo: 'corregir' | 'historial' | null) => void
  alCorregir: () => void
}) {
  const c = siembra.condicion
  const idPanel = `panel-siembra-${siembra.id}`
  return (
    <li className="etiqueta siembra">
      <img className="siembra-foto" src={siembra.fotoUrl} alt="Fotografía de la siembra" loading="lazy" width={240} height={240} />
      <div className="siembra-cuerpo">
        <div className="cabeza">
          <h3>Siembra del {formatoFechaHora(siembra.fechaHechoLocal)}</h3>
          <SelloLunar condicion={c} />
        </div>
        <dl className="campos">
          <div>
            <dt>Técnica</dt>
            <dd>{siembra.tecnicaEtiqueta}</dd>
          </div>
          <div>
            <dt>Fase lunar</dt>
            <dd>{c.fase ?? 'Sin información'}</dd>
          </div>
          <div>
            <dt>Registrada</dt>
            <dd>{formatoInstante(siembra.registradaEn, zonaHoraria)}</dd>
          </div>
          <div>
            <dt>{siembra.corregida ? 'Última corrección por' : 'Autor'}</dt>
            <dd>{siembra.autor}</dd>
          </div>
        </dl>
        {siembra.descripcion && <p style={{ margin: 0 }}>{siembra.descripcion}</p>}
        <p className="condicion">{c.mensaje}</p>
        <p className="fuente">{c.regla}</p>
        <div className="acciones-siembra">
          <button
            type="button"
            className="boton boton-secundario boton-pequeno"
            aria-expanded={modo === 'corregir'}
            aria-controls={idPanel}
            onClick={() => alCambiarModo(modo === 'corregir' ? null : 'corregir')}
          >
            <PencilSimple size={18} aria-hidden="true" />
            Corregir
          </button>
          <button
            type="button"
            className="boton boton-secundario boton-pequeno"
            aria-expanded={modo === 'historial'}
            aria-controls={idPanel}
            onClick={() => alCambiarModo(modo === 'historial' ? null : 'historial')}
          >
            <ClockCounterClockwise size={18} aria-hidden="true" />
            Historial{siembra.corregida ? ` (versión ${siembra.version})` : ''}
          </button>
        </div>
      </div>
      {modo && (
        <div id={idPanel} className="siembra-panel">
          {modo === 'corregir' ? (
              <FormularioCorreccion key={siembra.version} siembra={siembra}
                                    alCancelar={() => alCambiarModo(null)} alCorregir={alCorregir} />
          ) : (
            <HistorialSiembra siembraId={siembra.id} version={siembra.version} />
          )}
        </div>
      )}
    </li>
  )
}

/**
 * HU-016: nueva versión con motivo obligatorio (MSG-12). Fecha y hora precargadas con la versión vigente; la foto
 * solo se envía si se elige una nueva (MSG-07 conserva la vigente).
 */
function FormularioCorreccion({
  siembra,
  alCancelar,
  alCorregir,
}: {
  siembra: Siembra
  alCancelar: () => void
  alCorregir: () => void
}) {
  const [fechaInicial, horaInicial] = siembra.fechaHechoLocal.split('T')
  const [clave] = useState(nuevaClave)
  const [fecha, setFecha] = useState(fechaInicial)
  const [hora, setHora] = useState(horaInicial)
  const [motivo, setMotivo] = useState('')
  const [foto, setFoto] = useState<File | null>(null)
  const formulario = useRef<HTMLFormElement>(null)
  const envio = useEnvio(formulario)
  const fotoGrande = foto !== null && foto.size > TAMANO_MAXIMO
  const idTitulo = `titulo-corregir-${siembra.id}`

  useEffect(() => {
    formulario.current?.querySelector<HTMLElement>('[data-campo="motivo"]')?.focus()
  }, [])

  async function corregir(e: FormEvent) {
    e.preventDefault()
    const datos = new FormData()
    datos.append('fechaHecho', fecha && hora ? `${fecha}T${hora}` : '')
    datos.append('motivo', motivo)
    datos.append('versionBase', String(siembra.version))
    datos.append('claveEnvio', clave)
    if (foto) datos.append('foto', foto)
    const r = await envio.enviar(
      () => api.post<Siembra>(`/api/siembras/${siembra.id}/correcciones`, datos),
      'Corrección guardada.',
    )
    if (r) alCorregir()
  }

  return (
    <form ref={formulario} className="formulario correccion" onSubmit={corregir} noValidate aria-labelledby={idTitulo}>
      <h4 id={idTitulo}>Corregir la siembra</h4>
      <p className="secundario" style={{ margin: 0 }}>
        La versión actual se conserva en el historial. La condición lunar se calcula con la versión corregida.
      </p>
      <AvisoError>{envio.errorGeneral}</AvisoError>
      <div className="rejilla">
        <Campo etiqueta="Fecha de la siembra" error={envio.errorDe('fechaHecho')}>
          {(p) => (
            <input
              {...p} data-campo="fechaHecho" className="control" type="date" value={fecha}
              max={aFechaLocal(new Date())} onChange={(e) => setFecha(e.target.value)}
            />
          )}
        </Campo>
        <Campo etiqueta="Hora" ayuda="Hora del huerto en que se sembró.">
          {(p) => <input {...p} className="control" type="time" value={hora} onChange={(e) => setHora(e.target.value)} />}
        </Campo>
        <Campo etiqueta="Motivo de la corrección" obligatorio className="ancho-completo" error={envio.errorDe('motivo')}>
          {(p) => (
            <textarea
              {...p} data-campo="motivo" className="control" rows={2} value={motivo} maxLength={500}
              onChange={(e) => setMotivo(e.target.value)}
            />
          )}
        </Campo>
        <Campo
          etiqueta="Nueva fotografía"
          className="ancho-completo"
          error={envio.errorDe('foto') ?? (fotoGrande ? 'La imagen supera 5 MB: elige una más liviana.' : null)}
          ayuda="Opcional. JPEG o PNG de hasta 5 MB. Si no eliges una, se conserva la actual."
        >
          {(p) => (
            <input
              {...p} data-campo="foto" className="control control-archivo" type="file" accept="image/jpeg,image/png"
              onChange={(e) => setFoto(e.target.files?.[0] ?? null)}
            />
          )}
        </Campo>
      </div>
      <div className="acciones-siembra">
        <BotonEnviar enviando={envio.enviando} textoEnviando="Guardando…">
          Guardar corrección
        </BotonEnviar>
        <button type="button" className="boton boton-secundario" onClick={alCancelar}>
          Cancelar
        </button>
      </div>
    </form>
  )
}

/** HU-017: versiones de la más reciente a la más antigua, o MSG-23 si nunca se corrigió. */
function HistorialSiembra({ siembraId, version }: { siembraId: number; version: number }) {
  const [datos, setDatos] = useState<Historial | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let vigente = true
    api
      .get<Historial>(`/api/siembras/${siembraId}/historial`)
      .then((h) => vigente && setDatos(h))
      .catch((e) => vigente && setError(mensajeDe(e)))
    return () => {
      vigente = false
    }
    // La versión cambia tras una corrección: se vuelve a pedir el historial.
  }, [siembraId, version])

  if (error) return <AvisoError>{error}</AvisoError>
  if (!datos) return <Cargando texto="Cargando el historial…" />

  return (
    <section className="historial" aria-label="Historial de la siembra">
      <h4>Historial</h4>
      <AvisoInfo>{datos.mensaje}</AvisoInfo>
      <ol className="versiones">
        {datos.versiones.map((v) => (
          <li key={v.numero} className="version">
            <img src={v.fotoUrl} alt={`Fotografía de la versión ${v.numero}`} loading="lazy" width={96} height={96} />
            <div>
              <div className="cabeza">
                <strong>
                  {v.numero === 1 ? 'Registro original' : `Corrección ${v.numero - 1}`}
                  {v.numero === datos.siembra.version && ' · vigente'}
                </strong>
                <SelloLunar condicion={v.condicion} />
              </div>
              <dl className="campos">
                <div>
                  <dt>Fecha del hecho</dt>
                  <dd>{formatoFechaHora(v.fechaHechoLocal)}</dd>
                </div>
                <div>
                  <dt>{v.numero === 1 ? 'Registrada' : 'Corregida'}</dt>
                  <dd>{formatoInstante(v.creadaEn, datos.zonaHoraria)}</dd>
                </div>
                <div>
                  <dt>Autor</dt>
                  <dd>{v.autor}</dd>
                </div>
                <div>
                  <dt>Motivo</dt>
                  <dd>{v.motivo ?? '—'}</dd>
                </div>
              </dl>
            </div>
          </li>
        ))}
      </ol>
    </section>
  )
}

const pad = (n: number) => String(n).padStart(2, '0')
const aFechaLocal = (d: Date) => `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
const aHoraLocal = (d: Date) => `${pad(d.getHours())}:${pad(d.getMinutes())}`

/** «2026-10-10T08:00» → «10/10/2026 a las 08:00», sin conversión de zona: ya es la hora del huerto. */
function formatoFechaHora(local: string) {
  const [f, h] = local.split('T')
  const [a, m, d] = f.split('-')
  return `${d}/${m}/${a} a las ${h}`
}

function formatoInstante(iso: string, zona: string) {
  return new Intl.DateTimeFormat('es-EC', { dateStyle: 'short', timeStyle: 'short', timeZone: zona }).format(
    new Date(iso),
  )
}

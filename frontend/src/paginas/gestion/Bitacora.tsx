import { useCallback, useEffect, useRef, useState, type FormEvent } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft, Plant } from '@phosphor-icons/react'
import { api, mensajeDe } from '../../api/cliente'
import { nuevaClave } from '../../api/clave'
import { TECNICAS, type Bitacora as DatosBitacora, type CondicionLunar, type Siembra } from '../../api/tipos'
import { AvisoError, AvisoExito, Cargando, Vacio } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { SelloEstado, SenalClasificacion } from '../../componentes/Senal'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'

const TAMANO_MAXIMO = 5 * 1024 * 1024

/** HU-014 (registrar una siembra) y HU-015 (condición lunar de cada siembra). */
export function Bitacora() {
  const { id } = useParams()
  const [datos, setDatos] = useState<DatosBitacora | null>(null)
  const [error, setError] = useState<string | null>(null)

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
          <Vacio titulo="Aún no hay siembras registradas">
            Registra la primera con su fecha, técnica y una fotografía como evidencia.
          </Vacio>
        ) : (
          <ul className="siembras">
            {datos.siembras.map((s) => (
              <EtiquetaSiembra key={s.id} siembra={s} zonaHoraria={datos.zonaHoraria} />
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
  const envio = useEnvio()
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
    <form className="formulario seccion" onSubmit={registrar} noValidate aria-labelledby="titulo-nueva-siembra">
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

function EtiquetaSiembra({ siembra, zonaHoraria }: { siembra: Siembra; zonaHoraria: string }) {
  const c = siembra.condicion
  return (
    <li className="etiqueta siembra">
      <img className="siembra-foto" src={siembra.fotoUrl} alt="Fotografía de la siembra" loading="lazy" width={240} height={240} />
      <div className="siembra-cuerpo">
        <div className="cabeza">
          <h3>Siembra del {formatoFechaHora(siembra.fechaHechoLocal)}</h3>
          <span className={`sello sello-redondo sello-lunar-${c.resultado.toLowerCase()}`}>
            {ETIQUETA_RESULTADO[c.resultado]}
          </span>
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
            <dt>Autor</dt>
            <dd>{siembra.autor}</dd>
          </div>
        </dl>
        {siembra.descripcion && <p style={{ margin: 0 }}>{siembra.descripcion}</p>}
        <p className="condicion">{c.mensaje}</p>
        <p className="fuente">{c.regla}</p>
      </div>
    </li>
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

import { useEffect, useState, type FormEvent } from 'react'
import { Link } from 'react-router'
import { CheckCircle, FloppyDisk, Globe, MapPin, SquaresFour } from '@phosphor-icons/react'
import { api, mensajeDe } from '../../api/cliente'
import type { HuertoGestion } from '../../api/tipos'
import { AvisoError, AvisoExito, Cargando } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { useHuertoGestionado } from './Gestion'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'
import { ABONOS, CERCAS, DISENOS, FUENTES_AGUA, PROVINCIAS, SUELOS, TECNICAS_RIEGO } from './catalogos'

type Valores = Record<string, string>

const texto = (v: string | number | null | undefined) => (v == null ? '' : String(v))
const nulo = (v: string) => (v.trim() === '' ? null : v.trim())
const numero = (v: string) => (v.trim() === '' ? null : Number(v))

/** HU-003 (ubicación), HU-004 (características) y HU-005 (publicación). */
export function GestionHuerto() {
  const huertoId = useHuertoGestionado()
  const [huerto, setHuerto] = useState<HuertoGestion | null>(null)
  const [errorCarga, setErrorCarga] = useState<string | null>(null)

  useEffect(() => {
    api
      .get<HuertoGestion>(`/api/huertos/${huertoId}`)
      .then(setHuerto)
      .catch((e) => setErrorCarga(mensajeDe(e)))
  }, [huertoId])

  if (errorCarga) return <AvisoError>{errorCarga}</AvisoError>
  if (!huerto) return <Cargando lineas={6} />

  return (
    <>
      <Publicacion huerto={huerto} alGuardar={setHuerto} />
      <Ubicacion huerto={huerto} alGuardar={setHuerto} />
      <Caracteristicas huerto={huerto} alGuardar={setHuerto} />
    </>
  )
}

interface PropsSeccion {
  huerto: HuertoGestion
  alGuardar: (h: HuertoGestion) => void
}

function Ubicacion({ huerto, alGuardar }: PropsSeccion) {
  const [v, setV] = useState<Valores>({
    provincia: texto(huerto.provincia),
    parroquia: texto(huerto.parroquia),
    direccion: texto(huerto.direccion),
    latitud: texto(huerto.latitud),
    longitud: texto(huerto.longitud),
  })
  const envio = useEnvio()
  const cambiar = (k: string) => (e: { target: { value: string } }) => setV({ ...v, [k]: e.target.value })

  async function guardar(e: FormEvent) {
    e.preventDefault()
    const r = await envio.enviar(
      () =>
        api.put<HuertoGestion>(`/api/huertos/${huerto.id}/ubicacion`, {
          provincia: nulo(v.provincia),
          parroquia: nulo(v.parroquia),
          direccion: nulo(v.direccion),
          latitud: numero(v.latitud),
          longitud: numero(v.longitud),
        }),
      'Ubicación guardada.',
    )
    if (r) alGuardar(r)
  }

  // MSG-06 se refiere al par de coordenadas: se muestra bajo la latitud y ambas quedan marcadas.
  const errorCoordenadas = envio.errorDe('coordenadas')

  return (
    <form className="formulario seccion" onSubmit={guardar} aria-labelledby="titulo-ubicacion" noValidate>
      <h2 id="titulo-ubicacion" className="titulo-seccion">
        <MapPin size={24} aria-hidden="true" />
        Ubicación
      </h2>
      <AvisoError>{envio.errorGeneral}</AvisoError>
      <div className="rejilla">
        <Campo etiqueta="Provincia">
          {(p) => (
            <select {...p} className="control" value={v.provincia} onChange={cambiar('provincia')}>
              <option value="">Selecciona…</option>
              {PROVINCIAS.map((pr) => (
                <option key={pr}>{pr}</option>
              ))}
            </select>
          )}
        </Campo>
        <Campo etiqueta="Parroquia">
          {(p) => <input {...p} className="control" value={v.parroquia} onChange={cambiar('parroquia')} maxLength={120} />}
        </Campo>
        <Campo etiqueta="Dirección" className="ancho-completo">
          {(p) => (
            <input
              {...p} className="control" value={v.direccion} onChange={cambiar('direccion')} maxLength={250}
              autoComplete="street-address"
            />
          )}
        </Campo>
        <Campo etiqueta="Latitud" error={errorCoordenadas} ayuda="Entre -90 y 90. Ejemplo: -0.2">
          {(p) => (
            <input
              {...p} data-campo="coordenadas" className="control" type="number" step="any" inputMode="decimal"
              value={v.latitud} onChange={cambiar('latitud')}
            />
          )}
        </Campo>
        <Campo etiqueta="Longitud" ayuda="Entre -180 y 180. Ejemplo: -78.5">
          {(p) => (
            <input
              {...p} aria-invalid={Boolean(errorCoordenadas)} className="control" type="number" step="any"
              inputMode="decimal" value={v.longitud} onChange={cambiar('longitud')}
            />
          )}
        </Campo>
      </div>
      <AvisoExito>{envio.exito}</AvisoExito>
      <div>
        <BotonEnviar enviando={envio.enviando} icono={<FloppyDisk size={20} aria-hidden="true" />}>
          Guardar ubicación
        </BotonEnviar>
      </div>
    </form>
  )
}

function Caracteristicas({ huerto, alGuardar }: PropsSeccion) {
  const [v, setV] = useState<Valores>({
    nombre: texto(huerto.nombre),
    descripcion: texto(huerto.descripcion),
    diseno: texto(huerto.diseno),
    tipoCerca: texto(huerto.tipoCerca),
    superficieM2: texto(huerto.superficieM2),
    tipoSuelo: texto(huerto.tipoSuelo),
    fuenteAgua: texto(huerto.fuenteAgua),
    tecnicaRiego: texto(huerto.tecnicaRiego),
    tipoAbono: texto(huerto.tipoAbono),
  })
  const envio = useEnvio()
  const cambiar = (k: string) => (e: { target: { value: string } }) => setV({ ...v, [k]: e.target.value })

  async function guardar(e: FormEvent) {
    e.preventDefault()
    const r = await envio.enviar(
      () =>
        api.put<HuertoGestion>(`/api/huertos/${huerto.id}/caracteristicas`, {
          nombre: v.nombre,
          descripcion: nulo(v.descripcion),
          diseno: nulo(v.diseno),
          tipoCerca: nulo(v.tipoCerca),
          superficieM2: numero(v.superficieM2),
          tipoSuelo: nulo(v.tipoSuelo),
          fuenteAgua: nulo(v.fuenteAgua),
          tecnicaRiego: nulo(v.tecnicaRiego),
          tipoAbono: nulo(v.tipoAbono),
        }),
      'Características guardadas.',
    )
    if (r) alGuardar(r)
  }

  const conSugerencias = (k: string, etiqueta: string, opciones: string[]) => (
    <Campo etiqueta={etiqueta} ayuda="Elige una sugerencia o escribe otra.">
      {(p) => (
        <>
          <input {...p} className="control" value={v[k]} onChange={cambiar(k)} list={`lista-${k}`} maxLength={80} />
          <datalist id={`lista-${k}`}>
            {opciones.map((o) => (
              <option key={o} value={o} />
            ))}
          </datalist>
        </>
      )}
    </Campo>
  )

  return (
    <form className="formulario seccion" onSubmit={guardar} aria-labelledby="titulo-caracteristicas" noValidate>
      <h2 id="titulo-caracteristicas" className="titulo-seccion">
        <SquaresFour size={24} aria-hidden="true" />
        Características
      </h2>
      <AvisoError>{envio.errorGeneral}</AvisoError>
      <div className="rejilla">
        <Campo etiqueta="Nombre" obligatorio error={envio.errorDe('nombre')} className="ancho-completo">
          {(p) => (
            <input {...p} data-campo="nombre" className="control" value={v.nombre} onChange={cambiar('nombre')} maxLength={150} />
          )}
        </Campo>
        <Campo etiqueta="Descripción" className="ancho-completo">
          {(p) => (
            <textarea {...p} className="control" value={v.descripcion} onChange={cambiar('descripcion')} rows={3} maxLength={2000} />
          )}
        </Campo>
        {conSugerencias('diseno', 'Diseño', DISENOS)}
        {conSugerencias('tipoCerca', 'Tipo de cerca', CERCAS)}
        <Campo etiqueta="Superficie (m²)" error={envio.errorDe('superficieM2')} ayuda="Número mayor o igual a 0.">
          {(p) => (
            <input
              {...p} data-campo="superficieM2" className="control" type="number" step="any" min={0}
              inputMode="decimal" value={v.superficieM2} onChange={cambiar('superficieM2')}
            />
          )}
        </Campo>
        {conSugerencias('tipoSuelo', 'Tipo de suelo', SUELOS)}
        {conSugerencias('fuenteAgua', 'Fuente de agua', FUENTES_AGUA)}
        {conSugerencias('tecnicaRiego', 'Técnica de riego', TECNICAS_RIEGO)}
        {conSugerencias('tipoAbono', 'Tipo de abono', ABONOS)}
      </div>
      <AvisoExito>{envio.exito}</AvisoExito>
      <div>
        <BotonEnviar enviando={envio.enviando} icono={<FloppyDisk size={20} aria-hidden="true" />}>
          Guardar características
        </BotonEnviar>
      </div>
    </form>
  )
}

function Publicacion({ huerto, alGuardar }: PropsSeccion) {
  const envio = useEnvio()

  async function publicar() {
    const r = await envio.enviar(
      () => api.post<HuertoGestion>(`/api/huertos/${huerto.id}/publicacion`),
      'El huerto ya aparece en el mapa público.',
    )
    if (r) alGuardar(r)
  }

  return (
    <section className="seccion" aria-labelledby="titulo-publicacion">
      <h2 id="titulo-publicacion" className="titulo-seccion">
        <Globe size={24} aria-hidden="true" />
        Publicación en el mapa
      </h2>
      <AvisoError>{envio.error}</AvisoError>
      <AvisoExito>{envio.exito}</AvisoExito>
      {huerto.publicado ? (
        <p style={{ display: 'flex', alignItems: 'center', gap: 8, flexWrap: 'wrap', margin: 0 }}>
          <CheckCircle size={22} weight="fill" color="var(--c-exito)" aria-hidden="true" />
          <span>
            Tu huerto está publicado. <Link to={`/huertos/${huerto.id}`}>Ver la ficha pública</Link>
          </span>
        </p>
      ) : (
        <>
          <p className="secundario">
            Tu huerto todavía no es visible. Para publicarlo necesita un nombre y su ubicación con coordenadas.
          </p>
          <button type="button" className="boton" onClick={publicar} disabled={envio.enviando} aria-busy={envio.enviando}>
            <Globe size={20} aria-hidden="true" />
            Publicar en el mapa
          </button>
        </>
      )}
    </section>
  )
}

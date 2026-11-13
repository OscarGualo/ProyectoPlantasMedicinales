import { useEffect, useMemo, useState, type CSSProperties } from 'react'
import { Link } from 'react-router'
import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { ArrowRight } from '@phosphor-icons/react'
import { api, mensajeDe } from '../api/cliente'
import type { ResumenHuerto } from '../api/tipos'
import { AvisoError, AvisoInfo, Esqueleto, Vacio } from '../componentes/Estado'

// Marcador con forma de etiqueta colgante y una hoja: vectorial, sin imágenes que descargar.
const HOJA_SVG =
  '<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 256 256" fill="currentColor" aria-hidden="true">' +
  '<path d="M223.45,40.07a8,8,0,0,0-7.52-7.52C139.8,28.08,78.82,51,52.82,94a87.09,87.09,0,0,0-12.76,49A101.72,101.72,0,0,0,46.7,175.2a4,4,0,0,0,6.61,1.43l85-86.3a8,8,0,0,1,11.32,11.32L56.74,195.94,42.55,210.13a8.2,8.2,0,0,0-.6,11.1,8,8,0,0,0,11.71.43l16.79-16.79c14.14,6.84,28.41,10.57,42.56,11.07q1.67.06,3.33.06A86.93,86.93,0,0,0,162,203.18C205,177.18,227.93,116.21,223.45,40.07Z"/></svg>'

const icono = L.divIcon({
  className: '',
  html: `<span class="marcador-etiqueta">${HOJA_SVG}</span>`,
  iconSize: [30, 38],
  iconAnchor: [15, 38],
  popupAnchor: [0, -36],
})

const CENTRO_ECUADOR: [number, number] = [-1.4, -78.4]

const plural = (n: number, uno: string, varios: string) => `${n} ${n === 1 ? uno : varios}`

/** HU-010: mapa del Ecuador con un marcador por cada huerto publicado, y cada huerto como su etiqueta. */
export function Mapa() {
  const [falloMapa, setFalloMapa] = useState(false)
  const [huertos, setHuertos] = useState<ResumenHuerto[] | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    api
      .get<ResumenHuerto[]>('/api/publico/huertos')
      .then(setHuertos)
      .catch((e) => setError(mensajeDe(e)))
  }, [])

  const provincias = useMemo(() => new Set(huertos?.map((h) => h.provincia).filter(Boolean)).size, [huertos])
  const plantas = useMemo(() => huertos?.reduce((s, h) => s + h.plantas, 0) ?? 0, [huertos])

  return (
    <>
      <section className="bolsa bolsa-con-ventana" aria-labelledby="titulo-inicio">
        <h1 id="titulo-inicio">Huertos medicinales del Ecuador</h1>
        <p className="bajada">
          Cada huerto comunitario, con sus plantas, su fuente y lo que siembra según la luna. Elige uno en el mapa para
          leer su etiqueta.
        </p>
        <div className="conteos" aria-live="polite">
          {huertos ? (
            <>
              <p className="conteo">
                <strong>{huertos.length}</strong>
                <span>{huertos.length === 1 ? 'huerto publicado' : 'huertos publicados'}</span>
              </p>
              <p className="conteo">
                <strong>{provincias}</strong>
                <span>{provincias === 1 ? 'provincia' : 'provincias'}</span>
              </p>
              <p className="conteo">
                <strong>{plantas}</strong>
                <span>{plantas === 1 ? 'planta registrada' : 'plantas registradas'}</span>
              </p>
            </>
          ) : (
            !error && <Esqueleto alto={52} ancho={320} estilo={{ opacity: 0.25 }} />
          )}
        </div>
      </section>

      <div className="ventana-mapa" data-testid="mapa">
        <MapContainer
          center={CENTRO_ECUADOR}
          zoom={6}
          scrollWheelZoom={false}
          className="mapa-lienzo"
          aria-label="Mapa de huertos publicados en el Ecuador"
        >
          <TileLayer
            eventHandlers={{ tileerror: () => setFalloMapa(true) }}
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">colaboradores de OpenStreetMap</a>'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {huertos?.map((h) => (
            <Marker key={h.id} position={[h.latitud, h.longitud]} icon={icono} title={h.nombre} alt={h.nombre}>
              <Popup>
                <span className="popup-titulo">{h.nombre}</span>
                <span className="secundario">
                  {[h.parroquia, h.provincia].filter(Boolean).join(', ')} · {plural(h.plantas, 'planta', 'plantas')}
                </span>
                <div style={{ marginTop: 8 }}>
                  <Link to={`/huertos/${h.id}`}>Leer la etiqueta del huerto</Link>
                </div>
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </div>

      {falloMapa && <AvisoInfo>No se pudo cargar el mapa base. Puedes consultar los huertos en la lista.</AvisoInfo>}
      <AvisoError>{error}</AvisoError>

      <h2 id="titulo-lista">Huertos publicados</h2>
      {huertos === null && !error && (
        <div className="lista-huertos" aria-hidden="true">
          {[0, 1, 2].map((i) => (
            <Esqueleto key={i} alto={220} />
          ))}
        </div>
      )}
      {huertos?.length === 0 && (
        <Vacio titulo="Todavía no hay huertos publicados">
          Cuando los líderes publiquen sus huertos, aparecerán aquí y en el mapa.
        </Vacio>
      )}
      {huertos && huertos.length > 0 && (
        <ul className="lista-huertos" aria-labelledby="titulo-lista">
          {huertos.map((h, i) => (
            <li key={h.id} className="revelar" style={{ '--orden': Math.min(i, 8) } as CSSProperties}>
              <Link className="etiqueta-enlace" to={`/huertos/${h.id}`}>
                <article className="etiqueta">
                  <div className="etiqueta-cabeza">
                    <h3 className="etiqueta-nombre">{h.nombre}</h3>
                    <p className="secundario" style={{ margin: '6px 0 0' }}>
                      {h.comunidad}
                    </p>
                  </div>
                  <dl className="campos">
                    <div>
                      <dt>Provincia</dt>
                      <dd>{h.provincia ?? 'Sin registrar'}</dd>
                    </div>
                    <div>
                      <dt>Parroquia</dt>
                      <dd>{h.parroquia ?? 'Sin registrar'}</dd>
                    </div>
                    <div>
                      <dt>Plantas</dt>
                      <dd>{h.plantas}</dd>
                    </div>
                    <div>
                      <dt>Cultivos</dt>
                      <dd>{h.cultivos}</dd>
                    </div>
                  </dl>
                  <span className="ver">
                    Leer la etiqueta
                    <ArrowRight size={18} weight="bold" aria-hidden="true" />
                  </span>
                </article>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </>
  )
}

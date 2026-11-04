import { Component, lazy, Suspense, useEffect, useMemo, useState, type ReactNode } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft } from '@phosphor-icons/react'
import { api, mensajeDe } from '../api/cliente'
import type { Cultivo, FichaPublica, Zona } from '../api/tipos'
import { AvisoError, Cargando, Esqueleto } from '../componentes/Estado'
import { SelloEstado, SenalClasificacion } from '../componentes/Senal'

const Escena3D = lazy(() => import('./escena/Escena3D'))

const MSG_13 = 'No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.'
const MSG_22 = 'Esta zona no tiene cultivos registrados.'

const NOMBRE_TIPO: Record<Zona['tipo'], string> = {
  CULTIVO: 'Zona de cultivo',
  CAMINO: 'Camino',
  CERCA: 'Cerca',
  BODEGA: 'Bodega',
}

/** HU-012 (recorrer el huerto en 3D) y HU-013 (cultivos de una zona). */
export function Recorrido3D() {
  const { id } = useParams()
  const [ficha, setFicha] = useState<FichaPublica | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [seleccionada, setSeleccionada] = useState<string | null>(null)
  const [webgl] = useState(soportaWebGL)

  useEffect(() => {
    setFicha(null)
    setError(null)
    setSeleccionada(null)
    api
      .get<FichaPublica>(`/api/publico/huertos/${id}`)
      .then(setFicha)
      .catch((e) => setError(mensajeDe(e)))
  }, [id])

  const zonasCultivo = useMemo(() => ficha?.zonas.filter((z) => z.tipo === 'CULTIVO') ?? [], [ficha])
  const estructuras = useMemo(() => ficha?.zonas.filter((z) => z.tipo !== 'CULTIVO') ?? [], [ficha])

  if (error) {
    return (
      <>
        <section className="bolsa">
          <h1>Recorrido 3D no disponible</h1>
        </section>
        <AvisoError>{error}</AvisoError>
        <Link className="boton boton-secundario" to="/">
          <ArrowLeft size={18} aria-hidden="true" />
          Volver al mapa
        </Link>
      </>
    )
  }
  if (!ficha) return <Cargando texto="Cargando el huerto…" />

  const zona = ficha.zonas.find((z) => z.codigo === seleccionada) ?? null
  const cultivosDe = (codigo: string) => ficha.cultivos.filter((c) => c.zona === codigo)
  const lista = <ListaZonas zonas={zonasCultivo} cultivosDe={cultivosDe} />

  return (
    <>
      <header className="bolsa bolsa-compacta">
        <h1>{ficha.nombre}</h1>
        <p className="bajada">
          Gira la vista con un dedo o el ratón y acércate con dos dedos o la rueda. Toca una zona para ver qué crece
          en ella.
        </p>
        <div className="acciones">
          <Link className="boton boton-secundario boton-pequeno" to={`/huertos/${ficha.id}`}>
            <ArrowLeft size={18} aria-hidden="true" />
            Volver a la etiqueta del huerto
          </Link>
        </div>
      </header>

      <div className="recorrido">
        <div className="escena etiqueta">
          {webgl ? (
            <LimiteError alternativa={<FalloEscena>{lista}</FalloEscena>}>
              <Suspense fallback={<Esqueleto alto="100%" estilo={{ minHeight: 360 }} />}>
                <Escena3D
                  zonas={ficha.zonas}
                  cultivos={ficha.cultivos}
                  seleccionada={seleccionada}
                  alSeleccionar={setSeleccionada}
                />
              </Suspense>
            </LimiteError>
          ) : (
            <FalloEscena>{lista}</FalloEscena>
          )}
        </div>

        <aside className="recorrido-panel" aria-label="Zonas del huerto">
          <h2 id="titulo-zonas">Zonas de cultivo</h2>
          <div className="zonas-botones" role="group" aria-labelledby="titulo-zonas">
            {zonasCultivo.map((z) => (
              <button
                key={z.id}
                type="button"
                className="zona-boton"
                aria-pressed={seleccionada === z.codigo}
                onClick={() => setSeleccionada(z.codigo)}
              >
                <span className="zona-boton-codigo">{z.codigo}</span>
                {z.nombre && <span>{z.nombre}</span>}
              </button>
            ))}
          </div>

          <section className="panel-zona" aria-live="polite" aria-label="Cultivos de la zona elegida">
            {zona ? (
              <div key={zona.codigo} className="panel-zona-contenido">
                <h3>
                  {zona.tipo === 'CULTIVO' ? `Zona ${zona.codigo}` : NOMBRE_TIPO[zona.tipo]}
                  {zona.nombre ? ` · ${zona.nombre}` : ''}
                </h3>
                {zona.tipo !== 'CULTIVO' ? (
                  <p className="secundario">{NOMBRE_TIPO[zona.tipo]} del huerto: aquí no se siembra.</p>
                ) : (
                  <CultivosZona cultivos={cultivosDe(zona.codigo)} />
                )}
              </div>
            ) : (
              <p className="secundario">Elige una zona en la vista o con estos botones.</p>
            )}
          </section>

          {estructuras.length > 0 && (
            <>
              <h2>En el croquis</h2>
              <ul className="leyenda">
                {estructuras.map((z) => (
                  <li key={z.id}>
                    <span className={`leyenda-muestra muestra-${z.tipo.toLowerCase()}`} aria-hidden="true" />
                    {NOMBRE_TIPO[z.tipo]}
                    {z.nombre && z.nombre !== NOMBRE_TIPO[z.tipo] ? ` · ${z.nombre}` : ''}
                  </li>
                ))}
              </ul>
            </>
          )}
        </aside>
      </div>
    </>
  )
}

function CultivosZona({ cultivos }: { cultivos: Cultivo[] }) {
  if (cultivos.length === 0) return <p className="aviso aviso-info">{MSG_22}</p>
  return (
    <ul className="lista-cultivos">
      {cultivos.map((c) => (
        <li key={c.id}>
          <span className="nombre">{c.planta}</span>
          <SenalClasificacion clasificacion={c.clasificacion} />
          <SelloEstado estado={c.estado} etiqueta={c.estadoEtiqueta} />
        </li>
      ))}
    </ul>
  )
}

/** Alternativa en lista cuando la escena no puede cargarse (HU-012 CA-02). */
function FalloEscena({ children }: { children: ReactNode }) {
  return (
    <div className="escena-fallo">
      <AvisoError>{MSG_13}</AvisoError>
      {children}
    </div>
  )
}

function ListaZonas({ zonas, cultivosDe }: { zonas: Zona[]; cultivosDe: (codigo: string) => Cultivo[] }) {
  return (
    <ul className="lista-zonas" aria-label="Zonas y cultivos del huerto">
      {zonas.map((z) => (
        <li key={z.id}>
          <h3>
            Zona {z.codigo}
            {z.nombre ? ` · ${z.nombre}` : ''}
          </h3>
          <CultivosZona cultivos={cultivosDe(z.codigo)} />
        </li>
      ))}
    </ul>
  )
}

/** Captura el fallo de la escena o de la descarga de su módulo y muestra la alternativa. */
class LimiteError extends Component<{ alternativa: ReactNode; children: ReactNode }, { fallo: boolean }> {
  state = { fallo: false }

  static getDerivedStateFromError() {
    return { fallo: true }
  }

  render() {
    return this.state.fallo ? this.props.alternativa : this.props.children
  }
}

function soportaWebGL(): boolean {
  try {
    const lienzo = document.createElement('canvas')
    return Boolean(lienzo.getContext('webgl2') ?? lienzo.getContext('webgl'))
  } catch {
    return false
  }
}

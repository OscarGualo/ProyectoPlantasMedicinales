import { useEffect, useState, type CSSProperties } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft, Cube, Medal } from '@phosphor-icons/react'
import { api, mensajeDe } from '../api/cliente'
import type { Cultivo, FichaPublica, Planta } from '../api/tipos'
import { AvisoError, Esqueleto, Vacio } from '../componentes/Estado'
import { SelloEstado, SenalClasificacion } from '../componentes/Senal'
import { laminaDe } from '../datos/laminas'

/** HU-011: la ficha pública como la etiqueta del huerto, con sus plantas y cultivos, sin datos privados. */
export function FichaHuerto() {
  const { id } = useParams()
  const [ficha, setFicha] = useState<FichaPublica | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    setFicha(null)
    setError(null)
    api
      .get<FichaPublica>(`/api/publico/huertos/${id}`)
      .then(setFicha)
      .catch((e) => setError(mensajeDe(e))) // MSG-14 si no existe o no está publicado
  }, [id])

  if (error) {
    return (
      <>
        <section className="bolsa">
          <h1>Huerto no disponible</h1>
        </section>
        <AvisoError>{error}</AvisoError>
        <Link className="boton boton-secundario" to="/">
          <ArrowLeft size={18} aria-hidden="true" />
          Volver al mapa
        </Link>
      </>
    )
  }
  if (!ficha) return <EsqueletoFicha />

  const cultivosPorZona = agrupar(ficha.cultivos)
  const laminasHuerto = ficha.plantas
    .map((p) => ({ planta: p, lamina: laminaDe(p.nombreCientifico) }))
    .filter((x): x is { planta: Planta; lamina: NonNullable<ReturnType<typeof laminaDe>> } => x.lamina !== null)
    .slice(0, 3)
  const datos: [string, string | null][] = [
    ['Comunidad', ficha.comunidad],
    ['Provincia', ficha.provincia],
    ['Parroquia', ficha.parroquia],
    ['Dirección', ficha.direccion],
    ['Superficie', ficha.superficieM2 != null ? `${ficha.superficieM2} m²` : null],
    ['Diseño', ficha.diseno],
    ['Cerca', ficha.tipoCerca],
    ['Suelo', ficha.tipoSuelo],
    ['Fuente de agua', ficha.fuenteAgua],
    ['Técnica de riego', ficha.tecnicaRiego],
    ['Abono', ficha.tipoAbono],
    ['Hora del huerto', ficha.zonaHoraria === 'Pacific/Galapagos' ? 'Galápagos (UTC-6)' : 'Ecuador continental (UTC-5)'],
  ]

  return (
    <article>
      <header className={`bolsa${laminasHuerto.length > 0 ? ' bolsa-con-laminas' : ''}`}>
        <div className="bolsa-texto">
        <h1>{ficha.nombre}</h1>
        {ficha.descripcion && <p className="bajada">{ficha.descripcion}</p>}
        <div className="conteos">
          <p className="conteo">
            <strong>{ficha.plantas.length}</strong>
            <span>{ficha.plantas.length === 1 ? 'planta' : 'plantas'}</span>
          </p>
          <p className="conteo">
            <strong>{ficha.cultivos.length}</strong>
            <span>{ficha.cultivos.length === 1 ? 'cultivo' : 'cultivos'}</span>
          </p>
          <p className="conteo">
            <strong>{cultivosPorZona.length}</strong>
            <span>{cultivosPorZona.length === 1 ? 'zona sembrada' : 'zonas sembradas'}</span>
          </p>
        </div>
        <div className="acciones">
          <Link className="boton" to={`/huertos/${ficha.id}/3d`}>
            <Cube size={20} aria-hidden="true" />
            Recorrer en 3D
          </Link>
          <Link className="boton boton-secundario" to={`/huertos/${ficha.id}/reto`}>
            <Medal size={20} aria-hidden="true" />
            Reto Documentación lunar
          </Link>
        </div>
        </div>
        {laminasHuerto.length > 0 && (
          <figure className="laminas-huerto">
            <div className="laminas-fila">
              {laminasHuerto.map(({ planta, lamina }) => (
                <img
                  key={planta.id}
                  src={lamina.src}
                  alt={lamina.pie}
                  width={400}
                  height={560}
                  decoding="async"
                />
              ))}
            </div>
            <figcaption>
              Láminas de especies que crecen en este huerto:{' '}
              {laminasHuerto.map(({ planta, lamina }) => `${planta.nombreComun} (${lamina.autoria})`).join(', ')}
            </figcaption>
          </figure>
        )}
      </header>

      <h2 id="titulo-etiqueta">Etiqueta del huerto</h2>
      <section className="etiqueta revelar" aria-labelledby="titulo-etiqueta">
        <dl className="campos campos-4">
          {datos
            .filter(([, valor]) => valor)
            .map(([nombre, valor]) => (
              <div key={nombre}>
                <dt>{nombre}</dt>
                <dd>{valor}</dd>
              </div>
            ))}
        </dl>
      </section>

      <h2 id="titulo-plantas">Plantas medicinales</h2>
      {ficha.plantas.length === 0 ? (
        <Vacio titulo="Este huerto aún no registra plantas" vineta="maceta" />
      ) : (
        <ul className="plantas" aria-labelledby="titulo-plantas">
          {ficha.plantas.map((p, i) => (
            <EtiquetaPlanta key={p.id} planta={p} orden={i} />
          ))}
        </ul>
      )}

      <h2 id="titulo-cultivos">Qué crece en cada zona</h2>
      {cultivosPorZona.length === 0 ? (
        <Vacio titulo="Este huerto aún no registra cultivos" vineta="semillas" />
      ) : (
        <div className="etiqueta zonas revelar">
          {cultivosPorZona.map(([zona, cultivos]) => (
            <section key={zona} className="zona" aria-label={`Zona ${zona}`}>
              <span className="zona-codigo" aria-hidden="true">
                {zona}
              </span>
              <div>
                <h3>
                  {cultivos.length === 1 ? 'Un cultivo' : `${cultivos.length} cultivos`}
                  <span className="solo-lectores"> en la zona {zona}</span>
                </h3>
                <ul className="lista-cultivos">
                  {cultivos.map((c) => (
                    <li key={c.id}>
                      <span className="nombre">{c.planta}</span>
                      <SenalClasificacion clasificacion={c.clasificacion} />
                      <SelloEstado estado={c.estado} etiqueta={c.estadoEtiqueta} />
                    </li>
                  ))}
                </ul>
              </div>
            </section>
          ))}
        </div>
      )}
    </article>
  )
}

/** La planta como su etiqueta: fotografía propia o lámina de la especie, campos y sello de clasificación. */
function EtiquetaPlanta({ planta, orden }: { planta: Planta; orden: number }) {
  const lamina = planta.fotoUrl ? null : laminaDe(planta.nombreCientifico)
  return (
    <li className="revelar" style={{ '--orden': Math.min(orden, 8) } as CSSProperties}>
      <article className={`etiqueta planta${planta.fotoUrl || lamina ? '' : ' planta-sin-figura'}`}>
        {planta.fotoUrl ? (
          <figure className="planta-figura">
            <img
              src={planta.fotoUrl}
              alt={`Fotografía de ${planta.nombreComun} en el huerto`}
              loading="lazy"
              decoding="async"
              width={600}
              height={800}
            />
          </figure>
        ) : lamina ? (
          <figure className="planta-figura">
            <img src={lamina.src} alt={lamina.pie} loading="lazy" decoding="async" width={600} height={800} />
            <figcaption>
              {lamina.pie} ({lamina.autoria}).{lamina.fuente && <> <a href={lamina.fuente}>Fuente</a></>}
            </figcaption>
          </figure>
        ) : null}

        <div className="planta-cuerpo">
          <div className="cabeza">
            <div>
              <h3 className="etiqueta-nombre">{planta.nombreComun}</h3>
              <p className="cientifico">{planta.nombreCientifico}</p>
            </div>
            <SenalClasificacion clasificacion={planta.clasificacion} redondo orden={orden} />
          </div>
          <dl className="campos">
            <div>
              <dt>Familia</dt>
              <dd>{planta.familia}</dd>
            </div>
            <div>
              <dt>Ciclo</dt>
              <dd>{planta.ciclo === 'CORTO' ? 'Corto' : planta.ciclo === 'LARGO' ? 'Largo' : 'Sin indicar'}</dd>
            </div>
            {planta.otrosNombres && (
              <div className="ancho-completo">
                <dt>Otros nombres</dt>
                <dd>{planta.otrosNombres}</dd>
              </div>
            )}
            {planta.usos && (
              <div className="ancho-completo">
                <dt>Usos aprobados por el líder</dt>
                <dd>{planta.usos}</dd>
              </div>
            )}
          </dl>
          {planta.descripcion && <p style={{ margin: 0 }}>{planta.descripcion}</p>}
          <p className="fuente">Fuente: {planta.fuente}</p>
        </div>
      </article>
    </li>
  )
}

function EsqueletoFicha() {
  return (
    <div role="status" aria-live="polite" style={{ display: 'grid', gap: 16 }}>
      <span className="solo-lectores">Cargando la etiqueta del huerto…</span>
      <Esqueleto alto={320} />
      <Esqueleto alto={180} />
      <div className="plantas" aria-hidden="true">
        {[0, 1].map((i) => (
          <Esqueleto key={i} alto={340} />
        ))}
      </div>
    </div>
  )
}

function agrupar(cultivos: Cultivo[]): [string, Cultivo[]][] {
  const grupos = new Map<string, Cultivo[]>()
  for (const c of cultivos) {
    grupos.set(c.zona, [...(grupos.get(c.zona) ?? []), c])
  }
  return [...grupos.entries()]
}

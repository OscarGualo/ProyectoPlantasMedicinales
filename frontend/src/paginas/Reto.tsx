import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft, CalendarBlank } from '@phosphor-icons/react'
import { api, mensajeDe } from '../api/cliente'
import type { Reto as DatosReto } from '../api/tipos'
import { AvisoError, AvisoInfo, Cargando } from '../componentes/Estado'

/**
 * HU-018: reto Documentación lunar de la comunidad. La insignia se calcula en el servidor en cada consulta con la
 * versión vigente de cada siembra (RN-08); aquí solo se muestra. Público, con o sin sesión.
 */
export function Reto() {
  const { id } = useParams()
  const [reto, setReto] = useState<DatosReto | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    setReto(null)
    setError(null)
    api
      .get<DatosReto>(`/api/publico/huertos/${id}/reto`)
      .then(setReto)
      .catch((e) => setError(mensajeDe(e))) // MSG-14 si el huerto no existe o no está publicado
  }, [id])

  if (error) {
    return (
      <>
        <section className="bolsa bolsa-compacta">
          <h1>Reto no disponible</h1>
        </section>
        <AvisoError>{error}</AvisoError>
        <Link className="boton boton-secundario" to="/">
          <ArrowLeft size={18} aria-hidden="true" />
          Volver al mapa
        </Link>
      </>
    )
  }
  if (!reto) return <Cargando texto="Consultando el reto…" />

  const { insignia } = reto
  return (
    <article>
      <header className="bolsa bolsa-compacta">
        <h1>Reto {reto.reto}</h1>
        <p className="bajada">
          {reto.comunidad} · {reto.huerto}
        </p>
        <div className="acciones">
          <Link className="boton boton-secundario boton-pequeno" to={`/huertos/${reto.huertoId}`}>
            <ArrowLeft size={18} aria-hidden="true" />
            Volver a la etiqueta del huerto
          </Link>
        </div>
      </header>

      <div className="reto">
        <section className="etiqueta reto-estado" aria-labelledby="titulo-insignia">
          <h2 id="titulo-insignia">{insignia ? 'Insignia obtenida' : 'Insignia pendiente'}</h2>
          {insignia ? (
            <>
              <p className="sello sello-insignia" aria-label={`Insignia ${insignia.nombre} de ${reto.comunidad}`}>
                <span>{insignia.nombre}</span>
                <small>{reto.comunidad}</small>
              </p>
              <figure className="reto-sustento">
                <img src={insignia.sustento.fotoUrl} alt={`Siembra de ${insignia.sustento.planta}`} width={320} height={240} />
                <figcaption>
                  <strong>
                    La sustenta la siembra de {insignia.sustento.planta} en la zona {insignia.sustento.zona}
                  </strong>
                  <span>
                    {formatoFechaHora(insignia.sustento.fechaHechoLocal)}
                    {insignia.sustento.fase && ` · ${insignia.sustento.fase}`}
                  </span>
                  <span>{insignia.sustento.condicion}</span>
                </figcaption>
              </figure>
            </>
          ) : (
            <div className="reto-pendiente">
              {/* Decorativa: la luna creciente que la siembra está esperando. */}
              <img
                className="grabado-luna"
                src="/arte/luna/creciente.webp"
                alt=""
                width={120}
                height={120}
                loading="lazy"
                decoding="async"
              />
              <AvisoInfo>{reto.mensaje}</AvisoInfo>
            </div>
          )}
        </section>

        <section className="etiqueta reto-practica" aria-labelledby="titulo-practica">
          <h2 id="titulo-practica">La práctica que reconoce</h2>
          <p>{reto.explicacion}</p>
          <p className="secundario">{reto.alcance}</p>
          <p className="fuente">Fuente: {reto.fuente}.</p>
          <Link className="enlace-accion" to="/calendario">
            <CalendarBlank size={18} aria-hidden="true" />
            Ver los días de luna creciente y luna llena en el calendario
          </Link>
        </section>
      </div>
    </article>
  )
}

/** «2026-10-10T08:00» → «10/10/2026 a las 08:00» (hora del huerto). */
function formatoFechaHora(local: string) {
  const [f, h] = local.split('T')
  const [a, m, d] = f.split('-')
  return `${d}/${m}/${a} a las ${h}`
}

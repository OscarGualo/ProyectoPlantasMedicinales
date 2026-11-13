import { useEffect, useState } from 'react'
import { api, mensajeDe } from '../../api/cliente'
import type { HuertoGestion } from '../../api/tipos'
import { AvisoError, Cargando } from '../../componentes/Estado'
import { Link, NavLink, Outlet, useOutletContext } from 'react-router'
import { Eye, Flower, House, Plant } from '@phosphor-icons/react'
import { useSesion } from '../../sesion/ContextoSesion'
import { Vacio } from '../../componentes/Estado'

interface ContextoGestion {
  huertoId: number
  zonaHoraria: string
  huerto: HuertoGestion
  actualizarHuerto: (huerto: HuertoGestion) => void
}

/**
 * Contenedor de las pantallas del líder (modo Operate): hereda el campo verde de la bolsa en la cabecera, con
 * las pestañas como su borde inferior. En la versión 1 cada líder gestiona un huerto (D-03).
 */
export function Gestion() {
  const { sesion } = useSesion()
  const huerto = sesion.huertos[0]
  const [datos, setDatos] = useState<HuertoGestion | null>(null)
  const [error, setError] = useState<string | null>(null)
  useEffect(() => {
    let vigente = true
    setDatos(null)
    setError(null)
    if (huerto) api.get<HuertoGestion>(`/api/huertos/${huerto.id}`)
      .then((h) => { if (vigente) setDatos(h) })
      .catch((e) => { if (vigente) setError(mensajeDe(e)) })
    return () => { vigente = false }
  }, [huerto?.id])

  if (!huerto) {
    return (
      <Vacio titulo="Tu cuenta no tiene un huerto asignado">
        Comunícate con el Ministerio de Salud Pública para que te asignen tu huerto.
      </Vacio>
    )
  }

  if (error) return <AvisoError>{error}</AvisoError>
  if (!datos || datos.id !== huerto.id) return <Cargando texto="Cargando el huerto…" />

  return (
    <>
      <header className="cabeza-gestion">
        <div className="fila">
          <div>
            <h1>{huerto.nombre ?? 'Mi huerto'}</h1>
            <p>Gestiona los datos de tu huerto y su información institucional. La consulta pública depende de su publicación.</p>
          </div>
          <Link className="boton boton-tenue boton-pequeno" to={`/huertos/${huerto.id}`}>
            <Eye size={18} aria-hidden="true" />
            Ver etiqueta pública
          </Link>
        </div>
        <nav className="pestanas" aria-label="Gestión del huerto">
          <NavLink to="/gestion" end>
            <House size={18} aria-hidden="true" />
            Huerto
          </NavLink>
          <NavLink to="/gestion/plantas">
            <Flower size={18} aria-hidden="true" />
            Plantas
          </NavLink>
          <NavLink to="/gestion/cultivos">
            <Plant size={18} aria-hidden="true" />
            Cultivos
          </NavLink>
        </nav>
      </header>
      <Outlet context={{ huertoId: huerto.id, zonaHoraria: datos.zonaHoraria,
        huerto: datos, actualizarHuerto: setDatos } satisfies ContextoGestion} />
    </>
  )
}

export function useHuertoGestionado(): number {
  return useOutletContext<ContextoGestion>().huertoId
}

export function useZonaHorariaHuerto(): string {
  return useOutletContext<ContextoGestion>().zonaHoraria
}

export function useDatosHuertoGestionado() {
  return useOutletContext<ContextoGestion>()
}

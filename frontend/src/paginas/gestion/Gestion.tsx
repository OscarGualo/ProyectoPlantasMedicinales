import { Link, NavLink, Outlet, useOutletContext } from 'react-router'
import { Eye, Flower, House, Plant } from '@phosphor-icons/react'
import { useSesion } from '../../sesion/ContextoSesion'
import { Vacio } from '../../componentes/Estado'

interface ContextoGestion {
  huertoId: number
}

/**
 * Contenedor de las pantallas del líder (modo Operate): hereda el campo verde de la bolsa en la cabecera, con
 * las pestañas como su borde inferior. En la versión 1 cada líder gestiona un huerto (D-03).
 */
export function Gestion() {
  const { sesion } = useSesion()
  const huerto = sesion.huertos[0]

  if (!huerto) {
    return (
      <Vacio titulo="Tu cuenta no tiene un huerto asignado">
        Comunícate con el Ministerio de Salud Pública para que te asignen tu huerto.
      </Vacio>
    )
  }

  return (
    <>
      <header className="cabeza-gestion">
        <div className="fila">
          <div>
            <h1>{huerto.nombre ?? 'Mi huerto'}</h1>
            <p>Lo que registras aquí aparece en la etiqueta pública de tu huerto.</p>
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
      <Outlet context={{ huertoId: huerto.id } satisfies ContextoGestion} />
    </>
  )
}

export function useHuertoGestionado(): number {
  return useOutletContext<ContextoGestion>().huertoId
}

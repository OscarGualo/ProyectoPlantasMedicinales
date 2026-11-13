import { useEffect, useRef, useState } from 'react'
import { Link, NavLink, Outlet, useLocation, useNavigate } from 'react-router'
import { CalendarDots, Leaf, MapTrifold, Plant, SignIn, SignOut } from '@phosphor-icons/react'
import { useSesion } from '../sesion/ContextoSesion'
import { CREDITO_LAMINAS } from '../datos/laminas'

/**
 * Estructura común: barra plana en el verde de la bolsa (marca, tres destinos, sesión). En celulares la
 * navegación baja a una barra inferior con ícono y texto; cerrar sesión queda separado de ella.
 */
export function Diseno() {
  const { sesion, cerrar } = useSesion()
  const [errorSalida, setErrorSalida] = useState<string | null>(null)
  const [saliendo, setSaliendo] = useState(false)
  const navegar = useNavigate()
  const { pathname } = useLocation()
  const principal = useRef<HTMLElement>(null)
  const primeraCarga = useRef(true)

  // Al cambiar de pantalla, el foco va al contenido para los lectores de pantalla.
  useEffect(() => {
    if (primeraCarga.current) {
      primeraCarga.current = false
      return
    }
    principal.current?.focus({ preventScroll: true })
    window.scrollTo({ top: 0 })
  }, [pathname])

  async function salir() {
    if (saliendo) return
    setSaliendo(true)
    setErrorSalida(null)
    try {
      await cerrar()
      navegar('/')
    } catch {
      setErrorSalida('No se confirmó el cierre de sesión. Revisa tu conexión y vuelve a intentarlo.')
    } finally {
      setSaliendo(false)
    }
  }

  const destinos = [
    { a: '/', texto: 'Mapa', icono: MapTrifold, fin: true },
    { a: '/calendario', texto: 'Calendario lunar', corto: 'Calendario', icono: CalendarDots, fin: false },
    sesion.autenticado
      ? { a: '/gestion', texto: 'Mi huerto', icono: Plant, fin: false }
      : { a: '/ingresar', texto: 'Ingresar', icono: SignIn, fin: false },
  ]

  return (
    <div className="pagina">
      <a className="salto" href="#contenido">
        Saltar al contenido
      </a>

      <header className="barra">
        <div className="barra-interior">
          <Link to="/" className="marca">
            <Leaf size={22} weight="fill" aria-hidden="true" />
            Huertos Medicinales
          </Link>

          <nav className="nav-escritorio" aria-label="Principal">
            {destinos.map(({ a, texto, icono: Icono, fin }) => (
              <NavLink key={a} to={a} end={fin}>
                <Icono size={20} aria-hidden="true" />
                {texto}
              </NavLink>
            ))}
          </nav>

          {sesion.autenticado && (
            <button
              type="button"
              className="boton boton-tenue boton-pequeno boton-salir"
              onClick={salir}
              disabled={saliendo}
              aria-label="Cerrar sesión"
            >
              <SignOut size={20} aria-hidden="true" />
              <span className="solo-escritorio">Cerrar sesión</span>
            </button>
          )}
        </div>
      </header>

      <div className="lienzo">
        <main id="contenido" className="contenido" ref={principal} tabIndex={-1}>
          {errorSalida && <p className="aviso aviso-error" role="alert">{errorSalida}</p>}
          <Outlet />
        </main>
      </div>

      <footer className="pie">
        <div className="pie-interior">
          <p className="pie-nombre">Huertos Medicinales Comunitarios</p>
          <p>
            Aplicación educativa de huertos medicinales con información de los lineamientos del MSP. El contenido se atribuye a su
            fuente y no reemplaza la atención de salud.
          </p>
          <p>
            Láminas botánicas: {CREDITO_LAMINAS}, vía{' '}
            <a href="https://commons.wikimedia.org/wiki/Category:K%C3%B6hler%27s_Medizinal-Pflanzen">
              Wikimedia Commons
            </a>
            . Mapa: © colaboradores de OpenStreetMap.
          </p>
        </div>
      </footer>

      <nav className="nav-inferior" aria-label="Principal (móvil)">
        {destinos.map(({ a, texto, corto, icono: Icono, fin }) => (
          <NavLink key={a} to={a} end={fin}>
            {({ isActive }) => (
              <>
                <Icono size={24} weight={isActive ? 'fill' : 'regular'} aria-hidden="true" />
                {corto ?? texto}
              </>
            )}
          </NavLink>
        ))}
      </nav>
    </div>
  )
}

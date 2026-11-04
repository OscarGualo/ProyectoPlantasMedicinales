import { Navigate, Outlet, useLocation } from 'react-router'
import { useSesion } from './ContextoSesion'
import { Cargando } from '../componentes/Estado'

/** Pantallas de gestión: sin sesión se redirige al inicio de sesión. El servidor vuelve a verificarlo. */
export function RutaLider() {
  const { sesion, cargando } = useSesion()
  const ubicacion = useLocation()
  if (cargando) return <Cargando />
  if (!sesion.autenticado) {
    return <Navigate to="/ingresar" replace state={{ desde: ubicacion.pathname }} />
  }
  return <Outlet />
}

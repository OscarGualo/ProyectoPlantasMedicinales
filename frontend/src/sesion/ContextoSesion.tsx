import { createContext, useCallback, useContext, useEffect, useMemo, useState, type ReactNode } from 'react'
import { api, SESION_FINALIZADA } from '../api/cliente'
import type { Sesion } from '../api/tipos'

const ANONIMA: Sesion = { autenticado: false, id: null, usuario: null, nombre: null, huertos: [] }

interface ValorSesion {
  sesion: Sesion
  cargando: boolean
  /** true cuando el servidor informó MSG-02 y hay que pedir un nuevo inicio de sesión. */
  finalizada: boolean
  iniciar: (usuario: string, contrasena: string) => Promise<Sesion>
  cerrar: () => Promise<void>
}

const Contexto = createContext<ValorSesion | null>(null)

export function ProveedorSesion({ children }: { children: ReactNode }) {
  const [sesion, setSesion] = useState<Sesion>(ANONIMA)
  const [cargando, setCargando] = useState(true)
  const [finalizada, setFinalizada] = useState(false)

  // La primera consulta también entrega la cookie XSRF-TOKEN que exigen los envíos posteriores.
  useEffect(() => {
    api
      .get<Sesion>('/api/sesion')
      .then(setSesion)
      .catch(() => setSesion(ANONIMA))
      .finally(() => setCargando(false))
  }, [])

  useEffect(() => {
    const alFinalizar = () => {
      setSesion(ANONIMA)
      setFinalizada(true)
    }
    window.addEventListener(SESION_FINALIZADA, alFinalizar)
    return () => window.removeEventListener(SESION_FINALIZADA, alFinalizar)
  }, [])

  const iniciar = useCallback(async (usuario: string, contrasena: string) => {
    const nueva = await api.post<Sesion>('/api/sesion', { usuario, contrasena })
    setSesion(nueva)
    setFinalizada(false)
    return nueva
  }, [])

  const cerrar = useCallback(async () => {
    await api.delete('/api/sesion')
    setSesion(ANONIMA)
    setFinalizada(false)
  }, [])

  const valor = useMemo(
    () => ({ sesion, cargando, finalizada, iniciar, cerrar }),
    [sesion, cargando, finalizada, iniciar, cerrar],
  )
  return <Contexto.Provider value={valor}>{children}</Contexto.Provider>
}

export function useSesion(): ValorSesion {
  const valor = useContext(Contexto)
  if (!valor) throw new Error('useSesion debe usarse dentro de ProveedorSesion')
  return valor
}

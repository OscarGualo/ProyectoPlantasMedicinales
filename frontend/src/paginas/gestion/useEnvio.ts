import { useCallback, useEffect, useRef, useState } from 'react'
import { ErrorApi, mensajeDe } from '../../api/cliente'

const DURACION_EXITO_MS = 4000

/**
 * Estado de un envío de formulario. Si el servidor señala un campo, el error se muestra debajo de ese campo
 * y el foco se mueve a él; si no, se muestra como aviso general. El aviso de éxito se retira solo.
 */
export function useEnvio() {
  const [enviando, setEnviando] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [campo, setCampo] = useState<string | null>(null)
  const [exito, setExito] = useState<string | null>(null)
  const temporizador = useRef<number | undefined>(undefined)

  useEffect(() => () => window.clearTimeout(temporizador.current), [])

  const enviar = useCallback(async <T,>(accion: () => Promise<T>, mensajeExito: string): Promise<T | null> => {
    setEnviando(true)
    setError(null)
    setCampo(null)
    setExito(null)
    window.clearTimeout(temporizador.current)
    try {
      const resultado = await accion()
      setExito(mensajeExito)
      temporizador.current = window.setTimeout(() => setExito(null), DURACION_EXITO_MS)
      return resultado
    } catch (e) {
      const nombre = e instanceof ErrorApi ? e.campo : null
      setError(mensajeDe(e))
      setCampo(nombre)
      if (nombre) {
        // Tras un envío fallido, el foco va al primer campo inválido.
        requestAnimationFrame(() =>
          document.querySelector<HTMLElement>(`[data-campo="${nombre}"]`)?.focus(),
        )
      }
      return null
    } finally {
      setEnviando(false)
    }
  }, [])

  /** Error para mostrar debajo de un campo concreto. */
  const errorDe = useCallback((...nombres: string[]) => (campo && nombres.includes(campo) ? error : null), [campo, error])
  /** Error general (sin campo asociado) para el aviso superior. */
  const errorGeneral = campo ? null : error

  return { enviando, error, campo, exito, enviar, errorDe, errorGeneral }
}

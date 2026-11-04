import type { ReactNode } from 'react'
import { CircleNotch } from '@phosphor-icons/react'

/** Botón de envío que se deshabilita y muestra progreso mientras espera al servidor. */
export function BotonEnviar({ enviando, icono, children, textoEnviando = 'Guardando…' }: {
  enviando: boolean
  icono?: ReactNode
  children: ReactNode
  textoEnviando?: string
}) {
  return (
    <button type="submit" className="boton" disabled={enviando} aria-busy={enviando}>
      {enviando ? <CircleNotch size={20} className="girando" aria-hidden="true" /> : icono}
      {enviando ? textoEnviando : children}
    </button>
  )
}

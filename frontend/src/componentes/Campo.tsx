import { useId, type ReactElement, type ReactNode } from 'react'
import { WarningCircle } from '@phosphor-icons/react'

export interface PropsControl {
  id: string
  'aria-invalid': boolean
  'aria-describedby'?: string
  'aria-required'?: boolean
}

interface PropsCampo {
  etiqueta: string
  /** Mensaje de error del servidor para este campo (texto exacto del catálogo MSG). */
  error?: string | null
  ayuda?: ReactNode
  obligatorio?: boolean
  className?: string
  /** Recibe los atributos de accesibilidad que conectan el control con su etiqueta, ayuda y error. */
  children: (props: PropsControl) => ReactElement
}

/**
 * Campo con etiqueta visible, ayuda persistente y error debajo del control conectado con
 * aria-describedby (ui-ux-pro-max: «Error Placement», severidad alta).
 */
export function Campo({ etiqueta, error, ayuda, obligatorio, className = '', children }: PropsCampo) {
  const id = useId()
  const idAyuda = ayuda ? `${id}-ayuda` : undefined
  const idError = error ? `${id}-error` : undefined
  const describe = [idError, idAyuda].filter(Boolean).join(' ') || undefined

  return (
    <div className={`campo ${className}`}>
      <label className="campo-etiqueta" htmlFor={id}>
        {etiqueta}
        {obligatorio && (
          <span className="obligatorio" aria-hidden="true">
            *
          </span>
        )}
      </label>
      {children({
        id,
        'aria-invalid': Boolean(error),
        'aria-describedby': describe,
        'aria-required': obligatorio || undefined,
      })}
      {error && (
        <p className="campo-error" id={idError}>
          <WarningCircle size={16} weight="fill" aria-hidden="true" />
          {error}
        </p>
      )}
      {ayuda && (
        <p className="campo-ayuda" id={idAyuda}>
          {ayuda}
        </p>
      )}
    </div>
  )
}

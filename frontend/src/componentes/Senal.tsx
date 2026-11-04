import type { CSSProperties } from 'react'
import type { Clasificacion, EstadoCultivo } from '../api/tipos'

/**
 * Señalética caliente y fría (HU-013 CA-02) como sello de tinta: rojo caliente, verde fría, gris sin clasificar.
 * El estado es una marca con texto, nunca solo un color.
 */
export function SenalClasificacion({ clasificacion, redondo = false, orden }: {
  clasificacion: Clasificacion | null
  redondo?: boolean
  orden?: number
}) {
  const clase = clasificacion === 'CALIENTE' ? 'caliente' : clasificacion === 'FRIA' ? 'fria' : 'neutro'
  const texto = clasificacion === 'CALIENTE' ? 'Caliente' : clasificacion === 'FRIA' ? 'Fría' : 'Sin clasificar'
  return (
    <span
      className={`sello sello-${clase}${redondo ? ' sello-redondo' : ''}`}
      style={orden !== undefined ? ({ '--orden': orden } as CSSProperties) : undefined}
    >
      {texto}
    </span>
  )
}

/** Estado del cultivo como sello (RN-03): sembrado, en crecimiento, cosechado o perdido. */
export function SelloEstado({ estado, etiqueta }: { estado: EstadoCultivo; etiqueta: string }) {
  return <span className={`sello sello-${estado.toLowerCase()}`}>{etiqueta}</span>
}

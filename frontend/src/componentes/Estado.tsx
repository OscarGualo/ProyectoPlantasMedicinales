import type { CSSProperties, ReactNode } from 'react'
import { CheckCircle, Info, Plant, WarningCircle } from '@phosphor-icons/react'

/** Aviso de error con el texto exacto del catálogo MSG. Se anuncia a los lectores de pantalla. */
export function AvisoError({ children, titulo }: { children: ReactNode; titulo?: string }) {
  if (!children) return null
  return (
    <div className="aviso aviso-error" role="alert">
      <WarningCircle size={20} weight="fill" aria-hidden="true" />
      <div>
        {titulo && <span className="aviso-titulo">{titulo}</span>}
        {children}
      </div>
    </div>
  )
}

export function AvisoExito({ children }: { children: ReactNode }) {
  if (!children) return null
  return (
    <div className="aviso aviso-exito" role="status" aria-live="polite">
      <CheckCircle size={20} weight="fill" aria-hidden="true" />
      <div>{children}</div>
    </div>
  )
}

export function AvisoInfo({ children }: { children: ReactNode }) {
  if (!children) return null
  return (
    <div className="aviso aviso-info">
      <Info size={20} weight="fill" aria-hidden="true" />
      <div>{children}</div>
    </div>
  )
}

/** Viñetas grabadas de los estados vacíos (public/arte/vacios, generadas con IA y acreditadas). */
export type Vineta = 'maceta' | 'semillas' | 'mapa' | 'lupa' | 'regadera'

/**
 * Estado vacío con una indicación de qué hacer a continuación. Con `vineta`, la acompaña una viñeta grabada en
 * lugar del ícono; es decorativa (alt vacío) porque el título ya dice lo que pasa.
 */
export function Vacio({ titulo, children, icono, vineta }: {
  titulo: string
  children?: ReactNode
  icono?: ReactNode
  vineta?: Vineta
}) {
  return (
    <div className={`vacio${vineta ? ' vacio-ilustrado' : ''}`}>
      {vineta ? (
        <img className="vineta" src={`/arte/vacios/${vineta}.webp`} alt="" width={240} height={240} decoding="async" />
      ) : (
        (icono ?? <Plant size={32} weight="duotone" aria-hidden="true" />)
      )}
      <div className="vacio-texto">
        <strong>{titulo}</strong>
        {children && <p>{children}</p>}
      </div>
    </div>
  )
}

/** Bloque de carga que reserva el espacio del contenido para evitar saltos (CLS). */
export function Esqueleto({ alto = 16, ancho = '100%', estilo }: {
  alto?: number | string
  ancho?: number | string
  estilo?: CSSProperties
}) {
  return <span className="esqueleto" aria-hidden="true" style={{ height: alto, width: ancho, ...estilo }} />
}

export function Cargando({ texto = 'Cargando…', lineas = 3 }: { texto?: string; lineas?: number }) {
  return (
    <div role="status" aria-live="polite" style={{ display: 'grid', gap: 12 }}>
      <span className="solo-lectores">{texto}</span>
      <Esqueleto alto={32} ancho="55%" />
      {Array.from({ length: lineas }, (_, i) => (
        <Esqueleto key={i} alto={16} ancho={`${90 - i * 12}%`} />
      ))}
    </div>
  )
}

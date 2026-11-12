import { useEffect, useRef, useState, type ReactNode } from 'react'
import { Pause, Play } from '@phosphor-icons/react'

/** Coincidencia en vivo con una consulta de medios (sin parpadeo en el primer pintado: es CSR). */
export function useCoincide(consulta: string) {
  const [coincide, setCoincide] = useState(() => window.matchMedia(consulta).matches)
  useEffect(() => {
    const mq = window.matchMedia(consulta)
    const cambiar = () => setCoincide(mq.matches)
    mq.addEventListener('change', cambiar)
    return () => mq.removeEventListener('change', cambiar)
  }, [consulta])
  return coincide
}

interface Props {
  /** Imagen fija; también es el póster del video. */
  src: string
  srcSet?: string
  sizes?: string
  ancho: number
  alto: number
  alt: string
  /** Video en bucle, mudo, con movimiento mínimo. Si falta o hay movimiento reducido, se queda la imagen. */
  video?: string
  pie: ReactNode
  className?: string
}

/**
 * Lámina en el campo verde: papel troquelado con su pie. Con `video`, la lámina «respira» (hojas que se mecen)
 * y lleva un botón para pausarla (WCAG 2.2.2); con prefers-reduced-motion no se reproduce.
 */
export function LaminaViva({ src, srcSet, sizes, ancho, alto, alt, video, pie, className = '' }: Props) {
  const reducido = useCoincide('(prefers-reduced-motion: reduce)')
  const conVideo = Boolean(video) && !reducido
  const ref = useRef<HTMLVideoElement>(null)
  const [enPausa, setEnPausa] = useState(false)

  function alternar() {
    const v = ref.current
    if (!v) return
    if (v.paused) {
      void v.play()
      setEnPausa(false)
    } else {
      v.pause()
      setEnPausa(true)
    }
  }

  return (
    <figure className={`lamina-viva ${className}`}>
      <div className="lamina-viva-papel">
        {conVideo ? (
          <video
            ref={ref}
            src={video}
            poster={src}
            width={ancho}
            height={alto}
            autoPlay
            muted
            loop
            playsInline
            preload="metadata"
            aria-label={alt}
          />
        ) : (
          <img
            src={src}
            srcSet={srcSet}
            sizes={sizes}
            width={ancho}
            height={alto}
            alt={alt}
            decoding="async"
            fetchPriority="high"
          />
        )}
        {conVideo && (
          <button
            type="button"
            className="lamina-viva-pausa"
            onClick={alternar}
            aria-label={enPausa ? 'Reproducir la lámina' : 'Pausar la lámina'}
            aria-pressed={enPausa}
          >
            {enPausa ? <Play size={18} weight="fill" aria-hidden="true" /> : <Pause size={18} weight="fill" aria-hidden="true" />}
          </button>
        )}
      </div>
      <figcaption>{pie}</figcaption>
    </figure>
  )
}

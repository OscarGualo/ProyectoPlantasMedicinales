import type { ImgHTMLAttributes } from 'react'

/** Marca la imagen como cargada (también si falla, para que se vea su texto alternativo). */
function marcar(img: HTMLImageElement | null) {
  if (img) img.dataset.cargada = ''
}

/**
 * Imagen que entra con un fundido breve al terminar de cargar (MOTION_GUIDELINES.md, A): en conexiones lentas la
 * lámina no «salta». Reserva su espacio con width/height como cualquier <img>. Con movimiento reducido el fundido
 * desaparece (estilos.css).
 */
export function Imagen({ className = '', onLoad, onError, ...props }: ImgHTMLAttributes<HTMLImageElement>) {
  return (
    <img
      {...props}
      className={`fundido ${className}`.trim()}
      // Si ya estaba en caché, «load» pudo ocurrir antes de enlazar el manejador: se revisa al montar.
      ref={(img) => {
        if (img?.complete) marcar(img)
      }}
      onLoad={(e) => {
        marcar(e.currentTarget)
        onLoad?.(e)
      }}
      onError={(e) => {
        marcar(e.currentTarget)
        onError?.(e)
      }}
    />
  )
}

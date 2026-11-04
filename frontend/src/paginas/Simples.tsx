import { Link } from 'react-router'
import { ArrowLeft, Compass, Hammer } from '@phosphor-icons/react'
import { Vacio } from '../componentes/Estado'

export function NoEncontrado() {
  return (
    <>
      <h1>Página no encontrada</h1>
      <Vacio titulo="La dirección que abriste no existe" icono={<Compass size={36} weight="duotone" aria-hidden="true" />}>
        Revisa el enlace o vuelve al mapa para encontrar un huerto.
      </Vacio>
      <div className="acciones">
        <Link className="boton" to="/">
          <ArrowLeft size={18} aria-hidden="true" />
          Volver al mapa
        </Link>
      </div>
    </>
  )
}

/** Pantallas planificadas en la iteración 1 que aún no se construyen (doc 02 §4). */
export function EnConstruccion({ titulo }: { titulo: string }) {
  return (
    <>
      <h1>{titulo}</h1>
      <Vacio titulo="Esta pantalla está en construcción" icono={<Hammer size={36} weight="duotone" aria-hidden="true" />}>
        Estará disponible en la versión 1 del 13 de noviembre de 2026.
      </Vacio>
      <div className="acciones">
        <Link className="boton boton-secundario" to="/">
          <ArrowLeft size={18} aria-hidden="true" />
          Volver al mapa
        </Link>
      </div>
    </>
  )
}

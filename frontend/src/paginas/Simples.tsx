import { Link } from 'react-router'
import { ArrowLeft } from '@phosphor-icons/react'
import { Vacio } from '../componentes/Estado'

export function NoEncontrado() {
  return (
    <>
      <h1>Página no encontrada</h1>
      <Vacio titulo="La dirección que abriste no existe" vineta="mapa">
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

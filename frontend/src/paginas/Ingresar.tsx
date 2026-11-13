import { useState, type FormEvent } from 'react'
import { useLocation, useNavigate } from 'react-router'
import { CircleNotch, Eye, EyeSlash, SignIn } from '@phosphor-icons/react'
import { mensajeDe } from '../api/cliente'
import { useSesion } from '../sesion/ContextoSesion'
import { AvisoError, AvisoInfo } from '../componentes/Estado'
import { Campo } from '../componentes/Campo'

const MSG_02 = 'Tu sesión ha finalizado. Inicia sesión para continuar.'

/** HU-001: inicio de sesión del líder de huerto. Admite gestores de contraseñas y pegar. */
export function Ingresar() {
  const { iniciar, finalizada } = useSesion()
  const navegar = useNavigate()
  const ubicacion = useLocation()
  const [usuario, setUsuario] = useState('')
  const [contrasena, setContrasena] = useState('')
  const [visible, setVisible] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [enviando, setEnviando] = useState(false)

  async function enviar(e: FormEvent) {
    e.preventDefault()
    setError(null)
    setEnviando(true)
    try {
      await iniciar(usuario, contrasena)
      const desde = (ubicacion.state as { desde?: string } | null)?.desde
      navegar(desde ?? '/gestion', { replace: true })
    } catch (err) {
      setError(mensajeDe(err)) // MSG-01, sin indicar cuál dato es incorrecto
    } finally {
      setEnviando(false)
    }
  }

  return (
    <div className="ingreso revelar">
      <section className="etiqueta">
        <h1>Ingreso de líderes</h1>
        <p className="secundario">
          Solo los líderes escogidos por su comunidad y autorizados por el MSP tienen cuenta. El mapa y las fichas de los
          huertos son de consulta pública.
        </p>
        {finalizada && <AvisoInfo>{MSG_02}</AvisoInfo>}
        <AvisoError>{error}</AvisoError>
        <form onSubmit={enviar} className="formulario" noValidate>
          <Campo etiqueta="Usuario" obligatorio>
            {(p) => (
              <input
                {...p}
                className="control"
                name="usuario"
                autoComplete="username"
                autoCapitalize="none"
                spellCheck={false}
                value={usuario}
                onChange={(e) => setUsuario(e.target.value)}
                required
              />
            )}
          </Campo>
          <Campo etiqueta="Contraseña" obligatorio>
            {(p) => (
              <div className="con-boton">
                <input
                  {...p}
                  className="control"
                  name="contrasena"
                  type={visible ? 'text' : 'password'}
                  autoComplete="current-password"
                  value={contrasena}
                  onChange={(e) => setContrasena(e.target.value)}
                  required
                />
                <button
                  type="button"
                  className="boton-icono"
                  onClick={() => setVisible((v) => !v)}
                  aria-label={visible ? 'Ocultar contraseña' : 'Mostrar contraseña'}
                  aria-pressed={visible}
                >
                  {visible ? <EyeSlash size={22} aria-hidden="true" /> : <Eye size={22} aria-hidden="true" />}
                </button>
              </div>
            )}
          </Campo>
          <button type="submit" className="boton boton-ancho" disabled={enviando || !usuario || !contrasena}>
            {enviando ? (
              <CircleNotch size={20} className="girando" aria-hidden="true" />
            ) : (
              <SignIn size={20} aria-hidden="true" />
            )}
            {enviando ? 'Ingresando…' : 'Iniciar sesión'}
          </button>
        </form>
      </section>
    </div>
  )
}

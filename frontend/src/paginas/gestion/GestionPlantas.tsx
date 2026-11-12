import { useCallback, useEffect, useState, type FormEvent } from 'react'
import { FloppyDisk, Flower, PencilSimple, Plus, X } from '@phosphor-icons/react'
import { api, mensajeDe } from '../../api/cliente'
import type { Planta } from '../../api/tipos'
import { AvisoError, AvisoExito, Cargando, Vacio } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { SenalClasificacion } from '../../componentes/Senal'
import { useHuertoGestionado } from './Gestion'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'

const CAMPOS_VACIOS = {
  nombreComun: '', nombreCientifico: '', familia: '', fuente: '', otrosNombres: '', descripcion: '',
  altitud: '', distribucion: '', ciclo: '', clasificacion: '', usos: '',
}
type Campos = typeof CAMPOS_VACIOS

const TAMANO_MAXIMO = 5 * 1024 * 1024

/** HU-006 (registrar) y HU-007 (modificar) fichas de plantas del huerto. */
export function GestionPlantas() {
  const huertoId = useHuertoGestionado()
  const [plantas, setPlantas] = useState<Planta[] | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [editando, setEditando] = useState<Planta | 'nueva' | null>(null)
  const [guardada, setGuardada] = useState<string | null>(null)

  const cargar = useCallback(() => {
    api
      .get<Planta[]>(`/api/huertos/${huertoId}/plantas`)
      .then(setPlantas)
      .catch((e) => setError(mensajeDe(e)))
  }, [huertoId])

  useEffect(cargar, [cargar])

  useEffect(() => {
    if (!guardada) return
    const t = window.setTimeout(() => setGuardada(null), 4000)
    return () => window.clearTimeout(t)
  }, [guardada])

  if (error) return <AvisoError>{error}</AvisoError>
  if (!plantas) return <Cargando lineas={4} />

  return (
    <>
      {editando !== null && (
        <FormularioPlanta
          key={editando === 'nueva' ? 'nueva' : editando.id}
          huertoId={huertoId}
          planta={editando === 'nueva' ? null : editando}
          alTerminar={(nombre) => {
            setEditando(null)
            if (nombre) {
              setGuardada(`Ficha de ${nombre} guardada.`)
              cargar()
            }
          }}
        />
      )}

      <section className="seccion">
        <div className="cabecera-seccion">
          <h2 className="titulo-seccion">
            <Flower size={24} aria-hidden="true" />
            Fichas de plantas
          </h2>
          {editando === null && (
            <button type="button" className="boton boton-pequeno" onClick={() => setEditando('nueva')}>
              <Plus size={18} weight="bold" aria-hidden="true" />
              Registrar planta
            </button>
          )}
        </div>
        <AvisoExito>{guardada}</AvisoExito>

        {plantas.length === 0 ? (
          <Vacio titulo="Aún no registras plantas" vineta="maceta">
            Registra la primera ficha con su nombre, familia y la fuente de la información.
          </Vacio>
        ) : (
          <ul className="lista-gestion">
            {plantas.map((p) => (
              <li key={p.id}>
                {p.fotoUrl ? (
                  <img className="miniatura" src={p.fotoUrl} alt="" width={56} height={56} loading="lazy" />
                ) : (
                  <span className="miniatura miniatura-vacia" aria-hidden="true">
                    <Flower size={24} weight="duotone" />
                  </span>
                )}
                <div className="principal">
                  <strong>{p.nombreComun}</strong>
                  <span className="cientifico">{p.nombreCientifico}</span>
                  <SenalClasificacion clasificacion={p.clasificacion} />
                </div>
                <button
                  type="button"
                  className="boton boton-secundario boton-pequeno"
                  onClick={() => setEditando(p)}
                  aria-label={`Modificar la ficha de ${p.nombreComun}`}
                >
                  <PencilSimple size={18} aria-hidden="true" />
                  Modificar
                </button>
              </li>
            ))}
          </ul>
        )}
      </section>
    </>
  )
}

interface PropsFormulario {
  huertoId: number
  planta: Planta | null
  alTerminar: (nombreGuardado: string | null) => void
}

function FormularioPlanta({ huertoId, planta, alTerminar }: PropsFormulario) {
  const [v, setV] = useState<Campos>(
    planta
      ? {
          nombreComun: planta.nombreComun,
          nombreCientifico: planta.nombreCientifico,
          familia: planta.familia,
          fuente: planta.fuente,
          otrosNombres: planta.otrosNombres ?? '',
          descripcion: planta.descripcion ?? '',
          altitud: planta.altitud ?? '',
          distribucion: planta.distribucion ?? '',
          ciclo: planta.ciclo ?? '',
          clasificacion: planta.clasificacion ?? '',
          usos: planta.usos ?? '',
        }
      : CAMPOS_VACIOS,
  )
  const [foto, setFoto] = useState<File | null>(null)
  const envio = useEnvio()
  const cambiar = (k: keyof Campos) => (e: { target: { value: string } }) => setV({ ...v, [k]: e.target.value })
  const fotoGrande = foto !== null && foto.size > TAMANO_MAXIMO

  async function guardar(e: FormEvent) {
    e.preventDefault()
    const datos = new FormData()
    Object.entries(v).forEach(([k, valor]) => datos.append(k, valor))
    if (foto) datos.append('foto', foto)
    const r = await envio.enviar(
      () =>
        planta
          ? api.put<Planta>(`/api/plantas/${planta.id}`, datos)
          : api.post<Planta>(`/api/huertos/${huertoId}/plantas`, datos),
      'Ficha guardada.',
    )
    if (r) alTerminar(r.nombreComun)
  }

  const texto = (k: keyof Campos, etiqueta: string, extra?: { obligatorio?: boolean; ayuda?: string; ancho?: boolean }) => (
    <Campo
      etiqueta={etiqueta}
      obligatorio={extra?.obligatorio}
      ayuda={extra?.ayuda}
      error={envio.errorDe(k)}
      className={extra?.ancho ? 'ancho-completo' : ''}
    >
      {(p) => <input {...p} data-campo={k} className="control" value={v[k]} onChange={cambiar(k)} />}
    </Campo>
  )

  return (
    <form className="formulario panel" onSubmit={guardar} noValidate aria-labelledby="titulo-ficha">
      <div className="cabecera-seccion">
        <h2 id="titulo-ficha" style={{ margin: 0 }}>
          {planta ? `Modificar ${planta.nombreComun}` : 'Nueva planta'}
        </h2>
        <button type="button" className="boton boton-tenue boton-pequeno" onClick={() => alTerminar(null)}>
          <X size={18} aria-hidden="true" />
          Cancelar
        </button>
      </div>
      <AvisoError>{envio.errorGeneral}</AvisoError>

      <fieldset className="grupo-campos">
        <legend>Identificación</legend>
        <div className="rejilla">
          {texto('nombreComun', 'Nombre común', { obligatorio: true })}
          {texto('nombreCientifico', 'Nombre científico', { obligatorio: true })}
          {texto('familia', 'Familia', { obligatorio: true })}
          {texto('otrosNombres', 'Otros nombres')}
          {texto('fuente', 'Fuente', {
            obligatorio: true,
            ancho: true,
            ayuda: 'Libro, lineamiento del MSP o saber de la comunidad que respalda la ficha.',
          })}
        </div>
      </fieldset>

      <fieldset className="grupo-campos">
        <legend>Características</legend>
        <div className="rejilla">
          <Campo etiqueta="Clasificación" ayuda="Se muestra en rojo (caliente), verde (fría) o gris (sin clasificar).">
            {(p) => (
              <select {...p} className="control" value={v.clasificacion} onChange={cambiar('clasificacion')}>
                <option value="">Sin clasificar</option>
                <option value="CALIENTE">Caliente</option>
                <option value="FRIA">Fría</option>
              </select>
            )}
          </Campo>
          <Campo etiqueta="Ciclo">
            {(p) => (
              <select {...p} className="control" value={v.ciclo} onChange={cambiar('ciclo')}>
                <option value="">Sin indicar</option>
                <option value="CORTO">Corto</option>
                <option value="LARGO">Largo</option>
              </select>
            )}
          </Campo>
          {texto('altitud', 'Altitud', { ayuda: 'Ejemplo: 2 500 a 3 200 m' })}
          {texto('distribucion', 'Distribución por provincias')}
          <Campo etiqueta="Descripción" className="ancho-completo">
            {(p) => <textarea {...p} className="control" value={v.descripcion} onChange={cambiar('descripcion')} rows={3} />}
          </Campo>
          <Campo
            etiqueta="Usos"
            className="ancho-completo"
            ayuda="Regístralos tal como los aprueba el líder. La aplicación no genera recomendaciones ni dosis."
          >
            {(p) => <textarea {...p} className="control" value={v.usos} onChange={cambiar('usos')} rows={2} />}
          </Campo>
        </div>
      </fieldset>

      <fieldset className="grupo-campos">
        <legend>Fotografía</legend>
        <Campo
          etiqueta={planta?.fotoUrl ? 'Reemplazar fotografía' : 'Fotografía'}
          error={envio.errorDe('foto') ?? (fotoGrande ? 'La imagen supera 5 MB: elige una más liviana.' : null)}
          ayuda={`JPEG o PNG de hasta 5 MB.${planta?.fotoUrl ? ' Déjalo vacío para conservar la actual.' : ''}`}
        >
          {(p) => (
            <input
              {...p} data-campo="foto" className="control control-archivo" type="file" accept="image/jpeg,image/png"
              onChange={(e) => setFoto(e.target.files?.[0] ?? null)}
            />
          )}
        </Campo>
      </fieldset>

      <p className="campo-ayuda">
        <span className="obligatorio" aria-hidden="true">*</span> Campos obligatorios. No inventes datos: deja vacío lo
        que no esté validado.
      </p>
      <div className="acciones" style={{ marginTop: 0 }}>
        <BotonEnviar enviando={envio.enviando} icono={<FloppyDisk size={20} aria-hidden="true" />}>
          Guardar ficha
        </BotonEnviar>
      </div>
    </form>
  )
}

import { useCallback, useEffect, useState, type FormEvent } from 'react'
import { Link } from 'react-router'
import { ArrowRight, CircleNotch, Plant, Plus } from '@phosphor-icons/react'
import { api, mensajeDe } from '../../api/cliente'
import { ESTADOS_CULTIVO, type Cultivo, type EstadoCultivo, type Planta, type Zona } from '../../api/tipos'
import { AvisoError, AvisoExito, Cargando, Vacio } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { SenalClasificacion } from '../../componentes/Senal'
import { useHuertoGestionado } from './Gestion'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'

interface Datos {
  cultivos: Cultivo[]
  plantas: Planta[]
  zonas: Zona[]
}

const formatoFecha = new Intl.DateTimeFormat('es-EC', {
  dateStyle: 'medium',
  timeStyle: 'short',
  timeZone: 'America/Guayaquil',
})

/** HU-008 (registrar un cultivo) y HU-009 (cambiar su estado). */
export function GestionCultivos() {
  const huertoId = useHuertoGestionado()
  const [datos, setDatos] = useState<Datos | null>(null)
  const [error, setError] = useState<string | null>(null)

  const cargar = useCallback(() => {
    Promise.all([
      api.get<Cultivo[]>(`/api/huertos/${huertoId}/cultivos`),
      api.get<Planta[]>(`/api/huertos/${huertoId}/plantas`),
      api.get<Zona[]>(`/api/huertos/${huertoId}/zonas`),
    ])
      .then(([cultivos, plantas, zonas]) => setDatos({ cultivos, plantas, zonas }))
      .catch((e) => setError(mensajeDe(e)))
  }, [huertoId])

  useEffect(cargar, [cargar])

  if (error) return <AvisoError>{error}</AvisoError>
  if (!datos) return <Cargando lineas={4} />

  const zonasDeCultivo = datos.zonas.filter((z) => z.tipo === 'CULTIVO')

  return (
    <>
      {datos.plantas.length === 0 ? (
        <Vacio titulo="Primero registra una planta" vineta="maceta">
          Cada cultivo es una planta sembrada en una zona del huerto. <Link to="/gestion/plantas">Registrar planta</Link>
        </Vacio>
      ) : (
        <NuevoCultivo
          huertoId={huertoId}
          plantas={datos.plantas}
          zonas={zonasDeCultivo}
          alRegistrar={(c) => setDatos({ ...datos, cultivos: [...datos.cultivos, c] })}
        />
      )}
      <section className="seccion">
        <h2 className="titulo-seccion">
          <Plant size={24} aria-hidden="true" />
          Cultivos del huerto
        </h2>
        {datos.cultivos.length === 0 ? (
          <Vacio titulo="Aún no registras cultivos" vineta="semillas" />
        ) : (
          <ul className="lista-gestion">
            {datos.cultivos.map((c) => (
              <FilaCultivo
                key={c.id}
                cultivo={c}
                alCambiar={(nuevo) =>
                  setDatos({ ...datos, cultivos: datos.cultivos.map((x) => (x.id === nuevo.id ? nuevo : x)) })
                }
              />
            ))}
          </ul>
        )}
      </section>
    </>
  )
}

function NuevoCultivo(props: {
  huertoId: number
  plantas: Planta[]
  zonas: Zona[]
  alRegistrar: (c: Cultivo) => void
}) {
  const [plantaId, setPlantaId] = useState('')
  const [zonaId, setZonaId] = useState('')
  const envio = useEnvio()

  async function registrar(e: FormEvent) {
    e.preventDefault()
    const r = await envio.enviar(
      () =>
        api.post<Cultivo>(`/api/huertos/${props.huertoId}/cultivos`, {
          plantaId: plantaId ? Number(plantaId) : null,
          zonaId: zonaId ? Number(zonaId) : null,
        }),
      'Cultivo registrado en estado sembrado.',
    )
    if (r) {
      props.alRegistrar(r)
      setPlantaId('')
      setZonaId('')
    }
  }

  return (
    <form className="formulario seccion" onSubmit={registrar} noValidate aria-labelledby="titulo-nuevo-cultivo">
      <h2 id="titulo-nuevo-cultivo" className="titulo-seccion">
        <Plus size={24} aria-hidden="true" />
        Registrar un cultivo
      </h2>
      <AvisoError>{envio.errorGeneral}</AvisoError>
      <div className="rejilla">
        <Campo etiqueta="Planta" obligatorio error={envio.errorDe('plantaId')}>
          {(p) => (
            <select {...p} data-campo="plantaId" className="control" value={plantaId} onChange={(e) => setPlantaId(e.target.value)}>
              <option value="">Selecciona…</option>
              {props.plantas.map((pl) => (
                <option key={pl.id} value={pl.id}>
                  {pl.nombreComun}
                </option>
              ))}
            </select>
          )}
        </Campo>
        <Campo etiqueta="Zona" obligatorio error={envio.errorDe('zonaId')}>
          {(p) => (
            <select {...p} data-campo="zonaId" className="control" value={zonaId} onChange={(e) => setZonaId(e.target.value)}>
              <option value="">Selecciona…</option>
              {props.zonas.map((z) => (
                <option key={z.id} value={z.id}>
                  {z.codigo}
                  {z.nombre ? ` · ${z.nombre}` : ''}
                </option>
              ))}
            </select>
          )}
        </Campo>
      </div>
      <AvisoExito>{envio.exito}</AvisoExito>
      <div>
        <BotonEnviar enviando={envio.enviando} textoEnviando="Registrando…" icono={<Plus size={20} weight="bold" aria-hidden="true" />}>
          Registrar cultivo
        </BotonEnviar>
      </div>
    </form>
  )
}

function FilaCultivo({ cultivo, alCambiar }: { cultivo: Cultivo; alCambiar: (c: Cultivo) => void }) {
  const envio = useEnvio()

  async function cambiar(estado: EstadoCultivo) {
    const r = await envio.enviar(
      () => api.patch<Cultivo>(`/api/cultivos/${cultivo.id}/estado`, { estado }),
      'Estado actualizado.',
    )
    if (r) alCambiar(r)
  }

  return (
    <li>
      <span className="miniatura miniatura-vacia" aria-hidden="true">
        <Plant size={24} weight="duotone" />
      </span>
      <div className="principal">
        <strong>{cultivo.planta}</strong>
        <span className="secundario">
          Zona {cultivo.zona} · actualizado {formatoFecha.format(new Date(cultivo.actualizadoEn))}
        </span>
        <SenalClasificacion clasificacion={cultivo.clasificacion} />
        <Link className="enlace-accion" to={`/gestion/cultivos/${cultivo.id}/bitacora`}>
          Bitácora de siembras
          <ArrowRight size={16} weight="bold" aria-hidden="true" />
        </Link>
        <AvisoError>{envio.error}</AvisoError>
        <AvisoExito>{envio.exito}</AvisoExito>
      </div>
      <Campo etiqueta="Estado" className="estado-selector">
        {(p) => (
          <div className="con-boton">
            <select
              {...p}
              className="control"
              value={cultivo.estado}
              disabled={envio.enviando}
              onChange={(e) => cambiar(e.target.value as EstadoCultivo)}
              aria-label={`Estado de ${cultivo.planta} en la zona ${cultivo.zona}`}
            >
              {ESTADOS_CULTIVO.map((s) => (
                <option key={s.valor} value={s.valor}>
                  {s.etiqueta}
                </option>
              ))}
            </select>
            {envio.enviando && (
              <span className="boton-icono" aria-hidden="true" style={{ right: 28 }}>
                <CircleNotch size={18} className="girando" />
              </span>
            )}
          </div>
        )}
      </Campo>
    </li>
  )
}

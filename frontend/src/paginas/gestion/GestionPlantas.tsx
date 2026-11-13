import { useCallback, useEffect, useState, type FormEvent } from 'react'
import { api, mensajeDe } from '../../api/cliente'
import type { FichaMsp, Planta } from '../../api/tipos'
import { AvisoError, AvisoExito, Cargando, Vacio } from '../../componentes/Estado'
import { Campo } from '../../componentes/Campo'
import { SenalClasificacion } from '../../componentes/Senal'
import { useHuertoGestionado } from './Gestion'
import { useEnvio } from './useEnvio'
import { BotonEnviar } from './BotonEnviar'

/** El líder incorpora información institucional y aporta fotografías de su huerto. */
export function GestionPlantas() {
  const huertoId = useHuertoGestionado()
  const [plantas, setPlantas] = useState<Planta[] | null>(null)
  const [catalogo, setCatalogo] = useState<FichaMsp[]>([])
  const [error, setError] = useState<string | null>(null)
  const [editando, setEditando] = useState<Planta | 'nueva' | null>(null)
  const [guardada, setGuardada] = useState<string | null>(null)
  const cargar = useCallback(() => {
    Promise.all([
      api.get<Planta[]>(`/api/huertos/${huertoId}/plantas`),
      api.get<FichaMsp[]>('/api/publico/catalogo-plantas'),
    ]).then(([p,c]) => { setPlantas(p); setCatalogo(c); setError(null) })
      .catch((e) => setError(mensajeDe(e)))
  }, [huertoId])
  useEffect(cargar,[cargar])
  if (error) return <AvisoError>{error}</AvisoError>
  if (!plantas) return <Cargando />
  return <>
    <h2>Fichas institucionales de plantas</h2>
    <p>La comunidad escoge al líder y el MSP lo autoriza. El líder selecciona la información suministrada por el MSP
      para su huerto y aporta sus fotografías; no redacta ni modifica indicaciones, dosis o contraindicaciones.</p>
    <AvisoExito>{guardada}</AvisoExito>
    {editando === null && <button className="boton" onClick={() => setEditando('nueva')}>Incorporar planta del catálogo MSP</button>}
    {editando !== null && <Formulario key={editando === 'nueva' ? 'nueva' : editando.id}
      huertoId={huertoId} planta={editando === 'nueva' ? null : editando} catalogo={catalogo}
      alTerminar={(nombre) => { setEditando(null); if(nombre) {setGuardada(`Ficha de ${nombre} guardada.`);cargar()} }} />}
    {plantas.length === 0 ? <Vacio titulo="Aún no se han incorporado plantas del catálogo" /> :
      <ul className="lista-gestion">{plantas.map(p => <li key={p.id}>
        <div className="principal"><strong>{p.nombreComun}</strong>
          <span>{p.nombreCientifico ?? 'Nombre científico no incorporado en la fuente seleccionada'}</span>
          <SenalClasificacion clasificacion={p.clasificacion} />
          <small>{p.institucional ? 'Ficha institucional de lectura' : 'Registro anterior sin referencia institucional; no se publica'}</small>
        </div>
        {p.institucional && <button className="boton boton-secundario" onClick={() => setEditando(p)}
          aria-label={`Gestionar fotografía de ${p.nombreComun}`}>Gestionar fotografía</button>}
      </li>)}</ul>}
  </>
}

function Formulario({huertoId,planta,catalogo,alTerminar}:{
  huertoId:number;planta:Planta|null;catalogo:FichaMsp[];alTerminar:(nombre:string|null)=>void
}) {
  const [codigo,setCodigo] = useState(planta?.catalogoCodigo ?? '')
  const [foto,setFoto] = useState<File|null>(null)
  const envio = useEnvio()
  const ficha = catalogo.find(f => f.codigo === codigo)
  async function guardar(e:FormEvent) {
    e.preventDefault()
    const datos = new FormData();datos.append('catalogoCodigo',codigo)
    if (foto) datos.append('foto',foto)
    const r = await envio.enviar(() => planta
      ? api.put<Planta>(`/api/plantas/${planta.id}`,datos)
      : api.post<Planta>(`/api/huertos/${huertoId}/plantas`,datos), 'Ficha guardada.')
    if(r) alTerminar(r.nombreComun)
  }
  return <form className="formulario panel" onSubmit={guardar} noValidate aria-label="Ficha institucional MSP">
    <h3>{planta ? 'Gestionar fotografía del huerto' : 'Incorporar ficha institucional'}</h3>
    <AvisoError>{envio.errorGeneral}</AvisoError>
    <Campo etiqueta="Ficha del catálogo MSP" obligatorio error={envio.errorDe('catalogoCodigo')}>
      {p => <select {...p} className="control" value={codigo} disabled={!!planta}
        onChange={e=>setCodigo(e.target.value)}>
        <option value="">Selecciona una planta</option>
        {catalogo.map(f=><option key={f.codigo} value={f.codigo}>{f.nombreComun}</option>)}
      </select>}
    </Campo>
    {ficha && <section className="etiqueta" aria-label="Información institucional de lectura">
      <h4>{ficha.nombreComun}</h4>
      <dl className="campos">
        <div><dt>Nombre científico</dt><dd>{ficha.nombreCientifico ?? 'No consta en la ficha incorporada'}</dd></div>
        <div><dt>Familia</dt><dd>{ficha.familia ?? 'No consta en la ficha incorporada'}</dd></div>
      </dl>
      <SenalClasificacion clasificacion={ficha.clasificacion} />
      {ficha.descripcion && <p>{ficha.descripcion}</p>}
      {ficha.usos && <p>{ficha.usos}</p>}
      <p className="fuente">Fuente: {ficha.fuente} · Versión {ficha.version}</p>
      <a href="/fuentes/msp-lineamientos-2017.pdf" target="_blank" rel="noopener noreferrer">Consultar documento MSP de 2017</a>
    </section>}
    <Campo etiqueta="Fotografía del huerto" error={envio.errorDe('foto')}
      ayuda="JPEG o PNG de hasta 5 MB. La fotografía documenta tu huerto; no modifica el contenido institucional.">
      {p=><input {...p} className="control" type="file" accept="image/jpeg,image/png"
        onChange={e=>setFoto(e.target.files?.[0] ?? null)} />}
    </Campo>
    <div className="acciones"><BotonEnviar enviando={envio.enviando}>Guardar ficha</BotonEnviar>
      <button type="button" className="boton boton-secundario" onClick={()=>alTerminar(null)}>Cancelar</button></div>
  </form>
}

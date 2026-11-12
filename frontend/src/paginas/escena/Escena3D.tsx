import { useEffect, useMemo, useRef, useState } from 'react'
import { Canvas, useFrame, useThree, type ThreeEvent } from '@react-three/fiber'
import { CanvasTexture, RepeatWrapping, SRGBColorSpace, TextureLoader, Vector3, type Texture } from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import type { Cultivo, Zona } from '../../api/tipos'

/**
 * Escena 3D generada desde las zonas del huerto guardadas como datos (AD-04), como una maqueta de herbario
 * (docs/diseno/WEBGL_EXPERIENCES.md, propuesta A): terreno de césped, camellones de tierra, camino de grava y cerca
 * viva con texturas grabadas; cada cultivo es una planta de bajo polígono con la señalética caliente (rojo), fría
 * (verde) o sin clasificar (gris) como un anillo en el suelo. Las texturas llegan después del primer cuadro, así la
 * escena se ve enseguida en celulares de gama media (doc 06 §8.1, P-066). Este módulo se descarga solo al abrir la vista.
 */
export interface PropsEscena {
  zonas: Zona[]
  cultivos: Cultivo[]
  seleccionada: string | null
  alSeleccionar: (codigo: string) => void
}

interface Colores {
  terreno: string
  suelo: string
  camino: string
  cerca: string
  bodega: string
  techo: string
  papel: string
  accion: string
  sobreAccion: string
  caliente: string
  fria: string
  neutro: string
  etiqueta: string
  follaje: string
}

/** Colores de la escena tomados de los tokens CSS del sistema (claro u oscuro). */
function leerColores(): Colores {
  const css = getComputedStyle(document.documentElement)
  const v = (nombre: string, respaldo: string) => css.getPropertyValue(nombre).trim() || respaldo
  return {
    terreno: v('--banda-fuerte', '#cbdccd'),
    suelo: '#6b5340',
    camino: v('--papel', '#f4f8f3'),
    cerca: v('--bolsa', '#14532d'),
    bodega: v('--papel', '#f4f8f3'),
    techo: v('--tinta-suave', '#2c4334'),
    papel: v('--papel', '#f4f8f3'),
    accion: v('--accion', '#15803d'),
    sobreAccion: v('--sobre-accion', '#ffffff'),
    caliente: v('--sello-caliente', '#b4320f'),
    fria: v('--sello-fria', '#046c4e'),
    neutro: v('--sello-neutro', '#56605a'),
    etiqueta: v('--tinta', '#0f2417'),
    follaje: '#4f7d3a',
  }
}

type NombreTextura = 'tierra' | 'cesped' | 'grava' | 'cercaViva'
type Texturas = Partial<Record<NombreTextura, Texture>>

const RUTAS_TEXTURA: Record<NombreTextura, string> = {
  tierra: '/arte/texturas/tierra.webp',
  cesped: '/arte/texturas/cesped.webp',
  grava: '/arte/texturas/grava.webp',
  cercaViva: '/arte/texturas/cerca-viva.webp',
}

/**
 * Carga progresiva de las texturas grabadas: la escena se pinta primero con colores planos y cada textura entra al
 * llegar. Si una falla, esa superficie se queda con su color (la escena nunca depende de ellas).
 */
function useTexturas(): Texturas {
  const [texturas, setTexturas] = useState<Texturas>({})
  const { invalidate } = useThree()
  useEffect(() => {
    let vigente = true
    const cargador = new TextureLoader()
    const cargadas: Texture[] = []
    for (const [nombre, ruta] of Object.entries(RUTAS_TEXTURA) as [NombreTextura, string][]) {
      cargador.load(ruta, (t) => {
        t.colorSpace = SRGBColorSpace
        t.wrapS = RepeatWrapping
        t.wrapT = RepeatWrapping
        cargadas.push(t)
        if (!vigente) return t.dispose()
        setTexturas((previas) => ({ ...previas, [nombre]: t }))
        invalidate()
      })
    }
    return () => {
      vigente = false
      cargadas.forEach((t) => t.dispose())
    }
  }, [invalidate])
  return texturas
}

/** Copia de una textura con su propia repetición (comparte la imagen; solo cambia cómo se reparte). */
function useRepetida(base: Texture | undefined, repX: number, repY: number) {
  const t = useMemo(() => {
    if (!base) return null
    const copia = base.clone()
    copia.repeat.set(Math.max(1, repX), Math.max(1, repY))
    copia.needsUpdate = true
    return copia
  }, [base, repX, repY])
  useEffect(() => () => t?.dispose(), [t])
  return t
}

export default function Escena3D({ zonas, cultivos, seleccionada, alSeleccionar }: PropsEscena) {
  const [colores] = useState(leerColores)
  const limites = useMemo(() => calcularLimites(zonas), [zonas])
  const cultivosPorZona = useMemo(() => {
    const m = new Map<string, Cultivo[]>()
    cultivos.forEach((c) => m.set(c.zona, [...(m.get(c.zona) ?? []), c]))
    return m
  }, [cultivos])
  const zonaElegida = zonas.find((z) => z.codigo === seleccionada) ?? null

  const distancia = Math.max(limites.ancho, limites.largo) * 1.1 + 4

  return (
    <Canvas
      className="escena-lienzo"
      frameloop="demand"
      dpr={[1, 1.5]}
      gl={{ antialias: true, powerPreference: 'low-power' }}
      camera={{ position: [limites.cx + distancia * 0.55, distancia * 0.8, limites.cz + distancia * 0.75], fov: 45 }}
      aria-label="Recorrido 3D del huerto. Usa los botones de zona para elegir una zona con el teclado."
    >
      {/* Luz de cielo (arriba papel, abajo tierra) más un sol suave: volumen sin sombras costosas */}
      <hemisphereLight args={[colores.papel, colores.suelo, 1.1]} />
      <ambientLight intensity={0.7} />
      <directionalLight position={[6, 12, 8]} intensity={1.6} />

      <Maqueta
        zonas={zonas}
        cultivosPorZona={cultivosPorZona}
        seleccionada={seleccionada}
        colores={colores}
        limites={limites}
        alSeleccionar={alSeleccionar}
      />

      <Controles cx={limites.cx} cz={limites.cz} maxDistancia={distancia * 2.2} objetivo={zonaElegida} />
    </Canvas>
  )
}

function Maqueta({ zonas, cultivosPorZona, seleccionada, colores, limites, alSeleccionar }: {
  zonas: Zona[]
  cultivosPorZona: Map<string, Cultivo[]>
  seleccionada: string | null
  colores: Colores
  limites: ReturnType<typeof calcularLimites>
  alSeleccionar: (codigo: string) => void
}) {
  const texturas = useTexturas()
  const anchoTerreno = limites.ancho + 2
  const largoTerreno = limites.largo + 2
  const cesped = useRepetida(texturas.cesped, anchoTerreno / 2, largoTerreno / 2)

  return (
    <>
      {/* Terreno */}
      <mesh position={[limites.cx, -0.03, limites.cz]}>
        <boxGeometry args={[anchoTerreno, 0.06, largoTerreno]} />
        {/* La key cambia al llegar la textura: three.js no recompila un material ya creado sin mapa */}
        <meshStandardMaterial key={cesped ? 'con-mapa' : 'plano'} color={cesped ? '#ffffff' : colores.terreno} map={cesped} />
      </mesh>

      {zonas.map((z) => (
        <ZonaMalla
          key={z.id}
          zona={z}
          cultivos={cultivosPorZona.get(z.codigo) ?? []}
          seleccionada={seleccionada === z.codigo}
          colores={colores}
          texturas={texturas}
          alSeleccionar={alSeleccionar}
        />
      ))}
    </>
  )
}

function ZonaMalla({ zona, cultivos, seleccionada, colores, texturas, alSeleccionar }: {
  zona: Zona
  cultivos: Cultivo[]
  seleccionada: boolean
  colores: Colores
  texturas: Texturas
  alSeleccionar: (codigo: string) => void
}) {
  const { x, z, ancho, largo, alto } = zona.geometria
  const esCultivo = zona.tipo === 'CULTIVO'
  const base =
    zona.tipo === 'CULTIVO' ? texturas.tierra
      : zona.tipo === 'CAMINO' ? texturas.grava
        : zona.tipo === 'CERCA' ? texturas.cercaViva
          : undefined
  // Una repetición cada 1,5 m: el grano de la tierra o la grava conserva su escala en zonas grandes y pequeñas.
  const mapa = useRepetida(base, Math.max(ancho, largo) / 1.5, Math.min(ancho, largo) / 1.5 || 1)
  const colorPlano =
    zona.tipo === 'CULTIVO' ? colores.suelo
      : zona.tipo === 'CAMINO' ? colores.camino
        : zona.tipo === 'CERCA' ? colores.cerca
          : colores.bodega

  function elegir(e: ThreeEvent<MouseEvent>) {
    e.stopPropagation()
    alSeleccionar(zona.codigo)
  }

  return (
    <group position={[x, 0, z]}>
      <mesh
        position={[0, alto / 2, 0]}
        onClick={elegir}
        onPointerOver={(e) => {
          e.stopPropagation()
          document.body.style.cursor = 'pointer'
        }}
        onPointerOut={() => {
          document.body.style.cursor = ''
        }}
      >
        <boxGeometry args={[ancho, alto, largo]} />
        <meshStandardMaterial
          key={mapa ? 'con-mapa' : 'plano'}
          color={mapa ? '#ffffff' : colorPlano}
          map={mapa}
          // La zona elegida se tiñe del verde de acción sin perder su textura (el rótulo también cambia)
          emissive={seleccionada ? colores.accion : '#000000'}
          emissiveIntensity={seleccionada ? 0.45 : 0}
        />
      </mesh>

      {zona.tipo === 'BODEGA' && (
        <mesh position={[0, alto + 0.25, 0]} rotation={[0, Math.PI / 4, 0]}>
          <coneGeometry args={[Math.max(ancho, largo) * 0.78, 0.5, 4]} />
          <meshStandardMaterial color={colores.techo} flatShading />
        </mesh>
      )}

      {esCultivo &&
        cultivos.map((c, i) => {
          const [mx, mz] = posicionMarcador(i, cultivos.length, ancho, largo)
          const senal =
            c.clasificacion === 'CALIENTE' ? colores.caliente : c.clasificacion === 'FRIA' ? colores.fria : colores.neutro
          return <Planta key={c.id} posicion={[mx, alto, mz]} semilla={c.id} senal={senal} colores={colores} alElegir={elegir} />
        })}

      {(esCultivo || zona.tipo === 'BODEGA' || zona.tipo === 'CAMINO') && (
        <Rotulo texto={zona.codigo} activo={seleccionada} colores={colores} y={alto + (esCultivo ? 1.0 : 0.6)} />
      )}
    </group>
  )
}

/**
 * Planta de bajo polígono: mata de follaje facetado sobre un tallo, sombra falsa (un disco, sin mapas de sombra) y
 * la señalética de su clasificación como anillo en el suelo. La semilla varía tamaño y giro para que el camellón no
 * parezca una fila de clones.
 */
function Planta({ posicion, semilla, senal, colores, alElegir }: {
  posicion: [number, number, number]
  semilla: number
  senal: string
  colores: Colores
  alElegir: (e: ThreeEvent<MouseEvent>) => void
}) {
  const escala = 0.85 + ((semilla * 37) % 30) / 100
  const giro = ((semilla * 53) % 360) * (Math.PI / 180)
  return (
    <group position={posicion} rotation={[0, giro, 0]} scale={escala}>
      {/* Sombra falsa */}
      <mesh position={[0, 0.005, 0]} rotation={[-Math.PI / 2, 0, 0]}>
        <circleGeometry args={[0.3, 20]} />
        <meshBasicMaterial color="#000000" transparent opacity={0.22} depthWrite={false} />
      </mesh>
      {/* Señalética caliente, fría o sin clasificar */}
      <mesh position={[0, 0.012, 0]} rotation={[-Math.PI / 2, 0, 0]} onClick={alElegir}>
        <ringGeometry args={[0.3, 0.4, 28]} />
        <meshBasicMaterial color={senal} />
      </mesh>
      <mesh position={[0, 0.14, 0]} onClick={alElegir}>
        <cylinderGeometry args={[0.03, 0.04, 0.28, 6]} />
        <meshStandardMaterial color={colores.techo} flatShading />
      </mesh>
      <mesh position={[0, 0.36, 0]} scale={[1, 0.85, 1]} onClick={alElegir}>
        <icosahedronGeometry args={[0.24, 0]} />
        <meshStandardMaterial color={colores.follaje} flatShading />
      </mesh>
      <mesh position={[0.12, 0.26, 0.06]} onClick={alElegir}>
        <icosahedronGeometry args={[0.14, 0]} />
        <meshStandardMaterial color={colores.follaje} flatShading />
      </mesh>
    </group>
  )
}

/** Curva de movimiento del sistema (cubic-bezier(0.77, 0, 0.175, 1)), aproximada como entrada y salida cuártica. */
const curvaMovimiento = (t: number) => (t < 0.5 ? 8 * t ** 4 : 1 - (-2 * t + 2) ** 4 / 2)
const DURACION_CAMARA = 600

/**
 * Controles de órbita de three.js (rotar, acercar, alejar) con amortiguación. Con frameloop="demand" se pide un
 * cuadro nuevo en cada cambio, así la escena no consume batería mientras nadie la mueve.
 *
 * Cámara guiada: al elegir una zona la cámara la encuadra en 600 ms; sin zona, vuelve al encuadre inicial. Si la
 * persona toca la vista durante el vuelo, el vuelo se cancela (interrumpible). Con movimiento reducido, salta.
 */
function Controles({ cx, cz, maxDistancia, objetivo }: { cx: number; cz: number; maxDistancia: number; objetivo: Zona | null }) {
  const { camera, gl, invalidate } = useThree()
  const controles = useMemo(() => new OrbitControls(camera, gl.domElement), [camera, gl])
  const inicial = useRef<{ posicion: Vector3; objetivo: Vector3 } | null>(null)
  const vuelo = useRef<{
    desdePos: Vector3
    desdeObj: Vector3
    hastaPos: Vector3
    hastaObj: Vector3
    inicio: number
  } | null>(null)

  useEffect(() => {
    controles.target.set(cx, 0, cz)
    controles.enableDamping = true
    controles.minDistance = 3
    controles.maxDistance = maxDistancia
    controles.maxPolarAngle = Math.PI / 2.15
    controles.update()
    inicial.current = { posicion: camera.position.clone(), objetivo: controles.target.clone() }
    const alCambiar = () => invalidate()
    const alTocar = () => {
      vuelo.current = null // la persona toma el control: el vuelo se cancela
    }
    controles.addEventListener('change', alCambiar)
    controles.addEventListener('start', alTocar)
    return () => {
      controles.removeEventListener('change', alCambiar)
      controles.removeEventListener('start', alTocar)
    }
  }, [controles, camera, cx, cz, maxDistancia, invalidate])

  useEffect(() => () => controles.dispose(), [controles])

  // Al cambiar la zona elegida, se calcula el destino conservando el ángulo de vista actual.
  useEffect(() => {
    if (!inicial.current) return
    let hastaObj: Vector3
    let hastaPos: Vector3
    if (objetivo) {
      const g = objetivo.geometria
      hastaObj = new Vector3(g.x, g.alto, g.z)
      const direccion = camera.position.clone().sub(controles.target).normalize()
      const distancia = Math.max(3, Math.max(g.ancho, g.largo) * 1.6 + 2.5)
      hastaPos = hastaObj.clone().add(direccion.multiplyScalar(distancia))
    } else {
      hastaObj = inicial.current.objetivo.clone()
      hastaPos = inicial.current.posicion.clone()
    }
    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      vuelo.current = null
      controles.target.copy(hastaObj)
      camera.position.copy(hastaPos)
      controles.update()
      invalidate()
      return
    }
    vuelo.current = {
      desdePos: camera.position.clone(),
      desdeObj: controles.target.clone(),
      hastaPos,
      hastaObj,
      inicio: performance.now(),
    }
    invalidate()
  }, [objetivo, camera, controles, invalidate])

  useFrame(() => {
    const v = vuelo.current
    if (v) {
      const t = Math.min(1, (performance.now() - v.inicio) / DURACION_CAMARA)
      const k = curvaMovimiento(t)
      camera.position.lerpVectors(v.desdePos, v.hastaPos, k)
      controles.target.lerpVectors(v.desdeObj, v.hastaObj, k)
      if (t >= 1) vuelo.current = null
      invalidate()
    }
    controles.update()
  })
  return null
}

/**
 * Rótulo de la zona dibujado en un lienzo como sprite de tamaño constante en pantalla (no se achica al alejarse):
 * legible también en un celular de 390 px. Sin raíces de React adicionales ni fuentes externas.
 */
function Rotulo({ texto, activo, colores, y }: { texto: string; activo: boolean; colores: Colores; y: number }) {
  const textura = useMemo(() => {
    const lienzo = document.createElement('canvas')
    lienzo.width = 160
    lienzo.height = 80
    const ctx = lienzo.getContext('2d')
    if (ctx) {
      ctx.fillStyle = activo ? colores.accion : colores.papel
      ctx.fillRect(0, 0, lienzo.width, lienzo.height)
      ctx.fillStyle = activo ? colores.sobreAccion : colores.etiqueta
      ctx.font = '800 46px "Archivo Variable", "Archivo", system-ui, sans-serif'
      ctx.textAlign = 'center'
      ctx.textBaseline = 'middle'
      ctx.fillText(texto, lienzo.width / 2, lienzo.height / 2 + 2)
    }
    const t = new CanvasTexture(lienzo)
    t.colorSpace = SRGBColorSpace
    return t
  }, [texto, activo, colores])

  useEffect(() => () => textura.dispose(), [textura])

  return (
    <sprite position={[0, y, 0]} scale={[0.12, 0.06, 1]} renderOrder={10}>
      <spriteMaterial map={textura} depthTest={false} sizeAttenuation={false} />
    </sprite>
  )
}

/** Reparte los marcadores en una fila o una rejilla dentro de la zona. */
function posicionMarcador(i: number, total: number, ancho: number, largo: number): [number, number] {
  const columnas = Math.max(1, Math.ceil(Math.sqrt(total * (ancho / Math.max(largo, 0.1)))))
  const filas = Math.ceil(total / columnas)
  const col = i % columnas
  const fila = Math.floor(i / columnas)
  return [((col + 0.5) / columnas - 0.5) * ancho * 0.8, ((fila + 0.5) / filas - 0.5) * largo * 0.8]
}

function calcularLimites(zonas: Zona[]) {
  if (zonas.length === 0) return { cx: 0, cz: 0, ancho: 6, largo: 6 }
  let minX = Infinity
  let maxX = -Infinity
  let minZ = Infinity
  let maxZ = -Infinity
  for (const { geometria: g } of zonas) {
    minX = Math.min(minX, g.x - g.ancho / 2)
    maxX = Math.max(maxX, g.x + g.ancho / 2)
    minZ = Math.min(minZ, g.z - g.largo / 2)
    maxZ = Math.max(maxZ, g.z + g.largo / 2)
  }
  return { cx: (minX + maxX) / 2, cz: (minZ + maxZ) / 2, ancho: maxX - minX, largo: maxZ - minZ }
}

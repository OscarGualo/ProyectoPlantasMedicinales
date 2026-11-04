import { useEffect, useMemo, useState } from 'react'
import { Canvas, useFrame, useThree, type ThreeEvent } from '@react-three/fiber'
import { CanvasTexture, SRGBColorSpace } from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import type { Cultivo, Zona } from '../../api/tipos'

/**
 * Escena 3D generada desde las zonas del huerto guardadas como datos (AD-04): terreno, una caja por zona y los
 * cultivos como marcadores con la señalética caliente (rojo), fría (verde) o sin clasificar (gris). Figuras
 * simples sin texturas para celulares de gama media (doc 06 §8.1). Este módulo se descarga solo al abrir la vista.
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
  sueloSeleccionado: string
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
}

/** Colores de la escena tomados de los tokens CSS del sistema (claro u oscuro). */
function leerColores(): Colores {
  const css = getComputedStyle(document.documentElement)
  const v = (nombre: string, respaldo: string) => css.getPropertyValue(nombre).trim() || respaldo
  return {
    terreno: v('--banda-fuerte', '#cbdccd'),
    suelo: '#6b5340',
    sueloSeleccionado: v('--accion', '#15803d'),
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
  }
}

export default function Escena3D({ zonas, cultivos, seleccionada, alSeleccionar }: PropsEscena) {
  const [colores] = useState(leerColores)
  const limites = useMemo(() => calcularLimites(zonas), [zonas])
  const cultivosPorZona = useMemo(() => {
    const m = new Map<string, Cultivo[]>()
    cultivos.forEach((c) => m.set(c.zona, [...(m.get(c.zona) ?? []), c]))
    return m
  }, [cultivos])

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
      <ambientLight intensity={1.6} />
      <directionalLight position={[6, 12, 8]} intensity={1.8} />

      {/* Terreno */}
      <mesh position={[limites.cx, -0.03, limites.cz]} receiveShadow={false}>
        <boxGeometry args={[limites.ancho + 2, 0.06, limites.largo + 2]} />
        <meshStandardMaterial color={colores.terreno} />
      </mesh>

      {zonas.map((z) => (
        <ZonaMalla
          key={z.id}
          zona={z}
          cultivos={cultivosPorZona.get(z.codigo) ?? []}
          seleccionada={seleccionada === z.codigo}
          colores={colores}
          alSeleccionar={alSeleccionar}
        />
      ))}

      <Controles cx={limites.cx} cz={limites.cz} maxDistancia={distancia * 2.2} />
    </Canvas>
  )
}

function ZonaMalla({ zona, cultivos, seleccionada, colores, alSeleccionar }: {
  zona: Zona
  cultivos: Cultivo[]
  seleccionada: boolean
  colores: Colores
  alSeleccionar: (codigo: string) => void
}) {
  const { x, z, ancho, largo, alto } = zona.geometria
  const esCultivo = zona.tipo === 'CULTIVO'
  const color =
    zona.tipo === 'CULTIVO'
      ? seleccionada ? colores.sueloSeleccionado : colores.suelo
      : zona.tipo === 'CAMINO'
        ? colores.camino
        : zona.tipo === 'CERCA'
          ? colores.cerca
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
        <meshStandardMaterial color={color} />
      </mesh>

      {zona.tipo === 'BODEGA' && (
        <mesh position={[0, alto + 0.25, 0]} rotation={[0, Math.PI / 4, 0]}>
          <coneGeometry args={[Math.max(ancho, largo) * 0.78, 0.5, 4]} />
          <meshStandardMaterial color={colores.techo} />
        </mesh>
      )}

      {esCultivo &&
        cultivos.map((c, i) => {
          const [mx, mz] = posicionMarcador(i, cultivos.length, ancho, largo)
          const colorMarcador =
            c.clasificacion === 'CALIENTE' ? colores.caliente : c.clasificacion === 'FRIA' ? colores.fria : colores.neutro
          return (
            <group key={c.id} position={[mx, alto, mz]}>
              <mesh position={[0, 0.18, 0]} onClick={elegir}>
                <cylinderGeometry args={[0.04, 0.05, 0.36, 8]} />
                <meshStandardMaterial color={colores.techo} />
              </mesh>
              <mesh position={[0, 0.44, 0]} onClick={elegir}>
                <sphereGeometry args={[0.2, 16, 12]} />
                <meshStandardMaterial color={colorMarcador} />
              </mesh>
            </group>
          )
        })}

      {(esCultivo || zona.tipo === 'BODEGA' || zona.tipo === 'CAMINO') && (
        <Rotulo texto={zona.codigo} activo={seleccionada} colores={colores} y={alto + (esCultivo ? 1.0 : 0.6)} />
      )}
    </group>
  )
}

/**
 * Controles de órbita de three.js (rotar, acercar, alejar) con amortiguación. Con frameloop="demand" se pide un
 * cuadro nuevo en cada cambio, así la escena no consume batería mientras nadie la mueve.
 */
function Controles({ cx, cz, maxDistancia }: { cx: number; cz: number; maxDistancia: number }) {
  const { camera, gl, invalidate } = useThree()
  const controles = useMemo(() => new OrbitControls(camera, gl.domElement), [camera, gl])

  useEffect(() => {
    controles.target.set(cx, 0, cz)
    controles.enableDamping = true
    controles.minDistance = 3
    controles.maxDistance = maxDistancia
    controles.maxPolarAngle = Math.PI / 2.15
    controles.update()
    const alCambiar = () => invalidate()
    controles.addEventListener('change', alCambiar)
    return () => {
      controles.removeEventListener('change', alCambiar)
    }
  }, [controles, cx, cz, maxDistancia, invalidate])

  useEffect(() => () => controles.dispose(), [controles])
  useFrame(() => controles.update())
  return null
}

/** Rótulo de la zona dibujado en un lienzo como sprite: sin raíces de React adicionales ni fuentes externas. */
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
    <sprite position={[0, y, 0]} scale={[0.9, 0.45, 1]} renderOrder={10}>
      <spriteMaterial map={textura} depthTest={false} />
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

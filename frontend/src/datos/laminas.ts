// Láminas botánicas de la ESPECIE, nunca de la planta de un huerto: cuando el líder sube su propia fotografía,
// esa tiene prioridad. Dos orígenes, ambos acreditados en public/laminas/credits.json:
//  - Köhler (1887), dominio público, descargadas de Wikimedia Commons.
//  - Ilustraciones generadas con IA en el mismo estilo, para especies sin lámina histórica disponible. Se dicen
//    generadas en su pie y en el pie de página; no se presentan como láminas históricas.

export interface Lamina {
  src: string
  /** Versión reducida (440 px) para miniaturas; solo existe en las ilustraciones nuevas. */
  srcReducida?: string
  /** Pie que dice qué es la imagen. */
  pie: string
  /** Autoría corta para el pie: «Köhler, 1887» o «ilustración generada con IA». */
  autoria: string
  /** Página de la fuente; las ilustraciones IA no tienen. */
  fuente?: string
  ia: boolean
}

const KOHLER = 'Köhler, 1887'
const IA = 'ilustración generada con IA'

function kohler(especie: string, archivo: string, fuente: string): Lamina {
  return { src: `/laminas/${archivo}.jpg`, pie: `Lámina de la especie ${especie}`, autoria: KOHLER, fuente, ia: false }
}

function generada(especie: string, archivo: string): Lamina {
  return {
    src: `/laminas/${archivo}.webp`,
    srcReducida: `/laminas/${archivo}-440.webp`,
    pie: `Lámina de la especie ${especie}`,
    autoria: IA,
    ia: true,
  }
}

const LAMINAS: Record<string, Lamina> = {
  'matricaria chamomilla': kohler(
    'Matricaria chamomilla',
    'matricaria-chamomilla',
    'https://commons.wikimedia.org/wiki/File:Matricaria_recutita_-_K%C3%B6hler%E2%80%93s_Medizinal-Pflanzen-091.jpg',
  ),
  'melissa officinalis': kohler(
    'Melissa officinalis',
    'melissa-officinalis',
    'https://commons.wikimedia.org/wiki/File:Melissa_K%C3%B6hler.jpg',
  ),
  'ruta graveolens': kohler(
    'Ruta graveolens',
    'ruta-graveolens',
    "https://commons.wikimedia.org/wiki/File:K%C3%B6hler%27s_Medizinal-Pflanzen_in_naturgetreuen_Abbildungen_mit_kurz_erl%C3%A4uterndem_Texte_(Plate_24)_(7118315805).jpg",
  ),
  'cymbopogon citratus': generada('Cymbopogon citratus', 'cymbopogon-citratus'),
  'aloe vera': generada('Aloe vera', 'aloe-vera'),
  'mentha spicata': generada('Mentha spicata', 'mentha-spicata'),
  'origanum vulgare': generada('Origanum vulgare', 'origanum-vulgare'),
  'plantago major': generada('Plantago major', 'plantago-major'),
  'urtica dioica': generada('Urtica dioica', 'urtica-dioica'),
  'aloysia citrodora': generada('Aloysia citrodora', 'aloysia-citrodora'),
}

// Sinónimos botánicos aceptados.
const SINONIMOS: Record<string, string> = {
  'matricaria recutita': 'matricaria chamomilla',
  'chamomilla recutita': 'matricaria chamomilla',
  'aloe barbadensis': 'aloe vera',
  'andropogon citratus': 'cymbopogon citratus',
  'aloysia triphylla': 'aloysia citrodora',
  'lippia citriodora': 'aloysia citrodora',
}

export const CREDITO_LAMINAS = "Franz Eugen Köhler, «Köhler's Medizinal-Pflanzen» (1887), dominio público"
export const CREDITO_IA =
  'Las demás láminas e ilustraciones se generaron con IA en el estilo de Köhler y se revisaron a mano; no son fotos de los huertos'

/** Lámina de la especie por su nombre científico (género y especie), si existe en el catálogo. */
export function laminaDe(nombreCientifico: string): Lamina | null {
  const clave = nombreCientifico.trim().toLowerCase().split(/\s+/).slice(0, 2).join(' ')
  return LAMINAS[SINONIMOS[clave] ?? clave] ?? null
}

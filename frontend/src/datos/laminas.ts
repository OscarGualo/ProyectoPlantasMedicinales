// Láminas botánicas de colecciones abiertas (dominio público), descargadas con su crédito en
// public/laminas/credits.json. Ilustran la ESPECIE, nunca la planta de un huerto: cuando el líder sube su
// propia fotografía, esa tiene prioridad.

export interface Lamina {
  src: string
  /** Pie que dice qué es la imagen. */
  pie: string
  fuente: string
}

const KOHLER = "Franz Eugen Köhler, «Köhler's Medizinal-Pflanzen» (1887), dominio público"

const LAMINAS: Record<string, Lamina> = {
  'matricaria chamomilla': {
    src: '/laminas/matricaria-chamomilla.jpg',
    pie: 'Lámina de la especie Matricaria chamomilla',
    fuente: 'https://commons.wikimedia.org/wiki/File:Matricaria_recutita_-_K%C3%B6hler%E2%80%93s_Medizinal-Pflanzen-091.jpg',
  },
  'melissa officinalis': {
    src: '/laminas/melissa-officinalis.jpg',
    pie: 'Lámina de la especie Melissa officinalis',
    fuente: 'https://commons.wikimedia.org/wiki/File:Melissa_K%C3%B6hler.jpg',
  },
  'ruta graveolens': {
    src: '/laminas/ruta-graveolens.jpg',
    pie: 'Lámina de la especie Ruta graveolens',
    fuente:
      "https://commons.wikimedia.org/wiki/File:K%C3%B6hler%27s_Medizinal-Pflanzen_in_naturgetreuen_Abbildungen_mit_kurz_erl%C3%A4uterndem_Texte_(Plate_24)_(7118315805).jpg",
  },
}

// Sinónimos botánicos aceptados.
const SINONIMOS: Record<string, string> = {
  'matricaria recutita': 'matricaria chamomilla',
  'chamomilla recutita': 'matricaria chamomilla',
}

export const CREDITO_LAMINAS = KOHLER

/** Lámina de la especie por su nombre científico (género y especie), si existe en el catálogo. */
export function laminaDe(nombreCientifico: string | null): Lamina | null {
  if (!nombreCientifico) return null
  const clave = nombreCientifico.trim().toLowerCase().split(/\s+/).slice(0, 2).join(' ')
  return LAMINAS[SINONIMOS[clave] ?? clave] ?? null
}

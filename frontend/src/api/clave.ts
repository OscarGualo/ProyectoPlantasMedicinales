/**
 * Clave de envío (UUID v4) para que un reintento no duplique una siembra (HU-014 CA-02).
 * `crypto.randomUUID` solo existe en contextos seguros (HTTPS o localhost); en un celular que abre la app por la
 * red local se usa `getRandomValues`, disponible en cualquier contexto.
 */
export function nuevaClave(): string {
  if (typeof crypto.randomUUID === 'function') return crypto.randomUUID()
  const b = crypto.getRandomValues(new Uint8Array(16))
  b[6] = (b[6] & 0x0f) | 0x40
  b[8] = (b[8] & 0x3f) | 0x80
  const h = [...b].map((x) => x.toString(16).padStart(2, '0')).join('')
  return `${h.slice(0, 8)}-${h.slice(8, 12)}-${h.slice(12, 16)}-${h.slice(16, 20)}-${h.slice(20)}`
}

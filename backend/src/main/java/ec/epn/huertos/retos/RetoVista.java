package ec.epn.huertos.retos;

/**
 * Reto Documentación lunar de la comunidad de un huerto publicado (HU-018). Sin insignia, {@code insignia} es
 * {@code null} y {@code mensaje} lleva MSG-18. No hay puntos, niveles ni posiciones (D-09).
 *
 * @param explicacion la práctica que el reto reconoce (CA-01)
 * @param alcance     qué reconoce y qué no reconoce la insignia
 */
public record RetoVista(Long huertoId, String huerto, String comunidad, String reto, String explicacion,
                        String fuente, String alcance, Insignia insignia, String codigo, String mensaje) {

    /** La insignia activa y la siembra que la sustenta. */
    public record Insignia(String nombre, Sustento sustento) {
    }

    /** Siembra vigente, con foto y conforme, que hoy sustenta la insignia. */
    public record Sustento(Long siembraId, String planta, String zona, String fechaHechoLocal, String fotoUrl,
                           String fase, String condicion) {
    }
}

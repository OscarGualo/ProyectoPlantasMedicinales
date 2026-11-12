package ec.epn.huertos.consulta;

import java.math.BigDecimal;
import java.util.List;

/**
 * Huerto publicado tal como aparece en el mapa y en la lista pública (HU-010): sin contacto ni datos de cuenta
 * (RN-02), con los conteos que permiten elegir cuál consultar y las especies que crecen en él (para mostrar sus
 * láminas en la lista; son datos públicos que ya publica su ficha, HU-011).
 */
public record ResumenHuerto(Long id, String nombre, String comunidad, String provincia, String parroquia,
                            BigDecimal latitud, BigDecimal longitud, long plantas, long cultivos,
                            List<Especie> especies) {

    /** Planta del huerto reducida a sus nombres, en el orden en que se registraron. */
    public record Especie(String nombreComun, String nombreCientifico) {
    }
}

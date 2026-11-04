package ec.epn.huertos.consulta;

import java.math.BigDecimal;

/**
 * Huerto publicado tal como aparece en el mapa y en la lista pública (HU-010): sin contacto ni datos de cuenta
 * (RN-02), con los conteos que permiten elegir cuál consultar.
 */
public record ResumenHuerto(Long id, String nombre, String comunidad, String provincia, String parroquia,
                            BigDecimal latitud, BigDecimal longitud, long plantas, long cultivos) {
}

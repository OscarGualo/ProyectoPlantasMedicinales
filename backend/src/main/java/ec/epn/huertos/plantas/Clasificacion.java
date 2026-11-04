package ec.epn.huertos.plantas;

import ec.epn.huertos.comun.SolicitudInvalida;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Clasificación de la medicina ancestral andina (doc 05 §2.1). Se señala en rojo (caliente) o en verde (fría);
 * una planta sin clasificar se señala en gris (HU-013 CA-02).
 */
public enum Clasificacion {
    CALIENTE, FRIA;

    /** Acepta "CALIENTE", "caliente", "FRIA", "fría"…; vacío significa sin clasificar. */
    public static Clasificacion desde(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        String normal = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase(Locale.ROOT);
        try {
            return valueOf(normal);
        } catch (IllegalArgumentException e) {
            throw new SolicitudInvalida("Clasificación desconocida: " + valor);
        }
    }
}

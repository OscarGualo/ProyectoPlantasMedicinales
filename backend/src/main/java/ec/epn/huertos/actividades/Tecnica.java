package ec.epn.huertos.actividades;

import ec.epn.huertos.comun.ErrorNegocio;

import java.text.Normalizer;
import java.util.Locale;

/** Técnicas de siembra de HU-014 CA-01: semillas, estacas, esquejes o plántulas. */
public enum Tecnica {
    SEMILLAS("semillas"),
    ESTACAS("estacas"),
    ESQUEJES("esquejes"),
    PLANTULAS("plántulas");

    private final String etiqueta;

    Tecnica(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }

    /** Acepta el nombre técnico o la etiqueta visible; vacío o desconocido es MSG-04 sobre «Técnica». */
    public static Tecnica desde(String valor) {
        if (valor != null && !valor.isBlank()) {
            String normal = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toUpperCase(Locale.ROOT);
            for (Tecnica t : values()) {
                if (t.name().equals(normal)) {
                    return t;
                }
            }
        }
        throw ErrorNegocio.campoObligatorio("Técnica", "tecnica");
    }
}

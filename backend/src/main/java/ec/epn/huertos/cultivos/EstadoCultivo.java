package ec.epn.huertos.cultivos;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;

import java.util.Arrays;
import java.util.Locale;

/** Estados de un cultivo (RN-03). El estado lo informa el líder; el sistema no lo deduce. */
public enum EstadoCultivo {
    SEMBRADO("sembrado"),
    EN_CRECIMIENTO("en crecimiento"),
    COSECHADO("cosechado"),
    PERDIDO("perdido");

    private final String etiqueta;

    EstadoCultivo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }

    /** Acepta el nombre técnico o la etiqueta visible; cualquier otro valor es MSG-10 (HU-009 CA-02). */
    public static EstadoCultivo desde(String valor) {
        if (valor != null) {
            String v = valor.trim();
            return Arrays.stream(values())
                    .filter(e -> e.name().equalsIgnoreCase(v) || e.etiqueta.equals(v.toLowerCase(Locale.ROOT)))
                    .findFirst()
                    .orElseThrow(() -> ErrorNegocio.enCampo(Mensaje.MSG_10, "estado"));
        }
        throw ErrorNegocio.enCampo(Mensaje.MSG_10, "estado");
    }
}

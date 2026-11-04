package ec.epn.huertos.comun;

/**
 * Solicitud con un formato o valor que la interfaz nunca envía (por ejemplo, una clasificación desconocida) y
 * para la que el catálogo no define un mensaje MSG. Se responde 400 sin detalles internos.
 */
public class SolicitudInvalida extends RuntimeException {

    public SolicitudInvalida(String detalle) {
        super(detalle);
    }
}

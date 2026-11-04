package ec.epn.huertos.comun;

/**
 * Error de negocio que se traduce a una respuesta HTTP con su código MSG (doc 06 §9).
 */
public class ErrorNegocio extends RuntimeException {

    private final Mensaje mensaje;
    private final String campo;

    private ErrorNegocio(Mensaje mensaje, String campo, String texto) {
        super(texto);
        this.mensaje = mensaje;
        this.campo = campo;
    }

    public static ErrorNegocio de(Mensaje mensaje, Object... argumentos) {
        return new ErrorNegocio(mensaje, null, mensaje.texto(argumentos));
    }

    /** MSG-04 con el nombre visible del campo y su clave técnica para la interfaz. */
    public static ErrorNegocio campoObligatorio(String nombreVisible, String clave) {
        return new ErrorNegocio(Mensaje.MSG_04, clave, Mensaje.MSG_04.texto(nombreVisible));
    }

    public static ErrorNegocio enCampo(Mensaje mensaje, String clave, Object... argumentos) {
        return new ErrorNegocio(mensaje, clave, mensaje.texto(argumentos));
    }

    public Mensaje mensaje() {
        return mensaje;
    }

    public String campo() {
        return campo;
    }
}

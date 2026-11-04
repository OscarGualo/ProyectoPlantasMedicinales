package ec.epn.huertos.comun;

import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Catálogo de mensajes del sistema (doc 05 §6). Los textos son literales de los criterios de aceptación
 * (doc 03): los casos de prueba los comparan exactamente. Los valores entre llaves se reemplazan, en orden,
 * por los argumentos recibidos.
 */
public enum Mensaje {

    MSG_01("Usuario o contraseña incorrectos.", HttpStatus.UNAUTHORIZED),
    MSG_02("Tu sesión ha finalizado. Inicia sesión para continuar.", HttpStatus.UNAUTHORIZED),
    MSG_03("No tienes permiso para modificar la información de este huerto.", HttpStatus.FORBIDDEN),
    MSG_04("Completa el campo obligatorio: {campo}.", HttpStatus.BAD_REQUEST),
    MSG_05("La superficie debe ser un número mayor o igual a 0.", HttpStatus.BAD_REQUEST),
    MSG_06("Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180.",
            HttpStatus.BAD_REQUEST),
    MSG_07("La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.", HttpStatus.BAD_REQUEST),
    MSG_08("La fecha y hora de la actividad no pueden ser posteriores al momento actual.", HttpStatus.BAD_REQUEST),
    MSG_09("La zona seleccionada no pertenece a este huerto.", HttpStatus.BAD_REQUEST),
    MSG_10("Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido.", HttpStatus.BAD_REQUEST),
    MSG_11("No se pudo guardar la fotografía. La actividad no se registró; inténtalo nuevamente.",
            HttpStatus.SERVICE_UNAVAILABLE),
    MSG_12("Indica el motivo de la corrección.", HttpStatus.BAD_REQUEST),
    MSG_13("No se pudo cargar la vista 3D del huerto. Puedes consultar sus zonas y cultivos en la lista.",
            HttpStatus.OK),
    MSG_14("El huerto solicitado no existe o no está publicado.", HttpStatus.NOT_FOUND),
    MSG_15("Conforme con la condición lunar: la siembra se realizó en {fase}.", HttpStatus.OK),
    MSG_16("No conforme con la condición lunar: la siembra se realizó en {fase}; la práctica indica luna creciente o luna llena.",
            HttpStatus.OK),
    MSG_17("Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra.", HttpStatus.OK),
    MSG_18("Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una siembra con fotografía realizada en luna creciente o luna llena.",
            HttpStatus.OK),
    MSG_19("El cultivo seleccionado no pertenece a tu huerto.", HttpStatus.FORBIDDEN),
    MSG_20("El calendario lunar está disponible desde {fecha inicial} hasta {fecha final}.", HttpStatus.BAD_REQUEST),
    MSG_21("El huerto no tiene ubicación registrada y no puede publicarse en el mapa.", HttpStatus.BAD_REQUEST),
    MSG_22("Esta zona no tiene cultivos registrados.", HttpStatus.OK),
    MSG_23("Esta siembra no tiene correcciones registradas.", HttpStatus.OK);

    private static final Pattern MARCADOR = Pattern.compile("\\{[^}]+}");

    private final String plantilla;
    private final HttpStatus estadoHttp;

    Mensaje(String plantilla, HttpStatus estadoHttp) {
        this.plantilla = plantilla;
        this.estadoHttp = estadoHttp;
    }

    /** Identificador tal como aparece en los documentos, por ejemplo {@code MSG-04}. */
    public String codigo() {
        return name().replace('_', '-');
    }

    public HttpStatus estadoHttp() {
        return estadoHttp;
    }

    public String plantilla() {
        return plantilla;
    }

    /** Texto con los marcadores {…} reemplazados en orden por los argumentos. */
    public String texto(Object... argumentos) {
        Matcher m = MARCADOR.matcher(plantilla);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (m.find()) {
            String valor = i < argumentos.length ? String.valueOf(argumentos[i++]) : m.group();
            m.appendReplacement(sb, Matcher.quoteReplacement(valor));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}

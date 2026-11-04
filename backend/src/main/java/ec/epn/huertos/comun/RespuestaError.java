package ec.epn.huertos.comun;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Formato de error del doc 06 §9: {@code {"codigo": "MSG-04", "mensaje": "...", "campo": "nombre"}}. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RespuestaError(String codigo, String mensaje, String campo) {

    public static RespuestaError de(Mensaje mensaje) {
        return new RespuestaError(mensaje.codigo(), mensaje.texto(), null);
    }
}

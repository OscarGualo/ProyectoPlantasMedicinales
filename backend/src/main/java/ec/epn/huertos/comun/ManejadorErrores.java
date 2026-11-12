package ec.epn.huertos.comun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Traduce los errores a la respuesta {codigo, mensaje, campo}. Nunca expone detalles internos.
 */
@Slf4j
@RestControllerAdvice
public class ManejadorErrores {

    static final String SOLICITUD_INVALIDA = "La solicitud no tiene un formato válido.";

    @ExceptionHandler(ErrorNegocio.class)
    ResponseEntity<RespuestaError> negocio(ErrorNegocio e) {
        Mensaje m = e.mensaje();
        return ResponseEntity.status(m.estadoHttp())
                .body(new RespuestaError(m.codigo(), e.getMessage(), e.campo()));
    }

    /** El archivo supera el límite del servidor (6 MB): se responde igual que la validación del servicio. */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<RespuestaError> archivoGrande(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RespuestaError.de(Mensaje.MSG_07));
    }

    @ExceptionHandler({SolicitudInvalida.class, HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class, BindException.class})
    ResponseEntity<RespuestaError> solicitudInvalida(Exception e) {
        return ResponseEntity.badRequest()
                .body(new RespuestaError("SOLICITUD_INVALIDA", SOLICITUD_INVALIDA, null));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<RespuestaError> noEncontrado(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaError("NO_ENCONTRADO", "El recurso solicitado no existe.", null));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<RespuestaError> inesperado(Exception e) {
        log.error("Error no controlado", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaError("ERROR_INTERNO", "Ocurrió un error inesperado. Inténtalo nuevamente.", null));
    }
}

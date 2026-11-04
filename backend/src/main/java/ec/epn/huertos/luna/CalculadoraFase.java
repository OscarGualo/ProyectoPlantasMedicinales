package ec.epn.huertos.luna;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Fase lunar vigente en un instante, según la zona horaria del huerto (RN-09). Es la pieza que el entorno de
 * prueba sustituye por CAL-T, el calendario controlado con una fase fija por día (doc 04).
 */
public interface CalculadoraFase {

    /** Fase vigente o vacío si no hay información de fase (la siembra queda pendiente: MSG-17). */
    Optional<FaseLunar> faseEn(Instant instante, ZoneId zona);
}

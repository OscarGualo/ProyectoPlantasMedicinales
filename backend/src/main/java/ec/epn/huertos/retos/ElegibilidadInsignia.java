package ec.epn.huertos.retos;

import ec.epn.huertos.actividades.ServicioActividades.SiembraVigente;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * RN-08: la comunidad tiene la insignia Documentación lunar si al menos una siembra vigente tiene fotografía y es
 * conforme con la condición lunar. La insignia es única: varias siembras elegibles no la multiplican. Se evalúa al
 * consultar, así una corrección se refleja sin recálculo aparte (AD-07).
 */
public final class ElegibilidadInsignia {

    private ElegibilidadInsignia() {
    }

    /** Siembra que sustenta la insignia (la elegible más reciente por fecha del hecho) o vacío si no hay ninguna. */
    public static Optional<SiembraVigente> sustento(List<SiembraVigente> vigentes) {
        return vigentes.stream()
                .filter(ElegibilidadInsignia::elegible)
                .max(Comparator.comparing(SiembraVigente::fechaHecho).thenComparing(SiembraVigente::actividadId));
    }

    static boolean elegible(SiembraVigente s) {
        return s.fotoId() != null && s.condicion() != null && s.condicion().conforme();
    }
}

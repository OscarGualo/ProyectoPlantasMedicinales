package ec.epn.huertos.luna;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

/**
 * CAL-T (doc 05 §7): calendario lunar controlado para los entornos de prueba (pruebas de API y perfil e2e). No
 * describe fases reales: cada valor abarca el día completo, de 00:00 a 24:00, en la zona horaria del huerto.
 * Nunca se usa en producción.
 */
public class CalendarioControladoCalT implements CalculadoraFase {

    /** Primer y último día que CAL-T define (el 12/10 está dentro del rango, pero sin dato). */
    static final LocalDate DESDE = LocalDate.of(2026, 10, 10);
    static final LocalDate HASTA = LocalDate.of(2026, 10, 14);

    private static final Map<LocalDate, FaseLunar> DIAS = Map.of(
            LocalDate.of(2026, 10, 10), FaseLunar.LUNA_CRECIENTE,
            LocalDate.of(2026, 10, 11), FaseLunar.LUNA_MENGUANTE,
            // 12/10/2026: sin dato
            LocalDate.of(2026, 10, 13), FaseLunar.LUNA_LLENA,
            LocalDate.of(2026, 10, 14), FaseLunar.LUNA_OSCURA);

    @Override
    public Optional<FaseLunar> faseEn(Instant instante, ZoneId zona) {
        return Optional.ofNullable(DIAS.get(LocalDate.ofInstant(instante, zona)));
    }

    /** Verdadero si el día local del instante está en el rango que CAL-T controla. */
    boolean cubre(Instant instante, ZoneId zona) {
        LocalDate dia = LocalDate.ofInstant(instante, zona);
        return !dia.isBefore(DESDE) && !dia.isAfter(HASTA);
    }
}

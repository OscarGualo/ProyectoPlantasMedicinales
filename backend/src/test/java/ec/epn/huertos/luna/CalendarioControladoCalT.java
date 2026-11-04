package ec.epn.huertos.luna;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

/**
 * CAL-T (doc 05 §7): calendario lunar controlado que solo existe en el entorno de prueba. No describe fases
 * reales: cada valor abarca el día completo, de 00:00 a 24:00, en la zona horaria del huerto.
 */
public class CalendarioControladoCalT implements CalculadoraFase {

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
}

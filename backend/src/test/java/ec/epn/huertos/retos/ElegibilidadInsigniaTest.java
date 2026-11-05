package ec.epn.huertos.retos;

import ec.epn.huertos.actividades.ServicioActividades.SiembraVigente;
import ec.epn.huertos.luna.FaseLunar;
import ec.epn.huertos.luna.ReglaSiembra;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/** Lógica de RN-08 sin base de datos (doc 06 §13: P-047 a P-049, nivel unitario). */
class ElegibilidadInsigniaTest {

    private final ReglaSiembra regla = new ReglaSiembra();

    private SiembraVigente siembra(long id, String fecha, FaseLunar fase, Long fotoId) {
        return new SiembraVigente(id, 1L, "Planta", "Z1", Instant.parse(fecha), fecha, fotoId,
                regla.evaluar(Optional.ofNullable(fase)));
    }

    @Test
    @DisplayName("P-049 (lógica) Sin siembras, no conformes o pendientes: sin insignia")
    void sinElegibles() {
        assertThat(ElegibilidadInsignia.sustento(List.of())).isEmpty();
        assertThat(ElegibilidadInsignia.sustento(List.of(
                siembra(1, "2026-10-11T13:00:00Z", FaseLunar.LUNA_MENGUANTE, 10L),
                siembra(2, "2026-10-14T13:00:00Z", FaseLunar.LUNA_OSCURA, 11L),
                siembra(3, "2026-10-12T13:00:00Z", null, 12L)))).isEmpty();
    }

    @Test
    @DisplayName("P-047 (lógica) Una siembra conforme con foto sustenta la insignia")
    void unaElegible() {
        assertThat(ElegibilidadInsignia.sustento(List.of(
                siembra(1, "2026-10-10T13:00:00Z", FaseLunar.LUNA_CRECIENTE, 10L),
                siembra(2, "2026-10-11T13:00:00Z", FaseLunar.LUNA_MENGUANTE, 11L))))
                .get().extracting(SiembraVigente::actividadId).isEqualTo(1L);
    }

    @Test
    @DisplayName("P-048 (lógica) Varias elegibles: una sola insignia, sustentada por la más reciente")
    void variasElegibles() {
        assertThat(ElegibilidadInsignia.sustento(List.of(
                siembra(1, "2026-10-10T13:00:00Z", FaseLunar.LUNA_CRECIENTE, 10L),
                siembra(3, "2026-10-13T13:00:00Z", FaseLunar.LUNA_LLENA, 11L))))
                .get().extracting(SiembraVigente::actividadId).isEqualTo(3L);
    }

    @Test
    @DisplayName("RN-08 Sin fotografía no hay elegibilidad")
    void sinFoto() {
        assertThat(ElegibilidadInsignia.sustento(List.of(
                siembra(1, "2026-10-10T13:00:00Z", FaseLunar.LUNA_CRECIENTE, null)))).isEmpty();
    }
}

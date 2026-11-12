package ec.epn.huertos.luna;

import ec.epn.huertos.luna.CondicionLunar.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/** Pruebas unitarias del módulo luna: no dependen de la base de datos (doc 06 §6). */
class LunaTest {

    private static final ZoneId GYE = ZoneId.of("America/Guayaquil");
    private static final ZoneId GAL = ZoneId.of("Pacific/Galapagos");

    @Nested
    @DisplayName("RN-09: intervalos entre puntos medios")
    class PuntosMedios {

        // Ciclo sintético: una fase principal cada 8 días a partir de T0.
        private final Instant t0 = Instant.parse("2026-01-01T00:00:00Z");
        private final ServicioFaseLunar servicio = new ServicioFaseLunar((desde, hasta) -> {
            FasePrincipal[] orden = FasePrincipal.values();
            return java.util.stream.IntStream.range(-8, 16)
                    .mapToObj(i -> new EventoFase(orden[Math.floorMod(i, 4)], t0.plus(Duration.ofDays(8L * i))))
                    .filter(e -> !e.instante().isBefore(desde) && !e.instante().isAfter(hasta))
                    .toList();
        });

        @Test
        void elInstanteDeCadaFasePrincipalPerteneceASuFase() {
            assertThat(servicio.faseEn(t0, GYE)).contains(FaseLunar.LUNA_OSCURA);
            assertThat(servicio.faseEn(t0.plus(Duration.ofDays(8)), GYE)).contains(FaseLunar.LUNA_CRECIENTE);
            assertThat(servicio.faseEn(t0.plus(Duration.ofDays(16)), GYE)).contains(FaseLunar.LUNA_LLENA);
            assertThat(servicio.faseEn(t0.plus(Duration.ofDays(24)), GYE)).contains(FaseLunar.LUNA_MENGUANTE);
        }

        @Test
        void elCambioOcurreEnElPuntoMedio() {
            Instant medio = t0.plus(Duration.ofDays(4));
            assertThat(servicio.faseEn(medio.minusSeconds(1), GYE)).contains(FaseLunar.LUNA_OSCURA);
            assertThat(servicio.faseEn(medio, GYE)).contains(FaseLunar.LUNA_CRECIENTE);
            assertThat(servicio.faseEn(medio.plusSeconds(1), GYE)).contains(FaseLunar.LUNA_CRECIENTE);
        }

        @Test
        @DisplayName("P-062 La ventana cubre ambos vecinos de la fase y el cambio es continuo")
        void faseEntreEventos() {
            Instant nueva = Instant.parse("2026-11-09T07:02:00Z");
            var eventos = List.of(
                    new EventoFase(FasePrincipal.LUNA_LLENA, Instant.parse("2026-10-26T04:12:00Z")),
                    new EventoFase(FasePrincipal.CUARTO_MENGUANTE, Instant.parse("2026-11-01T20:28:00Z")),
                    new EventoFase(FasePrincipal.LUNA_NUEVA, nueva),
                    new EventoFase(FasePrincipal.CUARTO_CRECIENTE, Instant.parse("2026-11-17T11:48:00Z")));
            var s = new ServicioFaseLunar((d, h) -> eventos.stream()
                    .filter(e -> !e.instante().isBefore(d) && !e.instante().isAfter(h)).toList());
            Instant medio = Instant.parse("2026-11-05T13:45:00Z");
            assertThat(s.faseEn(medio.minusSeconds(1), GYE)).contains(FaseLunar.LUNA_MENGUANTE);
            assertThat(s.faseEn(medio, GYE)).contains(FaseLunar.LUNA_OSCURA);
            assertThat(s.faseEn(medio.plusSeconds(1), GYE)).contains(FaseLunar.LUNA_OSCURA);
            var real = new ServicioFaseLunar(new EfemeridesSuncalc());
            var galapagos = ZoneId.of("Pacific/Galapagos");
            Instant inicio = Instant.parse("2026-11-01T00:00:00Z");
            for (int hora = 0; hora < 30 * 24; hora++) {
                Instant instante = inicio.plus(Duration.ofHours(hora));
                assertThat(real.faseEn(instante, GYE)).isPresent().isEqualTo(real.faseEn(instante, galapagos));
            }
        }

        @Test
        void laLunaLlenaIncluyeLosDiasAlrededorDelPlenilunio() {
            Instant plenilunio = t0.plus(Duration.ofDays(16));
            assertThat(servicio.faseEn(plenilunio.minus(Duration.ofDays(3)), GYE)).contains(FaseLunar.LUNA_LLENA);
            assertThat(servicio.faseEn(plenilunio.plus(Duration.ofDays(3)), GYE)).contains(FaseLunar.LUNA_LLENA);
        }

        @Test
        void sinEfemeridesNoHayFase() {
            ServicioFaseLunar vacio = new ServicioFaseLunar((d, h) -> List.of());
            assertThat(vacio.faseEn(t0, GYE)).isEmpty();
        }
    }

    @Nested
    @DisplayName("Efemérides con commons-suncalc")
    class Efemerides {

        private final EfemeridesSuncalc efemerides = new EfemeridesSuncalc();

        @Test
        void lasFasesPrincipalesSeSucedenEnOrdenCadaSieteDiasYMedio() {
            List<EventoFase> eventos = efemerides.eventosEntre(
                    Instant.parse("2026-10-01T00:00:00Z"), Instant.parse("2027-01-01T00:00:00Z"));

            assertThat(eventos).hasSizeBetween(12, 14);
            for (int i = 1; i < eventos.size(); i++) {
                EventoFase anterior = eventos.get(i - 1);
                EventoFase actual = eventos.get(i);
                assertThat(actual.fase().ordinal()).isEqualTo((anterior.fase().ordinal() + 1) % 4);
                Duration separacion = Duration.between(anterior.instante(), actual.instante());
                assertThat(separacion).isBetween(Duration.ofDays(6), Duration.ofDays(9));
            }
        }

        @Test
        void unPlenilunioCalculadoEsLunaLlena() {
            ServicioFaseLunar servicio = new ServicioFaseLunar(efemerides);
            EventoFase llena = efemerides.eventosEntre(
                            Instant.parse("2026-11-01T00:00:00Z"), Instant.parse("2026-12-01T00:00:00Z"))
                    .stream().filter(e -> e.fase() == FasePrincipal.LUNA_LLENA).findFirst().orElseThrow();
            assertThat(servicio.faseEn(llena.instante(), GYE)).contains(FaseLunar.LUNA_LLENA);
        }
    }

    @Nested
    @DisplayName("RN-06: regla de siembra")
    class Regla {

        private final ReglaSiembra regla = new ReglaSiembra();

        @Test
        void crecienteOLlenaSonConformes() {
            CondicionLunar c = regla.evaluar(Optional.of(FaseLunar.LUNA_LLENA));
            assertThat(c.resultado()).isEqualTo(Resultado.CONFORME);
            assertThat(c.codigo()).isEqualTo("MSG-15");
            assertThat(c.mensaje()).isEqualTo("Conforme con la condición lunar: la siembra se realizó en luna llena.");
            assertThat(regla.evaluar(Optional.of(FaseLunar.LUNA_CRECIENTE)).conforme()).isTrue();
        }

        @Test
        void oscuraOMenguanteNoSonConformes() {
            CondicionLunar c = regla.evaluar(Optional.of(FaseLunar.LUNA_MENGUANTE));
            assertThat(c.resultado()).isEqualTo(Resultado.NO_CONFORME);
            assertThat(c.codigo()).isEqualTo("MSG-16");
            assertThat(c.mensaje()).isEqualTo("No conforme con la condición lunar: la siembra se realizó en "
                    + "luna menguante; la práctica indica luna creciente o luna llena.");
            assertThat(regla.evaluar(Optional.of(FaseLunar.LUNA_OSCURA)).resultado()).isEqualTo(Resultado.NO_CONFORME);
        }

        @Test
        void sinFaseQuedaPendiente() {
            CondicionLunar c = regla.evaluar(Optional.empty());
            assertThat(c.resultado()).isEqualTo(Resultado.PENDIENTE);
            assertThat(c.codigo()).isEqualTo("MSG-17");
            assertThat(c.fase()).isNull();
        }
    }

    @Nested
    @DisplayName("CAL-T: calendario controlado del entorno de prueba")
    class CalT {

        private final CalendarioControladoCalT calT = new CalendarioControladoCalT();

        private Optional<FaseLunar> fase(String fechaHoraLocal, ZoneId zona) {
            return calT.faseEn(LocalDateTime.parse(fechaHoraLocal).atZone(zona).toInstant(), zona);
        }

        @Test
        void cadaValorAbarcaElDiaCompletoEnLaZonaDelHuerto() {
            assertThat(fase("2026-10-10T00:00", GYE)).contains(FaseLunar.LUNA_CRECIENTE);
            assertThat(fase("2026-10-10T23:59", GYE)).contains(FaseLunar.LUNA_CRECIENTE);
            assertThat(fase("2026-10-11T08:00", GYE)).contains(FaseLunar.LUNA_MENGUANTE);
            assertThat(fase("2026-10-13T08:00", GAL)).contains(FaseLunar.LUNA_LLENA);
            assertThat(fase("2026-10-14T08:00", GYE)).contains(FaseLunar.LUNA_OSCURA);
        }

        @Test
        void el12DeOctubreNoTieneDato() {
            assertThat(fase("2026-10-12T08:00", GYE)).isEmpty();
        }
    }

    @Nested
    @DisplayName("RN-10: actividades por fase")
    class Actividades {

        @Test
        void laSiembraSeRecomiendaEnCrecienteYLlena() {
            assertThat(ActividadAgricola.SIEMBRA.recomendadaEn(FaseLunar.LUNA_CRECIENTE)).isTrue();
            assertThat(ActividadAgricola.SIEMBRA.recomendadaEn(FaseLunar.LUNA_LLENA)).isTrue();
            assertThat(ActividadAgricola.SIEMBRA.recomendadaEn(FaseLunar.LUNA_MENGUANTE)).isFalse();
            assertThat(ActividadAgricola.SIEMBRA.recomendadaEn(FaseLunar.LUNA_OSCURA)).isFalse();
        }

        @Test
        void laCosechaDeSemillasYRaicesSoloEnMenguante() {
            for (FaseLunar f : FaseLunar.values()) {
                assertThat(ActividadAgricola.COSECHA_SEMILLAS_RAICES.recomendadaEn(f))
                        .isEqualTo(f == FaseLunar.LUNA_MENGUANTE);
            }
        }

        @Test
        void cadaFaseTieneActividadesYLaTablaCitaSuFuente() {
            ActividadesPorFase tabla = new ActividadesPorFase();
            for (FaseLunar f : FaseLunar.values()) {
                assertThat(tabla.actividades(f)).isNotEmpty();
            }
            assertThat(tabla.actividades(FaseLunar.LUNA_MENGUANTE)).containsExactly("Cosecha de semillas y raíces");
            assertThat(tabla.fuente()).contains("Anexo 4");
        }
    }
}

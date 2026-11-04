package ec.epn.huertos.luna;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.comun.RelojConfig;
import ec.epn.huertos.comun.SolicitudInvalida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Calendario lunar para planificar (HU-019) y su filtro por actividad (HU-020): fase de cada día a las 12:00 en hora
 * del Ecuador continental (RN-09), instantes de las fases principales y actividades recomendadas por fase (RN-10).
 */
@Service
@RequiredArgsConstructor
public class ServicioCalendario {

    public static final int DIAS_POR_DEFECTO = 30;
    public static final int DIAS_MAXIMO = 60;
    private static final ZoneId ZONA = RelojConfig.ZONA_ECUADOR;
    private static final LocalTime MEDIODIA = LocalTime.NOON;
    private static final DateTimeFormatter FECHA_MSG = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT);

    private final CalculadoraFase calculadora;
    private final FuenteFaseLunar fuente;
    private final ActividadesPorFase actividades;
    private final Clock reloj;

    public record Dia(LocalDate fecha, FaseLunar fase, String faseEtiqueta, boolean resaltado) {
    }

    public record EventoVista(FasePrincipal fase, String etiqueta, OffsetDateTime instante) {
    }

    public record ActividadOpcion(ActividadAgricola id, String etiqueta) {
    }

    public record Calendario(LocalDate desde, LocalDate hasta, LocalDate disponibleDesde, LocalDate disponibleHasta,
                             String zonaHoraria, ActividadAgricola actividad, List<Dia> dias,
                             List<EventoVista> fasesPrincipales, Map<FaseLunar, List<String>> actividadesPorFase,
                             List<ActividadOpcion> opciones, String fuente) {
    }

    public Calendario calendario(LocalDate desdeSolicitado, Integer diasSolicitados, String actividadSolicitada) {
        LocalDate hoy = LocalDate.now(reloj.withZone(ZONA));
        LocalDate disponibleDesde = hoy.minusMonths(12);
        LocalDate disponibleHasta = hoy.plusMonths(12);
        int dias = diasSolicitados == null ? DIAS_POR_DEFECTO : diasSolicitados;
        if (dias < 1 || dias > DIAS_MAXIMO) {
            throw new SolicitudInvalida("Cantidad de días fuera de rango");
        }
        LocalDate desde = desdeSolicitado == null ? hoy : desdeSolicitado;
        LocalDate hasta = desde.plusDays(dias - 1L);
        // HU-019 CA-02: fuera del rango disponible de 12 meses antes o después de hoy.
        if (desde.isBefore(disponibleDesde) || hasta.isAfter(disponibleHasta)) {
            throw ErrorNegocio.de(Mensaje.MSG_20, FECHA_MSG.format(disponibleDesde), FECHA_MSG.format(disponibleHasta));
        }
        ActividadAgricola filtro = actividad(actividadSolicitada);

        List<Dia> listado = desde.datesUntil(hasta.plusDays(1))
                .map(fecha -> {
                    Optional<FaseLunar> fase = calculadora.faseEn(fecha.atTime(MEDIODIA).atZone(ZONA).toInstant(), ZONA);
                    boolean resaltado = filtro != null && fase.map(filtro::recomendadaEn).orElse(false);
                    return new Dia(fecha, fase.orElse(null), fase.map(FaseLunar::etiqueta).orElse(null), resaltado);
                })
                .toList();

        List<EventoVista> eventos = fuente.eventosEntre(desde.atStartOfDay(ZONA).toInstant(),
                        hasta.plusDays(1).atStartOfDay(ZONA).toInstant())
                .stream()
                .map(e -> new EventoVista(e.fase(), e.fase().etiqueta(), e.instante().atZone(ZONA).toOffsetDateTime()))
                .toList();

        Map<FaseLunar, List<String>> porFase = new EnumMap<>(FaseLunar.class);
        for (FaseLunar f : FaseLunar.values()) {
            porFase.put(f, actividades.actividades(f));
        }
        List<ActividadOpcion> opciones = Arrays.stream(ActividadAgricola.values())
                .map(a -> new ActividadOpcion(a, a.etiqueta()))
                .toList();

        return new Calendario(desde, hasta, disponibleDesde, disponibleHasta, ZONA.getId(), filtro, listado, eventos,
                porFase, opciones, actividades.fuente());
    }

    private static ActividadAgricola actividad(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return ActividadAgricola.valueOf(valor.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new SolicitudInvalida("Actividad desconocida: " + valor);
        }
    }
}

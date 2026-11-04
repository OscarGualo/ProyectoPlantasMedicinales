package ec.epn.huertos.luna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * Cálculo de la fase de un instante (RN-09, doc 06 §10.3): cada fase abarca desde el punto medio con la fase
 * principal anterior hasta el punto medio con la siguiente; así, la luna llena incluye los días alrededor del
 * plenilunio. El instante es absoluto: la zona horaria del huerto solo interviene al convertir fechas locales.
 */
@Service
@RequiredArgsConstructor
public class ServicioFaseLunar implements CalculadoraFase {

    /** Margen que garantiza al menos un evento anterior y uno posterior al de la fase buscada. */
    private static final Duration MARGEN = Duration.ofDays(20);

    private final FuenteFaseLunar fuente;

    @Override
    public Optional<FaseLunar> faseEn(Instant instante, ZoneId zona) {
        List<EventoFase> eventos = fuente.eventosEntre(instante.minus(MARGEN), instante.plus(MARGEN));
        for (int i = 1; i < eventos.size() - 1; i++) {
            Instant inicio = puntoMedio(eventos.get(i - 1).instante(), eventos.get(i).instante());
            Instant fin = puntoMedio(eventos.get(i).instante(), eventos.get(i + 1).instante());
            if (!instante.isBefore(inicio) && instante.isBefore(fin)) {
                return Optional.of(eventos.get(i).fase().fase());
            }
        }
        return Optional.empty();
    }

    static Instant puntoMedio(Instant a, Instant b) {
        return a.plus(Duration.between(a, b).dividedBy(2));
    }
}

package ec.epn.huertos.luna;

import org.shredzone.commons.suncalc.MoonPhase;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Efemérides calculadas localmente con commons-suncalc, sin depender de un servicio en línea (AD-06, D-11).
 */
@Component
public class EfemeridesSuncalc implements FuenteFaseLunar {

    private static final int MAXIMO_POR_FASE = 64;

    @Override
    public List<EventoFase> eventosEntre(Instant desde, Instant hasta) {
        List<EventoFase> eventos = new ArrayList<>();
        for (FasePrincipal fase : FasePrincipal.values()) {
            Instant cursor = desde;
            for (int i = 0; i < MAXIMO_POR_FASE; i++) {
                Instant instante = MoonPhase.compute().utc().on(cursor).phase(fase.suncalc()).execute()
                        .getTime().toInstant();
                if (instante.isAfter(hasta)) {
                    break;
                }
                if (!instante.isBefore(desde)) {
                    eventos.add(new EventoFase(fase, instante));
                }
                // Cada fase se repite cada ~29,5 días; avanzar un día evita encontrar el mismo evento.
                cursor = instante.plus(Duration.ofDays(1));
            }
        }
        eventos.sort(Comparator.comparing(EventoFase::instante));
        return eventos;
    }
}

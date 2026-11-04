package ec.epn.huertos.luna;

import java.time.Instant;
import java.util.List;

/** Fuente de efemérides: instantes de las fases principales (AD-06). */
public interface FuenteFaseLunar {

    /** Eventos con {@code desde ≤ instante ≤ hasta}, ordenados por instante. */
    List<EventoFase> eventosEntre(Instant desde, Instant hasta);
}

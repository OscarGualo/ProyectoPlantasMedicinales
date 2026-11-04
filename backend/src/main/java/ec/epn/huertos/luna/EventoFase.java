package ec.epn.huertos.luna;

import java.time.Instant;

/** Instante en que ocurre una fase principal. */
public record EventoFase(FasePrincipal fase, Instant instante) {
}

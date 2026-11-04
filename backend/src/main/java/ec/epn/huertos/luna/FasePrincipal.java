package ec.epn.huertos.luna;

import org.shredzone.commons.suncalc.MoonPhase;

/**
 * Instantes astronómicos principales y la fase que inician (doc 06 §10.3): luna nueva → luna oscura,
 * cuarto creciente → luna creciente, luna llena → luna llena, cuarto menguante → luna menguante.
 */
public enum FasePrincipal {
    LUNA_NUEVA("luna nueva", FaseLunar.LUNA_OSCURA, MoonPhase.Phase.NEW_MOON),
    CUARTO_CRECIENTE("cuarto creciente", FaseLunar.LUNA_CRECIENTE, MoonPhase.Phase.FIRST_QUARTER),
    LUNA_LLENA("luna llena", FaseLunar.LUNA_LLENA, MoonPhase.Phase.FULL_MOON),
    CUARTO_MENGUANTE("cuarto menguante", FaseLunar.LUNA_MENGUANTE, MoonPhase.Phase.LAST_QUARTER);

    private final String etiqueta;
    private final FaseLunar fase;
    private final MoonPhase.Phase suncalc;

    FasePrincipal(String etiqueta, FaseLunar fase, MoonPhase.Phase suncalc) {
        this.etiqueta = etiqueta;
        this.fase = fase;
        this.suncalc = suncalc;
    }

    public String etiqueta() {
        return etiqueta;
    }

    public FaseLunar fase() {
        return fase;
    }

    MoonPhase.Phase suncalc() {
        return suncalc;
    }
}

package ec.epn.huertos.luna;

import java.util.EnumSet;
import java.util.Set;

import static ec.epn.huertos.luna.FaseLunar.LUNA_CRECIENTE;
import static ec.epn.huertos.luna.FaseLunar.LUNA_LLENA;
import static ec.epn.huertos.luna.FaseLunar.LUNA_MENGUANTE;
import static ec.epn.huertos.luna.FaseLunar.LUNA_OSCURA;

/** Actividades del filtro del calendario y las fases en que se recomiendan (RN-10, HU-020). */
public enum ActividadAgricola {
    PREPARACION_TIERRA("Preparación de la tierra", EnumSet.of(LUNA_OSCURA)),
    ELABORACION_ABONO("Elaboración de abono", EnumSet.of(LUNA_OSCURA)),
    DESHIERBE("Deshierbe", EnumSet.of(LUNA_OSCURA)),
    CONTROL_PLAGAS("Control de plagas", EnumSet.of(LUNA_OSCURA)),
    SIEMBRA("Siembra", EnumSet.of(LUNA_CRECIENTE, LUNA_LLENA)),
    TRASPLANTE("Trasplante y resiembra", EnumSet.of(LUNA_CRECIENTE)),
    COSECHA_HOJAS_FLORES_FRUTOS("Cosecha de hojas, flores y frutos", EnumSet.of(LUNA_CRECIENTE, LUNA_LLENA)),
    BANOS_RITUALES("Baños rituales", EnumSet.of(LUNA_LLENA)),
    COSECHA_SEMILLAS_RAICES("Cosecha de semillas y raíces", EnumSet.of(LUNA_MENGUANTE));

    private final String etiqueta;
    private final Set<FaseLunar> fases;

    ActividadAgricola(String etiqueta, Set<FaseLunar> fases) {
        this.etiqueta = etiqueta;
        this.fases = fases;
    }

    public String etiqueta() {
        return etiqueta;
    }

    public boolean recomendadaEn(FaseLunar fase) {
        return fases.contains(fase);
    }
}

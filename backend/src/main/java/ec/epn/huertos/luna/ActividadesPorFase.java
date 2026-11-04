package ec.epn.huertos.luna;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Tabla RN-10: actividades recomendadas por fase, tal como las describen las fuentes. Debe validarse con un
 * líder antes de publicar resultados reales (D-06).
 */
@Component
public class ActividadesPorFase {

    public static final String FUENTE = "Definición del proyecto; MSP (2017), lineamientos de huertos medicinales, Anexo 4";

    private static final Map<FaseLunar, List<String>> TABLA = new EnumMap<>(Map.of(
            FaseLunar.LUNA_OSCURA, List.of(
                    "Preparación de la tierra (retiro y desmenuzado de maleza)",
                    "Elaboración de abono",
                    "Deshierbe",
                    "Control de plagas"),
            FaseLunar.LUNA_CRECIENTE, List.of(
                    "Siembra y ritual de siembra",
                    "Trasplante y resiembra",
                    "Cosecha de hojas, flores y frutos"),
            FaseLunar.LUNA_LLENA, List.of(
                    "Siembra y ritual de siembra",
                    "Cosecha de hojas, flores y frutos",
                    "Baños rituales"),
            FaseLunar.LUNA_MENGUANTE, List.of(
                    "Cosecha de semillas y raíces")));

    public List<String> actividades(FaseLunar fase) {
        return TABLA.get(fase);
    }

    public String fuente() {
        return FUENTE;
    }
}

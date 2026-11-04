package ec.epn.huertos.luna;

import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.luna.CondicionLunar.Resultado;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * RN-06: una siembra es conforme si la fecha y hora del hecho caen en luna creciente o luna llena; no conforme
 * si caen en luna oscura o luna menguante; pendiente si no hay fase disponible. El resultado no impide
 * registrar el hecho.
 */
@Component
public class ReglaSiembra {

    public static final String REGLA = "La siembra se realiza en luna creciente o luna llena "
            + "(RN-06; definición del proyecto y lineamientos del MSP).";

    private static final Set<FaseLunar> FASES_DE_SIEMBRA = EnumSet.of(FaseLunar.LUNA_CRECIENTE, FaseLunar.LUNA_LLENA);

    public CondicionLunar evaluar(Optional<FaseLunar> fase) {
        if (fase.isEmpty()) {
            return new CondicionLunar(Resultado.PENDIENTE, null, Mensaje.MSG_17.codigo(), Mensaje.MSG_17.texto(), REGLA);
        }
        FaseLunar f = fase.get();
        Mensaje m = FASES_DE_SIEMBRA.contains(f) ? Mensaje.MSG_15 : Mensaje.MSG_16;
        Resultado r = m == Mensaje.MSG_15 ? Resultado.CONFORME : Resultado.NO_CONFORME;
        return new CondicionLunar(r, f, m.codigo(), m.texto(f.etiqueta()), REGLA);
    }
}

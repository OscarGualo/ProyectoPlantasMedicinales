package ec.epn.huertos.luna;

/**
 * Resultado de comparar la fase de una siembra con la práctica (RN-06). No certifica ritual, hora ni técnica.
 *
 * @param fase    fase calculada, o {@code null} si no hay información
 * @param codigo  MSG-15, MSG-16 o MSG-17
 * @param mensaje texto del mensaje con la fase reemplazada
 * @param regla   regla aplicada, para mostrarla al líder (HU-015 CA-01)
 */
public record CondicionLunar(Resultado resultado, FaseLunar fase, String codigo, String mensaje, String regla) {

    public enum Resultado { CONFORME, NO_CONFORME, PENDIENTE }

    public boolean conforme() {
        return resultado == Resultado.CONFORME;
    }
}

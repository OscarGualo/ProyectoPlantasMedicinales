package ec.epn.huertos.actividades;

import ec.epn.huertos.cultivos.CultivoVista;
import ec.epn.huertos.luna.CondicionLunar;

import java.time.Instant;
import java.util.List;

/** Solicitudes y vistas de las siembras (HU-014, HU-015). */
public final class SiembraDtos {

    private SiembraDtos() {
    }

    /**
     * Campos del formulario multipart. {@code fechaHecho} es la hora local del huerto en formato
     * {@code yyyy-MM-ddTHH:mm}; {@code claveEnvio} la genera la interfaz al abrir el formulario.
     */
    public record SiembraSolicitud(String fechaHecho, String tecnica, String descripcion, String claveEnvio) {
    }

    /** Corrección de una siembra (HU-016): fecha opcional en la hora local del huerto y motivo obligatorio. */
    public record CorreccionSolicitud(String fechaHecho, String motivo, Integer versionBase, String claveEnvio) {    }

    /** Condición lunar de la siembra con la fase en texto para mostrarla (HU-015). */
    public record CondicionVista(CondicionLunar.Resultado resultado, String fase, String codigo, String mensaje,
                                 String regla) {

        static CondicionVista de(CondicionLunar c) {
            return new CondicionVista(c.resultado(), c.fase() == null ? null : c.fase().etiqueta(), c.codigo(),
                    c.mensaje(), c.regla());
        }
    }

    /**
     * Siembra vigente en la bitácora: fecha del hecho, fecha de registro y autor separados (HU-014 CA-01).
     *
     * @param fechaHechoLocal fecha y hora del hecho en la zona del huerto, para mostrarla tal como se registró
     */
    public record SiembraVista(Long id, Long cultivoId, int version, boolean corregida, Instant fechaHecho,
                               String fechaHechoLocal,
                               Instant registradaEn, String autor, Tecnica tecnica, String tecnicaEtiqueta,
                               String descripcion, String fotoUrl, CondicionVista condicion) {
    }

    /** Bitácora de un cultivo: su vista, la zona horaria del huerto y las siembras vigentes. */
    public record BitacoraVista(CultivoVista cultivo, String zonaHoraria, List<SiembraVista> siembras) {
    }

    /**
     * Una versión del historial (HU-017 CA-01). {@code creadaEn} es la fecha del registro en la versión 1 y la de
     * la corrección en las demás; {@code motivo} solo existe en las correcciones.
     */
    public record VersionVista(int numero, Instant fechaHecho, String fechaHechoLocal, String tecnicaEtiqueta,
                               String descripcion, String fotoUrl, String autor, Instant creadaEn, String motivo,
                               CondicionVista condicion) {
    }

    /**
     * Historial de una siembra: la vigente y sus versiones de la más reciente a la más antigua. Sin correcciones,
     * {@code mensaje} lleva MSG-23 (CA-02).
     */
    public record HistorialVista(SiembraVista siembra, String zonaHoraria, List<VersionVista> versiones,
                                 String mensaje) {
    }
}

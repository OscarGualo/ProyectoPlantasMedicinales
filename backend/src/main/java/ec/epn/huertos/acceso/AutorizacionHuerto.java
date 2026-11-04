package ec.epn.huertos.acceso;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Autorización por huerto en el servidor (RN-01). Se invoca desde cada servicio de gestión, de modo que
 * también protege las solicitudes directas al servidor (HU-001 CA-03, HU-014 CA-05).
 *
 * <p>Consulta la tabla huerto directamente para que el módulo acceso no dependa de huertos (sin ciclos).</p>
 */
@Component
@RequiredArgsConstructor
public class AutorizacionHuerto {

    private final JdbcClient jdbc;

    /** Devuelve el líder actual si gestiona el huerto; si no, MSG-03. Sin sesión, MSG-02. */
    public LiderSesion verificarLider(Long huertoId) {
        LiderSesion lider = UsuarioActual.requerido();
        if (!esLiderDe(lider.id(), huertoId)) {
            throw ErrorNegocio.de(Mensaje.MSG_03);
        }
        return lider;
    }

    /**
     * Huerto del cultivo si el líder actual lo gestiona. Un cultivo ajeno o inexistente es MSG-19
     * (HU-014 CA-05): no se revela si el cultivo existe en otro huerto.
     */
    public Long verificarCultivoPropio(Long cultivoId) {
        LiderSesion lider = UsuarioActual.requerido();
        Long huertoId = huertoDeCultivo(cultivoId);
        if (huertoId == null || !esLiderDe(lider.id(), huertoId)) {
            throw ErrorNegocio.de(Mensaje.MSG_19);
        }
        return huertoId;
    }

    /** Huerto del cultivo si el líder actual lo gestiona; si no, MSG-03 (consulta de la bitácora). */
    public Long verificarLiderDeCultivo(Long cultivoId) {
        Long huertoId = huertoDeCultivo(cultivoId);
        if (huertoId == null) {
            UsuarioActual.requerido();
            throw ErrorNegocio.de(Mensaje.MSG_03);
        }
        verificarLider(huertoId);
        return huertoId;
    }

    private Long huertoDeCultivo(Long cultivoId) {
        return jdbc.sql("SELECT huerto_id FROM cultivo WHERE id = :id")
                .param("id", cultivoId)
                .query(Long.class)
                .optional()
                .orElse(null);
    }

    public boolean esLiderDe(Long usuarioId, Long huertoId) {
        Optional<Long> liderId = jdbc.sql("SELECT lider_id FROM huerto WHERE id = :id")
                .param("id", huertoId)
                .query(Long.class)
                .optional();
        return liderId.isPresent() && Objects.equals(liderId.get(), usuarioId);
    }

    /** Huertos que gestiona el usuario (en la versión 1, uno por líder sin fijarlo como regla: D-03). */
    public List<HuertoGestionado> huertosDe(Long usuarioId) {
        return jdbc.sql("SELECT id, nombre FROM huerto WHERE lider_id = :lider ORDER BY id")
                .param("lider", usuarioId)
                .query(HuertoGestionado.class)
                .list();
    }

    public record HuertoGestionado(Long id, String nombre) {
    }
}

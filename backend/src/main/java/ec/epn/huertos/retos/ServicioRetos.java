package ec.epn.huertos.retos;

import ec.epn.huertos.actividades.ServicioActividades;
import ec.epn.huertos.actividades.ServicioActividades.SiembraVigente;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.huertos.Huerto;
import ec.epn.huertos.huertos.HuertoRepositorio;
import ec.epn.huertos.luna.ActividadesPorFase;
import ec.epn.huertos.retos.RetoVista.Insignia;
import ec.epn.huertos.retos.RetoVista.Sustento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consulta pública del reto Documentación lunar (HU-018, RN-08). Usa {@code actividades} y {@code luna}, nunca al
 * revés (doc 06 §5). La insignia se calcula en cada consulta con la versión vigente de cada siembra (AD-07).
 */
@Service
@RequiredArgsConstructor
public class ServicioRetos {

    static final String RETO = "Documentación lunar";
    static final String EXPLICACION = "La siembra se realiza en luna creciente o luna llena, según la práctica de "
            + "las comunidades. La comunidad obtiene la insignia al documentar al menos una siembra con fotografía "
            + "realizada en esas fases.";
    static final String ALCANCE = "La insignia reconoce una práctica documentada. No otorga puntos, niveles ni "
            + "posiciones, y no certifica talentos ni aprendizajes.";

    private final HuertoRepositorio huertos;
    private final ServicioActividades actividades;

    /** Reto de la comunidad del huerto; huerto inexistente o no publicado: MSG-14. */
    @Transactional(readOnly = true)
    public RetoVista reto(Long huertoId) {
        Huerto h = huertos.publicado(huertoId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_14));
        var sustento = ElegibilidadInsignia.sustento(actividades.siembrasVigentesDeComunidad(h.getComunidad().getId()));
        Insignia insignia = sustento.map(s -> new Insignia(RETO, sustentoDe(s))).orElse(null);
        return new RetoVista(h.getId(), h.getNombre(), h.getComunidad().getNombre(), RETO, EXPLICACION,
                ActividadesPorFase.FUENTE, ALCANCE, insignia,
                insignia == null ? Mensaje.MSG_18.codigo() : null,
                insignia == null ? Mensaje.MSG_18.texto() : null);
    }

    private static Sustento sustentoDe(SiembraVigente s) {
        return new Sustento(s.actividadId(), s.planta(), s.zona(), s.fechaHechoLocal(), "/api/fotos/" + s.fotoId(),
                s.condicion().fase() == null ? null : s.condicion().fase().etiqueta(), s.condicion().mensaje());
    }
}

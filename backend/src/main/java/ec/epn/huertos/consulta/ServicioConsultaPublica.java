package ec.epn.huertos.consulta;

import ec.epn.huertos.cultivos.ServicioCultivos;
import ec.epn.huertos.huertos.Huerto;
import ec.epn.huertos.huertos.ServicioHuertos;
import ec.epn.huertos.plantas.ServicioPlantas;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Consulta pública sin cuenta. Compone huertos, plantas y cultivos sin que esos módulos dependan entre sí
 * en sentido contrario (doc 06 §6: sin ciclos).
 */
@Service
@RequiredArgsConstructor
public class ServicioConsultaPublica {

    private final ServicioHuertos huertos;
    private final ServicioPlantas plantas;
    private final ServicioCultivos cultivos;
    private final JdbcClient jdbc;

    /**
     * HU-010: huertos publicados con coordenadas, cada uno con sus conteos de plantas y cultivos y los nombres de
     * sus especies (la lista pública muestra sus láminas).
     */
    @Transactional(readOnly = true)
    public List<ResumenHuerto> huertosPublicados() {
        return jdbc.sql("""
                        SELECT h.id, h.nombre, c.nombre AS comunidad, h.provincia, h.parroquia, h.latitud, h.longitud,
                               (SELECT count(*) FROM planta p WHERE p.huerto_id = h.id) AS plantas,
                               (SELECT count(*) FROM cultivo k WHERE k.huerto_id = h.id) AS cultivos,
                               ARRAY(SELECT p.nombre_comun FROM planta p WHERE p.huerto_id = h.id ORDER BY p.id)
                                   AS nombres_comunes,
                               ARRAY(SELECT p.nombre_cientifico FROM planta p WHERE p.huerto_id = h.id ORDER BY p.id)
                                   AS nombres_cientificos
                          FROM huerto h JOIN comunidad c ON c.id = h.comunidad_id
                         WHERE h.publicado AND h.latitud IS NOT NULL AND h.longitud IS NOT NULL
                         ORDER BY h.id""")
                .query((rs, n) -> new ResumenHuerto(rs.getLong("id"), rs.getString("nombre"),
                        rs.getString("comunidad"), rs.getString("provincia"), rs.getString("parroquia"),
                        rs.getBigDecimal("latitud"), rs.getBigDecimal("longitud"), rs.getLong("plantas"),
                        rs.getLong("cultivos"),
                        especies((String[]) rs.getArray("nombres_comunes").getArray(),
                                (String[]) rs.getArray("nombres_cientificos").getArray())))
                .list();
    }

    private static List<ResumenHuerto.Especie> especies(String[] comunes, String[] cientificos) {
        return java.util.stream.IntStream.range(0, comunes.length)
                .mapToObj(i -> new ResumenHuerto.Especie(comunes[i], cientificos[i]))
                .toList();
    }

    /** HU-011: ficha de un huerto publicado o MSG-14 (CA-02). */
    @Transactional(readOnly = true)
    public FichaPublica ficha(Long huertoId) {
        Huerto h = huertos.publicado(huertoId);
        return new FichaPublica(h.getId(), h.getNombre(), h.getDescripcion(), h.getComunidad().getNombre(),
                h.getProvincia(), h.getParroquia(), h.getDireccion(), h.getLatitud(), h.getLongitud(),
                h.getZonaHoraria(), h.getSuperficieM2(), h.getDiseno(), h.getTipoCerca(), h.getTipoSuelo(),
                h.getFuenteAgua(), h.getTecnicaRiego(), h.getTipoAbono(),
                plantas.deHuerto(huertoId), cultivos.deHuerto(huertoId), huertos.zonasDe(huertoId));
    }
}

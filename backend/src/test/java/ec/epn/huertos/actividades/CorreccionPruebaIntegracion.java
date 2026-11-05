package ec.epn.huertos.actividades;

import com.jayway.jsonpath.JsonPath;
import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenesPrueba;
import ec.epn.huertos.luna.ConfigCalT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HU-016 (corregir una siembra): P-041 a P-044, con CAL-T (10/10 creciente, 11/10 menguante). A1 es la siembra de
 * K1 del 10/10/2026 08:00 con F1 (doc 05 §7).
 */
@Import(ConfigCalT.class)
public class CorreccionPruebaIntegracion extends PruebaIntegracion {

    static final String MSG03 = "No tienes permiso para modificar la información de este huerto.";

    /** Registra A1 (o A3 con otra fecha) como L1 y devuelve su id. */
    public static long sembrarA(MockMvc mvc, MockHttpSession l1, String fecha) throws Exception {
        String json = SiembraPruebaIntegracion.sembrar(mvc, l1, 1, fecha, UUID.randomUUID().toString(),
                        SiembraPruebaIntegracion.foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(json, "$.id")).longValue();
    }

    public static ResultActions corregir(MockMvc mvc, MockHttpSession s, long siembraId, String fecha, String motivo,
                                  MockMultipartFile foto) throws Exception {
        var req = multipart("/api/siembras/" + siembraId + "/correcciones");
        if (foto != null) {
            req.file(foto);
        }
        if (fecha != null) {
            req.param("fechaHecho", fecha);
        }
        if (motivo != null) {
            req.param("motivo", motivo);
        }
        return mvc.perform(req.session(s).with(csrf()));
    }

    private ResultActions consultar(MockHttpSession s) throws Exception {
        return mvc.perform(get("/api/cultivos/1/bitacora").session(s));
    }

    private List<Map<String, Object>> versionesDe(long siembraId) {
        return jdbc.sql("""
                        SELECT numero, to_char(fecha_hecho AT TIME ZONE 'America/Guayaquil', 'DD/MM/YYYY') AS fecha,
                               motivo, autor_id, foto_id
                          FROM actividad_version WHERE actividad_id = :id ORDER BY numero""")
                .param("id", siembraId).query().listOfRows();
    }

    @Test
    @DisplayName("P-041 Corrección de una siembra con motivo")
    void p041() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");

        // Paso 1: corregir la fecha con motivo.
        corregir(mvc, l1, a1, "2026-10-11T08:00", "Fecha registrada por error", null)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.corregida").value(true));

        // Paso 2: A1 muestra la fecha nueva y la condición lunar de la versión vigente (MSG-16).
        consultar(l1)
                .andExpect(jsonPath("$.siembras", hasSize(1)))
                .andExpect(jsonPath("$.siembras[0].fechaHechoLocal").value("2026-10-11T08:00"))
                .andExpect(jsonPath("$.siembras[0].condicion.codigo").value("MSG-16"))
                .andExpect(jsonPath("$.siembras[0].condicion.mensaje").value("No conforme con la condición lunar: "
                        + "la siembra se realizó en luna menguante; la práctica indica luna creciente o luna llena."));

        // Paso 3: dos versiones; la anterior, sin modificar.
        List<Map<String, Object>> versiones = versionesDe(a1);
        assertThat(versiones).hasSize(2);
        assertThat(versiones.get(0)).containsEntry("fecha", "10/10/2026").containsEntry("motivo", null);
        assertThat(versiones.get(1)).containsEntry("fecha", "11/10/2026")
                .containsEntry("motivo", "Fecha registrada por error")
                .containsEntry("autor_id", 1L);
        assertThat(versiones.get(1).get("foto_id")).isEqualTo(versiones.get(0).get("foto_id"));
    }

    @Test
    @DisplayName("P-042 Corrección sin motivo")
    void p042() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");

        corregir(mvc, l1, a1, "2026-10-11T08:00", "   ", null)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-12"))
                .andExpect(jsonPath("$.mensaje").value("Indica el motivo de la corrección."))
                .andExpect(jsonPath("$.campo").value("motivo"));
        corregir(mvc, l1, a1, "2026-10-11T08:00", null, null)
                .andExpect(jsonPath("$.codigo").value("MSG-12"));

        consultar(l1)
                .andExpect(jsonPath("$.siembras[0].fechaHechoLocal").value("2026-10-10T08:00"))
                .andExpect(jsonPath("$.siembras[0].condicion.codigo").value("MSG-15"));
        assertThat(versionesDe(a1)).hasSize(1);
    }

    @Test
    @DisplayName("P-043 Corrección por el líder de otro huerto")
    void p043() throws Exception {
        long a1 = sembrarA(mvc, iniciarSesion("l1"), "2026-10-10T08:00");

        MockHttpSession l2 = iniciarSesion("l2");
        corregir(mvc, l2, a1, "2026-10-11T08:00", "Intento ajeno", null)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"))
                .andExpect(jsonPath("$.mensaje").value(MSG03));
        // Una siembra inexistente responde igual: no se revela qué ids existen.
        corregir(mvc, l2, 999_999, "2026-10-11T08:00", "Intento ajeno", null)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));

        assertThat(versionesDe(a1)).hasSize(1);
        assertThat(versionesDe(a1).getFirst()).containsEntry("fecha", "10/10/2026");
    }

    @Test
    @DisplayName("P-044 Fotografía no válida en una corrección")
    void p044() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");
        String fotoVigente = JsonPath.read(consultar(l1).andReturn().getResponse().getContentAsString(),
                "$.siembras[0].fotoUrl");

        corregir(mvc, l1, a1, null, "Cambio de foto", SiembraPruebaIntegracion.foto("F2.jpg", ImagenesPrueba.f2()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-07"))
                .andExpect(jsonPath("$.mensaje")
                        .value("La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB."));

        consultar(l1)
                .andExpect(jsonPath("$.siembras[0].fotoUrl").value(fotoVigente))
                .andExpect(jsonPath("$.siembras[0].version").value(1));
        assertThat(versionesDe(a1)).hasSize(1);

        // Con una foto válida la corrección sí la reemplaza y la anterior se conserva en su versión.
        corregir(mvc, l1, a1, null, "Cambio de foto", SiembraPruebaIntegracion.foto("F1b.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fechaHechoLocal").value("2026-10-10T08:00"));
        List<Map<String, Object>> versiones = versionesDe(a1);
        assertThat(versiones.get(1).get("foto_id")).isNotEqualTo(versiones.get(0).get("foto_id"));
    }

    @Test
    @DisplayName("HU-016 Corrección con fecha futura")
    void fechaFutura() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");
        corregir(mvc, l1, a1, "2026-11-01T08:00", "Fecha nueva", null)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-08"))
                .andExpect(jsonPath("$.campo").value("fechaHecho"));
        consultar(l1).andExpect(jsonPath("$.siembras[0].corregida").value(false))
                .andExpect(jsonPath("$.siembras[0].condicion.fase").value("luna creciente"))
                .andExpect(jsonPath("$.siembras[0].descripcion").value("Siembra de prueba"))
                .andExpect(jsonPath("$.siembras[0].tecnica").value("SEMILLAS"))
                .andExpect(jsonPath("$.siembras[0].motivo").doesNotExist());
        assertThat(versionesDe(a1)).hasSize(1);
    }
}

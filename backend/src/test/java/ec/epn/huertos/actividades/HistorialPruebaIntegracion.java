package ec.epn.huertos.actividades;

import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.luna.ConfigCalT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;

import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.corregir;
import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.sembrarA;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-017 (historial de una siembra): P-045 y P-046. El reloj fijo marca el registro y la corrección el 31/10. */
@Import(ConfigCalT.class)
class HistorialPruebaIntegracion extends PruebaIntegracion {

    @Test
    @DisplayName("P-045 Historial de una siembra corregida")
    void p045() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");
        corregir(mvc, l1, a1, "2026-10-11T08:00", "Fecha registrada por error", null)
                .andExpect(status().isCreated());

        mvc.perform(get("/api/siembras/" + a1 + "/historial").session(l1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").doesNotExist())
                .andExpect(jsonPath("$.siembra.version").value(2))
                .andExpect(jsonPath("$.versiones", hasSize(2)))
                // Primero la vigente…
                .andExpect(jsonPath("$.versiones[0].numero").value(2))
                .andExpect(jsonPath("$.versiones[0].fechaHechoLocal").value("2026-10-11T08:00"))
                .andExpect(jsonPath("$.versiones[0].motivo").value("Fecha registrada por error"))
                .andExpect(jsonPath("$.versiones[0].autor").value("Líder 1"))
                .andExpect(jsonPath("$.versiones[0].creadaEn").value(startsWith("2026-10-31T17:00")))
                .andExpect(jsonPath("$.versiones[0].fotoUrl").value(startsWith("/api/fotos/")))
                .andExpect(jsonPath("$.versiones[0].condicion.codigo").value("MSG-16"))
                // …después la original, sin motivo.
                .andExpect(jsonPath("$.versiones[1].numero").value(1))
                .andExpect(jsonPath("$.versiones[1].fechaHechoLocal").value("2026-10-10T08:00"))
                .andExpect(jsonPath("$.versiones[1].motivo").doesNotExist())
                .andExpect(jsonPath("$.versiones[1].autor").value("Líder 1"))
                .andExpect(jsonPath("$.versiones[1].condicion.codigo").value("MSG-15"));
    }

    @Test
    @DisplayName("P-046 Historial de una siembra sin correcciones")
    void p046() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a3 = sembrarA(mvc, l1, "2026-10-13T08:00");

        mvc.perform(get("/api/siembras/" + a3 + "/historial").session(l1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Esta siembra no tiene correcciones registradas."))
                .andExpect(jsonPath("$.versiones", hasSize(1)));
    }

    @Test
    @DisplayName("HU-017 Historial de una siembra de otro huerto")
    void historialAjeno() throws Exception {
        long a1 = sembrarA(mvc, iniciarSesion("l1"), "2026-10-10T08:00");
        mvc.perform(get("/api/siembras/" + a1 + "/historial").session(iniciarSesion("l2")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));
    }
}

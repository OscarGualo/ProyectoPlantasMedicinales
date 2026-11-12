package ec.epn.huertos.retos;

import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.luna.ConfigCalT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.corregir;
import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.sembrarA;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HU-018 (reto Documentación lunar): P-047 a P-049 con CAL-T. El reto es público: se consulta sin sesión.
 * A1 = 10/10 (luna creciente, conforme); A3 = 13/10 (luna llena, conforme); 11/10 menguante; 12/10 sin dato.
 */
@Import(ConfigCalT.class)
class RetoPruebaIntegracion extends PruebaIntegracion {

    static final String MSG18 = "Tu comunidad aún no tiene la insignia Documentación lunar: se necesita al menos una "
            + "siembra con fotografía realizada en luna creciente o luna llena.";

    private ResultActions reto(long huertoId) throws Exception {
        return mvc.perform(get("/api/publico/huertos/" + huertoId + "/reto"));
    }

    @Test
    @DisplayName("P-047 Insignia obtenida por la comunidad")
    void p047() throws Exception {
        long a1 = sembrarA(mvc, iniciarSesion("l1"), "2026-10-10T08:00");

        reto(1).andExpect(status().isOk())
                .andExpect(jsonPath("$.comunidad").value("Comunidad de prueba 1"))
                .andExpect(jsonPath("$.insignia.nombre").value("Documentación lunar"))
                .andExpect(jsonPath("$.insignia.sustento.siembraId").value(a1))
                .andExpect(jsonPath("$.insignia.sustento.fechaHechoLocal").value("2026-10-10T08:00"))
                .andExpect(jsonPath("$.insignia.sustento.fase").value("luna creciente"))
                .andExpect(jsonPath("$.insignia.sustento.fotoUrl").value(startsWith("/api/fotos/")))
                .andExpect(jsonPath("$.explicacion").value(containsString("luna creciente o luna llena")))
                .andExpect(jsonPath("$.mensaje").doesNotExist())
                // Sin datos privados de la comunidad (RN-02).
                .andExpect(jsonPath("$.contactoResponsable").doesNotExist());

        // La foto que sustenta la insignia se ve sin sesión: el huerto está publicado.
        String foto = com.jayway.jsonpath.JsonPath.read(reto(1).andReturn().getResponse().getContentAsString(),
                "$.insignia.sustento.fotoUrl");
        mvc.perform(get(foto)).andExpect(status().isOk());

        // Otra comunidad no comparte la insignia.
        reto(2).andExpect(jsonPath("$.insignia").doesNotExist()).andExpect(jsonPath("$.mensaje").value(MSG18));
    }

    @Test
    @DisplayName("P-048 Insignia única con varias siembras elegibles")
    void p048() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long a1 = sembrarA(mvc, l1, "2026-10-10T08:00");
        long a3 = sembrarA(mvc, l1, "2026-10-13T08:00");

        // Paso 1: una sola insignia, también al recargar.
        for (int i = 0; i < 2; i++) {
            reto(1).andExpect(jsonPath("$.insignia.nombre").value("Documentación lunar"))
                    .andExpect(jsonPath("$.insignia.sustento.siembraId").value(a3));
        }

        // Paso 2: A1 pasa a no conforme.
        corregir(mvc, l1, a1, "2026-10-11T08:00", "Fecha registrada por error", null)
                .andExpect(status().isCreated());

        // Paso 3: sigue activa, sustentada por A3.
        reto(1).andExpect(jsonPath("$.insignia.nombre").value("Documentación lunar"))
                .andExpect(jsonPath("$.insignia.sustento.siembraId").value(a3));

        // Si A3 también deja de ser elegible, la insignia se desactiva; al volver, se reactiva (RN-08).
        corregir(mvc, l1, a3, "2026-10-14T08:00", "Fecha registrada por error", null)
                .andExpect(status().isCreated());
        reto(1).andExpect(jsonPath("$.insignia").doesNotExist()).andExpect(jsonPath("$.mensaje").value(MSG18));
        corregir(mvc, l1, a1, "2026-10-10T08:00", "Se recupera la fecha original", null, 2,
                java.util.UUID.randomUUID().toString())
                .andExpect(status().isCreated());
        reto(1).andExpect(jsonPath("$.insignia.sustento.siembraId").value(a1));
    }

    @Test
    @DisplayName("P-049 Comunidad sin siembras elegibles")
    void p049() throws Exception {
        // Paso 1: H1 sin siembras.
        reto(1).andExpect(status().isOk())
                .andExpect(jsonPath("$.insignia").doesNotExist())
                .andExpect(jsonPath("$.codigo").value("MSG-18"))
                .andExpect(jsonPath("$.mensaje").value(MSG18));

        // Paso 2: una no conforme (11/10) y una pendiente (12/10).
        MockHttpSession l1 = iniciarSesion("l1");
        sembrarA(mvc, l1, "2026-10-11T08:00");
        sembrarA(mvc, l1, "2026-10-12T08:00");

        // Paso 3: sigue sin insignia; no hay puntos, niveles ni posiciones.
        reto(1).andExpect(jsonPath("$.insignia").doesNotExist())
                .andExpect(jsonPath("$.mensaje").value(MSG18))
                .andExpect(jsonPath("$.puntos").doesNotExist())
                .andExpect(jsonPath("$.nivel").doesNotExist())
                .andExpect(jsonPath("$.posicion").doesNotExist());
    }

    @Test
    @DisplayName("HU-018 Reto de un huerto no publicado")
    void huertoNoPublicado() throws Exception {
        reto(3).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("MSG-14"));
    }
}

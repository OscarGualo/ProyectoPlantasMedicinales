package ec.epn.huertos.actividades;

import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenesPrueba;
import ec.epn.huertos.luna.ConfigCalT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HU-015 (condición lunar de una siembra) con CAL-T: 10/10 creciente, 11/10 menguante, 12/10 sin dato,
 * 13/10 llena, 14/10 oscura. La fecha de registro (31/10) nunca influye en la fase.
 */
@Import(ConfigCalT.class)
class CondicionLunarPruebaIntegracion extends PruebaIntegracion {

    private ResultActions sembrar(MockHttpSession s, String fecha) throws Exception {
        return SiembraPruebaIntegracion.sembrar(mvc, s, 1, fecha, UUID.randomUUID().toString(),
                SiembraPruebaIntegracion.foto("F1.jpg", ImagenesPrueba.f1()));
    }

    @Test
    @DisplayName("P-038 Siembra conforme con la condición lunar")
    void p038() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, "2026-10-10T08:00")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.condicion.resultado").value("CONFORME"))
                .andExpect(jsonPath("$.condicion.fase").value("luna creciente"))
                .andExpect(jsonPath("$.condicion.codigo").value("MSG-15"))
                .andExpect(jsonPath("$.condicion.mensaje")
                        .value("Conforme con la condición lunar: la siembra se realizó en luna creciente."))
                .andExpect(jsonPath("$.condicion.regla").value(containsString("RN-06")));
        sembrar(l1, "2026-10-13T08:00")
                .andExpect(jsonPath("$.condicion.fase").value("luna llena"))
                .andExpect(jsonPath("$.condicion.mensaje")
                        .value("Conforme con la condición lunar: la siembra se realizó en luna llena."));
    }

    @Test
    @DisplayName("P-039 Siembra no conforme con la condición lunar")
    void p039() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, "2026-10-11T08:00")
                .andExpect(jsonPath("$.condicion.resultado").value("NO_CONFORME"))
                .andExpect(jsonPath("$.condicion.fase").value("luna menguante"))
                .andExpect(jsonPath("$.condicion.codigo").value("MSG-16"))
                .andExpect(jsonPath("$.condicion.mensaje").value("No conforme con la condición lunar: la siembra se "
                        + "realizó en luna menguante; la práctica indica luna creciente o luna llena."));
        sembrar(l1, "2026-10-14T08:00")
                .andExpect(jsonPath("$.condicion.fase").value("luna oscura"))
                .andExpect(jsonPath("$.condicion.codigo").value("MSG-16"));

        // Ambas permanecen en la bitácora: el resultado no impide registrar el hecho (RN-06).
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/api/cultivos/1/bitacora").session(l1))
                .andExpect(jsonPath("$.siembras", hasSize(2)));
    }

    @Test
    @DisplayName("P-040 Siembra sin información de fase")
    void p040() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, "2026-10-12T08:00")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.condicion.resultado").value("PENDIENTE"))
                .andExpect(jsonPath("$.condicion.codigo").value("MSG-17"))
                .andExpect(jsonPath("$.condicion.mensaje").value(
                        "Pendiente de comprobación: no hay información de fase lunar para la fecha de la siembra."));

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/api/cultivos/1/bitacora").session(l1))
                .andExpect(jsonPath("$.siembras", hasSize(1)))
                .andExpect(jsonPath("$.siembras[0].condicion.resultado").value("PENDIENTE"));
    }
}

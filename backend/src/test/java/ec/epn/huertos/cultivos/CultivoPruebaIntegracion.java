package ec.epn.huertos.cultivos;

import ec.epn.huertos.PruebaIntegracion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-008 (registrar un cultivo) y HU-009 (cambiar el estado de un cultivo). */
class CultivoPruebaIntegracion extends PruebaIntegracion {

    /** 31/10/2026 12:00 America/Guayaquil, el reloj fijado del entorno de prueba. */
    private static final String AHORA = "2026-10-31T17:00:00Z";

    private ResultActions registrar(MockHttpSession s, long plantaId, long zonaId) throws Exception {
        return mvc.perform(post("/api/huertos/1/cultivos").session(s).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"plantaId\": %d, \"zonaId\": %d}".formatted(plantaId, zonaId)));
    }

    private ResultActions cambiarEstado(MockHttpSession s, long cultivoId, String estado) throws Exception {
        return mvc.perform(patch("/api/cultivos/" + cultivoId + "/estado").session(s).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\": \"%s\"}".formatted(estado)));
    }

    private long cultivos() {
        return jdbc.sql("SELECT count(*) FROM cultivo").query(Long.class).single();
    }

    @Test
    @DisplayName("P-019 Registro de cultivos de una misma planta")
    void p019() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");

        for (var zona : new Object[][]{{1L, "Z1"}, {2L, "Z2"}}) {
            registrar(l1, 1, (Long) zona[0])
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.plantaId").value(1))
                    .andExpect(jsonPath("$.planta").value("Planta de prueba"))
                    .andExpect(jsonPath("$.zona").value(zona[1]))
                    .andExpect(jsonPath("$.estado").value("SEMBRADO"))
                    .andExpect(jsonPath("$.estadoEtiqueta").value("sembrado"))
                    .andExpect(jsonPath("$.registradoEn").value(AHORA));
        }
    }

    @Test
    @DisplayName("P-020 Cultivo en una zona de otro huerto")
    void p020() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long antes = cultivos();

        registrar(l1, 1, 3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-09"))
                .andExpect(jsonPath("$.mensaje").value("La zona seleccionada no pertenece a este huerto."));

        assertThat(cultivos()).isEqualTo(antes);
    }

    @Test
    @DisplayName("P-021 Cambio de estado de un cultivo")
    void p021() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");

        cambiarEstado(l1, 1, "en crecimiento")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_CRECIMIENTO"))
                .andExpect(jsonPath("$.estadoEtiqueta").value("en crecimiento"))
                .andExpect(jsonPath("$.actualizadoEn").value(AHORA));

        mvc.perform(get("/api/publico/huertos/1"))
                .andExpect(jsonPath("$.cultivos[?(@.id == 1)].estado").value("EN_CRECIMIENTO"))
                .andExpect(jsonPath("$.cultivos[?(@.id == 2)].estado").value("SEMBRADO"))
                .andExpect(jsonPath("$.cultivos[?(@.id == 2)].actualizadoEn").value("2026-10-30T14:00:00Z"))
                // La ficha de E1 no muestra un estado: el estado pertenece al cultivo.
                .andExpect(jsonPath("$.plantas[?(@.id == 1)].estado").isEmpty());
    }

    @Test
    @DisplayName("P-022 Estado fuera del catálogo")
    void p022() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");

        cambiarEstado(l1, 1, "gigante")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-10"))
                .andExpect(jsonPath("$.mensaje")
                        .value("Selecciona un estado válido: sembrado, en crecimiento, cosechado o perdido."));

        assertThat(jdbc.sql("SELECT estado FROM cultivo WHERE id = 1").query(String.class).single())
                .isEqualTo("SEMBRADO");
    }

    @Test
    @DisplayName("Un líder no cambia el estado de un cultivo de otro huerto (RN-01)")
    void cultivoAjeno() throws Exception {
        MockHttpSession l2 = iniciarSesion("l2");
        cambiarEstado(l2, 1, "perdido")
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));
        mvc.perform(post("/api/huertos/1/cultivos").session(l2).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content("{\"plantaId\": 1, \"zonaId\": 1}"))
                .andExpect(status().isForbidden());
    }
}

package ec.epn.huertos.luna;

import com.jayway.jsonpath.JsonPath;
import ec.epn.huertos.PruebaIntegracion;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.everyItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-019 (calendario lunar) y HU-020 (filtro por actividad) con el cálculo real, sin CAL-T. */
class CalendarioPruebaIntegracion extends PruebaIntegracion {

    private static final String RUTA = "/api/publico/calendario-lunar";

    private List<Map<String, Object>> dias(String consulta) throws Exception {
        String json = mvc.perform(get(RUTA + consulta)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.dias");
    }

    @Test
    @DisplayName("P-050 Calendario lunar de los próximos 30 días")
    void p050() throws Exception {
        mvc.perform(get(RUTA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desde").value("2026-10-31"))
                .andExpect(jsonPath("$.hasta").value("2026-11-29"))
                .andExpect(jsonPath("$.zonaHoraria").value("America/Guayaquil"))
                .andExpect(jsonPath("$.dias", hasSize(30)))
                .andExpect(jsonPath("$.dias[*].fase", everyItem(not(nullValue()))))
                .andExpect(jsonPath("$.fasesPrincipales", hasSize(4)))
                .andExpect(jsonPath("$.actividadesPorFase.LUNA_MENGUANTE[0]").value("Cosecha de semillas y raíces"))
                .andExpect(jsonPath("$.fuente").value(containsString("Anexo 4")));
    }

    @Test
    @Disabled("Pendiente: el equipo debe registrar REF-LUNA (fases oficiales de noviembre de 2026) antes de ejecutar P-050")
    @DisplayName("P-050 Horas de las fases principales frente a REF-LUNA (tolerancia de una hora)")
    void p050ReferenciaOficial() {
    }

    @Test
    @DisplayName("P-051 Fecha fuera del rango del calendario")
    void p051() throws Exception {
        String mensaje = "El calendario lunar está disponible desde 31/10/2025 hasta 31/10/2027.";
        for (String desde : new String[]{"2027-12-01", "2025-09-01"}) {
            mvc.perform(get(RUTA + "?desde=" + desde))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.codigo").value("MSG-20"))
                    .andExpect(jsonPath("$.mensaje").value(mensaje));
        }
    }

    @Test
    @DisplayName("P-052 Filtro de días de siembra (lógica)")
    void p052() throws Exception {
        Set<String> recomendadas = Set.of("LUNA_CRECIENTE", "LUNA_LLENA");
        for (Map<String, Object> d : dias("?actividad=SIEMBRA")) {
            assertThat(d.get("resaltado")).as("día %s", d.get("fecha"))
                    .isEqualTo(recomendadas.contains((String) d.get("fase")));
        }
    }

    @Test
    @DisplayName("P-053 Filtro de días de cosecha de raíces (lógica)")
    void p053() throws Exception {
        for (Map<String, Object> d : dias("?actividad=COSECHA_SEMILLAS_RAICES")) {
            assertThat(d.get("resaltado")).as("día %s", d.get("fecha")).isEqualTo("LUNA_MENGUANTE".equals(d.get("fase")));
        }
    }

    @Test
    @DisplayName("P-054 Retiro del filtro (lógica)")
    void p054() throws Exception {
        assertThat(dias("")).allSatisfy(d -> assertThat(d.get("resaltado")).isEqualTo(false));
    }
}

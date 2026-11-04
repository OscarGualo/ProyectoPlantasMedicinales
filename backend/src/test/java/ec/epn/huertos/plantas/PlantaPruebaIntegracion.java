package ec.epn.huertos.plantas;

import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenesPrueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-006 (registrar una planta) y HU-007 (modificar una planta). */
class PlantaPruebaIntegracion extends PruebaIntegracion {

    private static final String MSG07 = "La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.";

    /** Ficha completa de E1 según P-013. */
    private static Map<String, String> fichaE1() {
        Map<String, String> f = new HashMap<>();
        f.put("nombreComun", "Planta de prueba");
        f.put("nombreCientifico", "Plantae probatoria");
        f.put("familia", "Familia de prueba");
        f.put("descripcion", "Ficha sintética sin uso medicinal");
        f.put("fuente", "Catálogo de prueba del equipo");
        f.put("clasificacion", "fría");
        return f;
    }

    private static MockMultipartFile foto(String nombre, String tipo, byte[] datos) {
        return new MockMultipartFile("foto", nombre, tipo, datos);
    }

    private ResultActions enviar(MockMultipartHttpServletRequestBuilder req, MockHttpSession s,
                                 Map<String, String> campos, MockMultipartFile foto) throws Exception {
        if (foto != null) {
            req.file(foto);
        }
        campos.forEach(req::param);
        return mvc.perform(req.session(s).with(csrf()));
    }

    private ResultActions registrar(MockHttpSession s, Map<String, String> campos, MockMultipartFile foto)
            throws Exception {
        return enviar(multipart("/api/huertos/1/plantas"), s, campos, foto);
    }

    private ResultActions modificar(MockHttpSession s, long plantaId, Map<String, String> campos) throws Exception {
        return enviar(multipart(HttpMethod.PUT, "/api/plantas/" + plantaId), s, campos, null);
    }

    private long plantasDeH1() {
        return jdbc.sql("SELECT count(*) FROM planta WHERE huerto_id = 1").query(Long.class).single();
    }

    @Test
    @DisplayName("P-013 Registro de una planta medicinal")
    void p013() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        byte[] f1 = ImagenesPrueba.f1();
        String id = registrar(l1, fichaE1(), foto("F1.jpg", "image/jpeg", f1))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replaceAll(".*\"id\":(\\d+).*", "$1");

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        String ruta = "$.plantas[?(@.id == " + id + ")]";
        mvc.perform(get("/api/publico/huertos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ruta + ".nombreComun").value("Planta de prueba"))
                .andExpect(jsonPath(ruta + ".nombreCientifico").value("Plantae probatoria"))
                .andExpect(jsonPath(ruta + ".familia").value("Familia de prueba"))
                .andExpect(jsonPath(ruta + ".descripcion").value("Ficha sintética sin uso medicinal"))
                .andExpect(jsonPath(ruta + ".fuente").value("Catálogo de prueba del equipo"))
                .andExpect(jsonPath(ruta + ".clasificacion").value("FRIA"));

        String fotoUrl = jdbc.sql("SELECT '/api/fotos/' || foto_id FROM planta WHERE id = :id")
                .param("id", Long.parseLong(id)).query(String.class).single();
        mvc.perform(get(fotoUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(f1));
    }

    @Test
    @DisplayName("P-014 Planta con un dato obligatorio faltante")
    void p014() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long antes = plantasDeH1();
        Map<String, String> obligatorios = Map.of(
                "nombreComun", "Nombre común",
                "nombreCientifico", "Nombre científico",
                "familia", "Familia",
                "fuente", "Fuente");

        for (var campo : obligatorios.entrySet()) {
            Map<String, String> ficha = fichaE1();
            ficha.put(campo.getKey(), "");
            registrar(l1, ficha, foto("F1.jpg", "image/jpeg", ImagenesPrueba.f1()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.codigo").value("MSG-04"))
                    .andExpect(jsonPath("$.mensaje").value("Completa el campo obligatorio: " + campo.getValue() + "."))
                    .andExpect(jsonPath("$.campo").value(campo.getKey()));
        }

        assertThat(plantasDeH1()).isEqualTo(antes);
        assertThat(jdbc.sql("SELECT count(*) FROM foto").query(Long.class).single()).isZero();
    }

    @Test
    @DisplayName("P-015 Planta con fotografía no admitida")
    void p015() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long antes = plantasDeH1();

        registrar(l1, fichaE1(), foto("F2.jpg", "image/jpeg", ImagenesPrueba.f2()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-07"))
                .andExpect(jsonPath("$.mensaje").value(MSG07));
        registrar(l1, fichaE1(), foto("F3.png", "image/png", ImagenesPrueba.f3()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-07"));
        assertThat(plantasDeH1()).isEqualTo(antes);

        // F4 mide exactamente 5 MB: se registra.
        registrar(l1, fichaE1(), foto("F4.jpg", "image/jpeg", ImagenesPrueba.f4()))
                .andExpect(status().isCreated());
        assertThat(plantasDeH1()).isEqualTo(antes + 1);
    }

    @Test
    @DisplayName("P-016 Modificación de una planta")
    void p016() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        Map<String, String> ficha = fichaE1();
        ficha.put("descripcion", "Descripción corregida");
        modificar(l1, 1, ficha).andExpect(status().isOk());

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        mvc.perform(get("/api/publico/huertos/1"))
                .andExpect(jsonPath("$.plantas[?(@.id == 1)].descripcion").value("Descripción corregida"));
    }

    @Test
    @DisplayName("P-017 Independencia de fichas entre huertos")
    void p017() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        Map<String, String> ficha = fichaE1();
        ficha.put("descripcion", "Descripción corregida");
        modificar(l1, 1, ficha).andExpect(status().isOk());

        mvc.perform(get("/api/publico/huertos/2"))
                .andExpect(jsonPath("$.plantas[?(@.id == 2)].nombreComun").value("Planta de prueba"))
                .andExpect(jsonPath("$.plantas[?(@.id == 2)].descripcion").value("Ficha sintética sin uso medicinal"));
    }

    @Test
    @DisplayName("P-018 Borrado de un dato obligatorio")
    void p018() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        Map<String, String> ficha = fichaE1();
        ficha.put("nombreComun", "");
        ficha.put("descripcion", "Cambio que no debe publicarse");

        modificar(l1, 1, ficha)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-04"))
                .andExpect(jsonPath("$.mensaje").value("Completa el campo obligatorio: Nombre común."));

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        mvc.perform(get("/api/publico/huertos/1"))
                .andExpect(jsonPath("$.plantas[?(@.id == 1)].nombreComun").value("Planta de prueba"))
                .andExpect(jsonPath("$.plantas[?(@.id == 1)].descripcion").value("Ficha sintética sin uso medicinal"));
    }

    @Test
    @DisplayName("Un líder no modifica la ficha de otro huerto (RN-01)")
    void fichaAjena() throws Exception {
        MockHttpSession l2 = iniciarSesion("l2");
        modificar(l2, 1, fichaE1())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));
        mvc.perform(get("/api/huertos/1/plantas").session(l2))
                .andExpect(status().isForbidden());
        mvc.perform(get("/api/huertos/2/plantas").session(l2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(2)));
    }
}

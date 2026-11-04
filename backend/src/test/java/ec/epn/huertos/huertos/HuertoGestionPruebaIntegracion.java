package ec.epn.huertos.huertos;

import ec.epn.huertos.PruebaIntegracion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-003 (ubicación), HU-004 (características) y HU-005 (publicación). */
class HuertoGestionPruebaIntegracion extends PruebaIntegracion {

    private static final String UBICACION = """
            {"provincia": "Pichincha", "parroquia": "Parroquia de prueba", "direccion": "Dirección de prueba",
             "latitud": %s, "longitud": %s}""";

    private static final String CARACTERISTICAS = """
            {"nombre": %s, "descripcion": "Huerto de prueba", "diseno": "en tierra: camellones",
             "tipoCerca": "cerca viva", "superficieM2": %s, "tipoSuelo": "margoso", "fuenteAgua": "canal",
             "tecnicaRiego": "regadera", "tipoAbono": "compost"}""";

    private ResultActions guardarUbicacion(MockHttpSession s, String lat, String lon) throws Exception {
        return mvc.perform(put("/api/huertos/1/ubicacion").session(s).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(UBICACION.formatted(lat, lon)));
    }

    private ResultActions guardarCaracteristicas(MockHttpSession s, String nombreJson, String superficie)
            throws Exception {
        return mvc.perform(put("/api/huertos/1/caracteristicas").session(s).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(CARACTERISTICAS.formatted(nombreJson, superficie)));
    }

    @Test
    @DisplayName("P-006 Actualización de la ubicación del huerto")
    void p006() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        guardarUbicacion(l1, "-0.2", "-78.5").andExpect(status().isOk());

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        l1 = iniciarSesion("l1");
        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.provincia").value("Pichincha"))
                .andExpect(jsonPath("$.parroquia").value("Parroquia de prueba"))
                .andExpect(jsonPath("$.direccion").value("Dirección de prueba"))
                .andExpect(jsonPath("$.latitud").value(-0.2))
                .andExpect(jsonPath("$.longitud").value(-78.5));

        // Valores límite.
        guardarUbicacion(l1, "90", "-180").andExpect(status().isOk());
        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.latitud").value(90))
                .andExpect(jsonPath("$.longitud").value(-180));
        guardarUbicacion(l1, "-0.2", "-78.5").andExpect(status().isOk());
    }

    @Test
    @DisplayName("P-007 Coordenadas fuera de rango")
    void p007() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        String msg06 = "Las coordenadas no son válidas: la latitud debe estar entre -90 y 90 y la longitud entre -180 y 180.";

        guardarUbicacion(l1, "91", "-78.5")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-06"))
                .andExpect(jsonPath("$.mensaje").value(msg06));
        guardarUbicacion(l1, "-0.2", "181")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-06"));

        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.latitud").value(-0.2))
                .andExpect(jsonPath("$.longitud").value(-78.5));
    }

    @Test
    @DisplayName("P-008 Actualización de las características del huerto")
    void p008() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        guardarCaracteristicas(l1, "\"Huerto Demostrativo A\"", "25").andExpect(status().isOk());

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        l1 = iniciarSesion("l1");
        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.nombre").value("Huerto Demostrativo A"))
                .andExpect(jsonPath("$.descripcion").value("Huerto de prueba"))
                .andExpect(jsonPath("$.diseno").value("en tierra: camellones"))
                .andExpect(jsonPath("$.tipoCerca").value("cerca viva"))
                .andExpect(jsonPath("$.superficieM2").value(25))
                .andExpect(jsonPath("$.tipoSuelo").value("margoso"))
                .andExpect(jsonPath("$.fuenteAgua").value("canal"))
                .andExpect(jsonPath("$.tecnicaRiego").value("regadera"))
                .andExpect(jsonPath("$.tipoAbono").value("compost"));

        // Superficie 0 es válida.
        guardarCaracteristicas(l1, "\"Huerto Demostrativo A\"", "0").andExpect(status().isOk());
        mvc.perform(get("/api/huertos/1").session(l1)).andExpect(jsonPath("$.superficieM2").value(0));
        guardarCaracteristicas(l1, "\"Huerto Demostrativo A\"", "25").andExpect(status().isOk());
    }

    @Test
    @DisplayName("P-009 Huerto sin nombre")
    void p009() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        guardarCaracteristicas(l1, "\"Huerto Demostrativo A\"", "25").andExpect(status().isOk());

        guardarCaracteristicas(l1, "\"\"", "25")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-04"))
                .andExpect(jsonPath("$.mensaje").value("Completa el campo obligatorio: Nombre."));

        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.nombre").value("Huerto Demostrativo A"));
    }

    @Test
    @DisplayName("P-010 Superficie negativa")
    void p010() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");

        guardarCaracteristicas(l1, "\"Huerto Demostrativo A\"", "-1")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-05"))
                .andExpect(jsonPath("$.mensaje").value("La superficie debe ser un número mayor o igual a 0."));

        mvc.perform(get("/api/huertos/1").session(l1))
                .andExpect(jsonPath("$.superficieM2").value(25));
    }

    @Test
    @DisplayName("La zona horaria se asigna según la provincia: Galápagos usa Pacific/Galapagos (AD-10)")
    void zonaHorariaPorProvincia() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        mvc.perform(put("/api/huertos/1/ubicacion").session(l1).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"provincia\": \"Galápagos\", \"latitud\": -0.74, \"longitud\": -90.31}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zonaHoraria").value("Pacific/Galapagos"));
        guardarUbicacion(l1, "-0.2", "-78.5")
                .andExpect(jsonPath("$.zonaHoraria").value("America/Guayaquil"));
    }

    @Test
    @DisplayName("P-011 Publicación de un huerto con ubicación")
    void p011() throws Exception {
        MockHttpSession l4 = iniciarSesion("l4");
        mvc.perform(post("/api/huertos/5/publicacion").session(l4).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicado").value(true));

        mvc.perform(delete("/api/sesion").session(l4).with(csrf()));
        mvc.perform(get("/api/publico/huertos"))
                .andExpect(jsonPath("$[?(@.id == 5)].latitud").value(-2.9))
                .andExpect(jsonPath("$[?(@.id == 5)].longitud").value(-79.0));
    }

    @Test
    @DisplayName("P-012 Publicación de un huerto sin ubicación")
    void p012() throws Exception {
        MockHttpSession l3 = iniciarSesion("l3");
        mvc.perform(post("/api/huertos/4/publicacion").session(l3).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-21"))
                .andExpect(jsonPath("$.mensaje")
                        .value("El huerto no tiene ubicación registrada y no puede publicarse en el mapa."));

        mvc.perform(delete("/api/sesion").session(l3).with(csrf()));
        mvc.perform(get("/api/publico/huertos"))
                .andExpect(jsonPath("$[*].id", not(hasItem(4))));
    }
}

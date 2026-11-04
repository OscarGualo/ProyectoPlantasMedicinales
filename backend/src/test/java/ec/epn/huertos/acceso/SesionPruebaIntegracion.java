package ec.epn.huertos.acceso;

import ec.epn.huertos.PruebaIntegracion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-001 (iniciar sesión) y HU-002 (cerrar sesión). */
class SesionPruebaIntegracion extends PruebaIntegracion {

    private static final String CAMBIO_NOMBRE = """
            {"nombre": "Nombre alterado", "superficieM2": 25}""";

    @Test
    @DisplayName("P-001 Inicio de sesión con credenciales válidas")
    void p001() throws Exception {
        MockHttpSession sesion = iniciarSesion("l1");

        mvc.perform(get("/api/sesion").session(sesion))
                .andExpect(jsonPath("$.autenticado").value(true))
                .andExpect(jsonPath("$.huertos", hasSize(1)))
                .andExpect(jsonPath("$.huertos[0].id").value(1));
        mvc.perform(get("/api/huertos/1").session(sesion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        // No se ofrece la gestión de H2.
        mvc.perform(get("/api/huertos/2").session(sesion))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("P-002 Inicio de sesión con credenciales incorrectas")
    void p002() throws Exception {
        for (String cuerpo : new String[]{
                "{\"usuario\": \"l1\", \"contrasena\": \"incorrecta\"}",
                "{\"usuario\": \"usuario.inexistente\", \"contrasena\": \"cualquiera\"}"}) {
            mvc.perform(post("/api/sesion").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(cuerpo))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.codigo").value("MSG-01"))
                    .andExpect(jsonPath("$.mensaje").value("Usuario o contraseña incorrectos."));
        }
    }

    @Test
    @DisplayName("P-003 Modificación de un huerto no asignado")
    void p003() throws Exception {
        String nombreAnotado = nombreHuerto(1);
        MockHttpSession l2 = iniciarSesion("l2");

        mvc.perform(get("/api/huertos/1").session(l2))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"))
                .andExpect(jsonPath("$.mensaje").value("No tienes permiso para modificar la información de este huerto."));
        mvc.perform(put("/api/huertos/1/caracteristicas").session(l2).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(CAMBIO_NOMBRE))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));

        assertThat(nombreHuerto(1)).isEqualTo(nombreAnotado);
    }

    @Test
    @DisplayName("P-004 Cierre de sesión")
    void p004() throws Exception {
        MockHttpSession sesion = iniciarSesion("l1");

        mvc.perform(delete("/api/sesion").session(sesion).with(csrf()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/sesion").session(sesion))
                .andExpect(jsonPath("$.autenticado").value(false));
        mvc.perform(get("/api/huertos/1").session(sesion))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.codigo").value("MSG-02"));
    }

    @Test
    @DisplayName("P-005 Modificación después de cerrar sesión")
    void p005() throws Exception {
        String nombreAnotado = nombreHuerto(1);
        MockHttpSession sesion = iniciarSesion("l1");
        mvc.perform(delete("/api/sesion").session(sesion).with(csrf())).andExpect(status().isNoContent());

        // Reenvío de la solicitud capturada con su token CSRF.
        mvc.perform(put("/api/huertos/1/caracteristicas").session(sesion).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(CAMBIO_NOMBRE))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.codigo").value("MSG-02"))
                .andExpect(jsonPath("$.mensaje").value("Tu sesión ha finalizado. Inicia sesión para continuar."));
        // Reenvío sin un token CSRF válido: también MSG-02, no un error genérico.
        mvc.perform(put("/api/huertos/1/caracteristicas").session(sesion).with(csrf().useInvalidToken())
                        .contentType(MediaType.APPLICATION_JSON).content(CAMBIO_NOMBRE))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.codigo").value("MSG-02"));

        assertThat(nombreHuerto(1)).isEqualTo(nombreAnotado);
    }
}

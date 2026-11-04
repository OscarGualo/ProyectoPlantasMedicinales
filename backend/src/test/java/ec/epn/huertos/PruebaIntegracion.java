package ec.epn.huertos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base de las pruebas de integración de API: Spring completo, PostgreSQL en Testcontainers, reloj fijo y
 * datos del doc 05 §7 restaurados antes de cada caso.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(ContenedoresPrueba.class)
@Sql(scripts = "classpath:e2e/datos-prueba.sql")
public abstract class PruebaIntegracion {

    protected static final String CONTRASENA = "clave-prueba";

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected JdbcClient jdbc;

    /** Inicia sesión y devuelve la sesión del servidor para las solicitudes siguientes. */
    protected MockHttpSession iniciarSesion(String usuario) throws Exception {
        MvcResult r = mvc.perform(post("/api/sesion").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"usuario": "%s", "contrasena": "%s"}""".formatted(usuario, CONTRASENA)))
                .andExpect(status().isOk())
                .andReturn();
        return (MockHttpSession) r.getRequest().getSession(false);
    }

    protected String nombreHuerto(long id) {
        return jdbc.sql("SELECT nombre FROM huerto WHERE id = :id").param("id", id).query(String.class).single();
    }
}

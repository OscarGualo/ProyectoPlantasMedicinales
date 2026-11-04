package ec.epn.huertos.actividades;

import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenValidada;
import ec.epn.huertos.fotos.ImagenesPrueba;
import ec.epn.huertos.fotos.ServicioFotos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** HU-014 (registrar una siembra): P-032 a P-037. */
class SiembraPruebaIntegracion extends PruebaIntegracion {

    private static final String MSG07 = "La imagen debe estar en formato JPEG o PNG y pesar como máximo 5 MB.";

    @MockitoSpyBean
    ServicioFotos fotos;

    static MockMultipartFile foto(String nombre, byte[] datos) {
        return new MockMultipartFile("foto", nombre, "image/jpeg", datos);
    }

    static ResultActions sembrar(org.springframework.test.web.servlet.MockMvc mvc, MockHttpSession s, long cultivoId,
                                 String fecha, String clave, MockMultipartFile foto) throws Exception {
        var req = multipart("/api/cultivos/" + cultivoId + "/siembras");
        if (foto != null) {
            req.file(foto);
        }
        return mvc.perform(req.param("fechaHecho", fecha).param("tecnica", "SEMILLAS")
                .param("descripcion", "Siembra de prueba").param("claveEnvio", clave)
                .session(s).with(csrf()));
    }

    private ResultActions sembrar(MockHttpSession s, long cultivoId, String fecha, MockMultipartFile foto)
            throws Exception {
        return sembrar(mvc, s, cultivoId, fecha, UUID.randomUUID().toString(), foto);
    }

    private long siembrasDe(long cultivoId) {
        return jdbc.sql("SELECT count(*) FROM actividad WHERE cultivo_id = :c").param("c", cultivoId)
                .query(Long.class).single();
    }

    @Test
    @DisplayName("P-032 Registro de una siembra con fotografía")
    void p032() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        byte[] f1 = ImagenesPrueba.f1();
        sembrar(l1, 1, "2026-10-10T08:00", foto("F1.jpg", f1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fechaHechoLocal").value("2026-10-10T08:00"));

        mvc.perform(delete("/api/sesion").session(l1).with(csrf()));
        l1 = iniciarSesion("l1");
        String bitacora = mvc.perform(get("/api/cultivos/1/bitacora").session(l1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siembras", hasSize(1)))
                .andExpect(jsonPath("$.siembras[0].fechaHechoLocal").value("2026-10-10T08:00"))
                .andExpect(jsonPath("$.siembras[0].fechaHecho").value("2026-10-10T13:00:00Z"))
                .andExpect(jsonPath("$.siembras[0].registradaEn").value("2026-10-31T17:00:00Z"))
                .andExpect(jsonPath("$.siembras[0].autor").value("Líder 1"))
                .andExpect(jsonPath("$.siembras[0].tecnica").value("SEMILLAS"))
                .andExpect(jsonPath("$.siembras[0].descripcion").value("Siembra de prueba"))
                .andReturn().getResponse().getContentAsString();

        String fotoUrl = com.jayway.jsonpath.JsonPath.read(bitacora, "$.siembras[0].fotoUrl");
        mvc.perform(get(fotoUrl).session(l1)).andExpect(content().bytes(f1));
        // El estado de K1 no cambia automáticamente (RN-03).
        assertThat(jdbc.sql("SELECT estado FROM cultivo WHERE id = 1").query(String.class).single())
                .isEqualTo("SEMBRADO");
    }

    @Test
    @DisplayName("P-033 Envío repetido de una siembra")
    void p033() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        String clave = UUID.randomUUID().toString();
        String id = sembrar(mvc, l1, 1, "2026-10-10T08:00", clave, foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replaceAll(".*?\"id\":(\\d+).*", "$1");
        sembrar(mvc, l1, 1, "2026-10-10T08:00", clave, foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Long.parseLong(id)));
        sembrar(l1, 1, "2026-10-13T09:00", foto("F4.jpg", ImagenesPrueba.f4())).andExpect(status().isCreated());

        mvc.perform(get("/api/cultivos/1/bitacora").session(l1))
                .andExpect(jsonPath("$.siembras", hasSize(2)));
    }

    @Test
    @DisplayName("P-034 Siembra con fotografía no válida")
    void p034() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, 1, "2026-10-10T08:00", foto("F2.jpg", ImagenesPrueba.f2()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-07"))
                .andExpect(jsonPath("$.mensaje").value(MSG07));
        sembrar(l1, 1, "2026-10-10T08:00", new MockMultipartFile("foto", "F3.png", "image/png", ImagenesPrueba.f3()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-07"));

        mvc.perform(get("/api/cultivos/1/bitacora").session(l1)).andExpect(jsonPath("$.siembras", hasSize(0)));
    }

    @Test
    @DisplayName("P-035 Siembra con fecha futura")
    void p035() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, 1, "2026-11-01T08:00", foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("MSG-08"))
                .andExpect(jsonPath("$.mensaje")
                        .value("La fecha y hora de la actividad no pueden ser posteriores al momento actual."));
        assertThat(siembrasDe(1)).isZero();

        // Valor límite: un minuto antes del reloj fijado (31/10/2026 12:00).
        sembrar(l1, 1, "2026-10-31T11:59", foto("F1.jpg", ImagenesPrueba.f1())).andExpect(status().isCreated());
        assertThat(siembrasDe(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("P-036 Siembra en un cultivo de otro huerto")
    void p036() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        sembrar(l1, 3, "2026-10-10T08:00", foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-19"))
                .andExpect(jsonPath("$.mensaje").value("El cultivo seleccionado no pertenece a tu huerto."));

        assertThat(siembrasDe(3)).isZero();
        mvc.perform(get("/api/cultivos/3/bitacora").session(l1))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.codigo").value("MSG-03"));
    }

    @Test
    @DisplayName("P-037 Falla al guardar la fotografía")
    void p037() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        // Almacenamiento de imágenes desactivado.
        doThrow(new DataAccessResourceFailureException("almacenamiento no disponible"))
                .when(fotos).guardar(any(ImagenValidada.class));

        sembrar(l1, 1, "2026-10-10T08:00", foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.codigo").value("MSG-11"))
                .andExpect(jsonPath("$.mensaje")
                        .value("No se pudo guardar la fotografía. La actividad no se registró; inténtalo nuevamente."));
        assertThat(siembrasDe(1)).isZero();
        assertThat(jdbc.sql("SELECT count(*) FROM foto").query(Long.class).single()).isZero();

        // Almacenamiento reactivado: la siembra se registra con su fotografía.
        Mockito.reset(fotos);
        sembrar(l1, 1, "2026-10-10T08:00", foto("F1.jpg", ImagenesPrueba.f1())).andExpect(status().isCreated());
        mvc.perform(get("/api/cultivos/1/bitacora").session(l1)).andExpect(jsonPath("$.siembras", hasSize(1)));
    }
}

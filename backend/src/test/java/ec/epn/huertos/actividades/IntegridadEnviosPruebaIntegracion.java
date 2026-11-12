package ec.epn.huertos.actividades;

import com.jayway.jsonpath.JsonPath;
import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenesPrueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.corregir;
import static ec.epn.huertos.actividades.CorreccionPruebaIntegracion.sembrarA;
import static ec.epn.huertos.actividades.SiembraPruebaIntegracion.foto;
import static ec.epn.huertos.actividades.SiembraPruebaIntegracion.sembrar;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IntegridadEnviosPruebaIntegracion extends PruebaIntegracion {
    private static final String FECHA = "2026-10-10T08:00";

    @Test
    @DisplayName("P-056 Gestión privada de un huerto sin publicar")
    void gestionPrivada() throws Exception {
        MockHttpSession l3 = iniciarSesion("l3");
        MockHttpSession l2 = iniciarSesion("l2");
        for (String ruta : List.of("", "/zonas", "/plantas", "/cultivos")) {
            String url = "/api/huertos/4" + ruta;
            mvc.perform(get(url).session(l3)).andExpect(status().isOk());
            mvc.perform(get(url).session(l2)).andExpect(status().isForbidden());
            mvc.perform(get(url)).andExpect(status().isUnauthorized());
        }
        mvc.perform(get("/api/publico/huertos/4")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("P-058 Dos envíos simultáneos crean una sola siembra y una sola foto")
    void envioSimultaneo() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        MockHttpSession otraSesion = iniciarSesion("l1");
        String clave = clave();
        List<MvcResult> resultados = simultaneos(
                () -> sembrar(mvc, l1, 1, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1())).andReturn(),
                () -> sembrar(mvc, otraSesion, 1, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1())).andReturn());
        assertThat(resultados.stream().map(r -> r.getResponse().getStatus())).containsExactlyInAnyOrder(200, 201);
        assertThat(resultados.getFirst().getResponse().getContentAsByteArray())
                .isEqualTo(resultados.getLast().getResponse().getContentAsByteArray());
        assertThat(filas("actividad")).isEqualTo(1);
        assertThat(filas("actividad_version")).isEqualTo(1);
        assertThat(filas("foto")).isEqualTo(1);
    }

    @Test
    @DisplayName("P-061 Un huerto privado de la misma comunidad no sustenta la insignia pública")
    void insigniaSoloConHuertosPublicados() throws Exception {
        jdbc.sql("UPDATE huerto SET publicado = false, comunidad_id = 1 WHERE id = 2").update();
        String fecha = "2026-10-18T08:00";
        sembrar(mvc, iniciarSesion("l2"), 3, fecha, clave(), foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.condicion.resultado").value("CONFORME"));
        mvc.perform(get("/api/publico/huertos/1/reto")).andExpect(status().isOk())
                .andExpect(jsonPath("$.insignia").doesNotExist());
        String siembra = sembrar(mvc, iniciarSesion("l1"), 1, fecha, clave(), foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        int id = ((Number) JsonPath.read(siembra, "$.id")).intValue();
        mvc.perform(get("/api/publico/huertos/1/reto")).andExpect(status().isOk())
                .andExpect(jsonPath("$.insignia.sustento.siembraId").value(id));
    }

    @Test
    @DisplayName("P-059 Reusar la clave con otro contenido o cultivo rechaza el envío")
    void claveConOtrosDatos() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        String clave = clave();
        sembrar(mvc, l1, 1, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1())).andExpect(status().isCreated());
        sembrar(mvc, l1, 1, "2026-10-11T08:00", clave, foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.codigo").value("MSG-24"));
        sembrar(mvc, l1, 1, FECHA, clave, foto("F4.jpg", ImagenesPrueba.f4()))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.codigo").value("MSG-24"));
        sembrar(mvc, l1, 2, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.codigo").value("MSG-24"));
        assertThat(filas("actividad")).isEqualTo(1);
        assertThat(filas("foto")).isEqualTo(1);
    }

    @Test
    @DisplayName("P-060 Dos correcciones con la misma base dejan una sola nueva versión")
    void correccionSimultanea() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        MockHttpSession otraSesion = iniciarSesion("l1");
        long id = sembrarA(mvc, l1, FECHA);
        List<MvcResult> resultados = simultaneos(
                () -> corregir(mvc, l1, id, "2026-10-11T08:00", "Primera propuesta", null, 1, clave()).andReturn(),
                () -> corregir(mvc, otraSesion, id, "2026-10-12T08:00", "Segunda propuesta", null, 1, clave()).andReturn());
        assertThat(resultados.stream().map(r -> r.getResponse().getStatus())).containsExactlyInAnyOrder(201, 409);
        MvcResult rechazo = resultados.stream().filter(r -> r.getResponse().getStatus() == 409).findFirst().orElseThrow();
        assertThat(JsonPath.<String>read(rechazo.getResponse().getContentAsString(), "$.codigo")).isEqualTo("MSG-25");
        assertThat(filas("actividad_version")).isEqualTo(2);
        assertThat(filas("foto")).isEqualTo(1);
    }

    @Test
    @DisplayName("P-064 El visitante pierde acceso a la foto sustituida y el líder conserva el historial")
    void fotoHistoricaPrivada() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        String original = sembrar(mvc, l1, 1, FECHA, clave(), foto("F1.jpg", ImagenesPrueba.f1()))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        long id = ((Number) JsonPath.read(original, "$.id")).longValue();
        String anterior = JsonPath.read(original, "$.fotoUrl");
        mvc.perform(get(anterior)).andExpect(status().isOk()).andExpect(header().string("Cache-Control", "no-store"));
        String nueva = corregir(mvc, l1, id, null, "Sustituir evidencia", foto("F4.jpg", ImagenesPrueba.f4()))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        String vigente = JsonPath.read(nueva, "$.fotoUrl");
        mvc.perform(get(vigente)).andExpect(status().isOk());
        mvc.perform(get(anterior)).andExpect(status().isNotFound());
        mvc.perform(get(anterior).session(iniciarSesion("l2"))).andExpect(status().isNotFound());
        mvc.perform(get(anterior).session(l1)).andExpect(status().isOk());
        jdbc.sql("UPDATE huerto SET publicado = false WHERE id = 1").update();
        mvc.perform(get(vigente)).andExpect(status().isNotFound());
        mvc.perform(get(vigente).session(l1)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("P-065 Una falla real de la versión revierte la actividad y la foto ya insertadas")
    void reversionDespuesDeFoto() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        String clave = clave();
        jdbc.sql("ALTER TABLE actividad_version ADD CONSTRAINT prueba_version CHECK (numero < 0)").update();
        try {
            sembrar(mvc, l1, 1, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1()))
                    .andExpect(status().isServiceUnavailable()).andExpect(jsonPath("$.codigo").value("MSG-27"));
            assertThat(filas("actividad")).isZero();
            assertThat(filas("actividad_version")).isZero();
            assertThat(filas("foto")).isZero();
        } finally {
            jdbc.sql("ALTER TABLE actividad_version DROP CONSTRAINT prueba_version").update();
        }
        sembrar(mvc, l1, 1, FECHA, clave, foto("F1.jpg", ImagenesPrueba.f1())).andExpect(status().isCreated());
        assertThat(filas("actividad")).isEqualTo(1);
        assertThat(filas("actividad_version")).isEqualTo(1);
        assertThat(filas("foto")).isEqualTo(1);
    }

    @Test
    @DisplayName("P-065 Un fallo en la confirmación revierte la evidencia y los registros")
    void reversionAlConfirmar() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        jdbc.sql("""
                CREATE FUNCTION prueba_falla_commit() RETURNS trigger LANGUAGE plpgsql AS $$
                BEGIN RAISE EXCEPTION 'fallo de confirmación inyectado' USING ERRCODE = '23514'; END;
                $$""").update();
        try {
            jdbc.sql("""
                    CREATE CONSTRAINT TRIGGER prueba_falla_commit AFTER INSERT ON actividad_version
                    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION prueba_falla_commit()
                    """).update();
            sembrar(mvc, l1, 1, FECHA, clave(), foto("F1.jpg", ImagenesPrueba.f1()))
                    .andExpect(status().is5xxServerError());
            assertThat(filas("actividad")).isZero();
            assertThat(filas("actividad_version")).isZero();
            assertThat(filas("foto")).isZero();
        } finally {
            jdbc.sql("DROP TRIGGER IF EXISTS prueba_falla_commit ON actividad_version").update();
            jdbc.sql("DROP FUNCTION prueba_falla_commit()").update();
        }
    }

    @Test
    @DisplayName("P-069 Reintento idéntico recupera su corrección incluso tras un cambio posterior")
    void correccionRepetida() throws Exception {
        MockHttpSession l1 = iniciarSesion("l1");
        long id = sembrarA(mvc, l1, FECHA);
        String clave = clave();
        String fecha = "2026-10-11T08:00";
        corregir(mvc, l1, id, fecha, "Corrección", null, 1, clave).andExpect(status().isCreated());
        corregir(mvc, l1, id, "2026-10-12T08:00", "Ajuste posterior", null, 2, clave())
                .andExpect(status().isCreated());
        corregir(mvc, l1, id, fecha, "Corrección", null, 1, clave)
                .andExpect(status().isOk()).andExpect(jsonPath("$.version").value(2));
        corregir(mvc, l1, id, fecha, "Distinta", null, 1, clave)
                .andExpect(status().isConflict()).andExpect(jsonPath("$.codigo").value("MSG-24"));
        assertThat(filas("actividad_version")).isEqualTo(3);
        assertThat(filas("foto")).isEqualTo(1);
    }

    private long filas(String tabla) {
        return jdbc.sql("SELECT count(*) FROM " + tabla).query(Long.class).single();
    }

    private static String clave() {
        return UUID.randomUUID().toString();
    }

    private static List<MvcResult> simultaneos(Callable<MvcResult> a, Callable<MvcResult> b) throws Exception {
        CyclicBarrier barrera = new CyclicBarrier(2);
        try (var executor = Executors.newFixedThreadPool(2)) {
            var resultados = executor.invokeAll(List.<Callable<MvcResult>>of(
                    () -> { barrera.await(5, TimeUnit.SECONDS); return a.call(); },
                    () -> { barrera.await(5, TimeUnit.SECONDS); return b.call(); }), 30, TimeUnit.SECONDS);
            return List.of(resultados.get(0).get(), resultados.get(1).get());
        }
    }
}

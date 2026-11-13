package ec.epn.huertos.plantas;
import ec.epn.huertos.PruebaIntegracion;
import ec.epn.huertos.fotos.ImagenesPrueba;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlantaPruebaIntegracion extends PruebaIntegracion {
 @Test void p013() throws Exception {
  var s=iniciarSesion("l1");
  mvc.perform(multipart("/api/huertos/1/plantas").session(s).with(csrf()).param("catalogoCodigo","TEST-FRIA")
   .file(new MockMultipartFile("foto","f.jpg","image/jpeg",ImagenesPrueba.f1())))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.nombreComun").value("Planta de prueba"))
   .andExpect(jsonPath("$.institucional").value(true)).andExpect(jsonPath("$.catalogoCodigo").value("TEST-FRIA"));
  mvc.perform(get("/api/publico/huertos/1")).andExpect(status().isOk())
   .andExpect(jsonPath("$.plantas.length()").value(4));
 }
 @Test void p014() throws Exception {
  mvc.perform(multipart("/api/huertos/1/plantas").session(iniciarSesion("l1")).with(csrf()))
   .andExpect(status().isBadRequest()).andExpect(jsonPath("$.codigo").value("MSG-04"))
   .andExpect(jsonPath("$.campo").value("catalogoCodigo"));
 }
 @Test void p015() throws Exception {
  var s=iniciarSesion("l1");
  for(byte[] datos:new byte[][]{ImagenesPrueba.f2(),ImagenesPrueba.f3()})
   mvc.perform(multipart("/api/huertos/1/plantas").session(s).with(csrf()).param("catalogoCodigo","TEST-FRIA")
    .file(new MockMultipartFile("foto","f.jpg","image/jpeg",datos)))
    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.codigo").value("MSG-07"));
  mvc.perform(multipart("/api/huertos/1/plantas").session(s).with(csrf()).param("catalogoCodigo","TEST-FRIA")
   .file(new MockMultipartFile("foto","f.jpg","image/jpeg",ImagenesPrueba.f4())))
   .andExpect(status().isCreated());
 }
 @Test void p016() throws Exception {
  mvc.perform(multipart(HttpMethod.PUT,"/api/plantas/1").session(iniciarSesion("l1")).with(csrf())
   .param("catalogoCodigo","TEST-FRIA").file(new MockMultipartFile("foto","f.jpg","image/jpeg",ImagenesPrueba.f1())))
   .andExpect(status().isOk()).andExpect(jsonPath("$.fotoUrl").isNotEmpty())
   .andExpect(jsonPath("$.descripcion").value("Ficha sintética sin uso medicinal"));
 }
 @Test void p017() throws Exception {
  mvc.perform(multipart(HttpMethod.PUT,"/api/plantas/1").session(iniciarSesion("l1")).with(csrf())
   .param("catalogoCodigo","TEST-FRIA").file(new MockMultipartFile("foto","f.jpg","image/jpeg",ImagenesPrueba.f1())))
   .andExpect(status().isOk());
  mvc.perform(get("/api/publico/huertos/2")).andExpect(jsonPath("$.plantas[0].fotoUrl").doesNotExist())
   .andExpect(jsonPath("$.plantas[0].nombreComun").value("Planta de prueba"));
 }
 @Test void p018() throws Exception {
  mvc.perform(multipart(HttpMethod.PUT,"/api/plantas/1").session(iniciarSesion("l1")).with(csrf())
   .param("catalogoCodigo","TEST-FRIA").param("usos","Información inventada"))
   .andExpect(status().isBadRequest()).andExpect(jsonPath("$.codigo").value("MSG-28"));
  assertThat(jdbc.sql("SELECT usos FROM planta WHERE id=1").query(String.class).optional()).isEmpty();
 }
 @Test void fichaAjena() throws Exception {
  mvc.perform(multipart(HttpMethod.PUT,"/api/plantas/1").session(iniciarSesion("l2")).with(csrf())
   .param("catalogoCodigo","TEST-FRIA")).andExpect(status().isForbidden());
 }
 @Test void rechazaCatalogoInexistente() throws Exception {
  mvc.perform(multipart("/api/huertos/1/plantas").session(iniciarSesion("l1")).with(csrf())
   .param("catalogoCodigo","INEXISTENTE")).andExpect(status().isBadRequest())
   .andExpect(jsonPath("$.codigo").value("MSG-28"));
 }
 @Test void nombreLibreLargoNoProduce500() throws Exception {
  mvc.perform(multipart("/api/huertos/1/plantas").session(iniciarSesion("l1")).with(csrf())
   .param("catalogoCodigo","TEST-FRIA").param("nombreComun","A".repeat(121)))
   .andExpect(status().isBadRequest()).andExpect(jsonPath("$.codigo").value("MSG-28"));
 }
 @Test void fichaAnteriorSinCatalogoNoSePublica() throws Exception {
  jdbc.sql("UPDATE planta SET catalogo_codigo=NULL WHERE id=1").update();
  mvc.perform(get("/api/publico/huertos/1")).andExpect(jsonPath("$.plantas.length()").value(2))
   .andExpect(jsonPath("$.cultivos.length()").value(2));
  mvc.perform(get("/api/huertos/1/plantas").session(iniciarSesion("l1")))
   .andExpect(jsonPath("$.length()").value(3));
 }
 @Test void catalogoNoTieneOperacionDeEscritura() throws Exception {
  mvc.perform(get("/api/publico/catalogo-plantas")).andExpect(status().isOk());
  long antes=jdbc.sql("SELECT count(*) FROM catalogo_planta_msp").query(Long.class).single();
  mvc.perform(post("/api/publico/catalogo-plantas").session(iniciarSesion("l1")).with(csrf())
   .contentType("application/json").content("{}"));
  assertThat(jdbc.sql("SELECT count(*) FROM catalogo_planta_msp").query(Long.class).single()).isEqualTo(antes);
 }
}

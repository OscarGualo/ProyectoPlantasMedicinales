package ec.epn.huertos.plantas;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/** Información institucional de lectura; el líder no administra este catálogo. */
@Service @RequiredArgsConstructor
public class CatalogoMsp {
 private final JdbcClient jdbc;
 public record Ficha(String codigo,String nombreComun,String nombreCientifico,String familia,
  String fuente,String descripcion,String clasificacion,String usos,String version) { }
 private static final String SELECT = """
  SELECT codigo,nombre_comun,nombre_cientifico,familia,fuente,descripcion,clasificacion,usos,version
  FROM catalogo_planta_msp WHERE activo
  """;
 @Transactional(readOnly=true)
 public List<Ficha> listar() {return jdbc.sql(SELECT+" ORDER BY nombre_comun").query(Ficha.class).list();}
 @Transactional(readOnly=true)
 public Ficha ficha(String codigo) {
  if(codigo==null || codigo.isBlank()) throw ErrorNegocio.campoObligatorio("Ficha del catálogo MSP","catalogoCodigo");
  return jdbc.sql(SELECT+" AND codigo=:codigo").param("codigo",codigo.trim()).query(Ficha.class).optional()
   .orElseThrow(()->ErrorNegocio.enCampo(Mensaje.MSG_28,"catalogoCodigo"));
 }
}

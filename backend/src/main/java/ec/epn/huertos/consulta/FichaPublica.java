package ec.epn.huertos.consulta;

import ec.epn.huertos.cultivos.CultivoVista;
import ec.epn.huertos.huertos.HuertoDtos.ZonaVista;
import ec.epn.huertos.plantas.PlantaDtos.PlantaVista;

import java.math.BigDecimal;
import java.util.List;

/**
 * Ficha pública de un huerto publicado (HU-011): características, plantas, cultivos y zonas para la escena 3D.
 * Nunca incluye el contacto del responsable ni datos de la cuenta (RN-02).
 */
public record FichaPublica(Long id, String nombre, String descripcion, String comunidad, String provincia,
                           String parroquia, String direccion, BigDecimal latitud, BigDecimal longitud,
                           String zonaHoraria, BigDecimal superficieM2, String diseno, String tipoCerca,
                           String tipoSuelo, String fuenteAgua, String tecnicaRiego, String tipoAbono,
                           List<PlantaVista> plantas, List<CultivoVista> cultivos, List<ZonaVista> zonas) {
}

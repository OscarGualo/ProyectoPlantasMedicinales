package ec.epn.huertos.plantas;
public final class PlantaDtos {
 private PlantaDtos() { }
 public record PlantaSolicitud(String catalogoCodigo) { }
 public record PlantaVista(Long id,Long huertoId,String nombreComun,String nombreCientifico,
  String familia,String fuente,String otrosNombres,String descripcion,String altitud,String distribucion,
  String ciclo,Clasificacion clasificacion,String usos,String fotoUrl,String catalogoCodigo,boolean institucional) {
  public static PlantaVista de(Planta p) {
   return new PlantaVista(p.getId(),p.getHuertoId(),p.getNombreComun(),p.getNombreCientifico(),p.getFamilia(),
    p.getFuente(),p.getOtrosNombres(),p.getDescripcion(),p.getAltitud(),p.getDistribucion(),p.getCiclo(),
    p.getClasificacion(),p.getUsos(),p.getFotoId()==null?null:"/api/fotos/"+p.getFotoId(),
    p.getCatalogoCodigo(),p.getCatalogoCodigo()!=null);
  }
 }
}

package ec.epn.huertos.plantas;
import ec.epn.huertos.acceso.AutorizacionHuerto;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.fotos.ServicioFotos;
import ec.epn.huertos.plantas.PlantaDtos.PlantaSolicitud;
import ec.epn.huertos.plantas.PlantaDtos.PlantaVista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/** El líder selecciona fichas del MSP y gestiona fotografías de su huerto. */
@Service @RequiredArgsConstructor
public class ServicioPlantas {
 private final PlantaRepositorio plantas;
 private final ServicioFotos fotos;
 private final AutorizacionHuerto autorizacion;
 private final CatalogoMsp catalogo;
 @Transactional(readOnly=true)
 public List<PlantaVista> paraGestion(Long huertoId) {
  autorizacion.verificarLider(huertoId);
  return plantas.findByHuertoIdOrderByNombreComunAscIdAsc(huertoId).stream().map(PlantaVista::de).toList();
 }
 @Transactional(readOnly=true)
 public List<PlantaVista> deHuerto(Long huertoId) {
  return plantas.findByHuertoIdOrderByNombreComunAscIdAsc(huertoId).stream()
   .filter(p->p.getCatalogoCodigo()!=null).map(PlantaVista::de).toList();
 }
 @Transactional
 public PlantaVista registrar(Long huertoId,PlantaSolicitud s,MultipartFile foto) {
  autorizacion.verificarLider(huertoId);
  var ficha=catalogo.ficha(s==null?null:s.catalogoCodigo());
  Planta p=new Planta();p.setHuertoId(huertoId);aplicar(p,ficha);
  if(ServicioFotos.presente(foto)) p.setFotoId(fotos.guardar(foto).getId());
  return PlantaVista.de(plantas.save(p));
 }
 @Transactional
 public PlantaVista modificar(Long plantaId,PlantaSolicitud s,MultipartFile foto) {
  Planta p=plantas.findById(plantaId).orElseThrow(()->ErrorNegocio.de(Mensaje.MSG_03));
  autorizacion.verificarLider(p.getHuertoId());
  if(p.getCatalogoCodigo()==null || (s.catalogoCodigo()!=null && !s.catalogoCodigo().equals(p.getCatalogoCodigo())))
   throw ErrorNegocio.enCampo(Mensaje.MSG_28,"catalogoCodigo");
  aplicar(p,catalogo.ficha(p.getCatalogoCodigo()));
  if(ServicioFotos.presente(foto))p.setFotoId(fotos.guardar(foto).getId());
  return PlantaVista.de(p);
 }
 private void aplicar(Planta p,CatalogoMsp.Ficha f) {
  p.setCatalogoCodigo(f.codigo());p.setNombreComun(f.nombreComun());p.setNombreCientifico(f.nombreCientifico());
  p.setFamilia(f.familia());p.setFuente(f.fuente()+" Versión "+f.version());p.setDescripcion(f.descripcion());
  p.setClasificacion(Clasificacion.desde(f.clasificacion()));p.setUsos(f.usos());
  p.setOtrosNombres(null);p.setAltitud(null);p.setDistribucion(null);p.setCiclo(null);
 }
}

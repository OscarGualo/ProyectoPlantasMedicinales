package ec.epn.huertos.plantas;

import ec.epn.huertos.acceso.AutorizacionHuerto;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.comun.SolicitudInvalida;
import ec.epn.huertos.fotos.ServicioFotos;
import ec.epn.huertos.plantas.PlantaDtos.PlantaSolicitud;
import ec.epn.huertos.plantas.PlantaDtos.PlantaVista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Fichas educativas de plantas por huerto (HU-006, HU-007; RN-02, RN-03, RN-05). */
@Service
@RequiredArgsConstructor
public class ServicioPlantas {

    private static final Set<String> CICLOS = Set.of("CORTO", "LARGO");

    private final PlantaRepositorio plantas;
    private final ServicioFotos fotos;
    private final AutorizacionHuerto autorizacion;

    @Transactional(readOnly = true)
    public List<PlantaVista> paraGestion(Long huertoId) {
        autorizacion.verificarLider(huertoId);
        return deHuerto(huertoId);
    }

    @Transactional(readOnly = true)
    public List<PlantaVista> deHuerto(Long huertoId) {
        return plantas.findByHuertoIdOrderByNombreComunAscIdAsc(huertoId).stream().map(PlantaVista::de).toList();
    }

    /** HU-006: datos obligatorios (MSG-04) y fotografía JPEG o PNG de hasta 5 MB (MSG-07). */
    @Transactional
    public PlantaVista registrar(Long huertoId, PlantaSolicitud s, MultipartFile foto) {
        autorizacion.verificarLider(huertoId);
        Planta p = new Planta();
        p.setHuertoId(huertoId);
        aplicar(p, s);
        if (ServicioFotos.presente(foto)) {
            p.setFotoId(fotos.guardar(foto).getId());
        }
        return PlantaVista.de(plantas.save(p));
    }

    /**
     * HU-007: modifica solo la ficha de este huerto (D-10). Si la validación falla, la versión pública anterior
     * se mantiene: la transacción se revierte y la entidad no se modifica antes de validar.
     */
    @Transactional
    public PlantaVista modificar(Long plantaId, PlantaSolicitud s, MultipartFile foto) {
        Planta p = plantas.findById(plantaId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_03));
        autorizacion.verificarLider(p.getHuertoId());
        aplicar(p, s);
        if (ServicioFotos.presente(foto)) {
            p.setFotoId(fotos.guardar(foto).getId());
        }
        return PlantaVista.de(p);
    }

    private void aplicar(Planta p, PlantaSolicitud s) {
        validarObligatorios(s);
        Clasificacion clasificacion = Clasificacion.desde(s.clasificacion());
        String ciclo = ciclo(s.ciclo());
        p.setNombreComun(limpio(s.nombreComun()));
        p.setNombreCientifico(limpio(s.nombreCientifico()));
        p.setFamilia(limpio(s.familia()));
        p.setFuente(limpio(s.fuente()));
        p.setOtrosNombres(limpio(s.otrosNombres()));
        p.setDescripcion(limpio(s.descripcion()));
        p.setAltitud(limpio(s.altitud()));
        p.setDistribucion(limpio(s.distribucion()));
        p.setCiclo(ciclo);
        p.setClasificacion(clasificacion);
        p.setUsos(limpio(s.usos()));
    }

    private static void validarObligatorios(PlantaSolicitud s) {
        if (s == null || limpio(s.nombreComun()) == null) {
            throw ErrorNegocio.campoObligatorio("Nombre común", "nombreComun");
        }
        if (limpio(s.nombreCientifico()) == null) {
            throw ErrorNegocio.campoObligatorio("Nombre científico", "nombreCientifico");
        }
        if (limpio(s.familia()) == null) {
            throw ErrorNegocio.campoObligatorio("Familia", "familia");
        }
        if (limpio(s.fuente()) == null) {
            throw ErrorNegocio.campoObligatorio("Fuente", "fuente");
        }
    }

    private static String ciclo(String valor) {
        String c = limpio(valor);
        if (c == null) {
            return null;
        }
        c = c.toUpperCase(Locale.ROOT);
        if (!CICLOS.contains(c)) {
            throw new SolicitudInvalida("Ciclo desconocido: " + valor);
        }
        return c;
    }

    private static String limpio(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}

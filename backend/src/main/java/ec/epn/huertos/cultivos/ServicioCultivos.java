package ec.epn.huertos.cultivos;

import ec.epn.huertos.acceso.AutorizacionHuerto;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.huertos.Zona;
import ec.epn.huertos.huertos.ZonaRepositorio;
import ec.epn.huertos.plantas.Planta;
import ec.epn.huertos.plantas.PlantaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.Objects;

/** Cultivos por zona con su estado (HU-008, HU-009; RN-03). */
@Service
@RequiredArgsConstructor
public class ServicioCultivos {

    private final CultivoRepositorio cultivos;
    private final PlantaRepositorio plantas;
    private final ZonaRepositorio zonas;
    private final AutorizacionHuerto autorizacion;
    private final Clock reloj;

    public record CultivoSolicitud(Long plantaId, Long zonaId) {
    }

    public record EstadoSolicitud(String estado) {
    }

    @Transactional(readOnly = true)
    public List<CultivoVista> paraGestion(Long huertoId) {
        autorizacion.verificarLider(huertoId);
        return deHuerto(huertoId);
    }

    @Transactional(readOnly = true)
    public List<CultivoVista> deHuerto(Long huertoId) {
        return cultivos.vistasDeHuerto(huertoId);
    }

    /** HU-008: la planta y la zona deben ser del mismo huerto; una zona ajena es MSG-09. */
    @Transactional
    public CultivoVista registrar(Long huertoId, CultivoSolicitud s) {
        autorizacion.verificarLider(huertoId);
        if (s == null || s.plantaId() == null) {
            throw ErrorNegocio.campoObligatorio("Planta", "plantaId");
        }
        if (s.zonaId() == null) {
            throw ErrorNegocio.campoObligatorio("Zona", "zonaId");
        }
        Zona zona = zonas.findById(s.zonaId())
                .filter(z -> Objects.equals(z.getHuertoId(), huertoId))
                .orElseThrow(() -> ErrorNegocio.enCampo(Mensaje.MSG_09, "zonaId"));
        Planta planta = plantas.findById(s.plantaId())
                .filter(p -> Objects.equals(p.getHuertoId(), huertoId))
                .orElseThrow(() -> ErrorNegocio.enCampo(Mensaje.MSG_03, "plantaId"));
        Cultivo c = cultivos.save(new Cultivo(huertoId, planta.getId(), zona.getId(), reloj.instant()));
        return cultivos.vista(c.getId());
    }

    /** HU-009: cambia solo este cultivo; la ficha de la planta y los otros cultivos no cambian. */
    @Transactional
    public CultivoVista cambiarEstado(Long cultivoId, EstadoSolicitud s) {
        Cultivo c = cultivos.findById(cultivoId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_03));
        autorizacion.verificarLider(c.getHuertoId());
        EstadoCultivo nuevo = EstadoCultivo.desde(s == null ? null : s.estado());
        c.cambiarEstado(nuevo, reloj.instant());
        cultivos.flush();
        return cultivos.vista(c.getId());
    }
}

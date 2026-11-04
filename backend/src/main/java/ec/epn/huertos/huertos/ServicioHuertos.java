package ec.epn.huertos.huertos;

import ec.epn.huertos.acceso.AutorizacionHuerto;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.huertos.HuertoDtos.CaracteristicasSolicitud;
import ec.epn.huertos.huertos.HuertoDtos.HuertoGestion;
import ec.epn.huertos.huertos.HuertoDtos.UbicacionSolicitud;
import ec.epn.huertos.huertos.HuertoDtos.ZonaVista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;

/** Ficha, ubicación, características y publicación del huerto (HU-003, HU-004, HU-005). */
@Service
@RequiredArgsConstructor
public class ServicioHuertos {

    private static final BigDecimal LAT_MAX = BigDecimal.valueOf(90);
    private static final BigDecimal LON_MAX = BigDecimal.valueOf(180);
    static final String ZONA_CONTINENTAL = "America/Guayaquil";
    static final String ZONA_GALAPAGOS = "Pacific/Galapagos";

    private final HuertoRepositorio huertos;
    private final ZonaRepositorio zonas;
    private final AutorizacionHuerto autorizacion;

    @Transactional(readOnly = true)
    public HuertoGestion paraGestion(Long huertoId) {
        autorizacion.verificarLider(huertoId);
        return HuertoGestion.de(cargar(huertoId));
    }

    /** HU-003: coordenadas obligatorias y dentro de rango; si no, MSG-06 y se conserva la ubicación anterior. */
    @Transactional
    public HuertoGestion actualizarUbicacion(Long huertoId, UbicacionSolicitud s) {
        autorizacion.verificarLider(huertoId);
        if (!enRango(s.latitud(), LAT_MAX) || !enRango(s.longitud(), LON_MAX)) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_06, "coordenadas");
        }
        Huerto h = cargar(huertoId);
        h.setProvincia(limpio(s.provincia()));
        h.setParroquia(limpio(s.parroquia()));
        h.setDireccion(limpio(s.direccion()));
        h.setLatitud(s.latitud());
        h.setLongitud(s.longitud());
        // La fase lunar se evalúa en la hora local del huerto (AD-10, RN-09).
        h.setZonaHoraria(esGalapagos(h.getProvincia()) ? ZONA_GALAPAGOS : ZONA_CONTINENTAL);
        return HuertoGestion.de(h);
    }

    /** HU-004: nombre obligatorio (MSG-04) y superficie ≥ 0 (MSG-05). */
    @Transactional
    public HuertoGestion actualizarCaracteristicas(Long huertoId, CaracteristicasSolicitud s) {
        autorizacion.verificarLider(huertoId);
        if (limpio(s.nombre()) == null) {
            throw ErrorNegocio.campoObligatorio("Nombre", "nombre");
        }
        if (s.superficieM2() != null && s.superficieM2().signum() < 0) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_05, "superficieM2");
        }
        Huerto h = cargar(huertoId);
        h.setNombre(limpio(s.nombre()));
        h.setDescripcion(limpio(s.descripcion()));
        h.setDiseno(limpio(s.diseno()));
        h.setTipoCerca(limpio(s.tipoCerca()));
        h.setSuperficieM2(s.superficieM2());
        h.setTipoSuelo(limpio(s.tipoSuelo()));
        h.setFuenteAgua(limpio(s.fuenteAgua()));
        h.setTecnicaRiego(limpio(s.tecnicaRiego()));
        h.setTipoAbono(limpio(s.tipoAbono()));
        return HuertoGestion.de(h);
    }

    /** HU-005: solo se publica un huerto con nombre y coordenadas (RN-02). */
    @Transactional
    public HuertoGestion publicar(Long huertoId) {
        autorizacion.verificarLider(huertoId);
        Huerto h = cargar(huertoId);
        if (!h.tieneCoordenadas()) {
            throw ErrorNegocio.de(Mensaje.MSG_21);
        }
        if (limpio(h.getNombre()) == null) {
            throw ErrorNegocio.campoObligatorio("Nombre", "nombre");
        }
        h.setPublicado(true);
        return HuertoGestion.de(h);
    }

    @Transactional(readOnly = true)
    public List<ZonaVista> zonasParaGestion(Long huertoId) {
        autorizacion.verificarLider(huertoId);
        return zonas.findByHuertoIdOrderByCodigo(huertoId).stream().map(ZonaVista::de).toList();
    }

    /** Huerto publicado o MSG-14, sin distinguir entre inexistente y no publicado (doc 06 §9). */
    @Transactional(readOnly = true)
    public Huerto publicado(Long huertoId) {
        return huertos.publicado(huertoId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_14));
    }

    @Transactional(readOnly = true)
    public List<ZonaVista> zonasDe(Long huertoId) {
        return zonas.findByHuertoIdOrderByCodigo(huertoId).stream().map(ZonaVista::de).toList();
    }


    private Huerto cargar(Long id) {
        // La autorización ya verificó que el huerto existe y pertenece al líder.
        return huertos.findById(id).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_03));
    }

    private static boolean esGalapagos(String provincia) {
        if (provincia == null) {
            return false;
        }
        String normal = Normalizer.normalize(provincia, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return normal.equalsIgnoreCase("Galapagos");
    }

    private static boolean enRango(BigDecimal valor, BigDecimal maximo) {
        return valor != null && valor.abs().compareTo(maximo) <= 0;
    }

    private static String limpio(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}

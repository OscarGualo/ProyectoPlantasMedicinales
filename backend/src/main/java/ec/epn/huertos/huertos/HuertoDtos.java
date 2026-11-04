package ec.epn.huertos.huertos;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.math.BigDecimal;

/** Solicitudes y respuestas del módulo huertos. Las públicas nunca incluyen datos privados (RN-02). */
public final class HuertoDtos {

    private HuertoDtos() {
    }

    public record UbicacionSolicitud(String provincia, String parroquia, String direccion,
                                     BigDecimal latitud, BigDecimal longitud) {
    }

    public record CaracteristicasSolicitud(String nombre, String descripcion, String diseno, String tipoCerca,
                                           BigDecimal superficieM2, String tipoSuelo, String fuenteAgua,
                                           String tecnicaRiego, String tipoAbono) {
    }

    /** Vista de gestión para el líder: incluye el contacto del responsable (privado). */
    public record HuertoGestion(Long id, String nombre, String descripcion, String provincia, String parroquia,
                                String direccion, BigDecimal latitud, BigDecimal longitud, String zonaHoraria,
                                BigDecimal superficieM2, String diseno, String tipoCerca, String tipoSuelo,
                                String fuenteAgua, String tecnicaRiego, String tipoAbono, boolean publicado,
                                String comunidad, String contactoResponsable) {

        static HuertoGestion de(Huerto h) {
            return new HuertoGestion(h.getId(), h.getNombre(), h.getDescripcion(), h.getProvincia(),
                    h.getParroquia(), h.getDireccion(), h.getLatitud(), h.getLongitud(), h.getZonaHoraria(),
                    h.getSuperficieM2(), h.getDiseno(), h.getTipoCerca(), h.getTipoSuelo(), h.getFuenteAgua(),
                    h.getTecnicaRiego(), h.getTipoAbono(), h.isPublicado(), h.getComunidad().getNombre(),
                    h.getComunidad().getContactoResponsable());
        }
    }

    /** Zona con su geometría para la escena 3D y los formularios de cultivo. */
    public record ZonaVista(Long id, String codigo, String nombre, String tipo,
                            @JsonRawValue String geometria) {

        public static ZonaVista de(Zona z) {
            return new ZonaVista(z.getId(), z.getCodigo(), z.getNombre(), z.getTipo(), z.getGeometria());
        }
    }
}

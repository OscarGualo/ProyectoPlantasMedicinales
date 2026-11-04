package ec.epn.huertos.plantas;

/** Solicitudes y vistas de las fichas de plantas. */
public final class PlantaDtos {

    private PlantaDtos() {
    }

    /**
     * Campos del formulario multipart (doc 05 §7.1). Obligatorios: nombre común, nombre científico, familia y
     * fuente. Los opcionales nunca se inventan si no hay información validada.
     */
    public record PlantaSolicitud(String nombreComun, String nombreCientifico, String familia, String fuente,
                                  String otrosNombres, String descripcion, String altitud, String distribucion,
                                  String ciclo, String clasificacion, String usos) {
    }

    /** Ficha tal como la consulta el público. Sin estado: el estado es del cultivo (HU-009 CA-01). */
    public record PlantaVista(Long id, Long huertoId, String nombreComun, String nombreCientifico, String familia,
                              String fuente, String otrosNombres, String descripcion, String altitud,
                              String distribucion, String ciclo, Clasificacion clasificacion, String usos,
                              String fotoUrl) {

        public static PlantaVista de(Planta p) {
            return new PlantaVista(p.getId(), p.getHuertoId(), p.getNombreComun(), p.getNombreCientifico(),
                    p.getFamilia(), p.getFuente(), p.getOtrosNombres(), p.getDescripcion(), p.getAltitud(),
                    p.getDistribucion(), p.getCiclo(), p.getClasificacion(), p.getUsos(),
                    p.getFotoId() == null ? null : "/api/fotos/" + p.getFotoId());
        }
    }
}

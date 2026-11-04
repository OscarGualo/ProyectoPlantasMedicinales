package ec.epn.huertos.cultivos;

import ec.epn.huertos.plantas.Clasificacion;

import java.time.Instant;

/**
 * Cultivo con su planta, su zona y su estado. La clasificación de la planta define la señalética de la
 * escena 3D: rojo caliente, verde fría, gris sin clasificar (HU-013 CA-02).
 */
public record CultivoVista(Long id, Long plantaId, String planta, Clasificacion clasificacion, Long zonaId,
                           String zona, EstadoCultivo estado, String estadoEtiqueta, Instant registradoEn,
                           Instant actualizadoEn) {

    public CultivoVista(Long id, Long plantaId, String planta, Clasificacion clasificacion, Long zonaId,
                        String zona, EstadoCultivo estado, Instant registradoEn, Instant actualizadoEn) {
        this(id, plantaId, planta, clasificacion, zonaId, zona, estado, estado.etiqueta(), registradoEn,
                actualizadoEn);
    }
}

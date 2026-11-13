package ec.epn.huertos.actividades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActividadVersionRepositorio extends JpaRepository<ActividadVersion, Long> {

    /** Versión vigente (la de mayor número) de cada actividad del cultivo, de la más reciente a la más antigua. */
    @Query("""
            SELECT v FROM ActividadVersion v
             WHERE v.actividadId IN (SELECT a.id FROM Actividad a WHERE a.cultivoId = :cultivoId)
               AND v.numero = (SELECT MAX(v2.numero) FROM ActividadVersion v2 WHERE v2.actividadId = v.actividadId)
             ORDER BY v.fechaHecho DESC, v.actividadId DESC""")
    List<ActividadVersion> vigentesDeCultivo(Long cultivoId);

    @Query("""
            SELECT v FROM ActividadVersion v
             WHERE v.actividadId = :actividadId
               AND v.numero = (SELECT MAX(v2.numero) FROM ActividadVersion v2 WHERE v2.actividadId = :actividadId)""")
    ActividadVersion vigente(Long actividadId);

    Optional<ActividadVersion> findByActividadIdAndClaveCorreccion(Long actividadId, UUID claveCorreccion);

    Optional<ActividadVersion> findByActividadIdAndNumero(Long actividadId, int numero);

    /** Todas las versiones de una actividad, de la más reciente a la más antigua (HU-017). */
    List<ActividadVersion> findByActividadIdOrderByNumeroDesc(Long actividadId);

    /**
     * Versión vigente de cada siembra de los huertos publicados de una comunidad (RN-08), de la más reciente a la
     * más antigua por fecha del hecho.
     */
    @Query("""
            SELECT v FROM ActividadVersion v
             WHERE v.actividadId IN (
                   SELECT a.id FROM Actividad a, Cultivo c, Huerto h, Planta p
                    WHERE c.id = a.cultivoId AND h.id = c.huertoId
                      AND p.id = c.plantaId AND p.catalogoCodigo IS NOT NULL
                      AND h.comunidad.id = :comunidadId AND h.publicado = true)
               AND v.numero = (SELECT MAX(v2.numero) FROM ActividadVersion v2 WHERE v2.actividadId = v.actividadId)
             ORDER BY v.fechaHecho DESC, v.actividadId DESC""")
    List<ActividadVersion> vigentesDeComunidad(Long comunidadId);
}

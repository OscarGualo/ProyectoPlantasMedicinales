package ec.epn.huertos.cultivos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CultivoRepositorio extends JpaRepository<Cultivo, Long> {

    String VISTA = """
            SELECT new ec.epn.huertos.cultivos.CultivoVista(c.id, p.id, p.nombreComun, p.clasificacion,
                   z.id, z.codigo, c.estado, c.registradoEn, c.actualizadoEn)
              FROM Cultivo c, Planta p, Zona z
             WHERE p.id = c.plantaId AND z.id = c.zonaId""";

    @Query(VISTA + " AND c.huertoId = :huertoId ORDER BY z.codigo, c.id")
    List<CultivoVista> vistasDeHuerto(Long huertoId);

    @Query(VISTA + " AND c.id = :id")
    CultivoVista vista(Long id);
}

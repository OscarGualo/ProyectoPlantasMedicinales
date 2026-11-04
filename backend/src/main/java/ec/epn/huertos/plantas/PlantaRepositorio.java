package ec.epn.huertos.plantas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantaRepositorio extends JpaRepository<Planta, Long> {

    List<Planta> findByHuertoIdOrderByNombreComunAscIdAsc(Long huertoId);
}

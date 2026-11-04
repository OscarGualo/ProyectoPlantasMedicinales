package ec.epn.huertos.huertos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZonaRepositorio extends JpaRepository<Zona, Long> {

    List<Zona> findByHuertoIdOrderByCodigo(Long huertoId);
}

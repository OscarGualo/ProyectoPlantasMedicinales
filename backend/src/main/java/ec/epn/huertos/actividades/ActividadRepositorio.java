package ec.epn.huertos.actividades;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActividadRepositorio extends JpaRepository<Actividad, Long> {

    Optional<Actividad> findByClaveEnvio(UUID claveEnvio);
}

package ec.epn.huertos.actividades;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ActividadRepositorio extends JpaRepository<Actividad, Long> {

    Optional<Actividad> findByClaveEnvio(UUID claveEnvio);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Actividad a WHERE a.id = :id")
    Optional<Actividad> bloquear(Long id);
}

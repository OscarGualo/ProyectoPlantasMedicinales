package ec.epn.huertos.huertos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HuertoRepositorio extends JpaRepository<Huerto, Long> {

    /** Huerto publicado o vacío; la consulta pública no distingue inexistente de no publicado (MSG-14). */
    @Query("SELECT h FROM Huerto h WHERE h.id = :id AND h.publicado = true")
    Optional<Huerto> publicado(Long id);
}

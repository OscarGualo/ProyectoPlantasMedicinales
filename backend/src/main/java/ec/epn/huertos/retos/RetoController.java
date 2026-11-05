package ec.epn.huertos.retos;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Reto Documentación lunar, público con o sin sesión (doc 06 §9). */
@RestController
@RequiredArgsConstructor
class RetoController {

    private final ServicioRetos servicio;

    /** HU-018: insignia y su sustento, o MSG-18; huerto no publicado, 404 con MSG-14. */
    @GetMapping("/api/publico/huertos/{id}/reto")
    RetoVista reto(@PathVariable Long id) {
        return servicio.reto(id);
    }
}

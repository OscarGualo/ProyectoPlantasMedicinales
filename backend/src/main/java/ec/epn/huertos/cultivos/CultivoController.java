package ec.epn.huertos.cultivos;

import ec.epn.huertos.cultivos.ServicioCultivos.CultivoSolicitud;
import ec.epn.huertos.cultivos.ServicioCultivos.EstadoSolicitud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Gestión de cultivos por el líder (doc 06 §9). */
@RestController
@RequiredArgsConstructor
class CultivoController {

    private final ServicioCultivos servicio;

    @GetMapping("/api/huertos/{huertoId}/cultivos")
    List<CultivoVista> listar(@PathVariable Long huertoId) {
        return servicio.paraGestion(huertoId);
    }

    @PostMapping("/api/huertos/{huertoId}/cultivos")
    @ResponseStatus(HttpStatus.CREATED)
    CultivoVista registrar(@PathVariable Long huertoId, @RequestBody CultivoSolicitud solicitud) {
        return servicio.registrar(huertoId, solicitud);
    }

    @PatchMapping("/api/cultivos/{id}/estado")
    CultivoVista cambiarEstado(@PathVariable Long id, @RequestBody EstadoSolicitud solicitud) {
        return servicio.cambiarEstado(id, solicitud);
    }
}

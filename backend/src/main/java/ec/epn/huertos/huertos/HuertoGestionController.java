package ec.epn.huertos.huertos;

import ec.epn.huertos.huertos.HuertoDtos.CaracteristicasSolicitud;
import ec.epn.huertos.huertos.HuertoDtos.HuertoGestion;
import ec.epn.huertos.huertos.HuertoDtos.UbicacionSolicitud;
import ec.epn.huertos.huertos.HuertoDtos.ZonaVista;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Gestión del huerto por su líder (doc 06 §9). */
@RestController
@RequestMapping("/api/huertos/{id}")
@RequiredArgsConstructor
class HuertoGestionController {

    private final ServicioHuertos servicio;

    @GetMapping
    HuertoGestion obtener(@PathVariable Long id) {
        return servicio.paraGestion(id);
    }

    @GetMapping("/zonas")
    List<ZonaVista> zonas(@PathVariable Long id) {
        return servicio.zonasParaGestion(id);
    }

    @PutMapping("/ubicacion")
    HuertoGestion ubicacion(@PathVariable Long id, @RequestBody UbicacionSolicitud solicitud) {
        return servicio.actualizarUbicacion(id, solicitud);
    }

    @PutMapping("/caracteristicas")
    HuertoGestion caracteristicas(@PathVariable Long id, @RequestBody CaracteristicasSolicitud solicitud) {
        return servicio.actualizarCaracteristicas(id, solicitud);
    }

    @PostMapping("/publicacion")
    HuertoGestion publicar(@PathVariable Long id) {
        return servicio.publicar(id);
    }
}

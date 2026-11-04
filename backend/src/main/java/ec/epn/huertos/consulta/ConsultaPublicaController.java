package ec.epn.huertos.consulta;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Consulta pública sin cuenta (RN-02). */
@RestController
@RequestMapping("/api/publico/huertos")
@RequiredArgsConstructor
class ConsultaPublicaController {

    private final ServicioConsultaPublica servicio;

    /** HU-010: un marcador por cada huerto publicado con ubicación. */
    @GetMapping
    List<ResumenHuerto> huertos() {
        return servicio.huertosPublicados();
    }

    /** HU-011, HU-012 y HU-013: ficha pública con plantas, cultivos y zonas de la escena 3D. */
    @GetMapping("/{id}")
    FichaPublica ficha(@PathVariable Long id) {
        return servicio.ficha(id);
    }
}

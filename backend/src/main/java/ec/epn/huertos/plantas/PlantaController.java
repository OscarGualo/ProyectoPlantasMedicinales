package ec.epn.huertos.plantas;

import ec.epn.huertos.plantas.PlantaDtos.PlantaSolicitud;
import ec.epn.huertos.plantas.PlantaDtos.PlantaVista;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;

/** Gestión de fichas de plantas por el líder (doc 06 §9). */
@RestController
@RequiredArgsConstructor
class PlantaController {

    private final ServicioPlantas servicio;
    private final CatalogoMsp catalogo;

    @GetMapping("/api/publico/catalogo-plantas")
    List<CatalogoMsp.Ficha> catalogo() {
        return catalogo.listar();
    }

    @GetMapping("/api/huertos/{huertoId}/plantas")
    List<PlantaVista> listar(@PathVariable Long huertoId) {
        return servicio.paraGestion(huertoId);
    }

    @PostMapping(path = "/api/huertos/{huertoId}/plantas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PlantaVista registrar(@PathVariable Long huertoId, @ModelAttribute PlantaSolicitud solicitud,
                          @RequestPart(name = "foto", required = false) MultipartFile foto,
                          HttpServletRequest req) {
        validarCampos(req);
        return servicio.registrar(huertoId, solicitud, foto);
    }

    @PutMapping(path = "/api/plantas/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    PlantaVista modificar(@PathVariable Long id, @ModelAttribute PlantaSolicitud solicitud,
                          @RequestPart(name = "foto", required = false) MultipartFile foto,
                          HttpServletRequest req) {
        validarCampos(req);
        return servicio.modificar(id, solicitud, foto);
    }

    private static void validarCampos(HttpServletRequest req) {
        if (req.getParameterMap().keySet().stream()
                .anyMatch(k -> !Set.of("catalogoCodigo", "foto", "_csrf").contains(k))) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_28, "catalogoCodigo");
        }
    }
}

package ec.epn.huertos.actividades;

import ec.epn.huertos.actividades.ServicioActividades.Registro;
import ec.epn.huertos.actividades.SiembraDtos.BitacoraVista;
import ec.epn.huertos.actividades.SiembraDtos.SiembraSolicitud;
import ec.epn.huertos.actividades.SiembraDtos.SiembraVista;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** Registro y bitácora de siembras (doc 06 §9). */
@RestController
@RequiredArgsConstructor
class SiembraController {

    private final ServicioActividades servicio;

    /** HU-014: 201 si la siembra es nueva; 200 con la misma siembra si el envío se repitió (CA-02). */
    @PostMapping(path = "/api/cultivos/{id}/siembras", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SiembraVista> registrar(@PathVariable Long id, @ModelAttribute SiembraSolicitud solicitud,
                                           @RequestPart(name = "foto", required = false) MultipartFile foto) {
        try {
            Registro r = servicio.registrar(id, solicitud, foto);
            return ResponseEntity.status(r.creada() ? HttpStatus.CREATED : HttpStatus.OK).body(r.siembra());
        } catch (DataIntegrityViolationException e) {
            // Dos envíos simultáneos con la misma clave: el segundo choca con la restricción única y recibe el primero.
            return ResponseEntity.ok(servicio.porClave(id, solicitud.claveEnvio()));
        }
    }

    /** HU-014 y HU-015: siembras vigentes del cultivo con su condición lunar. */
    @GetMapping("/api/cultivos/{id}/bitacora")
    BitacoraVista bitacora(@PathVariable Long id) {
        return servicio.bitacora(id);
    }
}

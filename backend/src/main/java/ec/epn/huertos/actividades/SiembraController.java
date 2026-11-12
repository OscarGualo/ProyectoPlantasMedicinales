package ec.epn.huertos.actividades;

import ec.epn.huertos.actividades.ServicioActividades.Registro;
import ec.epn.huertos.actividades.SiembraDtos.BitacoraVista;
import ec.epn.huertos.actividades.SiembraDtos.CorreccionSolicitud;
import ec.epn.huertos.actividades.SiembraDtos.HistorialVista;
import ec.epn.huertos.actividades.SiembraDtos.SiembraSolicitud;
import ec.epn.huertos.actividades.SiembraDtos.SiembraVista;
import lombok.RequiredArgsConstructor;
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

/** Registro, bitácora, corrección e historial de siembras (doc 06 §9). */
@RestController
@RequiredArgsConstructor
class SiembraController {

    private final ServicioActividades servicio;

    /** HU-014: 201 si la siembra es nueva; 200 con la misma siembra si el envío se repitió (CA-02). */
    @PostMapping(path = "/api/cultivos/{id}/siembras", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SiembraVista> registrar(@PathVariable Long id, @ModelAttribute SiembraSolicitud solicitud,
                                           @RequestPart(name = "foto", required = false) MultipartFile foto) {
        Registro r = servicio.registrar(id, solicitud, foto);
        return ResponseEntity.status(r.creada() ? HttpStatus.CREATED : HttpStatus.OK).body(r.siembra());
    }

    /** HU-014 y HU-015: siembras vigentes del cultivo con su condición lunar. */
    @GetMapping("/api/cultivos/{id}/bitacora")
    BitacoraVista bitacora(@PathVariable Long id) {
        return servicio.bitacora(id);
    }

    /** HU-016: 201 para una versión nueva, 200 al repetir una corrección confirmada. */
    @PostMapping(path = "/api/siembras/{id}/correcciones", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<SiembraVista> corregir(@PathVariable Long id, @ModelAttribute CorreccionSolicitud solicitud,
                                          @RequestPart(name = "foto", required = false) MultipartFile foto) {
        Registro r = servicio.corregir(id, solicitud, foto);
        return ResponseEntity.status(r.creada() ? HttpStatus.CREATED : HttpStatus.OK).body(r.siembra());
    }

    /** HU-017: versiones de la siembra, de la más reciente a la más antigua. */
    @GetMapping("/api/siembras/{id}/historial")
    HistorialVista historial(@PathVariable Long id) {
        return servicio.historial(id);
    }
}

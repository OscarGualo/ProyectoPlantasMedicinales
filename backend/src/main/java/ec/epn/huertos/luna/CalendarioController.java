package ec.epn.huertos.luna;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/** Calendario lunar público, con o sin sesión (HU-019, HU-020). */
@RestController
@RequiredArgsConstructor
class CalendarioController {

    private final ServicioCalendario servicio;

    @GetMapping("/api/publico/calendario-lunar")
    ServicioCalendario.Calendario calendario(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) Integer dias,
            @RequestParam(required = false) String actividad) {
        return servicio.calendario(desde, dias, actividad);
    }
}

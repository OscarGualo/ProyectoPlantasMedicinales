package ec.epn.huertos.fotos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class FotoController {

    private final ServicioFotos servicio;

    @GetMapping("/api/fotos/{id}")
    ResponseEntity<byte[]> foto(@PathVariable Long id) {
        Foto f = servicio.visible(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.getTipoMime()))
                .cacheControl(CacheControl.noStore())
                .body(f.getContenido());
    }
}

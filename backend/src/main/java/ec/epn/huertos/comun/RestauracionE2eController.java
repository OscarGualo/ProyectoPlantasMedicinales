package ec.epn.huertos.comun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * Restaura los datos de prueba del doc 05 §7 antes de cada caso de Playwright («Cómo ejecutar y registrar» del
 * doc 04). Existe SOLO con el perfil {@code e2e}: en cualquier otro perfil la ruta no está registrada.
 */
@RestController
@Profile("e2e")
@RequiredArgsConstructor
class RestauracionE2eController {

    private final DataSource dataSource;

    @PostMapping("/api/publico/e2e/restaurar")
    ResponseEntity<Void> restaurar() {
        new ResourceDatabasePopulator(new ClassPathResource("e2e/datos-prueba.sql")).execute(dataSource);
        return ResponseEntity.noContent().build();
    }
}

package ec.epn.huertos.comun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Reloj inyectable (AD-06): las pruebas lo fijan en 31/10/2026 12:00 America/Guayaquil (doc 04).
 */
@Configuration(proxyBeanMethods = false)
public class RelojConfig {

    public static final ZoneId ZONA_ECUADOR = ZoneId.of("America/Guayaquil");

    @Bean
    Clock reloj(@Value("${huertos.reloj.fijo:}") String fijo) {
        if (fijo == null || fijo.isBlank()) {
            return Clock.system(ZONA_ECUADOR);
        }
        return Clock.fixed(OffsetDateTime.parse(fijo).toInstant(), ZONA_ECUADOR);
    }
}

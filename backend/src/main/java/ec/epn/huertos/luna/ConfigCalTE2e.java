package ec.epn.huertos.luna;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Solo en el perfil e2e: CAL-T para los días que define (10 a 14/10/2026) y el cálculo real en el resto. Así el
 * recorrido P-055 usa las fases controladas de doc 05 §7 para las siembras, y el calendario (P-052 a P-054,
 * noviembre de 2026) sigue con las fases reales.
 */
@Configuration(proxyBeanMethods = false)
@Profile("e2e")
class ConfigCalTE2e {

    @Bean
    @Primary
    CalculadoraFase calTSobreReal(ServicioFaseLunar real) {
        CalendarioControladoCalT calT = new CalendarioControladoCalT();
        return new CalculadoraFase() {
            @Override
            public Optional<FaseLunar> faseEn(Instant instante, ZoneId zona) {
                return calT.cubre(instante, zona) ? calT.faseEn(instante, zona) : real.faseEn(instante, zona);
            }
        };
    }
}

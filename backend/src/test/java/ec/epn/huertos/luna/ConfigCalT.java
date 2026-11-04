package ec.epn.huertos.luna;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Sustituye el cálculo lunar por CAL-T en las pruebas que lo importan. Los casos P-050 a P-054 no lo importan:
 * usan el cálculo real (doc 04).
 */
@TestConfiguration(proxyBeanMethods = false)
public class ConfigCalT {

    @Bean
    @Primary
    CalculadoraFase calT() {
        return new CalendarioControladoCalT();
    }
}

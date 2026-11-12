package ec.epn.huertos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

/** PostgreSQL real y efímero para las pruebas de integración. Requiere Docker. */
@TestConfiguration(proxyBeanMethods = false)
@ConditionalOnProperty(
        name = "huertos.pruebas.postgres-externo",
        havingValue = "false",
        matchIfMissing = true
)
public class ContenedoresPrueba {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgres() {
        return new PostgreSQLContainer("postgres:17-alpine");
    }
}
package ec.epn.huertos.acceso;

import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.comun.RespuestaError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

/**
 * Seguridad de la versión 1 (AD-03, RN-01, doc 06 §11): sesión con cookie en el servidor, protección CSRF
 * para la SPA del mismo origen y respuestas de error con el catálogo MSG.
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SeguridadConfig {

    @Bean
    SecurityFilterChain cadenaSeguridad(HttpSecurity http, JsonMapper json,
                                        SecurityContextRepository contextos) throws Exception {
        CookieCsrfTokenRepository tokens = CookieCsrfTokenRepository.withHttpOnlyFalse();
        tokens.setCookieCustomizer(c -> c.path("/").sameSite("Lax"));

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(tokens)
                        // La SPA envía en X-XSRF-TOKEN el mismo valor que lee de la cookie XSRF-TOKEN.
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        // Solo existe con el perfil e2e (RestauracionE2eController); fuera de él responde 404.
                        .ignoringRequestMatchers("/api/publico/e2e/**"))
                .addFilterAfter(new CargarTokenCsrf(), BasicAuthenticationFilter.class)
                .securityContext(c -> c.securityContextRepository(contextos))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/sesion").permitAll()
                        .requestMatchers("/api/publico/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/fotos/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(e -> e
                        // Sin sesión válida: MSG-02 (HU-002, CA-02).
                        .authenticationEntryPoint((req, res, ex) -> escribir(res, json, Mensaje.MSG_02))
                        // Una solicitud reenviada tras cerrar sesión suele fallar primero en el CSRF:
                        // si no hay sesión autenticada se informa MSG-02; si la hay, MSG-03.
                        .accessDeniedHandler((req, res, ex) -> escribir(res, json,
                                UsuarioActual.opcional().isPresent() ? Mensaje.MSG_03 : Mensaje.MSG_02)));
        return http.build();
    }

    @Bean
    SecurityContextRepository repositorioContextos() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    PasswordEncoder codificadorContrasenas() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager gestorAutenticacion(UserDetailsService usuarios, PasswordEncoder codificador) {
        DaoAuthenticationProvider proveedor = new DaoAuthenticationProvider(usuarios);
        proveedor.setPasswordEncoder(codificador);
        return new ProviderManager(proveedor);
    }

    private static void escribir(HttpServletResponse res, JsonMapper json, Mensaje mensaje) throws IOException {
        res.setStatus(mensaje.estadoHttp().value());
        res.setCharacterEncoding("UTF-8");
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        json.writeValue(res.getOutputStream(), RespuestaError.de(mensaje));
    }

    /** Fuerza la emisión de la cookie XSRF-TOKEN en cada respuesta para que la SPA pueda leerla. */
    static final class CargarTokenCsrf extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                throws ServletException, IOException {
            CsrfToken token = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
            if (token != null) {
                token.getToken();
            }
            chain.doFilter(req, res);
        }
    }
}

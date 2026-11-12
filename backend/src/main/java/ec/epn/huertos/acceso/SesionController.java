package ec.epn.huertos.acceso;

import ec.epn.huertos.acceso.AutorizacionHuerto.HuertoGestionado;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Inicio y cierre de sesión (HU-001, HU-002). */
@RestController
@RequestMapping("/api/sesion")
@RequiredArgsConstructor
class SesionController {

    private final AuthenticationManager autenticacion;
    private final SecurityContextRepository contextos;
    private final AutorizacionHuerto autorizacion;
    private final CookieCsrfTokenRepository tokens;

    record Credenciales(String usuario, String contrasena) {
    }

    record Sesion(boolean autenticado, Long id, String usuario, String nombre, List<HuertoGestionado> huertos) {
        static Sesion anonima() {
            return new Sesion(false, null, null, null, List.of());
        }
    }

    /** Estado de la sesión; también entrega la cookie XSRF-TOKEN a la SPA antes del primer envío. */
    @GetMapping
    Sesion actual() {
        return UsuarioActual.opcional().map(this::sesionDe).orElseGet(Sesion::anonima);
    }

    @PostMapping
    Sesion iniciar(@RequestBody Credenciales credenciales, HttpServletRequest req, HttpServletResponse res) {
        if (credenciales == null || vacio(credenciales.usuario()) || vacio(credenciales.contrasena())) {
            throw ErrorNegocio.de(Mensaje.MSG_01);
        }
        Authentication auth;
        try {
            auth = autenticacion.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                    credenciales.usuario().trim(), credenciales.contrasena()));
        } catch (AuthenticationException e) {
            // Mismo mensaje para usuario inexistente, contraseña incorrecta o cuenta inactiva (CA-02).
            throw ErrorNegocio.de(Mensaje.MSG_01);
        }
        // Nueva sesión tras autenticarse para evitar la fijación de sesión.
        HttpSession anterior = req.getSession(false);
        if (anterior != null) {
            anterior.invalidate();
        }
        req.getSession(true);
        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(auth);
        SecurityContextHolder.setContext(contexto);
        contextos.saveContext(contexto, req, res);
        renovarToken(req, res);
        return sesionDe((LiderSesion) auth.getPrincipal());
    }

    /** Cierra la sesión en el servidor: las solicitudes posteriores con esa sesión se rechazan (HU-002 CA-02). */
    @DeleteMapping
    ResponseEntity<Void> cerrar(HttpServletRequest req, HttpServletResponse res) {
        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
        SecurityContextHolder.clearContext();
        renovarToken(req, res);
        return ResponseEntity.noContent().build();
    }

    private void renovarToken(HttpServletRequest req, HttpServletResponse res) {
        tokens.saveToken(tokens.generateToken(req), req, res);
    }

    private Sesion sesionDe(LiderSesion lider) {
        return new Sesion(true, lider.id(), lider.getUsername(), lider.nombre(), autorizacion.huertosDe(lider.id()));
    }

    private static boolean vacio(String s) {
        return s == null || s.isBlank();
    }
}

package ec.epn.huertos.acceso;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/** Acceso al líder de la sesión actual. */
public final class UsuarioActual {

    private UsuarioActual() {
    }

    public static Optional<LiderSesion> opcional() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof LiderSesion lider) {
            return Optional.of(lider);
        }
        return Optional.empty();
    }

    /** Líder autenticado o MSG-02 si la sesión no existe o terminó. */
    public static LiderSesion requerido() {
        return opcional().orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_02));
    }
}

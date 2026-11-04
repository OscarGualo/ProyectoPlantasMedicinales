package ec.epn.huertos.acceso;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

/**
 * Usuario autenticado que se guarda en la sesión del servidor (AD-03). En la iteración 1 solo los líderes
 * designados por el MSP tienen cuenta (RN-01).
 */
public final class LiderSesion implements UserDetails, CredentialsContainer {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String usuario;
    private final String nombre;
    private final boolean activo;
    private String hashContrasena;

    LiderSesion(Usuario u) {
        this.id = u.getId();
        this.usuario = u.getUsuario();
        this.nombre = u.getNombre();
        this.activo = u.isActivo();
        this.hashContrasena = u.getHashContrasena();
    }

    public Long id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_LIDER"));
    }

    @Override
    public String getPassword() {
        return hashContrasena;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    @Override
    public void eraseCredentials() {
        hashContrasena = null;
    }
}

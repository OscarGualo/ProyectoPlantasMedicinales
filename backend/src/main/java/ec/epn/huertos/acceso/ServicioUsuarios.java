package ec.epn.huertos.acceso;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ServicioUsuarios implements UserDetailsService {

    private final UsuarioRepositorio usuarios;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usuario) {
        return usuarios.findByUsuario(usuario)
                .map(LiderSesion::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}

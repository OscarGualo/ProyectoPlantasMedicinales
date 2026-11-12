package ec.epn.huertos.fotos;

import ec.epn.huertos.acceso.UsuarioActual;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Clock;
import java.util.Optional;

/** Validar, guardar y servir fotografías (RN-05). */
@Service
@RequiredArgsConstructor
public class ServicioFotos {

    private final FotoRepositorio fotos;
    private final ValidadorImagen validador;
    private final JdbcClient jdbc;
    private final Clock reloj;

    /** Archivo presente en la solicitud (las fotos de una ficha de planta son opcionales: doc 05 §7.1). */
    public static boolean presente(MultipartFile archivo) {
        return archivo != null && !archivo.isEmpty();
    }

    /** Valida y guarda dentro de la transacción del llamador, de modo que ambas se confirmen o reviertan juntas. */
    @Transactional
    public Foto guardar(MultipartFile archivo) {
        return guardar(validar(archivo));
    }

    /** Guarda una imagen ya validada; una falla de almacenamiento se propaga como DataAccessException. */
    @Transactional
    public Foto guardar(ImagenValidada imagen) {
        return fotos.save(new Foto(imagen, reloj.instant()));
    }

    public ImagenValidada validar(MultipartFile archivo) {
        if (!presente(archivo)) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
        // Se rechaza por tamaño antes de leer el contenido completo.
        if (archivo.getSize() > ValidadorImagen.TAMANO_MAXIMO) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
        try {
            return validador.validar(archivo.getBytes());
        } catch (IOException e) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
    }

    /**
     * Foto visible para quien la solicita: de plantas o siembras vigentes de huertos publicados, o del huerto del líder
     * de la sesión. Las demás responden como no encontradas (doc 06 §9).
     */
    @Transactional(readOnly = true)
    public Foto visible(Long id) {
        long usuario = UsuarioActual.opcional().map(l -> l.id()).orElse(-1L);
        boolean visible = jdbc.sql("""
                        SELECT EXISTS (
                            SELECT 1 FROM planta p JOIN huerto h ON h.id = p.huerto_id
                             WHERE p.foto_id = :id AND (h.publicado OR h.lider_id = :usuario)
                            UNION ALL
                            SELECT 1 FROM actividad_version v
                              JOIN actividad a ON a.id = v.actividad_id
                              JOIN cultivo c ON c.id = a.cultivo_id
                              JOIN huerto h ON h.id = c.huerto_id
                             WHERE v.foto_id = :id
                               AND (h.lider_id = :usuario OR (
                                    h.publicado AND v.numero = (SELECT MAX(otra.numero)
                                        FROM actividad_version otra WHERE otra.actividad_id = v.actividad_id))))
                        """)
                .param("id", id)
                .param("usuario", usuario)
                .query(Boolean.class)
                .single();
        Optional<Foto> foto = visible ? fotos.findById(id) : Optional.empty();
        Foto f = foto.orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_14));
        f.getContenido(); // carga el contenido perezoso dentro de la transacción
        return f;
    }
}

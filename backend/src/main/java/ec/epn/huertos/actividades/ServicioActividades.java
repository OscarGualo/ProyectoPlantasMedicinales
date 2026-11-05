package ec.epn.huertos.actividades;

import ec.epn.huertos.acceso.AutorizacionHuerto;
import ec.epn.huertos.acceso.LiderSesion;
import ec.epn.huertos.acceso.Usuario;
import ec.epn.huertos.acceso.UsuarioActual;
import ec.epn.huertos.acceso.UsuarioRepositorio;
import ec.epn.huertos.actividades.SiembraDtos.BitacoraVista;
import ec.epn.huertos.actividades.SiembraDtos.CondicionVista;
import ec.epn.huertos.actividades.SiembraDtos.CorreccionSolicitud;
import ec.epn.huertos.actividades.SiembraDtos.HistorialVista;
import ec.epn.huertos.actividades.SiembraDtos.SiembraSolicitud;
import ec.epn.huertos.actividades.SiembraDtos.SiembraVista;
import ec.epn.huertos.actividades.SiembraDtos.VersionVista;
import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import ec.epn.huertos.comun.SolicitudInvalida;
import ec.epn.huertos.cultivos.Cultivo;
import ec.epn.huertos.cultivos.CultivoRepositorio;
import ec.epn.huertos.cultivos.CultivoVista;
import ec.epn.huertos.fotos.ImagenValidada;
import ec.epn.huertos.fotos.ServicioFotos;
import ec.epn.huertos.huertos.Huerto;
import ec.epn.huertos.huertos.HuertoRepositorio;
import ec.epn.huertos.luna.CalculadoraFase;
import ec.epn.huertos.luna.CondicionLunar;
import ec.epn.huertos.luna.ReglaSiembra;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Registro, corrección e historial de siembras con evidencia y su condición lunar (HU-014 a HU-017; RN-04 a RN-07).
 * La condición lunar se calcula siempre con la versión vigente y nunca se guarda (AD-07).
 */
@Service
@RequiredArgsConstructor
public class ServicioActividades {

    private static final DateTimeFormatter FECHA_LOCAL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final int MOTIVO_MAXIMO = 500;

    private final ActividadRepositorio actividades;
    private final ActividadVersionRepositorio versiones;
    private final CultivoRepositorio cultivos;
    private final HuertoRepositorio huertos;
    private final UsuarioRepositorio usuarios;
    private final ServicioFotos fotos;
    private final AutorizacionHuerto autorizacion;
    private final CalculadoraFase calculadoraFase;
    private final ReglaSiembra reglaSiembra;
    private final Clock reloj;

    /** Resultado del registro: {@code creada} es falso cuando el envío repetía una clave ya registrada. */
    public record Registro(SiembraVista siembra, boolean creada) {
    }

    /**
     * Siembra vigente de un huerto publicado, para el reto (HU-018). {@code condicion} se evaluó con la versión
     * vigente en la zona horaria de su huerto.
     */
    public record SiembraVigente(Long actividadId, Long huertoId, String planta, String zona, Instant fechaHecho,
                                 String fechaHechoLocal, Long fotoId, CondicionLunar condicion) {
    }

    /**
     * HU-014, en el orden de la figura 5 del doc 06: autoridad (MSG-19), envío repetido, datos obligatorios
     * (MSG-04), fecha futura (MSG-08), fotografía (MSG-07) y guardado en una sola transacción (MSG-11).
     */
    @Transactional
    public Registro registrar(Long cultivoId, SiembraSolicitud s, MultipartFile foto) {
        Long huertoId = autorizacion.verificarCultivoPropio(cultivoId);
        LiderSesion lider = UsuarioActual.requerido();
        UUID clave = clave(s);

        var existente = actividades.findByClaveEnvio(clave);
        if (existente.isPresent()) {
            if (!Objects.equals(existente.get().getCultivoId(), cultivoId)) {
                throw new SolicitudInvalida("La clave de envío pertenece a otra siembra");
            }
            return new Registro(vista(existente.get(), zonaDe(huertoId)), false);
        }

        ZoneId zona = zonaDe(huertoId);
        Instant fechaHecho = fechaHecho(s.fechaHecho(), zona);
        Tecnica tecnica = Tecnica.desde(s.tecnica());
        if (!ServicioFotos.presente(foto)) {
            throw ErrorNegocio.campoObligatorio("Fotografía", "foto");
        }
        Instant ahora = reloj.instant();
        if (fechaHecho.isAfter(ahora)) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_08, "fechaHecho");
        }
        ImagenValidada imagen = fotos.validar(foto);

        Long fotoId;
        try {
            fotoId = fotos.guardar(imagen).getId();
        } catch (DataAccessException e) {
            // Sin evidencia no hay siembra: se revierte toda la transacción (HU-014 CA-06).
            throw ErrorNegocio.de(Mensaje.MSG_11);
        }
        Actividad a = actividades.save(Actividad.siembra(cultivoId, clave, lider.id(), ahora));
        versiones.save(ActividadVersion.primera(a.getId(), fechaHecho, tecnica, limpio(s.descripcion()), fotoId,
                lider.id(), ahora));
        return new Registro(vista(a, zona), true);
    }

    /** Siembra ya registrada con esa clave, para responder un reenvío concurrente (HU-014 CA-02). */
    @Transactional(readOnly = true)
    public SiembraVista porClave(Long cultivoId, String claveEnvio) {
        Long huertoId = autorizacion.verificarCultivoPropio(cultivoId);
        Actividad a = actividades.findByClaveEnvio(UUID.fromString(claveEnvio))
                .filter(x -> Objects.equals(x.getCultivoId(), cultivoId))
                .orElseThrow(() -> new SolicitudInvalida("Clave de envío desconocida"));
        return vista(a, zonaDe(huertoId));
    }

    /**
     * HU-016: crea la versión siguiente con el motivo, en este orden: autoridad (MSG-03), motivo (MSG-12), fecha
     * (MSG-08 si es futura) y fotografía opcional (MSG-07). Si algo falla, la versión vigente queda igual.
     */
    @Transactional
    public SiembraVista corregir(Long actividadId, CorreccionSolicitud s, MultipartFile foto) {
        Long huertoId = autorizacion.verificarLiderDeActividad(actividadId);
        LiderSesion lider = UsuarioActual.requerido();
        String motivo = limpio(s == null ? null : s.motivo());
        if (motivo == null) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_12, "motivo");
        }
        if (motivo.length() > MOTIVO_MAXIMO) {
            throw new SolicitudInvalida("El motivo supera los " + MOTIVO_MAXIMO + " caracteres");
        }
        ZoneId zona = zonaDe(huertoId);
        ActividadVersion vigente = versiones.vigente(actividadId);
        Instant fecha = s.fechaHecho() == null || s.fechaHecho().isBlank()
                ? vigente.getFechaHecho()
                : fechaHecho(s.fechaHecho(), zona);
        Instant ahora = reloj.instant();
        if (fecha.isAfter(ahora)) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_08, "fechaHecho");
        }
        Long fotoId = vigente.getFotoId();
        if (ServicioFotos.presente(foto)) {
            ImagenValidada imagen = fotos.validar(foto);
            try {
                fotoId = fotos.guardar(imagen).getId();
            } catch (DataAccessException e) {
                throw ErrorNegocio.de(Mensaje.MSG_11);
            }
        }
        try {
            versiones.saveAndFlush(ActividadVersion.correccion(vigente, fecha, fotoId, motivo, lider.id(), ahora));
        } catch (DataIntegrityViolationException e) {
            // Otra corrección simultánea tomó el mismo número de versión: se pide recargar y reintentar.
            throw new SolicitudInvalida("La siembra cambió mientras la corregías; recarga e inténtalo de nuevo");
        }
        Actividad a = actividades.findById(actividadId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_03));
        return vista(a, zona);
    }

    /** HU-017: versiones de la más reciente a la más antigua; sin correcciones, MSG-23 (CA-02). */
    @Transactional(readOnly = true)
    public HistorialVista historial(Long actividadId) {
        Long huertoId = autorizacion.verificarLiderDeActividad(actividadId);
        ZoneId zona = zonaDe(huertoId);
        Actividad a = actividades.findById(actividadId).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_03));
        List<ActividadVersion> todas = versiones.findByActividadIdOrderByNumeroDesc(actividadId);
        Map<Long, String> autores = nombres(todas.stream().map(ActividadVersion::getAutorId).toList());
        List<VersionVista> vistas = todas.stream()
                .map(v -> new VersionVista(v.getNumero(), v.getFechaHecho(), local(v.getFechaHecho(), zona),
                        v.getTecnica().etiqueta(), v.getDescripcion(), "/api/fotos/" + v.getFotoId(),
                        autores.get(v.getAutorId()), v.getCreadaEn(), v.getMotivo(),
                        CondicionVista.de(condicion(v, zona))))
                .toList();
        String mensaje = todas.size() > 1 ? null : Mensaje.MSG_23.texto();
        return new HistorialVista(vista(a, todas.getFirst(), zona, autores), zona.getId(), vistas, mensaje);
    }

    /** Siembras vigentes de los huertos publicados de una comunidad con su condición lunar (RN-08, HU-018). */
    @Transactional(readOnly = true)
    public List<SiembraVigente> siembrasVigentesDeComunidad(Long comunidadId) {
        List<ActividadVersion> vigentes = versiones.vigentesDeComunidad(comunidadId);
        if (vigentes.isEmpty()) {
            return List.of();
        }
        Map<Long, Actividad> porId = actividades.findAllById(
                        vigentes.stream().map(ActividadVersion::getActividadId).toList())
                .stream().collect(Collectors.toMap(Actividad::getId, Function.identity()));
        Map<Long, Cultivo> cultivosPorId = cultivos.findAllById(
                        porId.values().stream().map(Actividad::getCultivoId).distinct().toList())
                .stream().collect(Collectors.toMap(Cultivo::getId, Function.identity()));
        Map<Long, ZoneId> zonas = huertos.findAllById(
                        cultivosPorId.values().stream().map(Cultivo::getHuertoId).distinct().toList())
                .stream().collect(Collectors.toMap(Huerto::getId, Huerto::zona));
        Map<Long, CultivoVista> vistas = cultivosPorId.keySet().stream()
                .collect(Collectors.toMap(Function.identity(), cultivos::vista));
        return vigentes.stream().map(v -> {
            Cultivo c = cultivosPorId.get(porId.get(v.getActividadId()).getCultivoId());
            ZoneId zona = zonas.get(c.getHuertoId());
            CultivoVista cv = vistas.get(c.getId());
            return new SiembraVigente(v.getActividadId(), c.getHuertoId(), cv.planta(), cv.zona(), v.getFechaHecho(),
                    local(v.getFechaHecho(), zona), v.getFotoId(), condicion(v, zona));
        }).toList();
    }

    /** Bitácora del cultivo con la condición lunar de cada siembra vigente (HU-014, HU-015). */
    @Transactional(readOnly = true)
    public BitacoraVista bitacora(Long cultivoId) {
        Long huertoId = autorizacion.verificarLiderDeCultivo(cultivoId);
        ZoneId zona = zonaDe(huertoId);
        List<ActividadVersion> vigentes = versiones.vigentesDeCultivo(cultivoId);
        Map<Long, Actividad> porId = actividades.findAllById(vigentes.stream().map(ActividadVersion::getActividadId).toList())
                .stream().collect(Collectors.toMap(Actividad::getId, Function.identity()));
        Map<Long, String> autores = nombres(vigentes.stream().map(ActividadVersion::getAutorId).toList());
        List<SiembraVista> siembras = vigentes.stream()
                .map(v -> vista(porId.get(v.getActividadId()), v, zona, autores))
                .toList();
        return new BitacoraVista(cultivos.vista(cultivoId), zona.getId(), siembras);
    }

    private SiembraVista vista(Actividad a, ZoneId zona) {
        ActividadVersion v = versiones.vigente(a.getId());
        return vista(a, v, zona, nombres(List.of(v.getAutorId())));
    }

    private SiembraVista vista(Actividad a, ActividadVersion v, ZoneId zona, Map<Long, String> autores) {
        CondicionVista condicion = CondicionVista.de(condicion(v, zona));
        return new SiembraVista(a.getId(), a.getCultivoId(), v.getNumero(), v.getNumero() > 1, v.getFechaHecho(),
                local(v.getFechaHecho(), zona), a.getRegistradaEn(),
                autores.get(v.getAutorId()), v.getTecnica(), v.getTecnica().etiqueta(), v.getDescripcion(),
                "/api/fotos/" + v.getFotoId(), condicion);
    }

    /** RN-06 con la fecha de esta versión en la zona del huerto. */
    private CondicionLunar condicion(ActividadVersion v, ZoneId zona) {
        return reglaSiembra.evaluar(calculadoraFase.faseEn(v.getFechaHecho(), zona));
    }

    private static String local(Instant instante, ZoneId zona) {
        return FECHA_LOCAL.format(LocalDateTime.ofInstant(instante, zona));
    }

    private Map<Long, String> nombres(List<Long> ids) {
        return usuarios.findAllById(ids).stream().collect(Collectors.toMap(Usuario::getId,
                u -> u.getNombre() != null ? u.getNombre() : u.getUsuario(), (x, y) -> x));
    }

    private ZoneId zonaDe(Long huertoId) {
        return huertos.findById(huertoId).map(Huerto::zona).orElseThrow(() -> ErrorNegocio.de(Mensaje.MSG_19));
    }

    private static UUID clave(SiembraSolicitud s) {
        if (s == null || s.claveEnvio() == null || s.claveEnvio().isBlank()) {
            return UUID.randomUUID();
        }
        try {
            return UUID.fromString(s.claveEnvio().trim());
        } catch (IllegalArgumentException e) {
            throw new SolicitudInvalida("Clave de envío no válida");
        }
    }

    /** La fecha y hora se ingresan en la hora local del huerto (AD-10) y se guardan como instante en UTC. */
    private static Instant fechaHecho(String texto, ZoneId zona) {
        if (texto == null || texto.isBlank()) {
            throw ErrorNegocio.campoObligatorio("Fecha y hora", "fechaHecho");
        }
        try {
            return LocalDateTime.parse(texto.trim(), FECHA_LOCAL).atZone(zona).toInstant();
        } catch (DateTimeParseException e) {
            throw ErrorNegocio.campoObligatorio("Fecha y hora", "fechaHecho");
        }
    }

    private static String limpio(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}

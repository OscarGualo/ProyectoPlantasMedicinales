package ec.epn.huertos.actividades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.Instant;
import java.util.UUID;

/**
 * Versión de una actividad (RN-07). Solo se inserta: la base lo garantiza con un disparador y la entidad es
 * inmutable. La versión vigente es la de mayor número.
 */
@Entity
@Immutable
@Table(name = "actividad_version")
@Getter
@NoArgsConstructor
public class ActividadVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actividad_id", nullable = false)
    private Long actividadId;

    @Column(nullable = false)
    private int numero;

    /** Fecha y hora del hecho, distinta de la fecha de registro (HU-014 CA-01). */
    @Column(name = "fecha_hecho", nullable = false)
    private Instant fechaHecho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tecnica tecnica;

    private String descripcion;

    @Column(name = "foto_id", nullable = false)
    private Long fotoId;

    private String motivo;

    @Column(name = "clave_correccion")
    private UUID claveCorreccion;

    @Column(name = "huella_correccion", length = 64)
    private String huellaCorreccion;

    @Column(name = "autor_id", nullable = false)
    private Long autorId;

    @Column(name = "creada_en", nullable = false)
    private Instant creadaEn;

    static ActividadVersion primera(Long actividadId, Instant fechaHecho, Tecnica tecnica, String descripcion,
                                    Long fotoId, Long autorId, Instant ahora) {
        ActividadVersion v = new ActividadVersion();
        v.actividadId = actividadId;
        v.numero = 1;
        v.fechaHecho = fechaHecho;
        v.tecnica = tecnica;
        v.descripcion = descripcion;
        v.fotoId = fotoId;
        v.autorId = autorId;
        v.creadaEn = ahora;
        return v;
    }

    /**
     * Corrección (RN-07, HU-016): versión siguiente con la fecha y la foto indicadas y el motivo. Técnica y
     * descripción se conservan de la versión vigente; la anterior no se toca.
     */
    static ActividadVersion correccion(ActividadVersion vigente, Instant fechaHecho, Long fotoId, String motivo,
                                       UUID clave, String huella, Long autorId, Instant ahora) {
        ActividadVersion v = new ActividadVersion();
        v.actividadId = vigente.actividadId;
        v.numero = vigente.numero + 1;
        v.fechaHecho = fechaHecho;
        v.tecnica = vigente.tecnica;
        v.descripcion = vigente.descripcion;
        v.fotoId = fotoId;
        v.motivo = motivo;
        v.claveCorreccion = clave;
        v.huellaCorreccion = huella;
        v.autorId = autorId;
        v.creadaEn = ahora;
        return v;
    }
}

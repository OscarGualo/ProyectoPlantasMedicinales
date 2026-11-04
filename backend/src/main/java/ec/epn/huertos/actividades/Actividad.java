package ec.epn.huertos.actividades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Identidad estable de un hecho del ciclo de cultivo (RN-04). Sus datos viven en versiones de solo inserción
 * (RN-07, AD-08). En la versión 1 el único tipo es la siembra.
 */
@Entity
@Table(name = "actividad")
@Getter
@NoArgsConstructor
public class Actividad {

    public static final String SIEMBRA = "SIEMBRA";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cultivo_id", nullable = false, updatable = false)
    private Long cultivoId;

    @Column(nullable = false, updatable = false)
    private String tipo;

    /** Clave que la interfaz genera al abrir el formulario: un reenvío no crea otra siembra (HU-014 CA-02). */
    @Column(name = "clave_envio", nullable = false, updatable = false, unique = true)
    private UUID claveEnvio;

    @Column(name = "autor_id", nullable = false, updatable = false)
    private Long autorId;

    @Column(name = "registrada_en", nullable = false, updatable = false)
    private Instant registradaEn;

    static Actividad siembra(Long cultivoId, UUID claveEnvio, Long autorId, Instant ahora) {
        Actividad a = new Actividad();
        a.cultivoId = cultivoId;
        a.tipo = SIEMBRA;
        a.claveEnvio = claveEnvio;
        a.autorId = autorId;
        a.registradaEn = ahora;
        return a;
    }
}

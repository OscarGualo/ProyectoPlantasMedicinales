package ec.epn.huertos.fotos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** Fotografía guardada en PostgreSQL junto con la ficha o la actividad que la usa (AD-05). */
@Entity
@Table(name = "foto")
@Getter
@NoArgsConstructor
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] contenido;

    @Column(name = "tipo_mime", nullable = false)
    private String tipoMime;

    @Column(name = "tamano_bytes", nullable = false)
    private int tamanoBytes;

    @Column(nullable = false, length = 64)
    private String sha256;

    @Column(name = "creada_en", nullable = false)
    private Instant creadaEn;

    Foto(ImagenValidada imagen, Instant creadaEn) {
        this.contenido = imagen.contenido();
        this.tipoMime = imagen.tipoMime();
        this.tamanoBytes = imagen.contenido().length;
        this.sha256 = imagen.sha256();
        this.creadaEn = creadaEn;
    }
}

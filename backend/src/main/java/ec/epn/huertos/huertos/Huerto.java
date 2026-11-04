package ec.epn.huertos.huertos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZoneId;

@Entity
@Table(name = "huerto")
@Getter
@Setter
@NoArgsConstructor
public class Huerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comunidad_id")
    private Comunidad comunidad;

    @Column(name = "lider_id", nullable = false)
    private Long liderId;

    private String nombre;
    private String descripcion;

    // Ubicación (HU-003)
    private String provincia;
    private String parroquia;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;

    @Column(name = "zona_horaria", nullable = false)
    private String zonaHoraria = "America/Guayaquil";

    // Características (HU-004)
    @Column(name = "superficie_m2")
    private BigDecimal superficieM2;
    private String diseno;
    @Column(name = "tipo_cerca")
    private String tipoCerca;
    @Column(name = "tipo_suelo")
    private String tipoSuelo;
    @Column(name = "fuente_agua")
    private String fuenteAgua;
    @Column(name = "tecnica_riego")
    private String tecnicaRiego;
    @Column(name = "tipo_abono")
    private String tipoAbono;

    @Column(nullable = false)
    private boolean publicado;

    public boolean tieneCoordenadas() {
        return latitud != null && longitud != null;
    }

    public ZoneId zona() {
        return ZoneId.of(zonaHoraria);
    }
}

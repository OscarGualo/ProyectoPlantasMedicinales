package ec.epn.huertos.cultivos;

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

import java.time.Instant;

/** Plantación concreta de una planta en una zona del mismo huerto, con su propio estado (RN-03). */
@Entity
@Table(name = "cultivo")
@Getter
@NoArgsConstructor
public class Cultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "huerto_id", nullable = false, updatable = false)
    private Long huertoId;

    @Column(name = "planta_id", nullable = false, updatable = false)
    private Long plantaId;

    @Column(name = "zona_id", nullable = false, updatable = false)
    private Long zonaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCultivo estado;

    @Column(name = "registrado_en", nullable = false, updatable = false)
    private Instant registradoEn;

    @Column(name = "actualizado_en", nullable = false)
    private Instant actualizadoEn;

    Cultivo(Long huertoId, Long plantaId, Long zonaId, Instant ahora) {
        this.huertoId = huertoId;
        this.plantaId = plantaId;
        this.zonaId = zonaId;
        this.estado = EstadoCultivo.SEMBRADO;
        this.registradoEn = ahora;
        this.actualizadoEn = ahora;
    }

    void cambiarEstado(EstadoCultivo nuevo, Instant ahora) {
        this.estado = nuevo;
        this.actualizadoEn = ahora;
    }
}

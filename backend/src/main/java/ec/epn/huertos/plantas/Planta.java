package ec.epn.huertos.plantas;

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
import lombok.Setter;

/**
 * Ficha educativa de una especie medicinal dentro de un huerto (D-10). No tiene estado de crecimiento:
 * el estado pertenece a cada cultivo (RN-03).
 */
@Entity
@Table(name = "planta")
@Getter
@Setter
@NoArgsConstructor
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "huerto_id", nullable = false, updatable = false)
    private Long huertoId;

    @Column(name = "nombre_comun", nullable = false)
    private String nombreComun;

    @Column(name = "nombre_cientifico", nullable = false)
    private String nombreCientifico;

    @Column(nullable = false)
    private String familia;

    @Column(nullable = false)
    private String fuente;

    @Column(name = "otros_nombres")
    private String otrosNombres;

    private String descripcion;
    private String altitud;
    private String distribucion;
    private String ciclo;

    @Enumerated(EnumType.STRING)
    private Clasificacion clasificacion;

    private String usos;

    @Column(name = "foto_id")
    private Long fotoId;
}

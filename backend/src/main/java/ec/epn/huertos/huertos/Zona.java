package ec.epn.huertos.huertos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Parte identificable de la estructura del huerto. Sus zonas forman también la escena 3D (AD-04). En la
 * versión 1 las prepara el equipo a partir del croquis aprobado (D-01): la aplicación solo las lee.
 */
@Entity
@Table(name = "zona")
@Getter
@NoArgsConstructor
public class Zona {

    @Id
    private Long id;

    @Column(name = "huerto_id", nullable = false)
    private Long huertoId;

    @Column(nullable = false)
    private String codigo;

    private String nombre;

    /** CULTIVO, CAMINO, CERCA o BODEGA. */
    @Column(nullable = false)
    private String tipo;

    /** JSON {x, z, ancho, largo, alto} en metros, tal como está en la base. */
    @Column(nullable = false, columnDefinition = "jsonb", insertable = false, updatable = false)
    private String geometria;
}

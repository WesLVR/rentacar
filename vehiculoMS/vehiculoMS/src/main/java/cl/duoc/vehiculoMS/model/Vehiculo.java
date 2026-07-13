package cl.duoc.vehiculoMS.model;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String patente;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private Integer anio;
    @Column(nullable = false)
    private Double precioPorDia;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaVehiculo categoria;
}

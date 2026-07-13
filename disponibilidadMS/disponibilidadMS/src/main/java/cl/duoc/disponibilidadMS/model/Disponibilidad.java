package cl.duoc.disponibilidadMS.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disponibilidad")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaTermino;

    @Column(nullable = false)
    private Boolean disponible;

    @Column
    private String motivo; // Ej: "Reservado", "En mantención", "Disponible", "No Disponible"

package cl.duoc.mantenimientoMS.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaTermino;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double costo;

    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;

    @Column(name = "empleado_id", nullable = false)
    private Integer empleadoId;

    @ManyToOne
    @JoinColumn(name = "tipo_mantenimiento_id", nullable = false)
    private TipoMantenimiento tipoMantenimiento;
}

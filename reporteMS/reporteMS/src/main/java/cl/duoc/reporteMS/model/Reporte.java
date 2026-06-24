package cl.duoc.reporteMS.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    private LocalDate periodoInicio;

    @Column(nullable = false)
    private LocalDate periodoTermino;

    @Column(name = "reserva_id")
    private Integer reservaId;

    @Column(name = "vehiculo_id")
    private Integer vehiculoId;

    @ManyToOne
    @JoinColumn(name = "tipo_reporte_id", nullable = false)
    private TipoReporte tipoReporte;
}

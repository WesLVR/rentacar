package cl.duoc.entregaMS.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Integer kilometraje;

    @Column
    private String observaciones;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(name = "empleado_id", nullable = false)
    private Integer empleadoId;

    @ManyToOne
    @JoinColumn(name = "tipo_entrega_id", nullable = false)
    private TipoEntrega tipoEntrega;
}

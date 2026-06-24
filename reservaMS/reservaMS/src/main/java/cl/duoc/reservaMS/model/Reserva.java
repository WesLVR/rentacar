package cl.duoc.reservaMS.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaTermino;

    @Column(nullable = false)
    private Double costoTotal;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id", nullable = false)
    private EstadoReserva estadoReserva;
}

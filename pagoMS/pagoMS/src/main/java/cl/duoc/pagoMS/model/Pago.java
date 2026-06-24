package cl.duoc.pagoMS.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false)
    private Double monto;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoPago metodoPago;
}

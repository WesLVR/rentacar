package cl.duoc.pagoMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDetalleDTO {

    private Integer id;
    private LocalDate fechaPago;
    private Double monto;

    private MetodoPagoDTO metodoPago;

    private ReservaDTO reserva;
    private ClienteDTO cliente;
}

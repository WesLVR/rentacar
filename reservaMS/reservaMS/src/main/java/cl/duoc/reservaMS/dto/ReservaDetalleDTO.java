package cl.duoc.reservaMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDetalleDTO {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Double costoTotal;

    private EstadoReservaDTO estadoReserva;

    private ClienteDTO cliente;
    private VehiculoDTO vehiculo;
}

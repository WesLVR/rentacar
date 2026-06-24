package cl.duoc.entregaMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaDetalleDTO {

    private Integer id;
    private LocalDate fecha;
    private Integer kilometraje;
    private String observaciones;

    private TipoEntregaDTO tipoEntrega;

    private ReservaDTO reserva;
    private EmpleadoDTO empleado;
}

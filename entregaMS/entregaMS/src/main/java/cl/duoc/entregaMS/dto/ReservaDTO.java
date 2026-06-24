package cl.duoc.entregaMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Double costoTotal;
    private Integer clienteId;
    private Integer vehiculoId;
}

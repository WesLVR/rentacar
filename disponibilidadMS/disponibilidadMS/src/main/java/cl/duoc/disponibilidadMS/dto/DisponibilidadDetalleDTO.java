package cl.duoc.disponibilidadMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadDetalleDTO {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Boolean disponible;
    private String motivo;

    private VehiculoDTO vehiculo;
}

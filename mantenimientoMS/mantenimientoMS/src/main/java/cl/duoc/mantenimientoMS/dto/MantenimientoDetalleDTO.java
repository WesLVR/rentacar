package cl.duoc.mantenimientoMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDetalleDTO {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String descripcion;
    private Double costo;

    private TipoMantenimientoDTO tipoMantenimiento;

    private VehiculoDTO vehiculo;
    private EmpleadoDTO empleado;
}

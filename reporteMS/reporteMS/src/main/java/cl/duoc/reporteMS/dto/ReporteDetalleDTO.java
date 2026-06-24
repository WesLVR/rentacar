package cl.duoc.reporteMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDetalleDTO {

    private Integer id;
    private String titulo;
    private LocalDate fechaGeneracion;
    private LocalDate periodoInicio;
    private LocalDate periodoTermino;

    private TipoReporteDTO tipoReporte;

    private ReservaDTO reserva;
    private VehiculoDTO vehiculo;
    private PagoDTO pago;
}

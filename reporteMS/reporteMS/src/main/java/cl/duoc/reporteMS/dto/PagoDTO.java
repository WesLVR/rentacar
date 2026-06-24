package cl.duoc.reporteMS.dto;

import java.time.LocalDate;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PagoDTO {
    private Integer id;
    private LocalDate fechaPago;
    private Double monto;
    private Integer reservaId;
    private Integer clienteId;
}

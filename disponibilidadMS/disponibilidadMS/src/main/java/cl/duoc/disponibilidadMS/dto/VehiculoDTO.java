package cl.duoc.disponibilidadMS.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private String marca;
    private String modelo;
    private String categoria;
}

package cl.duoc.empleadoMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String cargo;
}

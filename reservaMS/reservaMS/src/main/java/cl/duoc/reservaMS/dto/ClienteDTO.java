package cl.duoc.reservaMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String tipoLicencia;
}

package cl.duoc.clienteMS.dto;

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

package cl.duoc.categoriaVehiculoMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Integer id;
    private String nombre;
    private Double tarifaBaseDiaria;
}

package cl.duoc.disponibilidadMS.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import cl.duoc.disponibilidadMS.dto.VehiculoDTO;

@FeignClient(name = "vehiculoMS")
public interface VehiculoClient {

    @GetMapping("/api/v1/vehiculos/dto/{id}")
    VehiculoDTO obtenerVehiculo(@PathVariable("id") Integer id);
}

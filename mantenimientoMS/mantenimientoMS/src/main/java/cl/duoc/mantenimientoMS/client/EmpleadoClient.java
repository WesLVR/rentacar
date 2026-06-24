package cl.duoc.mantenimientoMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.mantenimientoMS.dto.EmpleadoDTO;

@FeignClient(name = "empleadoMS")
public interface EmpleadoClient {

    @GetMapping("/api/v1/empleados/dto/{id}")
    EmpleadoDTO obtenerEmpleado(@PathVariable("id") Integer id);
}

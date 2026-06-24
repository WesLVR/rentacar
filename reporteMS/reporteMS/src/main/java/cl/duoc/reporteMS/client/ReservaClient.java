package cl.duoc.reporteMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reporteMS.dto.ReservaDTO;

@FeignClient(name = "reservaMS")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO obtenerReserva(@PathVariable("id") Integer id);
}

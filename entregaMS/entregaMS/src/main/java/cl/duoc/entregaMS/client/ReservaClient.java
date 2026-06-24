package cl.duoc.entregaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.entregaMS.dto.ReservaDTO;

@FeignClient(name = "reservaMS")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO obtenerReserva(@PathVariable("id") Integer id);
}

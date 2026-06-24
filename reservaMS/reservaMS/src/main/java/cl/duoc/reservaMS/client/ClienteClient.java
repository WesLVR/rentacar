package cl.duoc.reservaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reservaMS.dto.ClienteDTO;

@FeignClient(name = "clienteMS")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/dto/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Integer id);
}

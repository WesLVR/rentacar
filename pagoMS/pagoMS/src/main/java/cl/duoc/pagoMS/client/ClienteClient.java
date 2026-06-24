package cl.duoc.pagoMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.pagoMS.dto.ClienteDTO;

@FeignClient(name = "clienteMS")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/dto/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Integer id);
}

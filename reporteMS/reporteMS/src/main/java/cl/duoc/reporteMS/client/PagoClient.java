package cl.duoc.reporteMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reporteMS.dto.PagoDTO;

import java.util.List;

@FeignClient(name = "pagoMS")
public interface PagoClient {

    @GetMapping("/api/v1/pagos/{id}")
    PagoDTO obtenerPago(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/pagos/reserva/{reservaId}")
    List<PagoDTO> obtenerPagosPorReserva(@PathVariable("reservaId") Integer reservaId);
}

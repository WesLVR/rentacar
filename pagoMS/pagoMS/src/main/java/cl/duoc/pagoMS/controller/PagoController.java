package cl.duoc.pagoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.pagoMS.dto.PagoDetalleDTO;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones sobre los pagos de reservas")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID")
    public ResponseEntity<Pago> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle del pago con datos de reserva y cliente vía Feign")
    public ResponseEntity<PagoDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Buscar pagos por reserva")
    public ResponseEntity<List<Pago>> buscarPorReserva(@PathVariable Integer reservaId) {
        List<Pago> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar pagos por cliente")
    public ResponseEntity<List<Pago>> buscarPorCliente(@PathVariable Integer clienteId) {
        List<Pago> lista = service.buscarPorCliente(clienteId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago")
    public ResponseEntity<Pago> guardar(@RequestBody Pago pago) {
        return ResponseEntity.ok(service.guardar(pago));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago existente")
    public ResponseEntity<Pago> actualizar(
            @PathVariable Integer id, @RequestBody Pago pago) {
        try {
            return ResponseEntity.ok(service.actualizar(id, pago));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

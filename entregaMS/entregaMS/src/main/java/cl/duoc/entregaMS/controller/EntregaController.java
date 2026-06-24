package cl.duoc.entregaMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.entregaMS.dto.EntregaDetalleDTO;
import cl.duoc.entregaMS.model.Entrega;
import cl.duoc.entregaMS.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/entregas")
@Tag(name = "Entregas", description = "Operaciones sobre entregas y devoluciones de vehículos")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @GetMapping
    @Operation(summary = "Listar todas las entregas")
    public ResponseEntity<List<Entrega>> listar() {
        List<Entrega> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrega por ID")
    public ResponseEntity<Entrega> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle con datos de reserva y empleado vía Feign")
    public ResponseEntity<EntregaDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Buscar entregas por reserva")
    public ResponseEntity<List<Entrega>> buscarPorReserva(@PathVariable Integer reservaId) {
        List<Entrega> lista = service.buscarPorReserva(reservaId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empleado/{empleadoId}")
    @Operation(summary = "Buscar entregas por empleado")
    public ResponseEntity<List<Entrega>> buscarPorEmpleado(@PathVariable Integer empleadoId) {
        List<Entrega> lista = service.buscarPorEmpleado(empleadoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva entrega")
    public ResponseEntity<Entrega> guardar(@RequestBody Entrega entrega) {
        return ResponseEntity.ok(service.guardar(entrega));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una entrega existente")
    public ResponseEntity<Entrega> actualizar(
            @PathVariable Integer id, @RequestBody Entrega entrega) {
        try {
            return ResponseEntity.ok(service.actualizar(id, entrega));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una entrega")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

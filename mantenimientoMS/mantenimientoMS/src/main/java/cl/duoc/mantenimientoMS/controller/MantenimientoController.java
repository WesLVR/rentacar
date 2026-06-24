package cl.duoc.mantenimientoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.mantenimientoMS.dto.MantenimientoDetalleDTO;
import cl.duoc.mantenimientoMS.model.Mantenimiento;
import cl.duoc.mantenimientoMS.service.MantenimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/mantenimientos")
@Tag(name = "Mantenimientos", description = "Operaciones sobre las mantenciones de vehículos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService service;

    @GetMapping
    @Operation(summary = "Listar todos los mantenimientos")
    public ResponseEntity<List<Mantenimiento>> listar() {
        List<Mantenimiento> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mantenimiento por ID")
    public ResponseEntity<Mantenimiento> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle con datos de vehículo y empleado vía Feign")
    public ResponseEntity<MantenimientoDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    @Operation(summary = "Buscar mantenimientos por vehículo")
    public ResponseEntity<List<Mantenimiento>> buscarPorVehiculo(@PathVariable Integer vehiculoId) {
        List<Mantenimiento> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empleado/{empleadoId}")
    @Operation(summary = "Buscar mantenimientos por empleado")
    public ResponseEntity<List<Mantenimiento>> buscarPorEmpleado(@PathVariable Integer empleadoId) {
        List<Mantenimiento> lista = service.buscarPorEmpleado(empleadoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo mantenimiento")
    public ResponseEntity<Mantenimiento> guardar(@RequestBody Mantenimiento mantenimiento) {
        return ResponseEntity.ok(service.guardar(mantenimiento));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un mantenimiento existente")
    public ResponseEntity<Mantenimiento> actualizar(
            @PathVariable Integer id, @RequestBody Mantenimiento mantenimiento) {
        try {
            return ResponseEntity.ok(service.actualizar(id, mantenimiento));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un mantenimiento")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

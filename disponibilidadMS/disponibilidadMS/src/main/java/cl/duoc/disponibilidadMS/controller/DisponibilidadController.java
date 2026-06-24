package cl.duoc.disponibilidadMS.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.disponibilidadMS.dto.DisponibilidadDetalleDTO;
import cl.duoc.disponibilidadMS.model.Disponibilidad;
import cl.duoc.disponibilidadMS.service.DisponibilidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/disponibilidad")
@Tag(name = "Disponibilidad", description = "Operaciones sobre la disponibilidad de vehículos")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService service;

    @GetMapping
    @Operation(summary = "Listar todos los registros")
    public ResponseEntity<List<Disponibilidad>> listar() {
        List<Disponibilidad> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro por ID")
    public ResponseEntity<Disponibilidad> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle con datos del vehículo vía Feign")
    public ResponseEntity<DisponibilidadDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    @Operation(summary = "Buscar disponibilidad por vehículo")
    public ResponseEntity<List<Disponibilidad>> buscarPorVehiculo(@PathVariable Integer vehiculoId) {
        List<Disponibilidad> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/libres")
    @Operation(summary = "Listar vehículos disponibles")
    public ResponseEntity<List<Disponibilidad>> listarDisponibles() {
        List<Disponibilidad> lista = service.listarDisponibles();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/verificar/{vehiculoId}")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable Integer vehiculoId,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate termino) {
        boolean disponible = service.estaDisponible(vehiculoId, inicio, termino);
        return ResponseEntity.ok(disponible);
    }

    @PostMapping
    @Operation(summary = "Registrar nueva disponibilidad")
    public ResponseEntity<Disponibilidad> guardar(@RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(service.guardar(disponibilidad));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un registro de disponibilidad")
    public ResponseEntity<Disponibilidad> actualizar(
            @PathVariable Integer id,
            @RequestBody Disponibilidad disponibilidad) {
        try {
            return ResponseEntity.ok(service.actualizar(id, disponibilidad));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de disponibilidad")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

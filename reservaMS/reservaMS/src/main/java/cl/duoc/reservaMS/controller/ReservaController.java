package cl.duoc.reservaMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reservaMS.dto.ReservaDetalleDTO;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas con cálculo automático de costo y consumo de otros microservicios")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @Operation(summary = "Listar todas las reservas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay reservas registradas")
    })
    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        List<Reserva> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener detalle completo de la reserva",
            description = "Devuelve la reserva junto con los datos del cliente (clienteMS) y del vehículo (vehiculoMS) obtenidos vía Feign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle completo armado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada o microservicio no disponible")
    })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<ReservaDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            ReservaDetalleDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar reservas por cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> buscarPorCliente(@PathVariable Integer clienteId) {
        List<Reserva> lista = service.buscarPorCliente(clienteId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reservas por vehículo")
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<List<Reserva>> buscarPorVehiculo(@PathVariable Integer vehiculoId) {
        List<Reserva> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reservas por estado")
    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<Reserva>> buscarPorEstado(@PathVariable Integer estadoId) {
        List<Reserva> lista = service.buscarPorEstado(estadoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Crear una nueva reserva",
            description = "El costo total se calcula automáticamente según el precio por día del vehículo y la cantidad de días")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva creada con costo calculado")
    })
    @PostMapping
    public ResponseEntity<Reserva> guardar(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(service.guardar(reserva));
    }

    @Operation(summary = "Actualizar una reserva existente")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(
            @PathVariable Integer id,
            @RequestBody Reserva reserva) {
        try {
            return ResponseEntity.ok(service.actualizar(id, reserva));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

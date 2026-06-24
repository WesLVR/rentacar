package cl.duoc.vehiculoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.vehiculoMS.dto.VehiculoDTO;
import cl.duoc.vehiculoMS.model.Vehiculo;
import cl.duoc.vehiculoMS.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehículos", description = "Operaciones CRUD sobre los vehículos de la flota")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @Operation(summary = "Listar todos los vehículos",
            description = "Devuelve la lista completa de vehículos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vehículos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay vehículos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        List<Vehiculo> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar vehículo por ID",
            description = "Devuelve un vehículo específico según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar vehículo por patente")
    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(@PathVariable String patente) {
        try {
            return ResponseEntity.ok(service.buscarPorPatente(patente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar vehículos por categoría")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Vehiculo>> buscarPorCategoria(@PathVariable Integer categoriaId) {
        List<Vehiculo> lista = service.buscarPorCategoria(categoriaId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener DTO del vehículo",
            description = "Devuelve un DTO reducido usado por otros microservicios vía Feign")
    @GetMapping("/dto/{id}")
    public ResponseEntity<VehiculoDTO> obtenerDTO(@PathVariable Integer id) {
        Vehiculo v = service.buscarPorId(id);
        VehiculoDTO dto = new VehiculoDTO(
            v.getId(),
            v.getPatente(),
            v.getMarca(),
            v.getModelo(),
            v.getCategoria().getNombre(),
            v.getPrecioPorDia()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Crear un nuevo vehículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(service.guardar(vehiculo));
    }

    @Operation(summary = "Actualizar un vehículo existente")
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(
            @PathVariable Integer id,
            @RequestBody Vehiculo vehiculo) {
        try {
            return ResponseEntity.ok(service.actualizar(id, vehiculo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un vehículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
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

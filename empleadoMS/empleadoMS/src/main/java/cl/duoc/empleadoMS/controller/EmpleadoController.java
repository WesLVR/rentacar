package cl.duoc.empleadoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.empleadoMS.dto.EmpleadoDTO;
import cl.duoc.empleadoMS.model.Empleado;
import cl.duoc.empleadoMS.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/empleados")
@Tag(name = "Empleados", description = "Operaciones CRUD sobre los empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    public ResponseEntity<List<Empleado>> listar() {
        List<Empleado> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empleado por ID")
    public ResponseEntity<Empleado> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar empleado por RUT")
    public ResponseEntity<Empleado> buscarPorRut(@PathVariable String rut) {
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cargo/{cargoId}")
    @Operation(summary = "Buscar empleados por cargo")
    public ResponseEntity<List<Empleado>> buscarPorCargo(@PathVariable Integer cargoId) {
        List<Empleado> lista = service.buscarPorCargo(cargoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO del empleado para otros microservicios")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoDTO(@PathVariable Integer id) {
        Empleado empleado = service.buscarPorId(id);
        EmpleadoDTO dto = new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getCargo().getNombre()
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo empleado")
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado empleado) {
        return ResponseEntity.ok(service.guardar(empleado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado existente")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Integer id,
            @RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok(service.actualizar(id, empleado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

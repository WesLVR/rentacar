package cl.duoc.reporteMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reporteMS.dto.ReporteDetalleDTO;
import cl.duoc.reporteMS.model.Reporte;
import cl.duoc.reporteMS.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Operaciones sobre los reportes del sistema")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    @Operation(summary = "Listar todos los reportes")
    public ResponseEntity<List<Reporte>> listar() {
        List<Reporte> lista = service.listar();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reporte por ID")
    public ResponseEntity<Reporte> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle con datos de reserva, vehículo y pago vía Feign")
    public ResponseEntity<ReporteDetalleDTO> detalle(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipoReporteId}")
    @Operation(summary = "Buscar reportes por tipo")
    public ResponseEntity<List<Reporte>> buscarPorTipo(@PathVariable Integer tipoReporteId) {
        List<Reporte> lista = service.buscarPorTipo(tipoReporteId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    @Operation(summary = "Buscar reportes por vehículo")
    public ResponseEntity<List<Reporte>> buscarPorVehiculo(@PathVariable Integer vehiculoId) {
        List<Reporte> lista = service.buscarPorVehiculo(vehiculoId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Generar un nuevo reporte")
    public ResponseEntity<Reporte> guardar(@RequestBody Reporte reporte) {
        return ResponseEntity.ok(service.guardar(reporte));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un reporte existente")
    public ResponseEntity<Reporte> actualizar(
            @PathVariable Integer id, @RequestBody Reporte reporte) {
        try {
            return ResponseEntity.ok(service.actualizar(id, reporte));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

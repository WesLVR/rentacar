package cl.duoc.categoriaVehiculoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.categoriaVehiculoMS.dto.CategoriaDTO;
import cl.duoc.categoriaVehiculoMS.model.Categoria;
import cl.duoc.categoriaVehiculoMS.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "Operaciones CRUD sobre las categorías de vehículo")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoría por ID")
    public ResponseEntity<Categoria> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO de la categoría para otros microservicios")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaDTO(@PathVariable Integer id) {
        Categoria c = service.buscarPorId(id);
        CategoriaDTO dto = new CategoriaDTO(
                c.getId(),
                c.getNombre(),
                c.getTarifaBaseDiaria()
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoría")
    public ResponseEntity<Categoria> guardar(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(service.guardar(categoria));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría existente")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Integer id,
            @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(service.actualizar(id, categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

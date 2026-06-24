package cl.duoc.clienteMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.clienteMS.dto.ClienteDTO;
import cl.duoc.clienteMS.model.Cliente;
import cl.duoc.clienteMS.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD sobre los clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar cliente por RUT")
    public ResponseEntity<Cliente> buscarPorRut(@PathVariable String rut) {
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO del cliente para otros microservicios")
    public ResponseEntity<ClienteDTO> obtenerClienteDTO(@PathVariable Integer id) {
        Cliente cliente = service.buscarPorId(id);
        ClienteDTO dto = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTipoLicencia().getNombre()
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.guardar(cliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente existente")
    public ResponseEntity<Cliente> actualizar(
            @PathVariable Integer id,
            @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.actualizar(id, cliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package cl.duoc.clienteMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.clienteMS.model.Cliente;
import cl.duoc.clienteMS.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Cliente buscarPorRut(String rut) {
        return clienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Cliente guardar(Cliente cliente) {
        

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Integer id, Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setRut(clienteActualizado.getRut());
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setEmail(clienteActualizado.getEmail());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setTipoLicencia(clienteActualizado.getTipoLicencia());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no existe");
        }
        clienteRepository.deleteById(id);
    }
}

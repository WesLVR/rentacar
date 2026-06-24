package cl.duoc.empleadoMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.empleadoMS.model.Empleado;
import cl.duoc.empleadoMS.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarPorId(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public Empleado buscarPorRut(String rut) {
        return empleadoRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public List<Empleado> buscarPorCargo(Integer cargoId) {
        return empleadoRepository.findByCargoId(cargoId);
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Integer id, Empleado empleadoActualizado) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setRut(empleadoActualizado.getRut());
        empleado.setNombre(empleadoActualizado.getNombre());
        empleado.setApellido(empleadoActualizado.getApellido());
        empleado.setEmail(empleadoActualizado.getEmail());
        empleado.setTelefono(empleadoActualizado.getTelefono());
        empleado.setCargo(empleadoActualizado.getCargo());

        return empleadoRepository.save(empleado);
    }

    public void eliminar(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no existe");
        }
        empleadoRepository.deleteById(id);
    }
}

package cl.duoc.vehiculoMS.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.vehiculoMS.model.Vehiculo;
import cl.duoc.vehiculoMS.repository.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo buscarPorId(Integer id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    public Vehiculo buscarPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    public List<Vehiculo> buscarPorCategoria(Integer categoriaId) {
        return vehiculoRepository.findByCategoriaId(categoriaId);
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo actualizar(Integer id, Vehiculo vehiculoActualizado) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculo.setPatente(vehiculoActualizado.getPatente());
        vehiculo.setMarca(vehiculoActualizado.getMarca());
        vehiculo.setModelo(vehiculoActualizado.getModelo());
        vehiculo.setAnio(vehiculoActualizado.getAnio());
        vehiculo.setPrecioPorDia(vehiculoActualizado.getPrecioPorDia());
        vehiculo.setCategoria(vehiculoActualizado.getCategoria());
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no existe");
        }
        vehiculoRepository.deleteById(id);
    }
}

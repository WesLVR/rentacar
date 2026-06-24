package cl.duoc.vehiculoMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.vehiculoMS.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Optional<Vehiculo> findByPatente(String patente);

    List<Vehiculo> findByCategoriaId(Integer categoriaId);
}

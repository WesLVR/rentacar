package cl.duoc.vehiculoMS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.vehiculoMS.model.CategoriaVehiculo;

@Repository
public interface CategoriaVehiculoRepository extends JpaRepository<CategoriaVehiculo, Integer> {
}

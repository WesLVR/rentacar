package cl.duoc.disponibilidadMS.repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.disponibilidadMS.model.Disponibilidad;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {

    List<Disponibilidad> findByVehiculoId(Integer vehiculoId);

    List<Disponibilidad> findByDisponibleTrue();

    List<Disponibilidad> findByVehiculoIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
            Integer vehiculoId, LocalDate fechaTermino, LocalDate fechaInicio);
}

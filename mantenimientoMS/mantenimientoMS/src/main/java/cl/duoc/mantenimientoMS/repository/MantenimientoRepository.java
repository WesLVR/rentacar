package cl.duoc.mantenimientoMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.mantenimientoMS.model.Mantenimiento;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {

    List<Mantenimiento> findByVehiculoId(Integer vehiculoId);

    List<Mantenimiento> findByEmpleadoId(Integer empleadoId);

    List<Mantenimiento> findByTipoMantenimientoId(Integer tipoId);
}

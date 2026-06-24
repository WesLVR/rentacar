package cl.duoc.mantenimientoMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.mantenimientoMS.model.TipoMantenimiento;

@Repository
public interface TipoMantenimientoRepository extends JpaRepository<TipoMantenimiento, Integer> {
}

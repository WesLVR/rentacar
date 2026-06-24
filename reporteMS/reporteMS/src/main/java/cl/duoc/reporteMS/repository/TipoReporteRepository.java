package cl.duoc.reporteMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.reporteMS.model.TipoReporte;

@Repository
public interface TipoReporteRepository extends JpaRepository<TipoReporte, Integer> {
}

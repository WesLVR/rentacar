package cl.duoc.reporteMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.reporteMS.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByTipoReporteId(Integer tipoReporteId);

    List<Reporte> findByVehiculoId(Integer vehiculoId);

    List<Reporte> findByReservaId(Integer reservaId);
}

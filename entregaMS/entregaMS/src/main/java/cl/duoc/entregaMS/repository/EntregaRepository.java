package cl.duoc.entregaMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.entregaMS.model.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {

    List<Entrega> findByReservaId(Integer reservaId);

    List<Entrega> findByEmpleadoId(Integer empleadoId);

    List<Entrega> findByTipoEntregaId(Integer tipoEntregaId);
}

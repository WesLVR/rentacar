package cl.duoc.entregaMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.entregaMS.model.TipoEntrega;

@Repository
public interface TipoEntregaRepository extends JpaRepository<TipoEntrega, Integer> {
}

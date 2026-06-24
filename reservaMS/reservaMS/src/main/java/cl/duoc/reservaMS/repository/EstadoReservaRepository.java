package cl.duoc.reservaMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.reservaMS.model.EstadoReserva;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Integer> {
}

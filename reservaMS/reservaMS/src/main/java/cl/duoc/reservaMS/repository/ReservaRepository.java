package cl.duoc.reservaMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.reservaMS.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByClienteId(Integer clienteId);

    List<Reserva> findByVehiculoId(Integer vehiculoId);

    List<Reserva> findByEstadoReservaId(Integer estadoId);
}

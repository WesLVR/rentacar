package cl.duoc.empleadoMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.empleadoMS.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}

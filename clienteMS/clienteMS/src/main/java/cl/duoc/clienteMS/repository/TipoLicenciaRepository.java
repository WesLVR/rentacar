package cl.duoc.clienteMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.clienteMS.model.TipoLicencia;

@Repository
public interface TipoLicenciaRepository extends JpaRepository<TipoLicencia, Integer> {
}

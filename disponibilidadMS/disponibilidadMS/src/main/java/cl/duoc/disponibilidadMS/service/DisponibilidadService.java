package cl.duoc.disponibilidadMS.service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.disponibilidadMS.client.VehiculoClient;
import cl.duoc.disponibilidadMS.dto.DisponibilidadDetalleDTO;
import cl.duoc.disponibilidadMS.dto.VehiculoDTO;
import cl.duoc.disponibilidadMS.model.Disponibilidad;
import cl.duoc.disponibilidadMS.repository.DisponibilidadRepository;

@Service
public class DisponibilidadService {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private VehiculoClient vehiculoClient;

    public List<Disponibilidad> listar() {
        return disponibilidadRepository.findAll();
    }

    public Disponibilidad buscarPorId(Integer id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de disponibilidad no encontrado"));
    }

    public List<Disponibilidad> buscarPorVehiculo(Integer vehiculoId) {
        return disponibilidadRepository.findByVehiculoId(vehiculoId);
    }

    public List<Disponibilidad> listarDisponibles() {
        return disponibilidadRepository.findByDisponibleTrue();
    }

    public boolean estaDisponible(Integer vehiculoId, LocalDate inicio, LocalDate termino) {
        List<Disponibilidad> conflictos = disponibilidadRepository
                .findByVehiculoIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
                        vehiculoId, termino, inicio);
        return conflictos.stream().allMatch(Disponibilidad::getDisponible);
    }

    public Disponibilidad guardar(Disponibilidad disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    public Disponibilidad actualizar(Integer id, Disponibilidad actualizada) {
        Disponibilidad d = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        d.setVehiculoId(actualizada.getVehiculoId());
        d.setFechaInicio(actualizada.getFechaInicio());
        d.setFechaTermino(actualizada.getFechaTermino());
        d.setDisponible(actualizada.getDisponible());
        d.setMotivo(actualizada.getMotivo());

        return disponibilidadRepository.save(d);
    }

    public void eliminar(Integer id) {
        if (!disponibilidadRepository.existsById(id)) {
            throw new RuntimeException("Registro no existe");
        }
        disponibilidadRepository.deleteById(id);
    }

    public DisponibilidadDetalleDTO obtenerDetalle(Integer id) {
        Disponibilidad d = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(d.getVehiculoId());

        DisponibilidadDetalleDTO dto = new DisponibilidadDetalleDTO();
        dto.setId(d.getId());
        dto.setFechaInicio(d.getFechaInicio());
        dto.setFechaTermino(d.getFechaTermino());
        dto.setDisponible(d.getDisponible());
        dto.setMotivo(d.getMotivo());
        dto.setVehiculo(vehiculo);

        return dto;
    }
}

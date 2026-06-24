package cl.duoc.entregaMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.entregaMS.client.EmpleadoClient;
import cl.duoc.entregaMS.client.ReservaClient;
import cl.duoc.entregaMS.dto.*;
import cl.duoc.entregaMS.model.Entrega;
import cl.duoc.entregaMS.model.TipoEntrega;
import cl.duoc.entregaMS.repository.EntregaRepository;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private EmpleadoClient empleadoClient;

    public List<Entrega> listar() {
        return entregaRepository.findAll();
    }

    public Entrega buscarPorId(Integer id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));
    }

    public List<Entrega> buscarPorReserva(Integer reservaId) {
        return entregaRepository.findByReservaId(reservaId);
    }

    public List<Entrega> buscarPorEmpleado(Integer empleadoId) {
        return entregaRepository.findByEmpleadoId(empleadoId);
    }

    public Entrega guardar(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    public Entrega actualizar(Integer id, Entrega actualizada) {
        Entrega e = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        e.setFecha(actualizada.getFecha());
        e.setKilometraje(actualizada.getKilometraje());
        e.setObservaciones(actualizada.getObservaciones());
        e.setReservaId(actualizada.getReservaId());
        e.setEmpleadoId(actualizada.getEmpleadoId());
        e.setTipoEntrega(actualizada.getTipoEntrega());

        return entregaRepository.save(e);
    }

    public void eliminar(Integer id) {
        if (!entregaRepository.existsById(id)) {
            throw new RuntimeException("Entrega no existe");
        }
        entregaRepository.deleteById(id);
    }

    public EntregaDetalleDTO obtenerDetalle(Integer id) {
        Entrega e = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        ReservaDTO reserva   = reservaClient.obtenerReserva(e.getReservaId());
        EmpleadoDTO empleado = empleadoClient.obtenerEmpleado(e.getEmpleadoId());

        TipoEntrega tipo    = e.getTipoEntrega();
        TipoEntregaDTO tipoDTO = new TipoEntregaDTO(tipo.getId(), tipo.getNombre());

        EntregaDetalleDTO dto = new EntregaDetalleDTO();
        dto.setId(e.getId());
        dto.setFecha(e.getFecha());
        dto.setKilometraje(e.getKilometraje());
        dto.setObservaciones(e.getObservaciones());
        dto.setTipoEntrega(tipoDTO);
        dto.setReserva(reserva);
        dto.setEmpleado(empleado);

        return dto;
    }
}

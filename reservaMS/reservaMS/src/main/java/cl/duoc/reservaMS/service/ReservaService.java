package cl.duoc.reservaMS.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.reservaMS.client.ClienteClient;
import cl.duoc.reservaMS.client.VehiculoClient;
import cl.duoc.reservaMS.dto.*;
import cl.duoc.reservaMS.model.EstadoReserva;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private VehiculoClient vehiculoClient;

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public List<Reserva> buscarPorCliente(Integer clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    public List<Reserva> buscarPorVehiculo(Integer vehiculoId) {
        return reservaRepository.findByVehiculoId(vehiculoId);
    }

    public List<Reserva> buscarPorEstado(Integer estadoId) {
        return reservaRepository.findByEstadoReservaId(estadoId);
    }


    public Reserva guardar(Reserva reserva) {

    VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(reserva.getVehiculoId());
    long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaTermino());
    reserva.setCostoTotal(vehiculo.getPrecioPorDia() * dias);
    return reservaRepository.save(reserva);

    }

    public Reserva guardarValidado(Reserva reserva) {

        ClienteDTO cliente = clienteClient.obtenerCliente(reserva.getClienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente no existe en clienteMS");
        }

        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(reserva.getVehiculoId());
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no existe en vehiculoMS");
        }

        return reservaRepository.save(reserva);
    }

    public Reserva actualizar(Integer id, Reserva reservaActualizada) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setFechaInicio(reservaActualizada.getFechaInicio());
        reserva.setFechaTermino(reservaActualizada.getFechaTermino());
        reserva.setCostoTotal(reservaActualizada.getCostoTotal());
        reserva.setClienteId(reservaActualizada.getClienteId());
        reserva.setVehiculoId(reservaActualizada.getVehiculoId());
        reserva.setEstadoReserva(reservaActualizada.getEstadoReserva());

        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(reservaActualizada.getVehiculoId());
        long dias = ChronoUnit.DAYS.between(reservaActualizada.getFechaInicio(), reservaActualizada.getFechaTermino());
        reserva.setCostoTotal(vehiculo.getPrecioPorDia() * dias);

        return reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no existe");
        }
        reservaRepository.deleteById(id);
    }

    public ReservaDetalleDTO obtenerDetalle(Integer id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        ClienteDTO cliente = clienteClient.obtenerCliente(reserva.getClienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado en clienteMS");
        }

        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculo(reserva.getVehiculoId());
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado en vehiculoMS");
        }

        EstadoReserva estado = reserva.getEstadoReserva();
        EstadoReservaDTO estadoDTO = new EstadoReservaDTO(
                estado.getId(),
                estado.getNombre()
        );

        ReservaDetalleDTO dto = new ReservaDetalleDTO();
        dto.setId(reserva.getId());
        dto.setFechaInicio(reserva.getFechaInicio());
        dto.setFechaTermino(reserva.getFechaTermino());
        dto.setCostoTotal(reserva.getCostoTotal());
        dto.setEstadoReserva(estadoDTO);
        dto.setCliente(cliente);
        dto.setVehiculo(vehiculo);

        return dto;
    }
}

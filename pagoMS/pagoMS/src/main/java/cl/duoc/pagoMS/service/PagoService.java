package cl.duoc.pagoMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.pagoMS.client.ClienteClient;
import cl.duoc.pagoMS.client.ReservaClient;
import cl.duoc.pagoMS.dto.*;
import cl.duoc.pagoMS.model.MetodoPago;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private ClienteClient clienteClient;

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public List<Pago> buscarPorReserva(Integer reservaId) {
        return pagoRepository.findByReservaId(reservaId);
    }

    public List<Pago> buscarPorCliente(Integer clienteId) {
        return pagoRepository.findByClienteId(clienteId);
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Pago actualizar(Integer id, Pago actualizado) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        p.setFechaPago(actualizado.getFechaPago());
        p.setMonto(actualizado.getMonto());
        p.setReservaId(actualizado.getReservaId());
        p.setClienteId(actualizado.getClienteId());
        p.setMetodoPago(actualizado.getMetodoPago());

        return pagoRepository.save(p);
    }

    public void eliminar(Integer id) {
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("Pago no existe");
        }
        pagoRepository.deleteById(id);
    }

    public PagoDetalleDTO obtenerDetalle(Integer id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        ReservaDTO reserva  = reservaClient.obtenerReserva(p.getReservaId());
        ClienteDTO cliente  = clienteClient.obtenerCliente(p.getClienteId());

        MetodoPago metodo   = p.getMetodoPago();
        MetodoPagoDTO metodoDTO = new MetodoPagoDTO(metodo.getId(), metodo.getNombre());

        PagoDetalleDTO dto = new PagoDetalleDTO();
        dto.setId(p.getId());
        dto.setFechaPago(p.getFechaPago());
        dto.setMonto(p.getMonto());
        dto.setMetodoPago(metodoDTO);
        dto.setReserva(reserva);
        dto.setCliente(cliente);

        return dto;
    }
}

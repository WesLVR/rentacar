package cl.duoc.reporteMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.reporteMS.client.PagoClient;
import cl.duoc.reporteMS.client.ReservaClient;
import cl.duoc.reporteMS.client.VehiculoClient;
import cl.duoc.reporteMS.dto.*;
import cl.duoc.reporteMS.model.Reporte;
import cl.duoc.reporteMS.model.TipoReporte;
import cl.duoc.reporteMS.repository.ReporteRepository;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private VehiculoClient vehiculoClient;

    @Autowired
    private PagoClient pagoClient;

    public List<Reporte> listar() {
        return reporteRepository.findAll();
    }

    public Reporte buscarPorId(Integer id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    public List<Reporte> buscarPorTipo(Integer tipoReporteId) {
        return reporteRepository.findByTipoReporteId(tipoReporteId);
    }

    public List<Reporte> buscarPorVehiculo(Integer vehiculoId) {
        return reporteRepository.findByVehiculoId(vehiculoId);
    }

    public Reporte guardar(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Reporte actualizar(Integer id, Reporte actualizado) {
        Reporte r = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        r.setTitulo(actualizado.getTitulo());
        r.setFechaGeneracion(actualizado.getFechaGeneracion());
        r.setPeriodoInicio(actualizado.getPeriodoInicio());
        r.setPeriodoTermino(actualizado.getPeriodoTermino());
        r.setReservaId(actualizado.getReservaId());
        r.setVehiculoId(actualizado.getVehiculoId());
        r.setTipoReporte(actualizado.getTipoReporte());

        return reporteRepository.save(r);
    }

    public void eliminar(Integer id) {
        if (!reporteRepository.existsById(id)) {
            throw new RuntimeException("Reporte no existe");
        }
        reporteRepository.deleteById(id);
    }

    public ReporteDetalleDTO obtenerDetalle(Integer id) {
        Reporte r = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        ReservaDTO reserva = (r.getReservaId() != null)
                ? reservaClient.obtenerReserva(r.getReservaId())
                : null;

        VehiculoDTO vehiculo = (r.getVehiculoId() != null)
                ? vehiculoClient.obtenerVehiculo(r.getVehiculoId())
                : null;

        PagoDTO pago = null;
        if (r.getReservaId() != null) {
            List<PagoDTO> pagos = pagoClient.obtenerPagosPorReserva(r.getReservaId());
            if (pagos != null && !pagos.isEmpty()) {
                pago = pagos.get(0);
            }
        }

        TipoReporte tipo    = r.getTipoReporte();
        TipoReporteDTO tipoDTO = new TipoReporteDTO(tipo.getId(), tipo.getNombre());

        ReporteDetalleDTO dto = new ReporteDetalleDTO();
        dto.setId(r.getId());
        dto.setTitulo(r.getTitulo());
        dto.setFechaGeneracion(r.getFechaGeneracion());
        dto.setPeriodoInicio(r.getPeriodoInicio());
        dto.setPeriodoTermino(r.getPeriodoTermino());
        dto.setTipoReporte(tipoDTO);
        dto.setReserva(reserva);
        dto.setVehiculo(vehiculo);
        dto.setPago(pago);

        return dto;
    }
}

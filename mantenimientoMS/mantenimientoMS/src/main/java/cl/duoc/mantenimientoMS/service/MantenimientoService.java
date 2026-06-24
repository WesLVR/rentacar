package cl.duoc.mantenimientoMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.mantenimientoMS.client.EmpleadoClient;
import cl.duoc.mantenimientoMS.client.VehiculoClient;
import cl.duoc.mantenimientoMS.dto.*;
import cl.duoc.mantenimientoMS.model.Mantenimiento;
import cl.duoc.mantenimientoMS.model.TipoMantenimiento;
import cl.duoc.mantenimientoMS.repository.MantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private VehiculoClient vehiculoClient;

    @Autowired
    private EmpleadoClient empleadoClient;

    public List<Mantenimiento> listar() {
        return mantenimientoRepository.findAll();
    }

    public Mantenimiento buscarPorId(Integer id) {
        return mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
    }

    public List<Mantenimiento> buscarPorVehiculo(Integer vehiculoId) {
        return mantenimientoRepository.findByVehiculoId(vehiculoId);
    }

    public List<Mantenimiento> buscarPorEmpleado(Integer empleadoId) {
        return mantenimientoRepository.findByEmpleadoId(empleadoId);
    }

    public Mantenimiento guardar(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    public Mantenimiento actualizar(Integer id, Mantenimiento actualizado) {
        Mantenimiento m = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));

        m.setFechaInicio(actualizado.getFechaInicio());
        m.setFechaTermino(actualizado.getFechaTermino());
        m.setDescripcion(actualizado.getDescripcion());
        m.setCosto(actualizado.getCosto());
        m.setVehiculoId(actualizado.getVehiculoId());
        m.setEmpleadoId(actualizado.getEmpleadoId());
        m.setTipoMantenimiento(actualizado.getTipoMantenimiento());

        return mantenimientoRepository.save(m);
    }

    public void eliminar(Integer id) {
        if (!mantenimientoRepository.existsById(id)) {
            throw new RuntimeException("Mantenimiento no existe");
        }
        mantenimientoRepository.deleteById(id);
    }

    public MantenimientoDetalleDTO obtenerDetalle(Integer id) {
        Mantenimiento m = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));

        VehiculoDTO vehiculo   = vehiculoClient.obtenerVehiculo(m.getVehiculoId());
        EmpleadoDTO empleado   = empleadoClient.obtenerEmpleado(m.getEmpleadoId());

        TipoMantenimiento tipo = m.getTipoMantenimiento();
        TipoMantenimientoDTO tipoDTO = new TipoMantenimientoDTO(tipo.getId(), tipo.getNombre());

        MantenimientoDetalleDTO dto = new MantenimientoDetalleDTO();
        dto.setId(m.getId());
        dto.setFechaInicio(m.getFechaInicio());
        dto.setFechaTermino(m.getFechaTermino());
        dto.setDescripcion(m.getDescripcion());
        dto.setCosto(m.getCosto());
        dto.setTipoMantenimiento(tipoDTO);
        dto.setVehiculo(vehiculo);
        dto.setEmpleado(empleado);

        return dto;
    }
}

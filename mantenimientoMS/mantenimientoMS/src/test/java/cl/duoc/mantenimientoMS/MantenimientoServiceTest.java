package cl.duoc.mantenimientoMS;

import cl.duoc.mantenimientoMS.client.EmpleadoClient;
import cl.duoc.mantenimientoMS.client.VehiculoClient;
import cl.duoc.mantenimientoMS.model.Mantenimiento;
import cl.duoc.mantenimientoMS.model.TipoMantenimiento;
import cl.duoc.mantenimientoMS.repository.MantenimientoRepository;
import cl.duoc.mantenimientoMS.service.MantenimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MantenimientoServiceTest {

    @Mock private MantenimientoRepository mantenimientoRepository;
    @Mock private VehiculoClient vehiculoClient;
    @Mock private EmpleadoClient empleadoClient;
    @InjectMocks private MantenimientoService mantenimientoService;

    @Test
    void listar_debeRetornarTodos() {
        // Given
        TipoMantenimiento t = new TipoMantenimiento(1, "Preventivo");
        Mantenimiento m1 = new Mantenimiento(1, LocalDate.of(2026,5,1), LocalDate.of(2026,5,2), "Aceite", 35000.0, 1, 2, t);
        when(mantenimientoRepository.findAll()).thenReturn(Arrays.asList(m1));

        // When
        List<Mantenimiento> resultado = mantenimientoService.listar();

        // Then
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(mantenimientoRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> mantenimientoService.buscarPorId(99));
        assertEquals("Mantenimiento no encontrado", ex.getMessage());
    }

    @Test
    void buscarPorVehiculo_debeRetornarMantenimientos() {
        // Given
        TipoMantenimiento t = new TipoMantenimiento(2, "Correctivo");
        Mantenimiento m = new Mantenimiento(1, LocalDate.of(2026,5,10), LocalDate.of(2026,5,12), "Frenos", 120000.0, 2, 2, t);
        when(mantenimientoRepository.findByVehiculoId(2)).thenReturn(Arrays.asList(m));

        // When
        List<Mantenimiento> resultado = mantenimientoService.buscarPorVehiculo(2);

        // Then
        assertEquals(1, resultado.size());
        assertEquals("Frenos", resultado.get(0).getDescripcion());
    }

    @Test
    void guardar_debePersistirMantenimiento() {
        // Given
        TipoMantenimiento t = new TipoMantenimiento(1, "Preventivo");
        Mantenimiento m = new Mantenimiento(null, LocalDate.of(2026,6,10), null, "Mantención 10000km", 55000.0, 3, 2, t);
        Mantenimiento guardado = new Mantenimiento(1, LocalDate.of(2026,6,10), null, "Mantención 10000km", 55000.0, 3, 2, t);
        when(mantenimientoRepository.save(m)).thenReturn(guardado);

        // When
        Mantenimiento resultado = mantenimientoService.guardar(m);

        // Then
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(mantenimientoRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> mantenimientoService.eliminar(99));
        verify(mantenimientoRepository, never()).deleteById(anyInt());
    }
}

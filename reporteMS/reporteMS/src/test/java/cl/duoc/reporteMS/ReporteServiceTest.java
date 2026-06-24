package cl.duoc.reporteMS;

import cl.duoc.reporteMS.client.PagoClient;
import cl.duoc.reporteMS.client.ReservaClient;
import cl.duoc.reporteMS.client.VehiculoClient;
import cl.duoc.reporteMS.model.Reporte;
import cl.duoc.reporteMS.model.TipoReporte;
import cl.duoc.reporteMS.repository.ReporteRepository;
import cl.duoc.reporteMS.service.ReporteService;
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
class ReporteServiceTest {

    @Mock private ReporteRepository reporteRepository;
    @Mock private ReservaClient reservaClient;
    @Mock private VehiculoClient vehiculoClient;
    @Mock private PagoClient pagoClient;
    @InjectMocks private ReporteService reporteService;

    @Test
    void listar_debeRetornarTodos() {
        // Given
        TipoReporte t = new TipoReporte(1, "Ingresos");
        Reporte r = new Reporte(1, "Ingresos Mayo", LocalDate.of(2026,6,1), LocalDate.of(2026,5,1), LocalDate.of(2026,5,31), null, null, t);
        when(reporteRepository.findAll()).thenReturn(Arrays.asList(r));

        // When
        List<Reporte> resultado = reporteService.listar();

        // Then
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reporteService.buscarPorId(99));
        assertEquals("Reporte no encontrado", ex.getMessage());
    }

    @Test
    void buscarPorTipo_debeRetornarReportes() {
        // Given
        TipoReporte t = new TipoReporte(2, "Arriendos por Vehículo");
        Reporte r = new Reporte(1, "Uso ABCD12", LocalDate.of(2026,6,1), LocalDate.of(2026,5,1), LocalDate.of(2026,5,31), 1, 1, t);
        when(reporteRepository.findByTipoReporteId(2)).thenReturn(Arrays.asList(r));

        // When
        List<Reporte> resultado = reporteService.buscarPorTipo(2);

        // Then
        assertEquals(1, resultado.size());
    }

    @Test
    void guardar_debePersistirReporte() {
        // Given
        TipoReporte t = new TipoReporte(1, "Ingresos");
        Reporte r = new Reporte(null, "Ingresos Junio", LocalDate.of(2026,7,1), LocalDate.of(2026,6,1), LocalDate.of(2026,6,30), null, null, t);
        Reporte guardado = new Reporte(1, "Ingresos Junio", LocalDate.of(2026,7,1), LocalDate.of(2026,6,1), LocalDate.of(2026,6,30), null, null, t);
        when(reporteRepository.save(r)).thenReturn(guardado);

        // When
        Reporte resultado = reporteService.guardar(r);

        // Then
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(reporteRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> reporteService.eliminar(99));
        verify(reporteRepository, never()).deleteById(anyInt());
    }
}

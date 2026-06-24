package cl.duoc.disponibilidadMS;

import cl.duoc.disponibilidadMS.client.VehiculoClient;
import cl.duoc.disponibilidadMS.model.Disponibilidad;
import cl.duoc.disponibilidadMS.repository.DisponibilidadRepository;
import cl.duoc.disponibilidadMS.service.DisponibilidadService;
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
class DisponibilidadServiceTest {

    @Mock private DisponibilidadRepository disponibilidadRepository;
    @Mock private VehiculoClient vehiculoClient;
    @InjectMocks private DisponibilidadService disponibilidadService;

    @Test
    void listar_debeRetornarTodosLosRegistros() {
        // Given
        Disponibilidad d1 = new Disponibilidad(1, 1, LocalDate.of(2026,6,1), LocalDate.of(2026,6,5), false, "Reservado");
        Disponibilidad d2 = new Disponibilidad(2, 2, LocalDate.of(2026,6,1), LocalDate.of(2026,6,30), true, "Disponible");
        when(disponibilidadRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        // When
        List<Disponibilidad> resultado = disponibilidadService.listar();

        // Then
        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(disponibilidadRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> disponibilidadService.buscarPorId(99));
        assertEquals("Registro no encontrado", ex.getMessage());
    }

    @Test
    void estaDisponible_cuandoTodosLibres_debeRetornarTrue() {
        // Given
        Disponibilidad d = new Disponibilidad(1, 1, LocalDate.of(2026,6,1), LocalDate.of(2026,6,30), true, "Disponible");
        when(disponibilidadRepository
                .findByVehiculoIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
                        eq(1), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(d));

        // When
        boolean resultado = disponibilidadService.estaDisponible(1, LocalDate.of(2026,6,2), LocalDate.of(2026,6,4));

        // Then
        assertTrue(resultado);
    }

    @Test
    void estaDisponible_cuandoHayConflicto_debeRetornarFalse() {
        // Given
        Disponibilidad d = new Disponibilidad(1, 1, LocalDate.of(2026,6,1), LocalDate.of(2026,6,30), false, "Reservado");
        when(disponibilidadRepository
                .findByVehiculoIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
                        eq(1), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(d));

        // When
        boolean resultado = disponibilidadService.estaDisponible(1, LocalDate.of(2026,6,2), LocalDate.of(2026,6,4));

        // Then
        assertFalse(resultado);
    }

    @Test
    void guardar_debePersistirRegistro() {
        // Given
        Disponibilidad d = new Disponibilidad(null, 1, LocalDate.of(2026,6,1), LocalDate.of(2026,6,5), true, "Disponible");
        Disponibilidad guardado = new Disponibilidad(1, 1, LocalDate.of(2026,6,1), LocalDate.of(2026,6,5), true, "Disponible");
        when(disponibilidadRepository.save(d)).thenReturn(guardado);

        // When
        Disponibilidad resultado = disponibilidadService.guardar(d);

        // Then
        assertEquals(1, resultado.getId());
    }
}

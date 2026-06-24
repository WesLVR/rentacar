package cl.duoc.entregaMS;

import cl.duoc.entregaMS.client.EmpleadoClient;
import cl.duoc.entregaMS.client.ReservaClient;
import cl.duoc.entregaMS.model.Entrega;
import cl.duoc.entregaMS.model.TipoEntrega;
import cl.duoc.entregaMS.repository.EntregaRepository;
import cl.duoc.entregaMS.service.EntregaService;
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
class EntregaServiceTest {

    @Mock private EntregaRepository entregaRepository;
    @Mock private ReservaClient reservaClient;
    @Mock private EmpleadoClient empleadoClient;
    @InjectMocks private EntregaService entregaService;

    @Test
    void listar_debeRetornarTodas() {
        // Given
        TipoEntrega t = new TipoEntrega(1, "Entrega");
        Entrega e = new Entrega(1, LocalDate.of(2026,6,1), 45230, "Sin observaciones", 1, 3, t);
        when(entregaRepository.findAll()).thenReturn(Arrays.asList(e));

        // When
        List<Entrega> resultado = entregaService.listar();

        // Then
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(entregaRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> entregaService.buscarPorId(99));
        assertEquals("Entrega no encontrada", ex.getMessage());
    }

    @Test
    void buscarPorReserva_debeRetornarEntregas() {
        // Given
        TipoEntrega t = new TipoEntrega(2, "Devolución");
        Entrega e = new Entrega(1, LocalDate.of(2026,6,5), 45890, "Rayón leve", 1, 3, t);
        when(entregaRepository.findByReservaId(1)).thenReturn(Arrays.asList(e));

        // When
        List<Entrega> resultado = entregaService.buscarPorReserva(1);

        // Then
        assertEquals(1, resultado.size());
        assertEquals(45890, resultado.get(0).getKilometraje());
    }

    @Test
    void guardar_debePersistirEntrega() {
        // Given
        TipoEntrega t = new TipoEntrega(1, "Entrega");
        Entrega e = new Entrega(null, LocalDate.of(2026,6,10), 32100, "Tanque lleno", 2, 3, t);
        Entrega guardado = new Entrega(1, LocalDate.of(2026,6,10), 32100, "Tanque lleno", 2, 3, t);
        when(entregaRepository.save(e)).thenReturn(guardado);

        // When
        Entrega resultado = entregaService.guardar(e);

        // Then
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(entregaRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> entregaService.eliminar(99));
        verify(entregaRepository, never()).deleteById(anyInt());
    }
}

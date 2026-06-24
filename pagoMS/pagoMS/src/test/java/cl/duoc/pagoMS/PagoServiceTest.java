package cl.duoc.pagoMS;

import cl.duoc.pagoMS.client.ClienteClient;
import cl.duoc.pagoMS.client.ReservaClient;
import cl.duoc.pagoMS.model.MetodoPago;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.repository.PagoRepository;
import cl.duoc.pagoMS.service.PagoService;
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
class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private ReservaClient reservaClient;
    @Mock private ClienteClient clienteClient;
    @InjectMocks private PagoService pagoService;

    @Test
    void listar_debeRetornarTodosLosPagos() {
        // Given
        MetodoPago m = new MetodoPago(1, "Tarjeta de Crédito");
        Pago p1 = new Pago(1, LocalDate.of(2026, 5, 31), 75000.0, 1, 1, m);
        Pago p2 = new Pago(2, LocalDate.of(2026, 6, 9), 40000.0, 2, 2, m);
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // When
        List<Pago> resultado = pagoService.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(pagoRepository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarPago() {
        // Given
        MetodoPago m = new MetodoPago(1, "Transferencia");
        Pago p = new Pago(1, LocalDate.of(2026, 5, 31), 75000.0, 1, 1, m);
        when(pagoRepository.findById(1)).thenReturn(Optional.of(p));

        // When
        Pago resultado = pagoService.buscarPorId(1);

        // Then
        assertEquals(75000.0, resultado.getMonto());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> pagoService.buscarPorId(99));
        assertEquals("Pago no encontrado", ex.getMessage());
    }

    @Test
    void guardar_debePersistirPago() {
        // Given
        MetodoPago m = new MetodoPago(1, "Efectivo");
        Pago p = new Pago(null, LocalDate.of(2026, 5, 31), 75000.0, 1, 1, m);
        Pago guardado = new Pago(1, LocalDate.of(2026, 5, 31), 75000.0, 1, 1, m);
        when(pagoRepository.save(p)).thenReturn(guardado);

        // When
        Pago resultado = pagoService.guardar(p);

        // Then
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(pagoRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> pagoService.eliminar(99));
        verify(pagoRepository, never()).deleteById(anyInt());
    }
}

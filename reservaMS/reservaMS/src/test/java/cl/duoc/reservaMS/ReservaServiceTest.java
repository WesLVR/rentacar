package cl.duoc.reservaMS;

import cl.duoc.reservaMS.client.ClienteClient;
import cl.duoc.reservaMS.client.VehiculoClient;
import cl.duoc.reservaMS.dto.ClienteDTO;
import cl.duoc.reservaMS.dto.ReservaDetalleDTO;
import cl.duoc.reservaMS.dto.VehiculoDTO;
import cl.duoc.reservaMS.model.EstadoReserva;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.ReservaRepository;
import cl.duoc.reservaMS.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void guardar_debeCalcularCostoTotalSegunDiasYPrecioPorDia() {
        // Given: una reserva de 4 días (1 al 5 de junio) y un vehículo de 25000/día
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(LocalDate.of(2026, 6, 1));
        reserva.setFechaTermino(LocalDate.of(2026, 6, 5));
        reserva.setClienteId(1);
        reserva.setVehiculoId(1);

        VehiculoDTO vehiculo = new VehiculoDTO(1, "ABCD12", "Toyota", "Yaris", "Económico", 25000.0);
        when(vehiculoClient.obtenerVehiculo(1)).thenReturn(vehiculo);
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Reserva resultado = reservaService.guardar(reserva);

        // Then: 4 días * 25000 = 100000
        assertEquals(100000.0, resultado.getCostoTotal());
        verify(vehiculoClient).obtenerVehiculo(1);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarReserva() {
        // Given
        Reserva reserva = new Reserva();
        reserva.setId(1);
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        // When
        Reserva resultado = reservaService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.buscarPorId(99));
        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void obtenerDetalle_debeCombinarDatosDeClienteYVehiculo() {
        // Given
        EstadoReserva estado = new EstadoReserva(2, "Confirmada");
        Reserva reserva = new Reserva();
        reserva.setId(1);
        reserva.setFechaInicio(LocalDate.of(2026, 6, 1));
        reserva.setFechaTermino(LocalDate.of(2026, 6, 5));
        reserva.setCostoTotal(100000.0);
        reserva.setClienteId(1);
        reserva.setVehiculoId(1);
        reserva.setEstadoReserva(estado);

        ClienteDTO cliente = new ClienteDTO(1, "Lucas", "Valderrama", "lucas@email.com", "Clase B");
        VehiculoDTO vehiculo = new VehiculoDTO(1, "ABCD12", "Toyota", "Yaris", "Económico", 25000.0);

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(clienteClient.obtenerCliente(1)).thenReturn(cliente);
        when(vehiculoClient.obtenerVehiculo(1)).thenReturn(vehiculo);

        // When
        ReservaDetalleDTO dto = reservaService.obtenerDetalle(1);

        // Then
        assertEquals(1, dto.getId());
        assertEquals("Lucas", dto.getCliente().getNombre());
        assertEquals("Toyota", dto.getVehiculo().getMarca());
        assertEquals("Confirmada", dto.getEstadoReserva().getNombre());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(reservaRepository.existsById(99)).thenReturn(false);

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.eliminar(99));
        assertEquals("Reserva no existe", ex.getMessage());
        verify(reservaRepository, never()).deleteById(anyInt());
    }
}

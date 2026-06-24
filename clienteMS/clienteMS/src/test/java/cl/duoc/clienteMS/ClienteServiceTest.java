package cl.duoc.clienteMS;

import cl.duoc.clienteMS.model.Cliente;
import cl.duoc.clienteMS.model.TipoLicencia;
import cl.duoc.clienteMS.repository.ClienteRepository;
import cl.duoc.clienteMS.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void listar_debeRetornarTodosLosClientes() {
        // Given
        TipoLicencia lic = new TipoLicencia(1, "Clase B");
        Cliente c1 = new Cliente(1, "12345678-9", "Gonzalo", "Gallardo", "g@email.com", "912345678", lic);
        Cliente c2 = new Cliente(2, "98765432-1", "Bastian", "Gonzalez", "b@email.com", "987654321", lic);
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        // When
        List<Cliente> resultado = clienteService.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarCliente() {
        // Given
        TipoLicencia lic = new TipoLicencia(1, "Clase B");
        Cliente c = new Cliente(1, "12345678-9", "Lucas", "Valderrama", "l@email.com", "911223344", lic);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(c));

        // When
        Cliente resultado = clienteService.buscarPorId(1);

        // Then
        assertEquals("Lucas", resultado.getNombre());
        verify(clienteRepository).findById(1);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.buscarPorId(99));
        assertEquals("Cliente no encontrado", ex.getMessage());
    }

    @Test
    void buscarPorRut_cuandoExiste_debeRetornarCliente() {
        // Given
        TipoLicencia lic = new TipoLicencia(1, "Clase A");
        Cliente c = new Cliente(1, "11223344-5", "Lucas", "Valderrama", "l@email.com", "911223344", lic);
        when(clienteRepository.findByRut("11223344-5")).thenReturn(Optional.of(c));

        // When
        Cliente resultado = clienteService.buscarPorRut("11223344-5");

        // Then
        assertEquals("11223344-5", resultado.getRut());
    }

    @Test
    void guardar_debePersistirCliente() {
        // Given
        TipoLicencia lic = new TipoLicencia(1, "Clase B");
        Cliente c = new Cliente(null, "12345678-9", "Gonzalo", "Gallardo", "g@email.com", "912345678", lic);
        Cliente guardado = new Cliente(1, "12345678-9", "Gonzalo", "Gallardo", "g@email.com", "912345678", lic);
        when(clienteRepository.save(c)).thenReturn(guardado);

        // When
        Cliente resultado = clienteService.guardar(c);

        // Then
        assertEquals(1, resultado.getId());
        verify(clienteRepository).save(c);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(clienteRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> clienteService.eliminar(99));
        verify(clienteRepository, never()).deleteById(anyInt());
    }
}

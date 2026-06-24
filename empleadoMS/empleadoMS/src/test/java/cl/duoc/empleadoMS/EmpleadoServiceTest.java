package cl.duoc.empleadoMS;

import cl.duoc.empleadoMS.model.Cargo;
import cl.duoc.empleadoMS.model.Empleado;
import cl.duoc.empleadoMS.repository.EmpleadoRepository;
import cl.duoc.empleadoMS.service.EmpleadoService;
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
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void listar_debeRetornarTodosLosEmpleados() {
        // Given
        Cargo cargo = new Cargo(1, "Administrador General");
        Empleado e1 = new Empleado(1, "10101010-1", "Roberto", "Muñoz", "r@rc.cl", "912000001", cargo);
        Empleado e2 = new Empleado(2, "20202020-2", "Patricia", "Rojas", "p@rc.cl", "912000002", cargo);
        when(empleadoRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        // When
        List<Empleado> resultado = empleadoService.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(empleadoRepository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarEmpleado() {
        // Given
        Cargo cargo = new Cargo(2, "Encargado de Flota");
        Empleado e = new Empleado(1, "10101010-1", "Roberto", "Muñoz", "r@rc.cl", "912000001", cargo);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(e));

        // When
        Empleado resultado = empleadoService.buscarPorId(1);

        // Then
        assertEquals("Roberto", resultado.getNombre());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> empleadoService.buscarPorId(99));
        assertEquals("Empleado no encontrado", ex.getMessage());
    }

    @Test
    void buscarPorCargo_debeRetornarEmpleadosDelCargo() {
        // Given
        Cargo cargo = new Cargo(1, "Logística");
        Empleado e = new Empleado(1, "40404040-4", "Camila", "Soto", "c@rc.cl", "912000004", cargo);
        when(empleadoRepository.findByCargoId(1)).thenReturn(Arrays.asList(e));

        // When
        List<Empleado> resultado = empleadoService.buscarPorCargo(1);

        // Then
        assertEquals(1, resultado.size());
        assertEquals("Camila", resultado.get(0).getNombre());
    }

    @Test
    void guardar_debePersistirEmpleado() {
        // Given
        Cargo cargo = new Cargo(1, "Marketing");
        Empleado e = new Empleado(null, "50505050-5", "Felipe", "Mora", "f@rc.cl", "912000005", cargo);
        Empleado guardado = new Empleado(1, "50505050-5", "Felipe", "Mora", "f@rc.cl", "912000005", cargo);
        when(empleadoRepository.save(e)).thenReturn(guardado);

        // When
        Empleado resultado = empleadoService.guardar(e);

        // Then
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(empleadoRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> empleadoService.eliminar(99));
        verify(empleadoRepository, never()).deleteById(anyInt());
    }
}

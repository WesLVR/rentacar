package cl.duoc.vehiculoMS;

import cl.duoc.vehiculoMS.model.CategoriaVehiculo;
import cl.duoc.vehiculoMS.model.Vehiculo;
import cl.duoc.vehiculoMS.repository.VehiculoRepository;
import cl.duoc.vehiculoMS.service.VehiculoService;
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
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void listar_debeRetornarTodosLosVehiculos() {
        // Given
        CategoriaVehiculo cat = new CategoriaVehiculo(1, "Económico");
        Vehiculo v1 = new Vehiculo(1, "ABCD12", "Toyota", "Yaris", 2022, 25000.0, cat);
        Vehiculo v2 = new Vehiculo(2, "EFGH34", "Kia", "Rio", 2023, 28000.0, cat);
        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        // When
        List<Vehiculo> resultado = vehiculoService.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarVehiculo() {
        // Given
        CategoriaVehiculo cat = new CategoriaVehiculo(1, "SUV");
        Vehiculo v = new Vehiculo(1, "ABCD12", "Hyundai", "Tucson", 2023, 45000.0, cat);
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(v));

        // When
        Vehiculo resultado = vehiculoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        assertEquals("Hyundai", resultado.getMarca());
        verify(vehiculoRepository).findById(1);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vehiculoService.buscarPorId(99));
        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void guardar_debePersistirYRetornarVehiculo() {
        // Given
        CategoriaVehiculo cat = new CategoriaVehiculo(1, "Premium");
        Vehiculo v = new Vehiculo(null, "MNOP78", "BMW", "320i", 2023, 90000.0, cat);
        Vehiculo guardado = new Vehiculo(1, "MNOP78", "BMW", "320i", 2023, 90000.0, cat);
        when(vehiculoRepository.save(v)).thenReturn(guardado);

        // When
        Vehiculo resultado = vehiculoService.guardar(v);

        // Then
        assertNotNull(resultado.getId());
        assertEquals(1, resultado.getId());
        verify(vehiculoRepository).save(v);
    }

    @Test
    void eliminar_cuandoExiste_debeLlamarDeleteById() {
        // Given
        when(vehiculoRepository.existsById(1)).thenReturn(true);

        // When
        vehiculoService.eliminar(1);

        // Then
        verify(vehiculoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(vehiculoRepository.existsById(99)).thenReturn(false);

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vehiculoService.eliminar(99));
        assertEquals("Vehículo no existe", ex.getMessage());
        verify(vehiculoRepository, never()).deleteById(anyInt());
    }
}

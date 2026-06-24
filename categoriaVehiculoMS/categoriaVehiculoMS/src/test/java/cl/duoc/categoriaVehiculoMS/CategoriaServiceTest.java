package cl.duoc.categoriaVehiculoMS;

import cl.duoc.categoriaVehiculoMS.model.Categoria;
import cl.duoc.categoriaVehiculoMS.repository.CategoriaRepository;
import cl.duoc.categoriaVehiculoMS.service.CategoriaService;
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
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void listar_debeRetornarTodasLasCategorias() {
        // Given
        Categoria c1 = new Categoria(1, "Económico", "Vehículos compactos", 20000.0);
        Categoria c2 = new Categoria(2, "SUV", "Todo terreno", 50000.0);
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        // When
        List<Categoria> resultado = categoriaService.listar();

        // Then
        assertEquals(2, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarCategoria() {
        // Given
        Categoria c = new Categoria(1, "Premium", "Alta gama", 90000.0);
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(c));

        // When
        Categoria resultado = categoriaService.buscarPorId(1);

        // Then
        assertEquals("Premium", resultado.getNombre());
        assertEquals(90000.0, resultado.getTarifaBaseDiaria());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> categoriaService.buscarPorId(99));
        assertEquals("Categoría no encontrada", ex.getMessage());
    }

    @Test
    void guardar_debePersistirCategoria() {
        // Given
        Categoria c = new Categoria(null, "Furgón", "Carga", 60000.0);
        Categoria guardado = new Categoria(1, "Furgón", "Carga", 60000.0);
        when(categoriaRepository.save(c)).thenReturn(guardado);

        // When
        Categoria resultado = categoriaService.guardar(c);

        // Then
        assertEquals(1, resultado.getId());
        verify(categoriaRepository).save(c);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(categoriaRepository.existsById(99)).thenReturn(false);

        // When + Then
        assertThrows(RuntimeException.class, () -> categoriaService.eliminar(99));
        verify(categoriaRepository, never()).deleteById(anyInt());
    }
}

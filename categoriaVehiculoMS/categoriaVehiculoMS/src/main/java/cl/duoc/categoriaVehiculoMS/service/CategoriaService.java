package cl.duoc.categoriaVehiculoMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.categoriaVehiculoMS.model.Categoria;
import cl.duoc.categoriaVehiculoMS.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Integer id, Categoria categoriaActualizada) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        categoria.setTarifaBaseDiaria(categoriaActualizada.getTarifaBaseDiaria());

        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no existe");
        }
        categoriaRepository.deleteById(id);
    }
}

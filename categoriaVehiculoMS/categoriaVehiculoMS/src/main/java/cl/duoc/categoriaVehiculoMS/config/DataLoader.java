package cl.duoc.categoriaVehiculoMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.categoriaVehiculoMS.model.Categoria;
import cl.duoc.categoriaVehiculoMS.repository.CategoriaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CategoriaRepository categoriaRepository) {
        return args -> {
            if (categoriaRepository.count() == 0) {

                categoriaRepository.save(new Categoria(null, "Económico",
                        "Vehículos compactos de bajo consumo", 20000.0));
                categoriaRepository.save(new Categoria(null, "Familiar",
                        "Vehículos espaciosos para grupos o familias", 40000.0));
                categoriaRepository.save(new Categoria(null, "SUV",
                        "Vehículos todo terreno de uso urbano y rural", 50000.0));
                categoriaRepository.save(new Categoria(null, "Premium",
                        "Vehículos de lujo y alta gama", 90000.0));
                categoriaRepository.save(new Categoria(null, "Furgón",
                        "Vehículos de carga o transporte de personal", 60000.0));

                System.out.println("Datos de categorías cargados");
            } else {
                System.out.println("Datos de categorías ya existen");
            }
        };
    }
}

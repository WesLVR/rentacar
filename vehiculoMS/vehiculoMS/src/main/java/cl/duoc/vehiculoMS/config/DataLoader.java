package cl.duoc.vehiculoMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.vehiculoMS.model.CategoriaVehiculo;
import cl.duoc.vehiculoMS.model.Vehiculo;
import cl.duoc.vehiculoMS.repository.CategoriaVehiculoRepository;
import cl.duoc.vehiculoMS.repository.VehiculoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(VehiculoRepository vehiculoRepository,
                               CategoriaVehiculoRepository categoriaRepository) {
        return args -> {
            if (categoriaRepository.count() == 0) {

                CategoriaVehiculo economico = categoriaRepository.save(new CategoriaVehiculo(null, "Económico"));
                CategoriaVehiculo familiar  = categoriaRepository.save(new CategoriaVehiculo(null, "Familiar"));
                CategoriaVehiculo suv       = categoriaRepository.save(new CategoriaVehiculo(null, "SUV"));
                CategoriaVehiculo premium   = categoriaRepository.save(new CategoriaVehiculo(null, "Premium"));
                categoriaRepository.save(new CategoriaVehiculo(null, "Furgón"));

                vehiculoRepository.save(new Vehiculo(null, "ABCD12", "Toyota", "Yaris",   2022, 25000.0, economico));
                vehiculoRepository.save(new Vehiculo(null, "EFGH34", "Hyundai","Tucson",  2023, 45000.0, suv));
                vehiculoRepository.save(new Vehiculo(null, "IJKL56", "Kia",    "Carnival",2021, 55000.0, familiar));
                vehiculoRepository.save(new Vehiculo(null, "MNOP78", "BMW",    "320i",    2023, 90000.0, premium));
                vehiculoRepository.save(new Vehiculo(null, "QRST90", "Suzuki", "Swift",   2022, 22000.0, economico));

                System.out.println("Datos de vehículos y categorías cargados exitosamente.");
            } else {
                System.out.println("Datos de vehículos ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

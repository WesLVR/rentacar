package cl.duoc.disponibilidadMS.config;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.duoc.disponibilidadMS.model.Disponibilidad;
import cl.duoc.disponibilidadMS.repository.DisponibilidadRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(DisponibilidadRepository disponibilidadRepository) {
        return args -> {
            if (disponibilidadRepository.count() == 0) {

                disponibilidadRepository.save(new Disponibilidad(null, 1,
                        LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 5),
                        false, "Reservado"));

                disponibilidadRepository.save(new Disponibilidad(null, 1,
                        LocalDate.of(2026, 6, 6), LocalDate.of(2026, 6, 30),
                        true, "Disponible"));

                disponibilidadRepository.save(new Disponibilidad(null, 2,
                        LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30),
                        true, "Disponible"));

                disponibilidadRepository.save(new Disponibilidad(null, 3,
                        LocalDate.of(2026, 6, 10), LocalDate.of(2026, 6, 12),
                        false, "En mantención"));

                System.out.println("Datos de disponibilidad cargados exitosamente.");
            } else {
                System.out.println("Datos de disponibilidad ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

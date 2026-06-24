package cl.duoc.entregaMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.entregaMS.model.Entrega;
import cl.duoc.entregaMS.model.TipoEntrega;
import cl.duoc.entregaMS.repository.EntregaRepository;
import cl.duoc.entregaMS.repository.TipoEntregaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(EntregaRepository entregaRepository,
                               TipoEntregaRepository tipoEntregaRepository) {
        return args -> {
            if (tipoEntregaRepository.count() == 0) {

                TipoEntrega entrega     = tipoEntregaRepository.save(new TipoEntrega(null, "Entrega"));
                TipoEntrega devolucion  = tipoEntregaRepository.save(new TipoEntrega(null, "Devolución"));

                entregaRepository.save(new Entrega(null,
                        LocalDate.of(2026, 6, 1), 45230,
                        "Vehículo entregado sin observaciones", 1, 3, entrega));

                entregaRepository.save(new Entrega(null,
                        LocalDate.of(2026, 6, 5), 45890,
                        "Devolución con rayón leve en parachoques trasero", 1, 3, devolucion));

                entregaRepository.save(new Entrega(null,
                        LocalDate.of(2026, 6, 10), 32100,
                        "Entrega con tanque lleno", 2, 3, entrega));

                System.out.println("Datos de entregas cargados exitosamente.");
            } else {
                System.out.println("Datos de entregas ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

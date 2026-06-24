package cl.duoc.mantenimientoMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.mantenimientoMS.model.Mantenimiento;
import cl.duoc.mantenimientoMS.model.TipoMantenimiento;
import cl.duoc.mantenimientoMS.repository.MantenimientoRepository;
import cl.duoc.mantenimientoMS.repository.TipoMantenimientoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(MantenimientoRepository mantenimientoRepository,
                               TipoMantenimientoRepository tipoMantenimientoRepository) {
        return args -> {
            if (tipoMantenimientoRepository.count() == 0) {

                TipoMantenimiento preventivo  = tipoMantenimientoRepository.save(new TipoMantenimiento(null, "Preventivo"));
                TipoMantenimiento correctivo  = tipoMantenimientoRepository.save(new TipoMantenimiento(null, "Correctivo"));
                tipoMantenimientoRepository.save(new TipoMantenimiento(null, "Revisión Técnica"));
                tipoMantenimientoRepository.save(new TipoMantenimiento(null, "Lavado"));

                mantenimientoRepository.save(new Mantenimiento(null,
                        LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 2),
                        "Cambio de aceite y filtros", 35000.0, 1, 2, preventivo));

                mantenimientoRepository.save(new Mantenimiento(null,
                        LocalDate.of(2026, 5, 10), LocalDate.of(2026, 5, 12),
                        "Reparación de frenos delanteros", 120000.0, 2, 2, correctivo));

                mantenimientoRepository.save(new Mantenimiento(null,
                        LocalDate.of(2026, 6, 10), null,
                        "Mantención programada 10.000 km", 55000.0, 3, 2, preventivo));

                System.out.println("Datos de mantenimiento cargados exitosamente.");
            } else {
                System.out.println("Datos de mantenimiento ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

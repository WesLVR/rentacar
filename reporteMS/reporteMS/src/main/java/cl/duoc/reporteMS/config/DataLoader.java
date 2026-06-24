package cl.duoc.reporteMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.reporteMS.model.Reporte;
import cl.duoc.reporteMS.model.TipoReporte;
import cl.duoc.reporteMS.repository.ReporteRepository;
import cl.duoc.reporteMS.repository.TipoReporteRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ReporteRepository reporteRepository,
                               TipoReporteRepository tipoReporteRepository) {
        return args -> {
            if (tipoReporteRepository.count() == 0) {

                TipoReporte ingresos    = tipoReporteRepository.save(new TipoReporte(null, "Ingresos"));
                TipoReporte porVehiculo = tipoReporteRepository.save(new TipoReporte(null, "Arriendos por Vehículo"));
                TipoReporte frecuentes  = tipoReporteRepository.save(new TipoReporte(null, "Clientes Frecuentes"));
                tipoReporteRepository.save(new TipoReporte(null, "Mantenimientos del Período"));

                reporteRepository.save(new Reporte(null,
                        "Ingresos Mayo 2026",
                        LocalDate.of(2026, 6, 1),
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 31),
                        null, null, ingresos));

                reporteRepository.save(new Reporte(null,
                        "Uso Vehículo ABCD12 - Mayo 2026",
                        LocalDate.of(2026, 6, 1),
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 31),
                        1, 1, porVehiculo));

                reporteRepository.save(new Reporte(null,
                        "Clientes Frecuentes Primer Semestre 2026",
                        LocalDate.of(2026, 6, 1),
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 6, 30),
                        null, null, frecuentes));

                System.out.println("Datos de reportes cargados exitosamente.");
            } else {
                System.out.println("Datos de reportes ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

package cl.duoc.pagoMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.pagoMS.model.MetodoPago;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.repository.MetodoPagoRepository;
import cl.duoc.pagoMS.repository.PagoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(PagoRepository pagoRepository,
                               MetodoPagoRepository metodoPagoRepository) {
        return args -> {
            if (metodoPagoRepository.count() == 0) {

                MetodoPago tarjetaCredito  = metodoPagoRepository.save(new MetodoPago(null, "Tarjeta de Crédito"));
                MetodoPago tarjetaDebito   = metodoPagoRepository.save(new MetodoPago(null, "Tarjeta de Débito"));
                MetodoPago transferencia   = metodoPagoRepository.save(new MetodoPago(null, "Transferencia"));
                metodoPagoRepository.save(new MetodoPago(null, "Efectivo"));

                pagoRepository.save(new Pago(null,
                        LocalDate.of(2026, 5, 31), 75000.0,
                        1, 1, tarjetaCredito));

                pagoRepository.save(new Pago(null,
                        LocalDate.of(2026, 6, 9), 40000.0,
                        2, 2, transferencia));

                pagoRepository.save(new Pago(null,
                        LocalDate.of(2026, 5, 19), 90000.0,
                        3, 3, tarjetaDebito));

                System.out.println("Datos de pagos cargados exitosamente.");
            } else {
                System.out.println("Datos de pagos ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

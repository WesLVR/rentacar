package cl.duoc.reservaMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.reservaMS.model.EstadoReserva;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.EstadoReservaRepository;
import cl.duoc.reservaMS.repository.ReservaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ReservaRepository reservaRepository,
                               EstadoReservaRepository estadoReservaRepository) {
        return args -> {
            if (estadoReservaRepository.count() == 0) {

                EstadoReserva pendiente   = estadoReservaRepository.save(new EstadoReserva(null, "Pendiente"));
                EstadoReserva confirmada  = estadoReservaRepository.save(new EstadoReserva(null, "Confirmada"));
                estadoReservaRepository.save(new EstadoReserva(null, "Cancelada"));
                EstadoReserva finalizada  = estadoReservaRepository.save(new EstadoReserva(null, "Finalizada"));

                reservaRepository.save(new Reserva(null,
                        LocalDate.of(2026, 6, 1),
                        LocalDate.of(2026, 6, 5),
                        75000.0, 1, 1, confirmada));

                reservaRepository.save(new Reserva(null,
                        LocalDate.of(2026, 6, 10),
                        LocalDate.of(2026, 6, 12),
                        40000.0, 2, 2, pendiente));

                reservaRepository.save(new Reserva(null,
                        LocalDate.of(2026, 5, 20),
                        LocalDate.of(2026, 5, 25),
                        90000.0, 3, 1, finalizada));

                System.out.println("Datos de reservas y estados cargados exitosamente.");
            } else {
                System.out.println("Datos de reservas ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

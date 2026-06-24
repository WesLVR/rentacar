package cl.duoc.clienteMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.clienteMS.model.Cliente;
import cl.duoc.clienteMS.model.TipoLicencia;
import cl.duoc.clienteMS.repository.ClienteRepository;
import cl.duoc.clienteMS.repository.TipoLicenciaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ClienteRepository clienteRepository,
                               TipoLicenciaRepository tipoLicenciaRepository) {
        return args -> {
            if (tipoLicenciaRepository.count() == 0) {

                TipoLicencia claseB = tipoLicenciaRepository.save(new TipoLicencia(null, "Clase B"));
                TipoLicencia claseA = tipoLicenciaRepository.save(new TipoLicencia(null, "Clase A"));
                tipoLicenciaRepository.save(new TipoLicencia(null, "Clase C"));

                clienteRepository.save(new Cliente(null, "11111111-1", "Gonzalo", "Gallardo",
                        "gonzalo@email.com", "912345678", claseB));
                clienteRepository.save(new Cliente(null, "22222222-2", "Bastian", "Gonzalez",
                        "bastian@email.com", "987654321", claseB));
                clienteRepository.save(new Cliente(null, "33333333-3", "Lucas", "Valderrama",
                        "lucas@email.com", "911223344", claseA));

                System.out.println("Datos de clientes y tipos de licencias cargados");
            } else {
                System.out.println("Datos de clientes ya existen");
            }
        };
    }
}

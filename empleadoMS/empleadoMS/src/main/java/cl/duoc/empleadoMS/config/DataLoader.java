package cl.duoc.empleadoMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.empleadoMS.model.Cargo;
import cl.duoc.empleadoMS.model.Empleado;
import cl.duoc.empleadoMS.repository.CargoRepository;
import cl.duoc.empleadoMS.repository.EmpleadoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(EmpleadoRepository empleadoRepository,
                               CargoRepository cargoRepository) {
        return args -> {
            if (cargoRepository.count() == 0) {

                Cargo adminGeneral   = cargoRepository.save(new Cargo(null, "Administrador General"));
                Cargo encFlota       = cargoRepository.save(new Cargo(null, "Encargado de Flota"));
                Cargo atencionCliente= cargoRepository.save(new Cargo(null, "Atención al Cliente"));
                Cargo logistica      = cargoRepository.save(new Cargo(null, "Logística"));
                Cargo marketing      = cargoRepository.save(new Cargo(null, "Marketing"));

                empleadoRepository.save(new Empleado(null, "10101010-1", "Roberto", "Muñoz",
                        "roberto@rentacar.cl", "912000001", adminGeneral));
                empleadoRepository.save(new Empleado(null, "20202020-2", "Patricia", "Rojas",
                        "patricia@rentacar.cl", "912000002", encFlota));
                empleadoRepository.save(new Empleado(null, "30303030-3", "Diego", "Castro",
                        "diego@rentacar.cl", "912000003", atencionCliente));
                empleadoRepository.save(new Empleado(null, "40404040-4", "Camila", "Soto",
                        "camila@rentacar.cl", "912000004", logistica));
                empleadoRepository.save(new Empleado(null, "50505050-5", "Felipe", "Mora",
                        "felipe@rentacar.cl", "912000005", marketing));

                System.out.println("Datos de empleados y cargos cargados exitosamente.");
            } else {
                System.out.println("Datos de empleados ya existen. No se cargarán datos de ejemplo.");
            }
        };
    }
}

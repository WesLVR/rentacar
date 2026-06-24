package cl.duoc.mantenimientoMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MantenimientoMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MantenimientoMsApplication.class, args);
    }

}

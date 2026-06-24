package cl.duoc.disponibilidadMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DisponibilidadMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisponibilidadMsApplication.class, args);
    }

}

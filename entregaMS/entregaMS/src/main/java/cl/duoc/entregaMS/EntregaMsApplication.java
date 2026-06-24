package cl.duoc.entregaMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EntregaMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntregaMsApplication.class, args);
    }

}

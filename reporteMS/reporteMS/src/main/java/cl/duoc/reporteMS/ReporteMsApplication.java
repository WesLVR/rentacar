package cl.duoc.reporteMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReporteMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReporteMsApplication.class, args);
    }

}

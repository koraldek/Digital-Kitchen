package pl.krasnowski.DigitalKitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
public class DigitalKitchenApplication {

    public static void main(String[] args) {

        SpringApplication.run(DigitalKitchenApplication.class, args);
    }
}
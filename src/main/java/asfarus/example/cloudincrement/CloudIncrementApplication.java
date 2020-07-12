package asfarus.example.cloudincrement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CloudIncrementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudIncrementApplication.class, args);
    }
}

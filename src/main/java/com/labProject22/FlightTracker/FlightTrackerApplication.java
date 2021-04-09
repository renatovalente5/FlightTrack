package com.labProject22.FlightTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@EnableKafka
@SpringBootApplication
public class FlightTrackerApplication {
    
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
            SpringApplication.run(FlightTrackerApplication.class, args);
    }

}

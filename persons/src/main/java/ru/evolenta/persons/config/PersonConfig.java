package ru.evolenta.persons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PersonConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

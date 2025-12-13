package com.example.consuming_web_service;

import com.example.consuming_web_service.CountryClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.consuming_web_service.wsdl.GetCountryResponse;

@SpringBootApplication
public class ConsumingWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumingWebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(CountryClient countryClient) {
        return args -> {
            String country = "Poland";

            if (args.length > 0) {
                country = args[0];
            }
            GetCountryResponse response = countryClient.getCountry(country);
            System.err.println(response.getCountry().getCurrency());
        };
    }

}
package org.example.customerservice;

import org.example.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.example.customerservice.entities.Customer;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder()
                    .name("Ahmed")
                    .email("mdd@gmail.com")
                    .build());

            customerRepository.save(Customer.builder()
                    .name("Youssef")
                    .email("youssef@gmail.com")
                    .build());

            customerRepository.findAll().forEach(c -> {
                System.out.println("++++++++++++++++++++++");
                System.out.println(c.getId() + " | " + c.getName() + " | " + c.getEmail());
            });
        };
    }
}

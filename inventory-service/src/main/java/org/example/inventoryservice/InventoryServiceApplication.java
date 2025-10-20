package org.example.inventoryservice;

import org.example.inventoryservice.entities.Product;
import org.example.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .id("P1")
                    .name("Laptop Lenovo ThinkPad")
                    .price(1200.0)
                    .quantity(10)
                    .build());

            productRepository.save(Product.builder()
                    .id("P2")
                    .name("Mouse Logitech MX Master")
                    .price(80.0)
                    .quantity(30)
                    .build());

            productRepository.save(Product.builder()
                    .id("P3")
                    .name("Keyboard Keychron K8")
                    .price(100.0)
                    .quantity(15)
                    .build());

            System.out.println("✅ Products added successfully to database!");
        };
    }
}

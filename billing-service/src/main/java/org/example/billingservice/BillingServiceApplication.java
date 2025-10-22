package org.example.billingservice;

import org.example.billingservice.Feign.CutomerRestClient;
import org.example.billingservice.Feign.ProductRestClient;
import org.example.billingservice.Repository.BillRepository;
import org.example.billingservice.Repository.ProductItemRepository;
import org.example.billingservice.entities.facture;
import org.example.billingservice.entities.productItem;
import org.example.billingservice.model.Customer;
import org.example.billingservice.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class BillingServiceApplication {

    private final ProductItemRepository productItemRepository;

    public BillingServiceApplication(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner init(BillRepository billRepository,
                           ProductRestClient productRestClient,
                           CutomerRestClient cutomerRestClient) {
        return args -> {
            Collection<Customer> customers = cutomerRestClient.getCustomer().getContent();
            Collection<Product> products = productRestClient.getProducts().getContent();

            customers.forEach(customer -> {
                facture bill = facture.builder()
                        .customerId(customer.getId())
                        .billingDate(new Date())
                        .build();
                billRepository.save(bill);
                products.forEach(product -> {
                    productItem pi = productItem.builder()
                            .productId(product.getId())
                            .quantity(1 + new Random().nextInt(10))
                            .price(product.getPrice())
                            .facture(bill)
                            .build();
                    productItemRepository.save(pi);
                });

            });
        };
    }

}

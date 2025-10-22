package org.example.billingservice.Feign;

import org.example.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CutomerRestClient {
    @GetMapping("/api/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id);
}

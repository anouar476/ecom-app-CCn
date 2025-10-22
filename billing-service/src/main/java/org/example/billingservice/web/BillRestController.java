package org.example.billingservice.web;

import org.example.billingservice.Feign.CutomerRestClient;
import org.example.billingservice.Feign.ProductRestClient;
import org.example.billingservice.Repository.BillRepository;
import org.example.billingservice.Repository.ProductItemRepository;
import org.example.billingservice.entities.facture;
import org.example.billingservice.model.Customer;
import org.example.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CutomerRestClient cutomerRestClient;
    @Autowired
    private ProductRestClient productRestClient;
    @GetMapping("/bills/{id}")
    public facture getBill(@PathVariable Long id){
        facture bill=billRepository.findById(id).get();
        bill.setCustomer(cutomerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi->{
            pi.setProduct(productRestClient.getProductById(pi.getProductId()));
        });
        return bill;
    }
}

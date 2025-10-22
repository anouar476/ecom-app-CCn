package org.example.billingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.billingservice.model.Customer;
import org.example.billingservice.model.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class facture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Date billingDate;
    private long customerId;
    @OneToMany(mappedBy = "facture")
    private List<productItem> productItems=new ArrayList<>();
    @Transient private Customer customer;
}

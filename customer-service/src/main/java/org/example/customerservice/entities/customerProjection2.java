package org.example.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "email", types = Customer.class)
public interface customerProjection2 {
    String getEmail();

}

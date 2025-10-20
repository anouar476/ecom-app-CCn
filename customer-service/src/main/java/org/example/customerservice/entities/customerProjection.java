package org.example.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "all", types = Customer.class)
public interface customerProjection {
    String getId();
    String getName();
    String getEmail();
}

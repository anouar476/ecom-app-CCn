package org.example.inventoryservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "namePrice", types = Product.class)
public interface projection {
    String getName();
    Double getPrice();
}

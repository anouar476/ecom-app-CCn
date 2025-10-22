package org.example.billingservice.Repository;

import org.example.billingservice.entities.productItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<productItem,Long> {
}

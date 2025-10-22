package org.example.billingservice.Repository;

import org.example.billingservice.entities.facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<facture, Long> {
}

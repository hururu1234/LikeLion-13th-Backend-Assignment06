package com.likelion.likelion_assignment.delivery.domain.repository;

import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByClient(Client client);
}

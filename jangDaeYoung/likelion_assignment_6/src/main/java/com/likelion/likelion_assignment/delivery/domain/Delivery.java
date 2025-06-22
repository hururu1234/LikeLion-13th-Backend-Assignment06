package com.likelion.likelion_assignment.delivery.domain;

import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.delivery.api.request.DeliveryUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    private String itemName;

    private String deliveryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder
    private Delivery(String itemName, String deliveryStatus, Client client) {
        this.itemName = itemName;
        this.deliveryStatus = deliveryStatus;
        this.client = client;
    }

    public void update(DeliveryUpdateRequestDto deliveryUpdateRequestDto){
        this.itemName = deliveryUpdateRequestDto.itemName();
        this.deliveryStatus = deliveryUpdateRequestDto.deliveryStatus();
    }
}

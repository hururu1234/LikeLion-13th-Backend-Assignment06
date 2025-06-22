package com.likelion.likelion_assignment.delivery.api.response;

import com.likelion.likelion_assignment.delivery.domain.Delivery;
import lombok.Builder;

@Builder
public record DeliveryInfoResponseDto(

        String clientName,
        String itemName,
        String deliveryStatus

) {

    public static DeliveryInfoResponseDto from(Delivery delivery){
        return DeliveryInfoResponseDto.builder()
                .clientName(delivery.getClient().getName())
                .itemName(delivery.getItemName())
                .deliveryStatus(delivery.getDeliveryStatus())
                .build();
    }
}

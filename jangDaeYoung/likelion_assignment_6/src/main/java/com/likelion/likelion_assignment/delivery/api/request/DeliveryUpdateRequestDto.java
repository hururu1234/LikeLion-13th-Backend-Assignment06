package com.likelion.likelion_assignment.delivery.api.request;

public record DeliveryUpdateRequestDto(
        String itemName,
        String deliveryStatus
) {
}

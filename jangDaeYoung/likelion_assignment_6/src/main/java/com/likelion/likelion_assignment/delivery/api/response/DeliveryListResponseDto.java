package com.likelion.likelion_assignment.delivery.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DeliveryListResponseDto(
        List<DeliveryInfoResponseDto> deliverys
) {
    public static DeliveryListResponseDto from(List<DeliveryInfoResponseDto> deliveryInfoResponseDtos)
    {
        return DeliveryListResponseDto.builder()
                .deliverys(deliveryInfoResponseDtos)
                .build();

    }
}

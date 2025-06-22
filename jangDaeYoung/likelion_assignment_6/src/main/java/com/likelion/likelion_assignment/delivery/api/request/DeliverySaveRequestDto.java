package com.likelion.likelion_assignment.delivery.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeliverySaveRequestDto(

        @Positive(message = "양수 입력해야 합니다.")
        Long clientId,

        @NotNull(message = "품목을 필수로 입력해야 합니다.")
        String itemName,

        @NotBlank(message = "배달 상태를 필수로 입력해야 합니다.")
        String deliveryStatus

) {
}

package com.likelion.likelion_assignment.client.api.dto.request;

import com.likelion.likelion_assignment.client.domain.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;

public record ClientUpdateRequestDto(

        String name,
        int age,
        Payment payment

) {
}

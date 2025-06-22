package com.likelion.likelion_assignment.client.api.dto.response;

import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.client.domain.Payment;
import com.likelion.likelion_assignment.client.domain.Role;
import lombok.Builder;

@Builder
public record ClientLoginInfoResponseDto(
        String name,
        int age,
        Payment payment,
        String email,
        Role role,
        String token

) {


    public static ClientLoginInfoResponseDto from(Client client, String token){
        return ClientLoginInfoResponseDto.builder()
                .name(client.getName())
                .age(client.getAge())
                .payment(client.getPayment())
                .email(client.getEmail())
                .role(client.getRole())
                .token(token)
                .build();
    }

}

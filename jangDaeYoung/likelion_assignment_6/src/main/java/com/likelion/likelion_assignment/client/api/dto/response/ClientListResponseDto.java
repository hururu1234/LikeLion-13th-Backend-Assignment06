package com.likelion.likelion_assignment.client.api.dto.response;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ClientListResponseDto(

        List<ClientInfoResponseDto> clients
) {

    public static ClientListResponseDto from(List<ClientInfoResponseDto> clientInfoResponseDtos)
    {
        return ClientListResponseDto.builder()
                .clients(clientInfoResponseDtos)
                .build();

    }
}

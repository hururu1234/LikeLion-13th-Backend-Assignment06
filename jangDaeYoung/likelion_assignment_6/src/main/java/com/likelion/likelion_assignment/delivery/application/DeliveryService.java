package com.likelion.likelion_assignment.delivery.application;

import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.client.domain.repository.ClientRepository;
import com.likelion.likelion_assignment.common.exception.BusinessException;
import com.likelion.likelion_assignment.delivery.api.request.DeliverySaveRequestDto;
import com.likelion.likelion_assignment.delivery.api.request.DeliveryUpdateRequestDto;
import com.likelion.likelion_assignment.delivery.api.response.DeliveryInfoResponseDto;
import com.likelion.likelion_assignment.delivery.api.response.DeliveryListResponseDto;
import com.likelion.likelion_assignment.delivery.domain.Delivery;
import com.likelion.likelion_assignment.delivery.domain.repository.DeliveryRepository;
import com.likelion.likelion_assignment.error.code.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.security.Principal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ClientRepository clientRepository;

    //주문 저장
    @Transactional
    public void deliverySave(DeliverySaveRequestDto deliverySaveRequestDto, Principal principal){

        Long clientId = Long.parseLong(principal.getName());

        Client client = clientRepository.findById(deliverySaveRequestDto.clientId()).orElseThrow(()-> new BusinessException(ErrorCode.CLIENT_NOT_FOUND_EXCEPTION, ErrorCode.CLIENT_NOT_FOUND_EXCEPTION.getMessage()));

        Delivery delivery = Delivery.builder()
                .client(client)
                .itemName(deliverySaveRequestDto.itemName())
                .deliveryStatus(deliverySaveRequestDto.deliveryStatus())
                .build();

        deliveryRepository.save(delivery);
    }

    //고객 Id로 주문조회
    public DeliveryListResponseDto deliveryFindByClientId(Principal principal){
        Long clientId = Long.parseLong(principal.getName());
        System.out.println( "aaa : " + clientId);
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.CLIENT_NOT_FOUND_EXCEPTION.getMessage() + clientId)
        );;
        List<Delivery> deliveries = deliveryRepository.findByClient(client);
        List<DeliveryInfoResponseDto> deliveryInfoResponseDtos = deliveries.stream()
                .map(DeliveryInfoResponseDto::from)
                .toList();

        return DeliveryListResponseDto.from(deliveryInfoResponseDtos);
    }



    //주문 수정
    @Transactional
    public void deliveryUpdate(Long deliveryId, DeliveryUpdateRequestDto deliveryUpdateRequestDto){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND_EXCEPTION, ErrorCode.DELIVERY_NOT_FOUND_EXCEPTION.getMessage()+deliveryId));
        delivery.update(deliveryUpdateRequestDto);
    }


    //주문 삭제
    @Transactional
    public void deliveryDelete(Long deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND_EXCEPTION, ErrorCode.DELIVERY_NOT_FOUND_EXCEPTION.getMessage()+deliveryId));
        deliveryRepository.delete(delivery);
    }




}

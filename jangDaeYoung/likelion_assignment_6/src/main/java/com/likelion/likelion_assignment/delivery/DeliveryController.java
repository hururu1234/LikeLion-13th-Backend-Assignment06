package com.likelion.likelion_assignment.delivery;

import com.likelion.likelion_assignment.common.template.ApiResTemplate;
import com.likelion.likelion_assignment.delivery.api.request.DeliverySaveRequestDto;
import com.likelion.likelion_assignment.delivery.api.request.DeliveryUpdateRequestDto;
import com.likelion.likelion_assignment.delivery.api.response.DeliveryListResponseDto;
import com.likelion.likelion_assignment.delivery.application.DeliveryService;
import com.likelion.likelion_assignment.delivery.domain.Delivery;
import com.likelion.likelion_assignment.delivery.domain.repository.DeliveryRepository;
import com.likelion.likelion_assignment.error.code.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;


    //주문 저장
    @PostMapping("/save")
    public ApiResTemplate<String> saveDelivery(@RequestBody @Valid DeliverySaveRequestDto deliverySaveRequestDto, Principal principal) {
        deliveryService.deliverySave(deliverySaveRequestDto, principal);
        return ApiResTemplate.successWithNoContent(SuccessCode.DELIVERY_SAVE_SUCCESS);
    }

    //고객 id로 주문 조회
    @GetMapping("/clients")
    public ApiResTemplate<DeliveryListResponseDto> deliveryFindByClientId(Principal principal){
        DeliveryListResponseDto deliveryListResponseDto = deliveryService.deliveryFindByClientId(principal);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, deliveryListResponseDto);
    }

    //id로 주문수정
    @PatchMapping("/{deliveryId}")
    public ApiResTemplate<String> deliveryUpdate(@PathVariable("deliveryId")Long deliveryId, @RequestBody DeliveryUpdateRequestDto deliveryUpdateRequestDto){
        deliveryService.deliveryUpdate(deliveryId, deliveryUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.DELIVERY_UPDATE_SUCCESS);
    }

    //주문삭제
    @DeleteMapping("/{deliveryId}")
    public ApiResTemplate<String> deliveryDelete(@PathVariable("deliveryId") Long deliveryId){
        deliveryService.deliveryDelete(deliveryId);
        return ApiResTemplate.successWithNoContent(SuccessCode.DELIVERY_DELETE_SUCCESS);
    }

}

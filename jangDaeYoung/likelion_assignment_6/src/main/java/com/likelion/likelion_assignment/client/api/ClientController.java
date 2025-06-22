package com.likelion.likelion_assignment.client.api;

import com.likelion.likelion_assignment.client.api.dto.request.ClientJoinRequestDto;
import com.likelion.likelion_assignment.client.api.dto.request.ClientLoginInfoRequestDto;
import com.likelion.likelion_assignment.client.api.dto.request.ClientUpdateRequestDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientInfoResponseDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientListResponseDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientLoginInfoResponseDto;
import com.likelion.likelion_assignment.client.application.ClientService;
import com.likelion.likelion_assignment.common.template.ApiResTemplate;
import com.likelion.likelion_assignment.error.code.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;


    //고객 저장
    @PostMapping("/save")
    public ApiResTemplate<String> clientSave(@RequestBody @Valid ClientJoinRequestDto clientJoinRequestDto){
        clientService.clientSave(clientJoinRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.CLIENT_SAVE_SUCCESS);
    }

    //로그인
    @PostMapping("/login")
    public ApiResTemplate<ClientLoginInfoResponseDto> login(@RequestBody @Valid ClientLoginInfoRequestDto clientLoginInfoRequestDto){
        ClientLoginInfoResponseDto clientLoginInfoResponseDto = clientService.login(clientLoginInfoRequestDto);
        return ApiResTemplate.successResponse(SuccessCode.CLIENT_LOGIN_SUCCESS, clientLoginInfoResponseDto);

    }




    //고객 전체 조회
    @GetMapping("/all")
    public ApiResTemplate<ClientListResponseDto> clientFindAll(){
         ClientListResponseDto clientListResponseDto = clientService.clientFindAll();
         return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, clientListResponseDto);
    }

    //고객 id로 고객 조회
    @GetMapping("/info")
    public ApiResTemplate<ClientInfoResponseDto> clientFindByClientId(Principal principal){
        ClientInfoResponseDto clientInfoResponseDto = clientService.clientFindById(principal);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, clientInfoResponseDto);
    }

    //고객 id로 고객 수정
    @PatchMapping("/update")
    public ApiResTemplate<String> clientUpdate(Principal principal, @RequestBody ClientUpdateRequestDto clientUpdateRequestDto){
        clientService.clientUpdate(principal, clientUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.CLIENT_UPDATE_SUCCESS);
    }

    //고객 삭제
    @DeleteMapping("/delete")
    public ApiResTemplate<String> clientDelete(Principal principal){
        clientService.clientDelete(principal);
        return ApiResTemplate.successWithNoContent(SuccessCode.CLIENT_DELETE_SUCCESS);
    }

}

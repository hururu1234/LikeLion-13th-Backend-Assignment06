package com.likelion.likelion_assignment.client.application;

import com.likelion.likelion_assignment.client.api.dto.request.ClientJoinRequestDto;
import com.likelion.likelion_assignment.client.api.dto.request.ClientLoginInfoRequestDto;
import com.likelion.likelion_assignment.client.api.dto.request.ClientUpdateRequestDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientInfoResponseDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientListResponseDto;
import com.likelion.likelion_assignment.client.api.dto.response.ClientLoginInfoResponseDto;
import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.client.domain.repository.ClientRepository;
import com.likelion.likelion_assignment.common.exception.BusinessException;
import com.likelion.likelion_assignment.error.code.ErrorCode;
import com.likelion.likelion_assignment.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //고객 회원가입 (email, password, nickname)
    @Transactional
    public void clientSave(ClientJoinRequestDto clientJoinRequestDto){

        if(clientRepository.existsByEmail(clientJoinRequestDto.email())){
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_EMAIL
                    , ErrorCode.ALREADY_EXISTS_EMAIL.getMessage());
        }


        Client client = Client.builder()
                .name(clientJoinRequestDto.name())
                .age(clientJoinRequestDto.age())
                .payment(clientJoinRequestDto.payment())
                .role(clientJoinRequestDto.role())
                .email(clientJoinRequestDto.email())
                .password(passwordEncoder.encode(clientJoinRequestDto.password()))
                .build();

        clientRepository.save(client);
    }

    //로그인
    public ClientLoginInfoResponseDto login(ClientLoginInfoRequestDto clientLoginInfoRequestDto){
        Client client = clientRepository.findByEmail(clientLoginInfoRequestDto.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND_EXCEPTION,
                ErrorCode.CLIENT_NOT_FOUND_EXCEPTION.getMessage()));

        if (!passwordEncoder.matches(clientLoginInfoRequestDto.password(), client.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        // 토큰 생성
        String token = jwtTokenProvider.generateToken(client);

        // 토큰과 함께 멤버 정보 리턴
        return ClientLoginInfoResponseDto.from(client, token);
    }




    //전체 고객 조회
    public ClientListResponseDto clientFindAll(){

        List<Client> clients = clientRepository.findAll();
        List<ClientInfoResponseDto> clientInfoResponseDtos = clients.stream()
                .map(ClientInfoResponseDto::from)
                .toList();

        return ClientListResponseDto.from(clientInfoResponseDtos);
    }

    //단일 고객 조회
     public ClientInfoResponseDto clientFindById(Principal principal){
        Long clientId = Long.parseLong(principal.getName());
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.CLIENT_NOT_FOUND_EXCEPTION.getMessage() + clientId));
        return ClientInfoResponseDto.from(client);
     }

    //고객 수정
    @Transactional
    public void clientUpdate(Principal principal, ClientUpdateRequestDto clientUpdateRequestDto){
        Long clientId = Long.parseLong(principal.getName());
        Client client = clientRepository.findById(clientId) .orElseThrow(
                () -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.CLIENT_NOT_FOUND_EXCEPTION.getMessage()+ clientId));
        client.update(clientUpdateRequestDto);
    }

    //고객 삭제
    @Transactional
    public void clientDelete(Principal principal){
        Long clientId = Long.parseLong(principal.getName());
        Client client = clientRepository.findById(clientId).orElseThrow(IllegalArgumentException::new);
        clientRepository.delete(client);
    }

}

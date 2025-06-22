package com.likelion.likelion_assignment.global.jwt;

import com.likelion.likelion_assignment.client.domain.Client;
import com.likelion.likelion_assignment.client.domain.repository.ClientRepository;

import com.likelion.likelion_assignment.common.exception.BusinessException;
import com.likelion.likelion_assignment.error.code.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private final ClientRepository clientRepository;

    @Value("${token.expire.time}") // factory annotation 임포트
    private String tokenExpireTime; // 토큰 만료 시간

    @Value("${jwt.secret}")
    private String secret;  // 비밀키

    private SecretKey key;  // 객체 key

    public JwtTokenProvider(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostConstruct  // Bean이 초기화 된 후에 실행
    public void init() {    // 필터 객체를 초기화하고 서비스에 추가하기 위한 메소드 init
        byte[] keyBytes = Decoders.BASE64.decode(secret);   // 시크릿 키 디코딩 후
        this.key = Keys.hmacShaKeyFor(keyBytes); // 키 암호화
    }


    // 토큰 생성
    public String generateToken(Client client) {
        // 만료 시간 설정 util로 임포트
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + Long.parseLong(tokenExpireTime));

        return Jwts.builder()
                .subject(client.getClientId().toString())// 토큰 주체를 id로 설정
                .claim(AUTHORITIES_KEY, client.getRole().toString())
                .issuedAt(now)  // 발행 시간
                .expiration(expireDate) // 만료 시간
                .signWith(key, Jwts.SIG.HS256)  // ㅏ토큰 암호화
                .compact(); // 압축, 서명 후 토큰 생성
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);  // 토큰 파싱, 검증
            return true;    // 검증 완료 -> 유효한 토큰
            // 검증 실패 시 반환하는 예외에 따라 다르게 실행
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "JWT 가 유효하지 않습니다.");
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "JWT 가 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "JWT 가 null 이거나 비어있거나 공백만 있습니다.");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "JWT 검증에 실패했습니다.");
        }
    }


    // 인증 객체 반환 core 로 임포트
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        String authority = claims.get(AUTHORITIES_KEY, String.class);
        if(authority == null) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "권한 정보가 없는 토큰입니다.");
        }

        List<GrantedAuthority> authorities = Arrays.stream(authority.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // memberId, 공백, authorities 반환
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    // Claims 파싱
    private Claims parseClaims(String accessToken) {
        try{
            JwtParser parser = Jwts.parser()
                    .verifyWith(key)
                    .build();

            return parser.parseSignedClaims(accessToken).getPayload();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }


    // 인증 객체 반환 core 로 임포트
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        // claim 정보를 통해 clientId 추출
//        Long clientId = Long.parseLong(claims.getSubject());
//
//        // clientId로 client 엔티티 조회
//        Client client = clientRepository.findById(clientId).orElseThrow();
//
//        // 권한이나 역할의 이름을 반환하는 메소드로 client.getRole()을 문자열로 만들어 배열로 리턴
//        List<GrantedAuthority> authorities = List.of(
//                new SimpleGrantedAuthority(client.getRole().toString())
//        );
//
//        // clientId, 공백, authorities 반환
//        return new UsernamePasswordAuthenticationToken(client.getClientId(), "", authorities);
//    }


}

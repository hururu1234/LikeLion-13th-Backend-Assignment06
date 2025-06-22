package com.likelion.likelion_assignment.client.domain;

import com.likelion.likelion_assignment.client.api.dto.request.ClientUpdateRequestDto;
import com.likelion.likelion_assignment.delivery.domain.Delivery;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_name", nullable = false)
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "client_email", nullable = false)
    String email;

    String password;

    @Column(name = "client_picture_url")
    private String pictureUrl;


    @Builder
    private Client(String name, int age, Payment payment, Role role, String email, String password, String pictureUrl) {
        this.name = name;
        this.age = age;
        this.payment = payment;
        this.role =role;
        this.email = email;
        this.password = password;
        this.pictureUrl = pictureUrl;
    }

    public void update(ClientUpdateRequestDto clientUpdateRequestDto){
        this.name = clientUpdateRequestDto.name();
        this.age = clientUpdateRequestDto.age();
    }
}

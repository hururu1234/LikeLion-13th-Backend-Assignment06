package com.likelion.likelion_assignment.client.api.dto.request;

import com.likelion.likelion_assignment.client.domain.Payment;
import com.likelion.likelion_assignment.client.domain.Role;
import jakarta.validation.constraints.*;


public record ClientJoinRequestDto(
        @NotNull(message = "이름을 필수로 입력해야 합니다.")
        String name,

        @Positive(message = "양수 입력해야 합니다.")
        int age,

        @NotBlank(message = "이메일을 필수로 입력해야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호를 필수로 입력해야 합니다.")
        @Size(min = 8, message = "8자 이상 입력하세요.")
        String password,

        Role role,


        Payment payment
) {


}

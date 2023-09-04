package com.rocket.front.member.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class MemberSignUpRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private LocalDateTime lastAccessAt;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    private String info;

    private String isOnline;

    private String githubLink;

    @NotBlank
    private Long roleSeq;

    private Long levelSeq;

    public void setPassword(String password) {
        this.password = password;
    }
}

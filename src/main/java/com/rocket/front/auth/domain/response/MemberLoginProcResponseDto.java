package com.rocket.front.auth.domain.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberLoginProcResponseDto {
    private Long memberSeq;
    private String email;
    private String password;
    private String role;
}

package com.rocket.front.auth.domain.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberLoginInfoResponseDto {
    private Long memberSeq;
    private String email;
    private String password;
    private String nickname;
    private Role roleResponseDto;
}



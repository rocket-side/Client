package com.rocket.front.member.domain.response;

import com.rocket.front.member.dto.RoleDto;
import lombok.Getter;

@Getter
public class MemberLoginInfoResponseDto {

    private Long memberSeq;

    private String password;

    private String email;

    private String nickname;

    private RoleDto roleResponseDto;

}

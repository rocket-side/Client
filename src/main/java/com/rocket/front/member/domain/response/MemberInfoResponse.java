package com.rocket.front.member.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocket.front.member.dto.LevelDto;
import com.rocket.front.member.dto.RoleDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInfoResponse {

    private Long memberSeq;

    private String email;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessAt;

    private String nickname;

    private String phoneNumber;

    private String info;

    private LevelDto level;

    private String isOnline;

    private RoleDto role;

    private String githubLink;

}

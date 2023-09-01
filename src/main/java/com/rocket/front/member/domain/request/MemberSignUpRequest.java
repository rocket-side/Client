package com.rocket.front.member.domain.request;

import java.time.LocalDateTime;

public class MemberSignUpRequest {

    private String email;

    private String password;

    private LocalDateTime lastAccessAt;

    private String nickname;

    private String phoneNumber;

    private String info;

    private String isOnline;

    private String githubLink;

    private Long roleSeq;

    private Long levelSeq;
}

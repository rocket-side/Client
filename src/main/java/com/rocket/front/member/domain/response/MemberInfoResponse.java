package com.rocket.front.member.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberInfoResponse {

    private Long memberSeq;

    private String email;

    private String password;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessAt;

    private String nickname;

    private String phoneNumber;

    private String info;

    private Level level;

    private String isOnline;

    private Role role;

    private String githubLink;

    @Getter
    @AllArgsConstructor
    public enum Level {

        LEVEL1(1, "꿈나무"),
        LEVEL2(2, "훈련생"),
        LEVEL3(3, "주니어비행사"),
        LEVEL4(4, "시니어비행사"),
        LEVEL5(5, "우주선장");

        private final int seq;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Role {

        USER("ROLE_USER", "일반 사용자 권한"),
        ADMIN("ROLE_ADMIN", "관리자 권한"),
        GUEST("GUEST", "게스트 권한");

        private final String code;
        private final String name;
    }
}

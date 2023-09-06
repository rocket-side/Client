package com.rocket.front.project.position.domain.response;

import lombok.Getter;

@Getter
public class RecruitCrewResponse {
    private Long memberSeq;
    private Long recruitSeq;
    private Long positionSeq;
    private String positionName;
    private String role;
}

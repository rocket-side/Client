package com.rocket.front.project.position.domain.response;

import lombok.Getter;

@Getter
public class ApplicantsResponse {
    private Long memberSeq;
    private Long recruitSeq;
    private Long positionSeq;
    private String positionName;
    private String isAccept;
}

package com.rocket.front.project.domain.position.response;

import lombok.Getter;

@Getter
public class ApplyStatusResponse {
    private Long positionSeq;
    private Long recruitSeq;
    private String name;
    private Integer applyCnt;
    private Integer wantCnt;
}

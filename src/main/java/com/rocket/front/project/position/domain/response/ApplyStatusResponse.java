package com.rocket.front.project.position.domain.response;

import lombok.Getter;

@Getter
public class ApplyStatusResponse {
    private Long positionSeq;
    private Long recruitSeq;
    private String name;
    private Integer applyCnt;
    private Integer wantCnt;
}

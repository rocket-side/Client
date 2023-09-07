package com.rocket.front.project.recruit.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RecruitCardResponse {

    private Long recruitSeq;

    private String name;

    private TypeResponse type;

    private FieldResponse field;

    private List<PositionForCardsResponse> positionForCards;

    private Boolean isKeep;

}

package com.rocket.front.project.recruit.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class InfoCardsResponse {

    private Long recruitSeq;

    private String name;

    private TypeResponse type;

    private FieldResponse field;

    private List<KeepResponse> isKeep;
}

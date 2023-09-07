package com.rocket.front.project.recruit.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RecruitTagResponse {
    private List<TypeResponse> types;

    private List<FieldResponse> fields;

    private List<PositionResponse> positions;
}

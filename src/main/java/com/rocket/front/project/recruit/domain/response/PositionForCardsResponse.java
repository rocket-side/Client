package com.rocket.front.project.recruit.domain.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@Getter
@JsonRootName("position")
public class PositionForCardsResponse {
    private Long positionSeq;
    private String name;

    private Long recruitSeq;
}

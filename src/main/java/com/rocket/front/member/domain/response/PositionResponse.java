package com.rocket.front.member.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PositionResponse {

    private Long seq;

    private List<String> name;
}

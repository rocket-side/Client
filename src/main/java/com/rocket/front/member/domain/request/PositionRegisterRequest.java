package com.rocket.front.member.domain.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class PositionRegisterRequest {

    @NotBlank
    private List<Long> positionSeq;
}

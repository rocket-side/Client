package com.rocket.front.member.domain.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class PreferenceRegisterRequest {

    @NotBlank
    private List<Long> preferenceSeq;
}

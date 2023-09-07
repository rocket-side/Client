package com.rocket.front.project.recruit.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponse {
    private Long recruitSeq;
    private Long skillSeq;
    private String name;
}

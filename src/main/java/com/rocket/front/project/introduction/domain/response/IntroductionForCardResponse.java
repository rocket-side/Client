package com.rocket.front.project.introduction.domain.response;

import com.rocket.front.project.recruit.domain.response.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntroductionForCardResponse {
    private Long recruitSeq;
    private String name;
    private FieldResponse field;
    private String info;
    private String status;


}

package com.rocket.front.project.domain.introduction.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocket.front.project.domain.position.response.RecruitCrewResponse;
import com.rocket.front.project.domain.recruit.response.RecruitResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class IntroductionResponse {
    private RecruitResponse recruit;
    private Integer likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String content;

    private List<RecruitCrewResponse> recruitCrews;
}

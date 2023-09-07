package com.rocket.front.project.introduction.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocket.front.project.position.domain.response.RecruitCrewResponse;
import com.rocket.front.project.recruit.domain.response.RecruitResponse;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
public class IntroductionResponse {
    private RecruitResponse recruit;
    private Integer likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
//    private LocalDateTime createdAt;
    private String content;

    private List<RecruitCrewResponse> recruitCrews;
}

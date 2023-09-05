package com.rocket.front.project.domain.recruit.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RecruitResponse {
    private Long recruitSeq;

    private String name;

    private String status;

    private Long leader;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private String content;

    private String info;

    private FieldResponse projectField;

    private TypeResponse projectType;

    private Integer keepCount;

    private List<SkillResponse> skills;
}

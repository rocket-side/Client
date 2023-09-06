package com.rocket.front.project.recruit.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate createdAt;
//    private LocalDateTime createdAt;


    private String content;

    private String info;

    private FieldResponse projectField;

    private TypeResponse projectType;

    private Integer keepCount;

    private List<SkillResponse> skills;
}

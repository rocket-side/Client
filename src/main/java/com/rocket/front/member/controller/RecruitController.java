package com.rocket.front.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recruits")
public class RecruitController {

    /**
     * 공고글 조회
     * @return 공고글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getRecruit(@PathVariable("recruit-seq") String parameter) {
        return "recruit/recruit-read";
    }

    /**
     * 공고글 팀원 조회
     * @return 공고글 팀원 템플릿
     */
    @GetMapping("/{recruit-seq}/crews")
    public String getRecruitMember(@PathVariable("recruit-seq") String parameter) {
        return "recruit/project-member";
    }

    /**
     * 공고글 지원자 조회
     * @return 공고글 지원자 템플릿
     */
    @GetMapping("/{recruit-seq}/applicants")
    public String getRecruitApplicant(@PathVariable("recruit-seq") String parameter) {
        return "recruit/recruit-applicant";
    }

//    /**
//     * TODO 공고글 작성 없음
//     * @return 공고글 작성 템플릿
//     */
//    @GetMapping
//    public String getRecuritRegisterForm() {
//        return "recruit-"
//    }

    /**
     * 모든 공고글 조회
     * @return 공고글 목록 템플릿
     */
    @GetMapping
    public String getRecruitList() {
        return "recruit/recruit-list";
    }
}

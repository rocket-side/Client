package com.rocket.front.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rocket/introduction")
public class IntroductionController {

    /**
     * 해당 소개글 조회
     * @return 소개글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getIntroduction() {
        return "official-notice-page";
    }

    /**
     * 소개글 작성
     * @return 소개글 작성 템플릿
     */
    @GetMapping("/register")
    public String getIntroductionRegisterForm() {
        return "official-notice-write";
    }

    /**
     * 모든 소개글 조회
     * @return 소개글 목록 템플릿
     */
    @GetMapping
    public String getIntroductionList() {
        return "project-intro-list";
    }
}

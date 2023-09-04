package com.rocket.front.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * 프로필 조회
     * @return 프로필 조회 템플릿
     */
    @GetMapping
    public String getProfile() {
        return "profile";
    }
}

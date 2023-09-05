package com.rocket.front.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    /**
     * 시작페이지
     * @return 시작페이지 템플릿
     */
    @GetMapping({"","/"})
    public String getHome() {
        return "home";
    }

}

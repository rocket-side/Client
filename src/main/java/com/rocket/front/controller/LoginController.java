package com.rocket.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}

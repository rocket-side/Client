package com.rocket.front.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping({"","/"})
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/1")
    public String a(){
        return "community-read";
    }

    @GetMapping("/2")
    public String b(){
        return "community-write";
    }

    @GetMapping("/3")
    public String c(){
        return "commutiy-list";
    }

    @GetMapping("/4")
    public String d(){
        return "introduction-read";
    }

    @GetMapping("/5")
    public String e(){
        return "introduction-write";
    }

    @GetMapping("/6")
    public String f(){
        return "profile";
    }

    @GetMapping("/7")
    public String g(){
        return "introduction-list";
    }
    @GetMapping("/8")
    public String h(){
        return "recruit-member";
    }
    @GetMapping("/recruits")
    public String i(){
        return "recruit/recruit-list";
    }
    @GetMapping("/10")
    public String j(){
        return "recruit-applicant";
    }
    @GetMapping("/11")
    public String k(){
        return "recruit-read";
    }
    @GetMapping("/12")
    public String l(){
        return "signup-first";
    }
    @GetMapping("/13")
    public String m(){
        return "signup-second";
    }
    @GetMapping("/14")
    public String n(){
        return "search-result";
    }




}

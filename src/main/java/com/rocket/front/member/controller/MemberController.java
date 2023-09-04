package com.rocket.front.member.controller;

import com.rocket.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/api")
public class MemberController {

    private final MemberService memberService;


}

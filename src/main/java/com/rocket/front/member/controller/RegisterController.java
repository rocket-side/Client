package com.rocket.front.member.controller;

import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String getRegisterFirstForm() {
        return "signup-first";
    }

    @GetMapping("/signup2")
    public String getRegisterSecondForm() {
        return "signup-second";
    }

    @PostMapping
    public String signup(@Valid MemberSignUpRequest request, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("member", request);

            Map<String, String> validatorError = memberService.validateHandling(bindingResult);
            for(String key : validatorError.keySet()) {
                model.addAttribute(key, validatorError.get(key));
            }

            return "signup-first";
        }

        String encodedPassword = memberService.passwordEncoding(request.getPassword());
        request.setPassword(encodedPassword);

        memberService.signUp(request);

        return "login";
    }


}

package com.rocket.front.member.controller;

import com.rocket.front.member.domain.request.MemberSignUpRequest;
import com.rocket.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    /**
     * 회원 가입 - 필수 정보 입력
     * @return 회원 가입 첫번째 템플릿
     */
    @GetMapping("/signup")
    public String getRegisterFirstForm() {
        return "signup-first";
    }

    /**
     * 회원 가입 - 필수가 아닌 정보 입력
     * @return 회원 가입 두번째 템플릿
     */
    @GetMapping("/signup2")
    public String getRegisterSecondForm() {
        return "signup-second";
    }

    /**
     * 회원 가입 (멤버 추가)
     * @param request 회원 가입 요청 객체
     * @param bindingResult 바인딩 결과
     * @param model 모델 객체
     * @return 성공 시 로그인 템플릿, 실패 시 회원 가입 템플릿
     */
    @PostMapping("/signup")
    public String signup(@Valid MemberSignUpRequest request, Model model, BindingResult bindingResult) {
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

        return "signup-second";
    }

}

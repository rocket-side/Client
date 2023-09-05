package com.rocket.front.member.controller;

import com.rocket.front.member.domain.response.MemberInfoResponse;
import com.rocket.front.member.domain.response.PositionResponse;
import com.rocket.front.member.domain.response.PreferenceResponse;
import com.rocket.front.member.exception.MemberNotFoundException;
import com.rocket.front.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final MemberService memberService;

    /**
     * 프로필 조회
     * @param seq 멤버 시퀀스
     * @param model 모델 객체
     * @return 성공 시 프로필 템플릿, 실패 시 공고 목록 템플릸
     * @throws MemberNotFoundException 멤버를 찾을 수 없는 경우 발생
     */
    @GetMapping("/member/{seq}")
    public String getProfile(@PathVariable Long seq, Model model) {
        try {
            MemberInfoResponse memberInfo = memberService.getMemberInfo(seq);
            model.addAttribute("memberInfo", memberInfo);

            PositionResponse memberPosition = memberService.getMemberPosition(seq);
            model.addAttribute("memberPosition", memberPosition);

            PreferenceResponse memberPreference = memberService.getMemberPreference(seq);
            model.addAttribute("memberPreference", memberPreference);

            return "profile";
        } catch (MemberNotFoundException e) {
            model.addAttribute("errorMessage", "멤버를 찾을 수 없습니다.");
            return "recruit/recruit-list";
        }
    }


}

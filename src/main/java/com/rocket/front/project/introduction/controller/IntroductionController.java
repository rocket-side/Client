package com.rocket.front.project.introduction.controller;

import com.rocket.front.project.introduction.adapter.IntroductionAdapter;
import com.rocket.front.project.introduction.domain.response.CommentResponse;
import com.rocket.front.project.introduction.domain.response.IntroductionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/introductions")
public class IntroductionController {
    private final IntroductionAdapter introductionAdapter;

    /**
     * 해당 소개글 조회
     * @return 소개글 조회 템플릿
     */
    @GetMapping("/{recruit-seq}")
    public String getIntroduction(@PathVariable("recruit-seq") String recruitSeq, Model model) {
        try {
            IntroductionResponse introduction = introductionAdapter.getIntroduction(recruitSeq);
            List<CommentResponse> comments = introductionAdapter.getIntroductionComments(recruitSeq);
            model.addAttribute("introduction",introduction);
            model.addAttribute("comments",comments);
        }catch (Exception e) {
//            return "error";
            return "introduction/introduction-read";
        }

        return "introduction/introduction-read";
    }

    /**
     * 소개글 작성
     * @return 소개글 작성 템플릿
     */
    @GetMapping("/register")
    public String getIntroductionRegisterForm() {
        return "introduction/introduction-write";
    }

    /**
     * 모든 소개글 조회
     * @return 소개글 목록 템플릿
     */
    @GetMapping
    public String getIntroductionList(Model model) {
        return "introduction/introduction-list";
    }
}
